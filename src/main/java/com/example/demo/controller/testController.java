package com.example.demo.controller;

import com.example.demo.redlock.AquiredLockWorker;
import com.example.demo.redlock.RedisLocker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Created by lailai on 2018/1/20.
 */
@RestController
public class testController {

    @Autowired
    private RedisLocker redisLocker;

    @RequestMapping(value = "/redlock")
    public String testRedlock() throws Exception {
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            new Thread(new Worker(startSignal,doneSignal){
            }).start();
        }
        startSignal.countDown();
        doneSignal.await();
        System.out.println("all processors done,shutdown connection");
        return "redlock success";
    }

    class Worker implements Runnable{
        private final CountDownLatch startSignal;
        private final CountDownLatch doneSignal;

        Worker(CountDownLatch startSignal,CountDownLatch doneSignal){
            this.startSignal=startSignal;
            this.doneSignal=doneSignal;
        }

        public void run(){
            try {
                startSignal.await();
                redisLocker.lock("tets", new AquiredLockWorker<Object>() {
                    @Override
                    public Object invokeAfterLockAquire() throws Exception {
                        doTask();
                        return null;
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void doTask(){
            System.out.println(Thread.currentThread().getName()+" start");
            Random random=new Random();
            int _int=random.nextInt(200);
            System.out.println(Thread.currentThread().getName()+" sleep "+_int+" millis");
            try {
                Thread.sleep(_int);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+" end");
            doneSignal.countDown();
        }
    }
}
