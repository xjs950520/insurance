package com.insurance.web;

import com.insurance.util.ApiSendMobile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/**
 * Created by xujunshuai on 2017/8/8.
 */
@RestController
@RequestMapping(value = "/sendCodeController")
public class SendCodeController {

    @RequestMapping(value = "/sendCode")
    public String sendCode( HttpServletRequest request){
        String phone=request.getParameter("phone");
        int num = new Random().nextInt(999999);
        String message = "{\"number\":\""+num+"\"}";

        ApiSendMobile apiSendMobile = new ApiSendMobile();
        String result = apiSendMobile.sendSM(phone, message);
        if(result.equals("OK")){
            request.getSession().setAttribute("num", num);
            request.getSession().setMaxInactiveInterval(60);//session的失效时间为60秒
        }

        return result;
    }

}
