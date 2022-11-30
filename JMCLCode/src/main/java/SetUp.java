public class SetUp {
    static int threadQuantity = 0;
    static int maxThreadsQuantity = 1;
    static boolean ifMultiThreadedDownloadAFile = true;
    static int multiThreadedDownloadAFileSegmentSize = 5*1024*1024;
    public static void threadWait(){
        for (; ; ) {
            if (SetUp.threadQuantity < SetUp.maxThreadsQuantity || maxThreadsQuantity == 0) {
                threadQuantity++;
                return;
            }
        }
    }
}
