package com.header.correlation.api;

import com.header.correlation.biz.LengthGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LengthApi {

    private LengthGeneratorService lengthGeneratorService;

    @Autowired
    public LengthApi(LengthGeneratorService lengthGeneratorService) {
        this.lengthGeneratorService = lengthGeneratorService;
    }

    @GetMapping("/api/length")
    public LengthRes getLength() {
        int len = lengthGeneratorService.getLength();
        return LengthRes.of(len);
    }

}
