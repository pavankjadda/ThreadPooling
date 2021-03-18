package com.pj.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ExecutorImpl
{
    private final Executor executor;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public ExecutorImpl()
    {
        this.executor = Executors.newSingleThreadExecutor();
    }

    public void submitTasks()
    {
        executor.execute(() -> logger.info("Inside execute method()"));
    }
}
