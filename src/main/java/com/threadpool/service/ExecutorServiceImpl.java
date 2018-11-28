package com.threadpool.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceImpl
{
    private ExecutorService executorService=null;

    public ExecutorServiceImpl(int numberOfThreads)
    {
        executorService= Executors.newFixedThreadPool(numberOfThreads);;
    }

    public void submitTasks()
    {
        Future future=null;
        for(int i=0;i<100;i++)
        {
            future=executorService.submit(()->
            {
                System.out.println("Thread Name: "+Thread.currentThread().getName());
                return Thread.currentThread().getName();
            });
        }

        try
        {
            System.out.println("Thread Name: "+future.get());
            future.isDone();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            Runtime.getRuntime().gc();
        }
    }
}
