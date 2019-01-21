package com.lee.springsessionredis;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@SpringBootApplication
@EnableRedisHttpSession
@RestController
public class SpringSessionRedisApplication {

    @RequestMapping("/test")
    public Object test(HttpSession session){
        session.setAttribute("name","zhangsan");
        return "123";
    }

    @RequestMapping("/get")
    public Object get(HttpSession session){
        return session.getAttribute("name");
    }
    @Bean("springSessionDefaultRedisSerializer")
    public GenericJackson2JsonRedisSerializer serializer(){
        return new GenericJackson2JsonRedisSerializer();
    }
    public static void main(String[] args) {
        SpringApplication.run(SpringSessionRedisApplication.class, args);
    }

}

