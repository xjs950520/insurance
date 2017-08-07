package com.insurance.web;

import com.insurance.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xujunshuai on 2017/8/7.
 */
@Controller
@RequestMapping(value = "/loginController")
public class loginController {

    @Autowired
    private RegisterService registerService;

    @GetMapping(value = "/checkLogin")
    public String checkLogin(HttpServletRequest request, HttpServletResponse response){
        return "join";
    }

    @GetMapping(value = "checkLogin2")
    public String checkLogin2(){
        return "result";
    }
}
