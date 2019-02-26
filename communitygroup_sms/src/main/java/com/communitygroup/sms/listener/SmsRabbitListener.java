package com.communitygroup.sms.listener;

import com.aliyuncs.exceptions.ClientException;
import com.communitygroup.sms.util.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @program:
 * @description:
 * @author: Song
 * @create: Created in 2019-02-26 14:23
 * @Modified by:
 **/
@Component
@RabbitListener(queues = "sms")
public class SmsRabbitListener {

    @Autowired
    SmsUtil smsUtil;

    @Value("${aliyun.sms.template_code}")
    private String template_code;

    @Value("${aliyun.sms.sign_name}")
    private String sign_name;

    @RabbitHandler
    public void sendSms(Map<String, String> map)  {
        String mobile = map.get("mobile");
        String checkcode = map.get("checkcode");

        //测试用一下
        System.out.println(mobile);
        System.out.println(checkcode);
//        try{
//            smsUtil.sendSms(mobile, template_code, sign_name, checkcode);
//        }catch (ClientException e){
//            e.printStackTrace();
//        }



    }
}
