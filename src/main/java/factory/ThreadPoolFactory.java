package factory;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工厂
 */
public class ThreadPoolFactory {
    private static final int CORE_POOL_SIZE = 6;    //核心线程数
    private static final int MAX_POOL_SIZE = 6;     //最大线程数
    private static final int QUEUE_CAPACITY = 1000; //等待队列长度
    private static final Long KEEP_ALIVE_TIME = 1L; //过期时间

    public static ThreadPoolExecutor getPool(){
        return new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
