package com.fatesgo.admin.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WxXcxLoginController {
    @GetMapping("/getOpenid")
    public void getOpenid(String code) {

    }
}
