package com.thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName TestController
 * @Description: Thymeleaf模板引擎
 * @Author ning.chai@foxmail.com
 * @Date 2020/9/15 0015
 * @Version V1.0
 **/
@Controller
@RequestMapping(value = "/test")
public class TestController {

    @GetMapping(value = "/hello")
    public String hello(Model model){
        model.addAttribute("hello","hello welcome");
        return "demo1";
    }

}
