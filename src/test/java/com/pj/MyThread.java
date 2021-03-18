package com.pj;

public class MyThread implements Runnable
{
    private String threadName;
    private Thread t;

    public MyThread(String threadName)
    {
        Thread.currentThread().setName(threadName);
        this.threadName=threadName;
    }


    public void run()
    {
        try
        {
            System.out.println("Thread "+Thread.currentThread().getName()+" is running");
            Thread.sleep(10000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public void start()
    {
        System.out.println("Starting "+threadName);
        if(t == null)
        {
            t=new Thread(this,threadName);
            t.start();
        }
    }

    public void getThreadStatus()
    {
        System.out.println(t.getName()+" status: "+t.getState());
        System.out.println();
    }
}
