package com.threadpool;

import com.threadpool.model.Greeting;
import com.threadpool.reactivestreams.GreetingSubscriber;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.SubmissionPublisher;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ThreadPoolingApplicationTests
{

    @Test
    public void contextLoads()
    {
    }

    @Test
    public void testReactiveStreams()
    {
        SubmissionPublisher<Greeting> greetingPublisher = new SubmissionPublisher<>();
        GreetingSubscriber<Greeting> greetingSubscriber = new GreetingSubscriber<>();
        greetingPublisher.subscribe(greetingSubscriber);

        List<Greeting> greetingList = new ArrayList<>();
        createGreetings(greetingList);
        greetingList.forEach(greetingPublisher::submit);

        //Wait until messages sent
        while (greetingList.size() != greetingSubscriber.getConsumedMessages())
        {
            try
            {
                Thread.sleep(10);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

    }



    private void createGreetings(List<Greeting> greetingList)
    {
        for (int i=0;i<10;i++)
        {
            greetingList.add(new Greeting("Message Number: "+i, "Pavan Jadda", LocalDateTime.now()));
        }
    }
}
