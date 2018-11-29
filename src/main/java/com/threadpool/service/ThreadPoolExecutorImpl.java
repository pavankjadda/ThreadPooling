package com.threadpool.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadPoolExecutorImpl
{
    private ThreadPoolExecutor threadPoolExecutor =null;

    public ThreadPoolExecutorImpl(int coreNumberOfThreads, int maxNumberOfThreads, Long keepAliveTime)
    {
        //this.threadPoolExecutor= (ThreadPoolExecutor) Executors.newFixedThreadPool(coreNumberOfThreads);
        this.threadPoolExecutor= new ThreadPoolExecutor(coreNumberOfThreads,maxNumberOfThreads, keepAliveTime,
                TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());
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

    public void invokeAll()
    {
        try
        {
            List<Callable<String>> callableList=new ArrayList<>();
            Callable<String> callableTask=null;
            for(int i=0;i<100;i++)
            {
                callableTask= () ->
                {
                    TimeUnit.MILLISECONDS.sleep(100);
                    System.out.println("Active Number of Threads: "+threadPoolExecutor.getActiveCount() +" Pool Size: "+threadPoolExecutor.getPoolSize());
                    System.out.println("getCompletedTaskCount() "+threadPoolExecutor.getCompletedTaskCount());
                    return "Current Thread: "+Thread.currentThread().getName();
                };
                callableList.add(callableTask);
            }
            try
            {
                List<Future<String>> futureList=threadPoolExecutor.invokeAll(callableList,100000L,TimeUnit.MILLISECONDS);
                for(Future<String> message: futureList)
                {
                    message.get();
                    //System.out.println(message.get());
                }

            }
            catch (CancellationException e)
            {
                System.out.println("CancellationException occurred. threadPoolExecutor.invokeAll() Timeout Hit");
            }

            //threadPoolExecutor.shutdown();
            List<Runnable> notExecutedTasks = threadPoolExecutor.shutdownNow();
            for (Runnable runnable:notExecutedTasks)
                System.out.println("Not Executed Task: "+runnable.toString());

            shutdownthreadPoolExecutor(threadPoolExecutor,500);

        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void invokeOnebyOne()
    {
        try
        {
            Callable<String> callableTask=addNewCallableTask(" File1 ");
            Future<String> future=threadPoolExecutor.submit(callableTask);
            String result=null;
            try
            {
                result=future.get(100,TimeUnit.MILLISECONDS);
                System.out.println(result);
            }
            catch (TimeoutException| InterruptedException | ExecutionException e)
            {
                System.out.println(e.getClass()+" occurred before finishing task");
                e.printStackTrace();
            }

            shutdownthreadPoolExecutor(threadPoolExecutor,500);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private Callable<String> addNewCallableTask(String message)
    {
        return () ->
        {
            //TimeUnit.MILLISECONDS.sleep(10);
            System.out.println("Active Number of Threads: "+Thread.activeCount());
            return "Current Thread: " + Thread.currentThread().getName() + " and Message from Parent: " + message;
        };
    }


    private void shutdownthreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor, int timeout)
    {
        try
        {
            if (!threadPoolExecutor.awaitTermination(timeout, TimeUnit.MILLISECONDS))
            {
                threadPoolExecutor.shutdownNow();
            }
        }
        catch (InterruptedException e)
        {
            threadPoolExecutor.shutdownNow();
        }
    }
}
