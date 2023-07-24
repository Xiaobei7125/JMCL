package other;


import jsonProcessing.setup.Setup;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PublicVariable {
    public static final ExecutorService multiThreadedDownloadExecutorService = Executors.newCachedThreadPool();
    public static final ExecutorService executorService = Executors.newFixedThreadPool(Setup.getSetupInstance().download.threads.maxThreadsQuantity);
    public static int threadQuantity = 0;
    public static String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
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
