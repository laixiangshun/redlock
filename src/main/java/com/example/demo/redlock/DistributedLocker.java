package com.example.demo.redlock;

/**
 * Created by lailai on 2018/1/20.
 * 获取锁管理类
 */
public interface DistributedLocker {

    /**
     *
     * @param resourceName 锁的名称
     * @param worker 获取锁后的处理类
     * @param <T>
     * @return  处理完具体的业务逻辑后返回的数据
     * @throws UnableToAquireLockException
     * @throws Exception
     */
    <T> T lock(String resourceName,AquiredLockWorker<T> worker) throws UnableToAquireLockException,Exception;

    <T> T lock(String resourceName,AquiredLockWorker<T> worker,int locktime) throws UnableToAquireLockException,Exception;
}
