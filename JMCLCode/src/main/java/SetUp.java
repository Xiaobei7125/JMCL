public class SetUp {
    static int threadQuantity = 0;
    static int maxThreadsQuantity = 1024;
    static boolean ifMultiThreadedDownloadAFile = true;
    static int multiThreadedDownloadAFileSegmentSize = 5*1024*1024;
    static int downloadRetries = 100;
    static int downloadConnectTimeout = 1000;
    static int downloadReadTimeout = 1000;
    static boolean ifCheckFileSha1BeforeDownloading = false;
    public static void threadWait(){
        for (; ; ) {
            if (SetUp.threadQuantity < SetUp.maxThreadsQuantity || maxThreadsQuantity == 0) {
                threadQuantity++;
                return;
            }
        }
    }
}
