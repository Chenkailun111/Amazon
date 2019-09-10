package com.jackchen.mq;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.jackchen.utils.JsonUtils;
import com.jackchen.utils.SmsUtil;
import java.util.Map;

/**
 * 消费者
 * @author zhijun
 *
 */
public class Foo {

    /**
     *  发送短信，消费者项目一直开着，一有短信就消费掉，发送短信
     * @param message
     */

    SmsUtil smsUtil = new SmsUtil();

    public void sendSms(String message){//routingkey sms
        Map map = JsonUtils.jsonToPojo(message, Map.class);
        String smscode = (String) map.get("smscode"); //验证码
        String mobile = (String)map.get("mobile");

        String accessKeyId =(String) map.get("accessKeyId");
        String accessKeySecret = (String)map.get("accessKeySecret");
        String template_code = (String)map.get("template_code");
        String sign_name = (String)map.get("sign_name");
        System.out.println("sign_name="+sign_name+"============================");
        System.out.println("accessKeyId="+accessKeyId+"============================");
        System.out.println("template_code="+template_code+"============================");
        System.out.println("手机号：="+mobile); //手机号
        System.out.println("验证码：="+smscode);
        try {
            //param 模板，key-value 对应短信验证码{\"code\":\"+"变量"+\"}
            SendSmsResponse sendSmsResponse = smsUtil.sendSms(mobile, template_code, sign_name, accessKeyId, accessKeySecret, "{\"code\":\"" + smscode + "\"}");
            System.out.println("code="+sendSmsResponse.getCode());
            System.out.println( "bizID="+sendSmsResponse.getBizId());
            System.out.println("message="+sendSmsResponse.getMessage());
            System.out.println("requestID="+sendSmsResponse.getRequestId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}