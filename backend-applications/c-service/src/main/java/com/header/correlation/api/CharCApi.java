package com.header.correlation.api;

import com.header.correlation.biz.CharService;
import com.header.correlation.biz.StringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CharCApi {

    private CharService charService;
    private StringService stringService;

    @Autowired
    public CharCApi(CharService charService,
                    StringService stringService) {
        this.charService = charService;
        this.stringService = stringService;
    }

    @GetMapping("/api/char")
    public CharInfo getChar() {
        String ci = charService.getChar();
        return CharInfo.of(ci);
    }

    @GetMapping("/api/string")
    public StringInfo getString() {
        String si = stringService.getString();
        return StringInfo.of(si);
    }
}
