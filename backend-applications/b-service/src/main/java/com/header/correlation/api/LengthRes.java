package com.header.correlation.api;

import lombok.Getter;

@Getter
public class LengthRes {
    private int length;

    private LengthRes(int length) {
        this.length = length;
    }

    public static LengthRes of(int length) {
        return new LengthRes(length);
    }
}
