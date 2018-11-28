package com.threadpool.service;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ExecutorImpl
{
    private Executor executor=null;

    public ExecutorImpl()
    {
        this.executor= Executors.newSingleThreadExecutor();
    }

    public void submitTasks()
    {
        executor.execute(() ->
        {
            System.out.println("Inside execute method()");
        });
    }
}
