package com.jackchen.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

public class Test1 {
    public static void main(String[] args) {
        Md5Hash chen = new Md5Hash("1234", "chen", 1024);
        System.out.println(chen.toString());
    }
}
