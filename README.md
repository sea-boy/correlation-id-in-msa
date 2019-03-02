# correlation-id-in-msa

## 소개

마이크로서비스 아키텍처(이하 MSA) 환경에서는 모놀리식 아키텍처와 달리, 작은 서비스 단위로 나뉜 각 컴포넌트들이 서로 협업하는 서비스를 의미한다. 서비스를 나누는 기준은, 각 서비스 및 개발 조직 등 여러 가지 고려 요소가 존재한다. 얼마나 작게 서비스가 나누어져 있는지와 무관하게, MSA는 외부에서 전달 받은 요청(request)에 대해, 다양한 컴포넌트들 간의 상호 작용에 의한 처리 결과를 외부로 응답(response)한다. 이때, 예외 상황이 발생하면, 로그를 분석하여, 어디에서 예기치 못한 동작이 발생했는지 디버깅을 해야 한다. MSA라는 환경에서, 이러한 디버깅을 할 때 접하는 1차적인 어려움은, End Point에서 서비스의 흐름을 따라가면서 발생하게 된다. 서비스 간 API 호출 흐름 중, 어느 컴포넌트에서 문제가 발생했는지, 각 컴포넌트간 API 호출을 정상적으로 이뤄졌는지, 각 Bounded Context 간 오동작을 하는 부분은 어디인지 등. 특히, 실시간으로 많은 트레픽이 발생하는 환경에서는, 단순히 End Point에서의 입력 시간을 공유하는 것만으로는, 문제의 원인을 파악하기에는 효율적이지 못하다. 여기서 유용한 방법 중 하나는, Correlation Id 를 이용하는 것이다.


## Correlation Id

Correlation Id 란 API 호출에 대한 식별자를 정의하고, 컴포넌트 간, 그 식별자를 공유하는 것이다. MSA를 이루는 작은 서비스 컴포넌트들은 각 비즈니스 모델에 맞는 Bounded Context 라는 도메인 모델의 경계를 이루며 동작하고 있다. 컴포넌트 간 API 호출 방식은 서비스 별로 다양하며, 동시에 동일한 API를 여러번 호출할 수도 있다. API 호출 시, 공유할 식별자를 로그에 기록함으로써, 컴포넌트간 요청과 응답을 API 별로 구분이 가능하며, 시스템 전반에 걸쳐 일관되게 추적할 수 있다.


## Correlation Id 생성

Correlation Id를 생성하는 로직은 common-module로 구성하였다. 해당 로직은, 모든 컴포넌트에 동일하게 적용되므로, 공통 모듈로 subproject를 생성하여 구현해도 되고, library 형태로 배포하여, 적용해도 된다.

컴포넌트 간의 통신은 Json 기반의 Http 통신을 한다고 가정하고, request를 받았을 때, Filter 에서 생성하는 것이 적합하다.

```java
@Slf4j
public class CorrelationIdFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        CorrelationHttpHeaderHelper.prepareCorrelationParams(request);
        CorrelationLoggerUtil.updateCorrelation();
        filterChain.doFilter(request, response);
        CorrelationLoggerUtil.clear();
    }
 }
```

Filter에서는, 요청받은 request 를 확인하여, Correlation-Id가 존재할 경우, 해당 데이터를 식별자로 사용하고, 존재하지 않을 경우에는, 신규 Correlation Id를 생성한다. 관련 로직은 다음과 같다.

```java
@Slf4j
public class CorrelationHttpHeaderHelper {

    public static void prepareCorrelationParams(HttpServletRequest httpServletRequest) {
        String currentCorrelationId = prepareCorrelationId(httpServletRequest);
        setCorrelations(httpServletRequest, currentCorrelationId);
        log.debug("Request Correlation Parameters : ");
        CorrelationHeaderField[] headerFields = CorrelationHeaderField.values();
        for (CorrelationHeaderField field : headerFields) {
            String value = CorrelationHeaderUtil.get(field);
            log.debug("{} : {}", field.getValue(), value);
        }
    }

    private static String prepareCorrelationId(HttpServletRequest httpServletRequest) {
        String currentCorrelationId = httpServletRequest.getHeader(CorrelationHeaderField.CORRELATION_ID.getValue());
        if (currentCorrelationId == null) {
            currentCorrelationId = CorrelationContext.generateId();
            log.trace("Generated Correlation Id: {}", currentCorrelationId);
        } else {
            log.trace("Incoming Correlation Id: {}", currentCorrelationId);
        }
        return currentCorrelationId;
    }
}
```

Correlation-Id를 생성하는 로직은 다음과 같다. 

```java
public class CorrelationContext {

    private static final String ID_HEADER_FORMAT = "yyMMddHHmmss";
    private static ThreadLocal<Map<ContextField, String>> contextMap = ThreadLocal.withInitial(() -> new HashMap<>());

    public static String generateId() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ID_HEADER_FORMAT);
        String date = simpleDateFormat.format(new Date());
        return date + "-" + getRandomHexString(7);
    }

    private static String getRandomHexString(int num) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        while(stringBuilder.length() < num) {
            stringBuilder.append(Integer.toHexString(random.nextInt()));
        }

        return stringBuilder.toString().substring(0, num);
    }
```
Correlation-Id의 포맷은, "날짜(yyMMddHHmmss)-Random Number(7자리)"로 하였다.

본 프로젝트는 logback을 사용하였다. logback에서 Correlation-Id를 기록하기 위해서는, logback의 xml 파일에 Correlation-Id를 기록하기 위한 패턴을 지정해야 한다. 여기서는, log-correlation-id에 Correlation-Id가 맵핑되어 기록된다.

```xml
    <encoder>
        <pattern>
            %d{yyyy-MM-dd HH:mm:ss.SSS}.%thread> %-5level - TID[%X{log-correlation-id}] UID[%X{log-user-id}] - %msg %n
        </pattern>
    </encoder>
```


reference

* [rapid7 blog](https://blog.rapid7.com/2016/12/23/the-value-of-correlation-ids/)
 
* [Building Microservices](http://shop.oreilly.com/product/0636920033158.do)
 
* [Enterprise Integration Pattern](https://www.enterpriseintegrationpatterns.com/patterns/messaging/CorrelationIdentifier.html)

* [Correlation IDs for microservices architectures](https://hilton.org.uk/blog/microservices-correlation-id)
