package com.example.demo.redlock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by lailai on 2018/1/20.
 * 获取锁的管理实现类
 */
@Component
public class RedisLocker implements DistributedLocker {
    private final static String LOCKEY_PREFIX="lock";

    @Autowired
    private RedissonConnector redissonConnector;

    @Override
    public <T> T lock(String resourceName, AquiredLockWorker<T> worker) throws UnableToAquireLockException, Exception {
        return lock(resourceName,worker,100);
    }

    @Override
    public <T> T lock(String resourceName, AquiredLockWorker<T> worker, int locktime) throws UnableToAquireLockException, Exception {
        RedissonClient redisson=redissonConnector.getClient();
        RLock lock=redisson.getLock(LOCKEY_PREFIX + resourceName);
        // Wait for 100 seconds seconds and automatically unlock it after lockTime seconds
        boolean success=lock.tryLock(100,locktime, TimeUnit.SECONDS);
        if(success){
            try {
                return worker.invokeAfterLockAquire();
            }finally {
                lock.unlock();
            }
        }
       throw new UnableToAquireLockException();
    }
}
