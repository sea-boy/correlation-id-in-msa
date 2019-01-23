package com.header.correlation.api;

import com.header.correlation.biz.RandomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Request {

    private RandomService randomService;

    @Autowired
    public Request(RandomService randomService) {
        this.randomService = randomService;
    }

    @GetMapping(value = "/random/number")
    public String getRandomNumber() {
        return randomService.getRandom();
    }
}
