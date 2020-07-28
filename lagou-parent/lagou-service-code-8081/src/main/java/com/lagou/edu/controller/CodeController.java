package com.lagou.edu.controller;

import com.lagou.edu.service.impl.CodeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/code")
public class CodeController {

    @Autowired
    private CodeServiceImpl codeServiceImpl;

    @GetMapping("/create/{email}")
    public String createCode(@PathVariable("email") String email) {
        return codeServiceImpl.createCode(email);
    }
}
