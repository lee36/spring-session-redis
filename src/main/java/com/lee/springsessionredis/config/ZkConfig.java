package com.lee.springsessionredis.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZkConfig {

    @Value("${zk.host}")
    private String zkHost;
    @Bean
    public RetryNTimes retryNTimes(){
        return new RetryNTimes(3,1000);
    }
    @Bean(initMethod = "start")
    public CuratorFramework curatorFramework(){
        return CuratorFrameworkFactory.builder().connectString(zkHost)
                .retryPolicy(retryNTimes()).namespace("lee").build();
    }
}
