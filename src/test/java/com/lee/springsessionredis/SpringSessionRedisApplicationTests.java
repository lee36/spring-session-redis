package com.lee.springsessionredis;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.DefaultScriptExecutor;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.awt.image.RescaleOp;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringSessionRedisApplicationTests {

    @Autowired
    private CuratorFramework curatorFramework;
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void contextLoads() throws Exception {
        boolean started = curatorFramework.isStarted();
        curatorFramework.create().creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT).withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                .forPath("/aa/bb/cc","zhangsan".getBytes());
        System.out.println(started);
    }
    @Test
    public void setData() throws Exception {
        curatorFramework.setData().forPath("/aa/bb/cc","heiheoi".getBytes());
    }
    @Test
    public void del() throws Exception {
        curatorFramework.delete().guaranteed().deletingChildrenIfNeeded().forPath("/aa/bb/cc");
    }
    @Test
    public void state() throws Exception {
        Stat stat = new Stat();
        byte[] bytes = curatorFramework.getData().storingStatIn(stat).forPath("/aa/bb/cc");
        System.out.println(new String(bytes)+"===========");
        System.out.println(stat+"================");
    }
    @Test
    public void oneWatch() throws Exception {
        byte[] bytes = curatorFramework.getData().usingWatcher(new CuratorWatcher() {
            @Override
            public void process(WatchedEvent watchedEvent) throws Exception {
                System.out.println("触发啦");
            }
        }).forPath("/aa/bb/cc");
    }
    @Test
    public void watch() throws InterruptedException {
      curatorFramework.getCuratorListenable().addListener(new CuratorListener() {
          @Override
          public void eventReceived(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
            System.out.println("listener.....");
            System.out.println(curatorEvent.getType().toString());
          }
      });
      Thread.sleep(1000000L);
    }
    @Test
    public void test(){
        Jedis jedis = new Jedis("118.31.64.158",6379);
        ScanParams params = new ScanParams();
        params.match("*").count(10);
        ScanResult<String> scan = jedis.scan("0", params);
        List<String> result = scan.getResult();
        result.stream().forEach((s)->{
           System.out.println(s);
        });

    }

}

