package com.jackchen.service.impl;

import com.jackchen.mapper.UserMapper;
import com.jackchen.pojo.User;
import com.jackchen.pojo.UserExample;
import com.jackchen.service.UserLoginService;
import com.jackchen.utils.JedisClientPool;
import com.jackchen.utils.JsonUtils;
import com.jackchen.utils.R;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


@Service
public class UserLoginServiceImpl implements UserLoginService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByName(String name) {
        UserExample tUserExample = new UserExample();

        UserExample.Criteria criteria = tUserExample.createCriteria();

        criteria.andUnameEqualTo(name);
        List<User> list = userMapper.selectByExample(tUserExample);
        /**
         * 防止查找不到get(0)异常
         */
        if(list.size() > 0 && list != null){
            return list.get(0);
        }
        return null;
    }


    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
    JedisPool jedisPool = (JedisPool) applicationContext.getBean("redisClient");
    private JedisClientPool jedisClientPool = new JedisClientPool(jedisPool);
    //新增数据
    @Override
    public R InsertSelective(User user, String code){
        R r = null;
        String syscode = jedisClientPool.get("smscode_" + user.getMobile());
        try {
        if(syscode==null){
            r = new R();
            r.put("code","1001");
            r.put("msg","请点击获取短信验证码");
            throw new RuntimeException("请点击获取短信验证码");
        }
        if(!syscode.equals(code)){
            r = new R();
            r.put("code","1002");
            r.put("msg","验证码输入不正确");
            throw new RuntimeException("验证码输入不正确");
        }

            if (user != null && user.getPwd() != null){
               Md5Hash md5Hash = new Md5Hash(user.getPwd(), user.getUname(), 1024);
               user.setPwd(md5Hash.toString());
           }
            userMapper.insertSelective(user);
        } catch (RuntimeException e){
            //注册失败
            //TODO 在日志系统中插入具体的message信息，页面不应该报太多信息e.getMessage()
            String code1 = (String) r.get("code");
            String msg = e.getMessage();
            System.out.println("错误信息=="+msg);
            System.out.println("r的错误码：=="+code1);
           return r;
//            throw new Exception(msg);
        }  catch(Exception e) {
            //数据库异常
            r = new R();
            r.put("code","1003");
            r.put("msg","数据库异常");
           return r;
        }
        return r.ok();
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Override
    public void sendSms(String mobile,Map map) {
        //1.生成6位短信验证码
        /**
         * 另一种生成随机数的做法：导入commo-lang3的jar包
         * String random = RandomStringUtils.randomNumeric(6);
         *
         */
        System.out.println("~~~~~~~~~telephoneNumber"+mobile);
        Random random=new Random();
        int max=999999;//最大数
        int min=100000;//最小数
        int code= random.nextInt(max);//随机生成
        if(code<min){
            code=code+min;
        }
        //存入redis
        jedisClientPool.set("smscode_"+mobile,code+"");
        jedisClientPool.expire("smscode_"+mobile,300);

        //3.将验证码和手机号发送到rabbitMQ
        map.put("mobile",mobile);
        map.put("smscode",code+"");

        //todo mq发送到bing的队列
        String mapJson = JsonUtils.objectToJson(map);
        rabbitTemplate.convertAndSend("","sms",mapJson);
        //测试值是否放入
        String s = jedisClientPool.get("smscode_" + mobile);
        System.out.println(mobile+ "收到验证码："+code);
        System.out.println("redis的值"+s);
    }
}
