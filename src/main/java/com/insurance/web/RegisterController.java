package com.insurance.web;

import com.insurance.bean.Register;
import com.insurance.dao.impl.RegisterDaoImpl;
import com.insurance.service.RegisterService;
import com.insurance.service.impl.RegisterServiceImpl;
import com.insurance.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xujunshuai on 2017/8/9.
 */
@RestController
@RequestMapping(value = "/registerController")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    private final SimpleDateFormat sdf=new SimpleDateFormat("yyyy.MM.dd");

    @PostMapping(value = "/add")
    public int add(HttpServletRequest request, HttpServletResponse response){
        String name=request.getParameter("name");
        String phone=request.getParameter("phone");
        String password=request.getParameter("password");
        String intro_phone=request.getParameter("intro_phone");
        String intro_source=request.getParameter("intro_source");
        String ct_date=sdf.format(new Date());
        Register register = new Register();
        register.setName(name);
        register.setPhone(phone);
        register.setPassword(MD5.md5(password));
        register.setIntro_phone(intro_phone);
        register.setIntro_source(intro_source);
        register.setCt_date(ct_date);
        int i=registerService.add(register);
        return  i;

    }
}
