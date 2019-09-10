package com.jackchen.service;

import com.jackchen.pojo.User;
import com.jackchen.utils.R;

import java.util.Map;

/**
 * 用户登录与注册
 */
public interface UserLoginService {
    //根据名字查找用户
    User findByName(String name);

    R InsertSelective(User user, String code);

    public void sendSms(String mobile, Map map);
}
