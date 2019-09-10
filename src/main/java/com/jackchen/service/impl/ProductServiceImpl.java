package com.jackchen.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jackchen.mapper.ProductMapper;
import com.jackchen.pojo.A;
import com.jackchen.pojo.Product;
import com.jackchen.service.ProductService;
import com.jackchen.utils.JedisClientPool;
import com.jackchen.utils.JsonUtils;
import com.jackchen.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
//    @Autowired
//    @Qualifier("redisClient")
//    @Autowired
//    private JedisPool jedisPool;

    @Autowired
    private JedisClientPool jedisClientPool;

      /*  ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
        JedisPool jedisPool = (JedisPool) applicationContext.getBean("redisClient");*/
      //private JedisClientPool jedisClientPool = new JedisClientPool(jedisPool);

    //    private JedisClientPool jedisClientPool = new JedisClientPool();
    @Override
    public R ishot() {
//        System.out.println(jedisPool+"----------------------");
         List<Product> ishot = null;
        String json = jedisClientPool.get("ishot");
        if (json == null){
            //第一次访问走数据库
            ishot = productMapper.findIshot();
            if (ishot != null){
                json = JsonUtils.objectToJson(ishot);
                //todo 缓存雪崩 针对热点排行设置不同的过期时间
                jedisClientPool.set("ishot",json);
                jedisClientPool.expire("ishot",30*60*3);
            }else {
                //jack 缓存穿透
                jedisClientPool.set("ishot",null);
                jedisClientPool.expire("ishot",60);
            }

        }else {
            //存入过数据，将数据取出来
            ishot = JsonUtils.jsonToList(json,Product.class);
        }
        //热点数据存入数据库
        return R.ok().put("ishot",ishot);
    }

    //如果热点数据被更改过
    public R delRedis(String key){
        //TODO 缓存数据不一致
        // jack 先删除缓存，再更改数据库，等待1秒删除脏数据
        try {
            jedisClientPool.del(key);
            //db.update
            Thread.sleep(1000);
            jedisClientPool.del(key);
        } catch (InterruptedException e) {
           throw new RuntimeException();
        }
        return R.ok();
    }
    @Override
    public Product selectByPrimaryKey(long id) {
        Product product = productMapper.selectByPrimaryKey(id);
        return product;
    }

    @Override
    public PageInfo findList(int pageindex, int size) {
        Map map = new HashMap();
        //封装条件

        PageHelper.startPage(pageindex,size);
        //根据条件查找列表
        List<Product> list = productMapper.findList(map);
        for (Product product : list) {
            System.out.println(product);
        }
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }
}
