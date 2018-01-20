package com.example.demo.redlock;

/**
 * Created by lailai on 2018/1/20.
 * 获取锁后需要处理的逻辑
 */
public interface AquiredLockWorker<T> {

    T invokeAfterLockAquire() throws Exception;
}
