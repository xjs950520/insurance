package com.insurance.web;

import com.insurance.bean.Register;
import com.insurance.service.RegisterService;
import com.insurance.util.ApiSendMobile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/**
 * Created by xujunshuai on 2017/8/8.
 */
@Controller
@RequestMapping(value = "/sendCodeController")
public class SendCodeController {

    @Autowired
    private RegisterService registerService;

    @PostMapping(value = "/sendCode")
    @ResponseBody
    public String sendCode( HttpServletRequest request){

        String phone="";
        Register register=null;
        String result="";
        String l=request.getParameter("phone");
        String l2=request.getParameter("r_phone");
        if(request.getParameter("phone")!=null){//代表的是注册,手机号唯一，即需未注册过
            phone=request.getParameter("phone");
            register=registerService.getRegisterByPhone(phone);
            if(register!=null){
                return "exist";
            }else{
                result = this.send(phone,request);
            }
        }else if(request.getParameter("r_phone")!=null){//重置时的手机号，手机号数据需已存在
            phone=request.getParameter("r_phone");
            if(registerService.getRegisterByPhone(phone)==null){//不存在
                return "noExist";
            }else{//存在
               result = this.send(phone,request);
            }
        }
        return result;
    }
    public static String send(String phone,HttpServletRequest request){
        int num = new Random().nextInt(999999);
        String message =String.valueOf(num);

        ApiSendMobile apiSendMobile = new ApiSendMobile();
        String result = apiSendMobile.sendSM2(phone, message);
        if(result.equals("OK")){
            request.getSession().setAttribute("num", num);
            /*request.getSession().setMaxInactiveInterval(60);*/  //session的失效时间为60秒
        }
        return result;
    }

}
