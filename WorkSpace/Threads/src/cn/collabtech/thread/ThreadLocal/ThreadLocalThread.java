package cn.collabtech.thread.ThreadLocal;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Darrick_AZ
 * 线程局部变量 
 * 每个线程都有自己的备份数据，不相互影响 
 * 
 * 而volatile关键字,则是各个线程维护者统一变量，保证每次读到都是最新的值。
 * 使用volatile关键字的时候，该变量一旦被修改，会立即写入到主存中，同时会让其他线程的工作内存中的缓存失效，
 * 这样，其他线程在访问该变量的时候会重新从内存中读取可以获得该变量最新的数据，从而保证的变量的可见性。
 */
public class ThreadLocalThread extends Thread
{
    private static AtomicInteger ai = new AtomicInteger();
    
    public ThreadLocalThread(String name)
    {
        super(name);
    }
    
    public void run()
    {
        try
        {
            for (int i = 0; i < 3; i++)
            {
                Tools.t1.set(ai.addAndGet(1) + "");
                System.out.println(this.getName() + " get value--->" + Tools.t1.get());
                Thread.sleep(200);
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}