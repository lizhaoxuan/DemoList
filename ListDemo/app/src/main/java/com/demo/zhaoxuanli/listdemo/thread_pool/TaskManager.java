package com.demo.zhaoxuanli.listdemo.thread_pool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * describe
 * zhaoxuan.li
 * 2015/10/28.
 */
public class TaskManager {
    public static final int SINGLE_THREAD_EXECUTOR = 1;
    public static final int FIXED_THREAD_POOL = 2;
    public static final int CACHED_THREAD_POOL = 3;
    public static final int CUSTOM_THREAD_POOL = 4;






    // 当线程数超过core_pool_size时，超出的线程在等待新任务时的存活时间
    private static final int KEEP_ALIVE_TIME = 60;

    // 设置时间单位
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;;

    // 线程池的核心线程数（初始线程数）
    private static final int CORE_POOL_SIZE = 2;

    // 线程池的最大线程数
    private static final int MAXIMUM_POOL_SIZE = 6;

    //等待执行的任务队列
    private final BlockingQueue<Runnable> mTaskQueue = new PriorityBlockingQueue<Runnable>(4);;

    //线程池
    private ExecutorService mTaskThreadPool ;

    //任务超出队列时的拒绝策略
    private final RejectedExecutionHandler mRejectedHandler = new ThreadPoolExecutor.AbortPolicy();;

    /**
     * 根据kind值去初始化不同类型线程池
     * @param kind
     */
    public TaskManager(int kind){

        switch (kind){
            case SINGLE_THREAD_EXECUTOR:
                mTaskThreadPool = Executors.newSingleThreadExecutor();// 每次只执行一个线程任务的线程池
                break;
            case FIXED_THREAD_POOL:
                mTaskThreadPool = Executors.newFixedThreadPool(3);// 限制线程池大小为7的线程池
                break;
            case CACHED_THREAD_POOL :
                mTaskThreadPool = Executors.newCachedThreadPool(); // 一个没有限制最大线程数的线程池
                break;
            case CUSTOM_THREAD_POOL:
                initCustonPool();
                break;
        }

    }

    private void initCustonPool(){
//        如果此时线程池中的数量小于corePoolSize，即使线程池中的线程都处于空闲状态，也要创建新的线程来处理被添加的任务。
//        如果此时线程池中的数量等于 corePoolSize，但是缓冲队列 workQueue未满，那么任务被放入缓冲队列。
//        如果此时线程池中的数量大于corePoolSize，缓冲队列workQueue满，并且线程池中的数量小于maximumPoolSize，建新的线程来处理被添加的任务。
//        如果此时线程池中的数量大于corePoolSize，缓冲队列workQueue满，并且线程池中的数量等于maximumPoolSize，那么通过 handler所指定的策略来处理此任务。

//        corePoolSize：         核心线程数，会一直存活，即使没有任务，线程池也会维护线程的最少数量
//        maximumPoolSize： 线程池维护线程的最大数量
//        keepAliveTime：      线程池维护线程所允许的空闲时间，当线程空闲时间达到keepAliveTime，该线程会退出，直到线程数量等于corePoolSize。如果allowCoreThreadTimeout设置为true，则所有线程均会退出直到线程数量为0。
//        unit： 线程池维护线程所允许的空闲时间的单位、可选参数值为：TimeUnit中的几个静态属性：NANOSECONDS、MICROSECONDS、MILLISECONDS、SECONDS。
//        workQueue： 线程池所使用的缓冲队列，常用的是：java.util.concurrent.ArrayBlockingQueue、LinkedBlockingQueue、SynchronousQueue
//        handler： 线程池中的数量大于maximumPoolSize，对拒绝任务的处理策略，默认值ThreadPoolExecutor.AbortPolicy()。

        mTaskThreadPool = new ThreadPoolExecutor(CORE_POOL_SIZE,
                MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT,
                mTaskQueue, mRejectedHandler);

        /**
         * ExecutorService 没有allowCoreThreadTimeOut 方法
         * ThreadPoolExecutor 才有
         * 表示，即使没有到达 corePoolSize ，超时的线程也会被终止
         */
        //mTaskThreadPool.allowCoreThreadTimeOut(true);

    }

    /**
     * @param task
     *            放入线程池执行的任务
     */
    public void executeTask(Runnable task) {
        mTaskThreadPool.execute(task);
    }

    /**
     * 终止线程池中的所有任务
     * 警告：终止之后再向线程池提交就会抛异常出来。
     */
    public void terminateAllTask(){
        mTaskThreadPool.shutdownNow();
    }


}
