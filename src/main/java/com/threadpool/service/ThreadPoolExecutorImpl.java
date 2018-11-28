package com.threadpool.service;

import java.util.concurrent.*;

public class ThreadPoolExecutorImpl
{
    ThreadPoolExecutor threadPoolExecutor =null;

    public ThreadPoolExecutorImpl(int coreNumberOfThreads, int maxNumberOfThreads)
    {
        this.threadPoolExecutor= (ThreadPoolExecutor) Executors.newFixedThreadPool(coreNumberOfThreads);
    }

    public void submitTasks()
    {
        for(int i=0;i<100;i++)
        {
            threadPoolExecutor.submit(() -> {
                Thread.sleep(1000);
                return null;
            });
        }

        System.out.println(threadPoolExecutor.getPoolSize());
        System.out.println(threadPoolExecutor.getActiveCount());

        BlockingQueue<Runnable> blockingQueue= threadPoolExecutor.getQueue();
        threadPoolExecutor.getCompletedTaskCount();
        for (Runnable runnable: blockingQueue)
        {

            System.out.println(runnable.toString());
        }
    }
}
