package com.threadpool.service;

public class ThreadPoolImplementation
{
    public static void main(String[] args)
    {
        /* Implement Executor */
        ExecutorImpl executorImpl=new ExecutorImpl();
        //executorImpl.submitTasks();


        /* Implement Executor Service Interface*/
        ExecutorServiceImpl executorServiceImpl=new ExecutorServiceImpl(10);
        //executorServiceImpl.submitTasks();


        ThreadPoolExecutorImpl threadPoolExecutorImpl=new ThreadPoolExecutorImpl(10,20,0L);
        threadPoolExecutorImpl.invokeAll();
        //threadPoolExecutorImpl.invokeOnebyOne();


    }
}
