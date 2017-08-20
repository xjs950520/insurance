package com.insurance.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import java.util.Random;

/**
 * Created by xujunshuai on 2017/8/8.
 */
public class ApiSendMobile {
    private static String product = "Dysmsapi";
    private static String domain = "dysmsapi.aliyuncs.com";
    private static String accessKeyId = "LTAId9GuV22cjFKW";
    private static String accessKeySecret = "6PX6fSWZoRAwekECIcFSTD4NFRCLnL";
    private static String SignName = "许军帅";
    private static String templateCode = "SMS_82630015";

    public String sendSM(String phoneNumber, String message){

        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号

        request.setPhoneNumbers(phoneNumber);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(SignName);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
 //       int num = new Random().nextInt(999999);
//        request.setTemplateParam("{\"number\":\""+num+"\"}");
        request.setTemplateParam(message);
        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        //request.setOutId("yourOutId");

        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        /*System.out.println(sendSmsResponse.getCode());
        System.out.println(sendSmsResponse.getMessage());
        System.out.println(sendSmsResponse.getRequestId());
        System.out.println(sendSmsResponse.getBizId());*/
        String code = sendSmsResponse.getCode();
        System.out.println("返回的状态码------------------------------------------"+code);
        if(code.equals("isv.MOBILE_NUMBER_ILLEGAL")){
            //非法手机号
        }else if(code.equals("isv.BUSINESS_LIMIT_CONTROL")){
            //操纵过于频繁
        }
        return code;
    }
}
