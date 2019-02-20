package com.header.correlation.infra.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CharRes {
    private String charInfo;

    public CharRes() {
        super();
    }

    public CharRes(String charInfo) {
        this.charInfo = charInfo;
    }
}
