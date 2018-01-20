package com.example.demo.redlock;

/**
 * Created by lailai on 2018/1/20.
 * 不能获取锁的异常类
 */
public class UnableToAquireLockException extends RuntimeException{

    public UnableToAquireLockException(){}

    public UnableToAquireLockException(String message){
        super(message);
    }

    public  UnableToAquireLockException(String message,Throwable cause){
        super(message,cause);
    }
}
