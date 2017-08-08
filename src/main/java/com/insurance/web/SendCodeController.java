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
        String phone="13162235587";
        int num = new Random().nextInt(999999);
        String message = "{\"number\":\""+num+"\"}";

        ApiSendMobile apiSendMobile = new ApiSendMobile();
        String result = apiSendMobile.sendSM(phone, message);
        if(result.equals("OK")){
            request.getSession().setAttribute("num", num);
            request.getSession().setMaxInactiveInterval(60);//session的失效时间为60秒
        }

        return apiSendMobile.sendSM(phone, message);
        //产品名称:云通信短信API产品,开发者无需替换
//       String product = messageParamater.getProduct();
        //产品域名,开发者无需替换
//       String domain = messageParamater.getDomain();
        // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
//        String accessKeyId = messageParamater.getAccessKeyId();
//        String accessKeySecret = messageParamater.getAccessKeySecret();
//
//        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
//        try {
//            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
//        } catch (ClientException e) {
//            e.printStackTrace();
//        }
//        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象-具体描述见控制台-文档部分内容
//        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号

//        request.setPhoneNumbers(messageParamater.getPhoneNumber());
        //必填:短信签名-可在短信控制台中找到
//        request.setSignName(messageParamater.getSignName());
        //必填:短信模板-可在短信控制台中找到
//        request.setTemplateCode(messageParamater.getTemplateCode());
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
//        int num = new Random().nextInt(999999);
//        request.setTemplateParam("{\"number\":\""+num+"\"}");

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        //request.setOutId("yourOutId");

//        SendSmsResponse sendSmsResponse = null;
//        try {
//            sendSmsResponse = acsClient.getAcsResponse(request);
//        } catch (ClientException e) {
//            e.printStackTrace();
//        }
        /*System.out.println(sendSmsResponse.getCode());
        System.out.println(sendSmsResponse.getMessage());
        System.out.println(sendSmsResponse.getRequestId());
        System.out.println(sendSmsResponse.getBizId());*/
//        String code = sendSmsResponse.getCode();
//        if(code.equals("isv.MOBILE_NUMBER_ILLEGAL")){
//            //非法手机号
//        }else if(code.equals("isv.BUSINESS_LIMIT_CONTROL")){
//            //操纵过于频繁
//        }
//        return sendSmsResponse.getCode();

    }
}
