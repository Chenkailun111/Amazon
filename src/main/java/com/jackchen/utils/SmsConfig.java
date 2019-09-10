package com.jackchen.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;


@PropertySource("classpath:sms.properties")
public class SmsConfig {
    @Value("${aliyun.sms.template_code}")
    public static String template_code;//模板编号

    @Value("${aliyun.sms.sign_name}")
    public static String sign_name;//签名

    @Value("${aliyun.sms.accessKeyId}")
    public static String accessKeyId;

    @Value("${aliyun.sms.accessKeySecret}")
    public static String accessKeySecret;

    private static String a,b,c,d;

    public static String getTemplate_code() {
        return template_code;
    }

    public static void setTemplate_code(String template_code) {
        SmsConfig.template_code = template_code;
    }

    public static String getSign_name() {
        return sign_name;
    }

    public static void setSign_name(String sign_name) {
        SmsConfig.sign_name = sign_name;
    }

    public static String getAccessKeyId() {
        return accessKeyId;
    }

    public static void setAccessKeyId(String accessKeyId) {
        SmsConfig.accessKeyId = accessKeyId;
    }

    public static String getAccessKeySecret() {
        return accessKeySecret;
    }

    public static void setAccessKeySecret(String accessKeySecret) {
        SmsConfig.accessKeySecret = accessKeySecret;
    }

    public static String getA() {
        return a;
    }

    public static void setA(String a) {
        SmsConfig.a = a;
    }

    public static String getB() {
        return b;
    }

    public static void setB(String b) {
        SmsConfig.b = b;
    }

    public static String getC() {
        return c;
    }

    public static void setC(String c) {
        SmsConfig.c = c;
    }

    public static String getD() {
        return d;
    }

    public static void setD(String d) {
        SmsConfig.d = d;
    }

    public SmsConfig() {
        this.a = template_code;
        this.b = sign_name;
        this.c = accessKeyId;
        this.d = accessKeySecret;
    }


    @Override
    public String toString() {
        return "SmsConfig{" +
                "template_code='" + template_code + '\'' +
                ", sign_name='" + sign_name + '\'' +
                ", accessKeyId='" + accessKeyId + '\'' +
                ", accessKeySecret='" + accessKeySecret + '\'' +
                '}';
    }
}
