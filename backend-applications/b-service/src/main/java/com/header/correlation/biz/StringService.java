package com.header.correlation.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StringService {

    private OtherStringGenerator otherStringGen;

    @Autowired
    public StringService(OtherStringGenerator otherStringGen) {
        this.otherStringGen = otherStringGen;
    }

    public String getString() {
        String strC = otherStringGen.getCharC();
        String strA = otherStringGen.getCharA();
        return new StringBuilder("b")
                .append(strC)
                .append(strA)
                .toString();
    }
}
