package com.threadpool.reactivestreams;

import com.threadpool.model.Greeting;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

public class TestClass
{
    public static void main(String[] args)
    {
        GreetingPublisher<Greeting> greetingPublisher = new GreetingPublisher<>(new SubmissionPublisher<Greeting>());
        GreetingSubscriber<Greeting> greetingSubscriber = new GreetingSubscriber<>();
        greetingPublisher.subscribe(greetingSubscriber);

        List<Greeting> greetingList = new ArrayList<>();
        createGreetings(greetingList);
        greetingList.forEach(greetingPublisher::submitItem);
        greetingPublisher.close();

        greetingSubscriber.onComplete();

    }


    private static void createGreetings(List<Greeting> greetingList)
    {
        for (int i=0;i<10;i++)
        {
            greetingList.add(new Greeting("Message Number: "+i, "Pavan Jadda", LocalDateTime.now()));
        }
    }
}
