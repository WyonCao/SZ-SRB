package com.wyon.srb.test;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
public class RedisTemplateTests {


    @Resource
    private RedisTemplate redisTemplate;




    @Test
    public void c(){

        // 选择不同类型的redis存储方式
        // 以购物车数据为例，购物车要求集合中的每一个元素有自己独立的key，所以hash结构比较合理
//        redisTemplate.opsForHash().put("bkey","skey","sv");
        redisTemplate.opsForHash().put("a","key1","sv");


        // 需要定义rediskey的可读性，Object:id:field


    }

    @Test
    public void HashSetData(){

        redisTemplate.opsForHash().put("user:1:cart","001","product1");
    }

    @Test
    public void HashGetData(){

        Object key = redisTemplate.opsForHash().get("user:1:cart","001");
        System.out.println(key);

    }
    @Test
    public void d (){
        redisTemplate.opsForValue().set("key","2");
        Object key = redisTemplate.opsForValue().get("key");
        System.out.println(key);
    }





}
