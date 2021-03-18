package com.pj.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorImpl
{
	private final ThreadPoolExecutor threadPoolExecutor;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public ThreadPoolExecutorImpl(int coreNumberOfThreads, int maxNumberOfThreads, Long keepAliveTime)
	{
		this.threadPoolExecutor = new ThreadPoolExecutor(coreNumberOfThreads, maxNumberOfThreads, keepAliveTime, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
	}

	public void invokeAll()
	{
		try
		{
			List<Callable<String>> callableList = new ArrayList<>();
			Callable<String> callableTask;
			for (int i = 0; i < 100; i++)
			{
				callableTask = () ->
				{
					TimeUnit.MILLISECONDS.sleep(100);
					logger.info("Active Number of Threads: {} Pool Size: {}", threadPoolExecutor.getActiveCount(), threadPoolExecutor.getPoolSize());
					logger.info("getCompletedTaskCount() {}", threadPoolExecutor.getCompletedTaskCount());
					return "Current Thread: " + Thread.currentThread().getName();
				};
				callableList.add(callableTask);
			}
			try
			{
				List<Future<String>> futureList = threadPoolExecutor.invokeAll(callableList, 100000L, TimeUnit.MILLISECONDS);
				for (Future<String> message : futureList)
				{
					message.get();
					logger.info(message.get());
				}

			}
			catch (CancellationException e)
			{
				logger.info("CancellationException occurred. threadPoolExecutor.invokeAll() Timeout Hit");
			}

			threadPoolExecutor.shutdown();
			List<Runnable> notExecutedTasks = threadPoolExecutor.shutdownNow();
			for (Runnable runnable : notExecutedTasks)
			{
				logger.info("Not Executed Task: {}", runnable);
			}

			shutDownThreadPoolExecutor(threadPoolExecutor);
		}

		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void shutDownThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor)
	{
		try
		{
			if (!threadPoolExecutor.awaitTermination(500, TimeUnit.MILLISECONDS))
			{
				threadPoolExecutor.shutdownNow();
			}
		}
		catch (Exception e)
		{
			threadPoolExecutor.shutdownNow();
		}
	}
}
