public class SetUp {
    static int threadQuantity = 0;
    static int maxThreadsQuantity = 1024;
    static boolean ifMultiThreadedDownloadAFile = true;
    static int multiThreadedDownloadAFileSegmentSize = 5 * 1024 * 1024;
    static int downloadRetries = -1;
    static int downloadConnectTimeout = 1000;
    static int downloadReadTimeout = 1000;
    static boolean ifCheckFileSha1BeforeDownloading = true;
    static boolean ifDownloadAssetIndexCopy = true;
    static boolean ifUseMcbbsDownloadSource = false;
    static boolean ifUseBmclapiDownloadSource = false;
    static boolean ifUseOfficialDownloadSource = false;

    public static void threadWait() {
        for (; ; ) {
            if (SetUp.threadQuantity < SetUp.maxThreadsQuantity || maxThreadsQuantity == 0) {
                threadQuantity++;
                return;
            }
        }
    }
}
