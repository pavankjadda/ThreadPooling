package com.threadpool.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadPoolExecutorImpl
{
    private ThreadPoolExecutor threadPoolExecutor =null;
    private ExecutorService executorService = null;

    public ThreadPoolExecutorImpl(int coreNumberOfThreads, int maxNumberOfThreads, Long keepAliveTime)
    {
        //this.threadPoolExecutor= (ThreadPoolExecutor) Executors.newFixedThreadPool(coreNumberOfThreads);
        this.threadPoolExecutor= new ThreadPoolExecutor(coreNumberOfThreads,maxNumberOfThreads, keepAliveTime,
                TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());
        this.executorService= this.threadPoolExecutor;

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
                    //TimeUnit.MILLISECONDS.sleep(300);
                    System.out.println("Active Number of Threads: "+threadPoolExecutor.getActiveCount() +" Pool Size: "+threadPoolExecutor.getPoolSize());
                    return "Current Thread: "+Thread.currentThread().getName();
                };
                callableList.add(callableTask);
            }
            try
            {
                List<Future<String>> futureList=executorService.invokeAll(callableList,1000L,TimeUnit.MILLISECONDS);
                for(Future<String> message: futureList)
                    System.out.println(message.get());
            }
            catch (CancellationException e)
            {
                System.out.println("CancellationException occurred. executorService.invokeAll() Timeout Hit");
            }

            //executorService.shutdown();
            List<Runnable> notExecutedTasks = executorService.shutdownNow();
            for (Runnable runnable:notExecutedTasks)
                System.out.println("Not Executed Task: "+runnable.toString());

            shutdownExecutorService(executorService,500);

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
            Future<String> future=executorService.submit(callableTask);
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

            shutdownExecutorService(executorService,500);
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


    private void shutdownExecutorService(ExecutorService executorService, int timeout)
    {
        try
        {
            if (!executorService.awaitTermination(timeout, TimeUnit.MILLISECONDS))
            {
                executorService.shutdownNow();
            }
        }
        catch (InterruptedException e)
        {
            executorService.shutdownNow();
        }
    }
}
