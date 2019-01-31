package com.header.correlation.api;

import com.header.correlation.biz.CharService;
import com.header.correlation.biz.StringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CharAApi {

    private CharService charService;
    private StringService stringService;

    @Autowired
    public CharAApi(CharService charService,
                    StringService stringService) {
        this.charService = charService;
        this.stringService = stringService;
    }

    @GetMapping("/api/char")
    public String getChar() {
        return charService.getChar();
    }

    @GetMapping("/api/string")
    public String getString() {
        return stringService.getString();
    }

}
