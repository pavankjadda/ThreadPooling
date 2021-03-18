package com.pj.reactivestreams;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class GreetingSubscriber<T> implements Subscriber<T>
{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private Subscription subscription;
    private final List<T> consumedMessages = new LinkedList<>();

    @Override
    public void onSubscribe(Subscription subscription)
    {
        this.subscription=subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(T item)
    {
        consumedMessages.add(item);
        logger.info("Greeting Received: {}", item);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable)
    {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete()
    {
        logger.info("All Messages received");
    }

    public int getConsumedMessages()
    {
        return consumedMessages.size();
    }
}
