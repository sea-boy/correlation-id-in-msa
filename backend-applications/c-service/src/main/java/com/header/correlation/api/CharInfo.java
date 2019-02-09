package com.header.correlation.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CharInfo {
    @JsonProperty(value = "charInfo")
    private String ci;
    public static CharInfo of(String ci) {
        CharInfo charInfo = new CharInfo();
        charInfo.ci = ci;
        return charInfo;
    }
}
