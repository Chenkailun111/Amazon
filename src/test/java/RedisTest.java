import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisTest {
    //单独测试
    @Test
    public void testJedisPool() {
        //创建连接池对象
        JedisPool jedisPool = new JedisPool("192.168.29.102", 6379);
        //从连接池中获取一个jedis对象
        Jedis jedis = jedisPool.getResource();
        jedis.set("key2", "jedisPool2");
        String string = jedis.get("key2");
        System.out.println(string);
        //关闭jedis对象
        jedis.close();
        //关闭连接池
        jedisPool.close();
    }

    //整合spring的测试
    @Test
    public  void testSpringJedisSingle(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
        JedisPool pool = (JedisPool) applicationContext.getBean("redisClient");
        Jedis jedis = pool.getResource();
        jedis.set("key1", "key1value");
        String string = jedis.get("key1");
        System.out.println("------: "+string);
        jedis.close();
        pool.close();
    }
}
