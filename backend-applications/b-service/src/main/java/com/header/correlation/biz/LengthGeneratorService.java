package com.header.correlation.biz;


import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class LengthGeneratorService {

    public int getLength() {
        return LocalTime.now().getSecond() % 10;
    }
}
