package com.threadpool;

public class MainClass
{
    public static void main(String[] args)
    {
        MyThread myThread;
        for (int i=0;i<100;i++)
        {
            myThread=new MyThread("Thread"+i);
            myThread.start();
            myThread.getThreadStatus();
        }
    }
}
