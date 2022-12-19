public class SetUp {
    static int threadQuantity = 0;
    static int maxThreadsQuantity = 1;
    static boolean ifMultiThreadedDownloadAFile = true;
    static int multiThreadedDownloadAFileSegmentSize = 1*1024*1024;
    static int downloadRetries = 10;
    static int downloadConnectTimeout = 1000;
    static int downloadReadTimeout = 1000;
    public static void threadWait(){
        for (; ; ) {
            if (SetUp.threadQuantity < SetUp.maxThreadsQuantity || maxThreadsQuantity == 0) {
                threadQuantity++;
                return;
            }
        }
    }
}
