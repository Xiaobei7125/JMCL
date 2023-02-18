import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PublicVariable {
    static int threadQuantity = 0;
    static ExecutorService multiThreadedDownloadExecutorService = Executors.newCachedThreadPool();

    static ExecutorService executorService = Executors.newFixedThreadPool(SetUp.getInstance().download.threads.maxThreadsQuantity);

    public static void threadWait() {
        SetUp set = SetUp.getInstance();
        for (; ; ) {
            if (threadQuantity < set.download.threads.maxThreadsQuantity || set.download.threads.maxThreadsQuantity == 0) {
                threadQuantity++;
                return;
            }
        }
    }

}
