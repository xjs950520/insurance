package com.insurance.util;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudTopic;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.BatchSmsAttributes;
import com.aliyun.mns.model.MessageAttributes;
import com.aliyun.mns.model.RawTopicMessage;
import com.aliyun.mns.model.TopicMessage;
import com.aliyun.oss.ServiceException;
/*import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;*/

/*import java.util.Random;*/

/**
 * Created by xujunshuai on 2017/8/8.
 */
public class ApiSendMobile {
    private static String product = "Dysmsapi";
    private static String domain = "dysmsapi.aliyuncs.com";
    //个人
    /*private static String accessKeyId = "LTAId9GuV22cjFKW";
    private static String accessKeySecret = "6PX6fSWZoRAwekECIcFSTD4NFRCLnL";
    private static String signName = "许军帅";
    private static String templateCode = "SMS_82630015";*/
    //公司
    private static String accessKeyId = "LTAI8etZricJSsdV";
    private static String accessKeySecret = "XyuC459lB29lwh4BLTfDYDfVKNfhAp";
    private static String signName = "优医家";
    private static String templateCode = "SMS_90990007";//待定
    private static String endPoint = "https://1366391809550679.mns.cn-hangzhou.aliyuncs.com";
    private static String topic1 = "sms.topic-cn-hangzhou";
   /* public String sendSM(String phoneNumber, String message){

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
        request.setSignName(signName);
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
        *//*System.out.println(sendSmsResponse.getCode());
        System.out.println(sendSmsResponse.getMessage());
        System.out.println(sendSmsResponse.getRequestId());
        System.out.println(sendSmsResponse.getBizId());*//*
        String code = sendSmsResponse.getCode();
        System.out.println("返回的状态码------------------------------------------"+code);
        if(code.equals("isv.MOBILE_NUMBER_ILLEGAL")){
            //非法手机号
        }else if(code.equals("isv.BUSINESS_LIMIT_CONTROL")){
            //操纵过于频繁
        }
        return code;
    }*/
    public String sendSM2(String phone,String message){
        //Step 1 获取主体引用
        String phone1= phone;
        String message1=message;
        CloudAccount account = new CloudAccount(accessKeyId, accessKeySecret, endPoint);
        MNSClient client = account.getMNSClient();
        CloudTopic topic = client.getTopicRef(topic1);
        //Step 2 设置消息体（必需）
        RawTopicMessage msg = new RawTopicMessage();
        msg.setMessageBody("sms-message");
        //Step 3 生成SMS消息属性
        MessageAttributes messageAttributes = new MessageAttributes();
        BatchSmsAttributes batchSmsAttributes = new BatchSmsAttributes();
        //设置发送短信的签名
        batchSmsAttributes.setFreeSignName(signName);
        //设置发送短信使用的模板
        batchSmsAttributes.setTemplateCode(templateCode);
        //设置发送短信所适用的摸板中参数对应的值（在短信模板中定义的，）没有可以不用设置
        BatchSmsAttributes.SmsReceiverParams smsReceiverParams = new BatchSmsAttributes.SmsReceiverParams();

        smsReceiverParams.setParam("number",message);
        /*smsReceiverParams.setParam("","");*/
        //增加接收短信的号码
        batchSmsAttributes.addSmsReceiver(phone,smsReceiverParams);
        messageAttributes.setBatchSmsAttributes(batchSmsAttributes);
        String result="";
        try{
            //发布SMS消息
            TopicMessage ret = topic.publishMessage(msg,messageAttributes);

            System.out.println("ret==============================="+ret);
            System.out.println("MessageId:" + ret.getMessageId());
            System.out.println("MessageMD5:" + ret.getMessageBodyMD5());
            result = "OK";
        }catch(ServiceException se){
            System.out.println(se.getErrorCode() + se.getRequestId());
            System.out.println(se.getMessage());
            se.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        client.close();

        return result;
    }
}
