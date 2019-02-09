package com.header.correlation.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class StringInfo {
    @JsonProperty(value = "stringInfo")
    private String si;
    public static StringInfo of(String si) {
        StringInfo stringInfo = new StringInfo();
        stringInfo.si = si;
        return stringInfo;
    }

}
