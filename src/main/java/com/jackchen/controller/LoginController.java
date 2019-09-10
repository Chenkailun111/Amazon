package com.jackchen.controller;

import com.jackchen.DTO.TuserDto;
import com.jackchen.pojo.User;
import com.jackchen.service.UserLoginService;
import com.jackchen.utils.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Controller
@PropertySource("classpath:sms.properties")
public class LoginController {

    @Autowired
    private UserLoginService userLoginService;
    @RequestMapping("/login")
    public String login(TuserDto userlogin, String yanzheng, ModelMap map) {
        System.out.println(userlogin.getUsername()+userlogin.getPassword()+"---------");
        String code = ImageUtil.getCode();
        try {
            if (code.equalsIgnoreCase(yanzheng)) {
                //Shiro实现登录 jack md5验证
                Md5Hash md5Hash = new Md5Hash(userlogin.getPassword(),userlogin.getUsername(),1024);
                userlogin.setPassword(md5Hash.toString());

                UsernamePasswordToken token = new UsernamePasswordToken(userlogin.getUsername(),userlogin.getPassword());
                //d得到subject对象
                Subject subject = SecurityUtils.getSubject();

                subject.login(token);
                User byName = userLoginService.findByName(userlogin.getUsername());
                UserContext.putLogininfo(byName);

                User currentUser = ShiroUtils.getCurrentUser();

                    System.out.println(currentUser+"----------");

            } else {
               return "/login";
            }
        } catch (Exception e) {
            return "/login";
        }
        return "/index";
    }

    //查找名字是否相同
    @RequestMapping("/CheckUserName")
    @ResponseBody
    public R checkUsername(String userName){
        User byName = userLoginService.findByName(userName);
        if (byName == null){
            return R.ok();
        }
       return R.error("姓名重复");
    }
    //注册
    @RequestMapping("/registerUser")
    @ResponseBody
    public R register(User user,String veryCode,
                      HttpServletResponse response,
                      HttpServletRequest request){

        //校验是否重复
        R r1 = checkUsername(user.getUname());
        Integer code = (Integer) r1.get("code");

            if ( code!= null && code == 0 ){
                r1 = userLoginService.InsertSelective(user, veryCode);
            }
            String msg = (String) r1.get("msg");
            System.out.println("r1的信息"+msg);

        return r1;
    }


    //重置密码：验证码是否正确，接收密码，更改用户密码


    @Value("${aliyun.sms.template_code}")
    private String template_code;//模板编号

    @Value("${aliyun.sms.sign_name}")
    private String sign_name;//签名

    @Value("${aliyun.sms.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.sms.accessKeySecret}")
    private String accessKeySecret;

    //发送短息验证码
    @RequestMapping(value = "/sendCode")
    @ResponseBody
    public R sendCode(String mobile) throws Exception{
        System.out.println("发送短信!!!!!!!!!!!!!!!"+mobile);
        //短信配置数据
        Map<String,String> map = new HashMap<String,String>();
        map.put("accessKeyId",accessKeyId);
        map.put("accessKeySecret",accessKeySecret);
        map.put("template_code",template_code);
        //todo 由于properties编码不对，所以再次转码
        String signName = sign_name;
        signName = new String(sign_name.getBytes("ISO-8859-1"), "utf-8");
        map.put("sign_name",signName);
        System.out.println("得到的sign_name"+signName+"--------?????????????????????");
        userLoginService.sendSms(mobile,map);
        //返回发送成功的消息
        return R.ok("验证码正在发送，请等待");
    }
}
