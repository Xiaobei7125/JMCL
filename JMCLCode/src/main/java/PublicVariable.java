import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PublicVariable {
    static ExecutorService multiThreadedDownloadExecutorService =  Executors.newCachedThreadPool();

    static ExecutorService executorService = Executors.newFixedThreadPool(SetUp.getInstance().maxThreadsQuantity);
}
