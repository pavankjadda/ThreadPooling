package com.pj.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceImpl
{
    private final ExecutorService executorService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public ExecutorServiceImpl(int numberOfThreads)
    {
        executorService = Executors.newFixedThreadPool(numberOfThreads);
    }

    public void submitTasks()
    {
        Future future = null;
        for (int i = 0; i < 100; i++)
        {
            future=executorService.submit(()->
            {
                logger.info("Thread Name: {}", Thread.currentThread().getName());
                return Thread.currentThread().getName();
            });
        }

        try
        {
            logger.info("Thread Name: {}", future.get());
            future.isDone();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
