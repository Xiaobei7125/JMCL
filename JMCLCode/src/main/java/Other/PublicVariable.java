package Other;


import jsonAnalysis.setup.Setup;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PublicVariable {
    public static final ExecutorService multiThreadedDownloadExecutorService = Executors.newCachedThreadPool();
    public static final ExecutorService executorService = Executors.newFixedThreadPool(Setup.getSetupInstance().download.threads.maxThreadsQuantity);
    public static int threadQuantity = 0;

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
