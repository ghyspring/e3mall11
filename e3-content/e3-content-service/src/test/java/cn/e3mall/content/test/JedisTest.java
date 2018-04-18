package cn.e3mall.content.test;

import cn.e3mall.common.redis.JedisClient;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

public class JedisTest {

    //单机版测试
    @Test
    public void testJedis () throws Exception{
        Jedis jedis = new Jedis("192.168.25.4",6379);
        jedis.set("gao","gao");
        String gao = jedis.get("gao");
        System.out.println(gao);
        jedis.close();
    }

    //单机版使用连接池测试
    @Test
    public void testJedisPool() throws Exception{
        JedisPool jedisPool = new JedisPool("192.168.25.4",6379);
        Jedis jedis = jedisPool.getResource();
        jedis.set("hello","helloword");
        String hello = jedis.get("hello");
        System.out.println(hello);
        jedis.close();
        jedisPool.close();
    }

    //连接集群版测试
    @Test
    public void testJedisCluster() throws Exception{
        //创建JedisCluster对象需要传递一个set集合，里面存放的是Redis集群结点列表
        Set<HostAndPort> set = new HashSet();
        set.add(new HostAndPort("192.168.25.4",7001));
        set.add(new HostAndPort("192.168.25.4",7002));
        set.add(new HostAndPort("192.168.25.4",7003));
        set.add(new HostAndPort("192.168.25.4",7004));
        set.add(new HostAndPort("192.168.25.4",7005));
        set.add(new HostAndPort("192.168.25.4",7006));
        //创建JedisCluster对象
        JedisCluster jedisCluster = new JedisCluster(set);

        jedisCluster.hset("key1","key","value");
        String hget = jedisCluster.hget("key1", "key");
        System.out.println(hget);
        jedisCluster.close();
    }

    @Test
    public void jedisClientTest() throws Exception{
        //初始化Spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContent-redis.xml");
        //从容器中获取JedisCluster对象
        JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
        //操作redis数据库
        jedisClient.set("斗破","斗罗");
        String temp = jedisClient.get("斗破");
        System.out.println(temp);

    }

}
