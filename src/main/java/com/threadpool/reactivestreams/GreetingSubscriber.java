package com.threadpool.reactivestreams;


import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Flow.*;

import com.threadpool.model.Greeting;


public class GreetingSubscriber<T> implements Subscriber<T>
{
    private Subscription subscription;
    private List<T> consumedMessages=new LinkedList<>();

    @Override
    public void onSubscribe(Subscription subscription)
    {
        this.subscription=subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(T item)
    {
        System.out.println("Item Received: "+item.toString());
        Greeting greeting= (Greeting) item;
        System.out.println(greeting.toString());
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
        System.out.println("All Messages received");
    }
}
