package com.itheima;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName JavaTest
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2020/7/14 0014
 * @Version V1.0
 **/
class Student{
    private String name;
}
public class JavaTest {
    public static void main(String[] args) {
        ExecutorService singleThread = Executors.newSingleThreadExecutor();
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
    }
}
