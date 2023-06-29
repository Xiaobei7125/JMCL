package Other;

import JsonAnalysis.Setup.Setup;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PublicVariable {
    public static ExecutorService multiThreadedDownloadExecutorService = Executors.newCachedThreadPool();

    static int threadQuantity = 0;
    public static ExecutorService executorService = Executors.newFixedThreadPool(Setup.getSetupInstance().download.threads.maxThreadsQuantity);

    public PublicVariable() {
    }

    public static void threadWait() {
        Setup set = Setup.getSetupInstance();
        for (; ; ) {
            if (threadQuantity < set.download.threads.maxThreadsQuantity || set.download.threads.maxThreadsQuantity == 0) {
                threadQuantity++;
                return;
            }
        }
    }

}
