package com.insurance.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xushuaijun on 2017-08-18 .
 */
@Controller
@RequestMapping(value = "/backgroundController")
public class BackgroundController {
    @RequestMapping(value = "/toIndex")
    public  String toIndex(){
        return "background/index";
    }
}
