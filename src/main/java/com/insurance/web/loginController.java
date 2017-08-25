package com.insurance.web;


import com.insurance.bean.Register;
import com.insurance.service.RegisterService;
import com.insurance.util.MD5;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/login")
    public String login(){
        return "front/login";
    }
    @GetMapping(value = "/toResetPwd")
    public String toResetPwd(){
        return "front/resetPwd";
    }
    @GetMapping(value = "/toRegister")
    public String toRegister(){
        return "front/register";
    }
    @PostMapping(value = "/checkLogin")
    @ResponseBody
    public String checkLogin(HttpServletRequest request, HttpServletResponse response){
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String result="";
        Object mark = request.getSession().getAttribute("mark");
        Register register = registerService.getRegisterByPhone(phone);
        if(register != null){
            result = "namePass"+mark;
            if(register.getPassword().equals(MD5.md5(password))){
                result = "pass"+mark;//密码正确，返回pass
                request.getSession().setAttribute("phone",phone);
                request.setAttribute("phone", phone);
            }else{
                result = "pwdFail"+mark;
            }
        }else{
            result = "nameFail"+mark;
        }
        System.out.println(result);
        return result;

    }

    @GetMapping(value = "checkLogin2")
    public String checkLogin2(){
        return "result";
    }
}
