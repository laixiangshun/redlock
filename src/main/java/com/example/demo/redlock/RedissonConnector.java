package com.example.demo.redlock;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by lailai on 2018/1/20.
 * 链接类
 */
@Component
public class RedissonConnector {

    RedissonClient redisson;

    @PostConstruct
    public void init(){
        redisson= Redisson.create();
    }

    public RedissonClient getClient(){
        return redisson;
    }

}
