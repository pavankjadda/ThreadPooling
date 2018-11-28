package com.threadpool.service;

import java.util.concurrent.*;

public class ThreadPoolImplementation
{
    public static void main(String[] args)
    {
        Executor executor=Executors.newSingleThreadExecutor();
        executor.execute(() ->
        {
            System.out.println("Inside execute method()");
        });

        /* Implementing Executor Service*/
        ExecutorServiceImpl executorServiceImpl=new ExecutorServiceImpl(10);
        executorServiceImpl.submitTasks();
    }
}
