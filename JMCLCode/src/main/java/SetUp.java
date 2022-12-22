import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SetUp {
    private static SetUp set = new SetUp();

    static {
        try {
            String contents = new String(Utils.readToString("JMCL.properties"), StandardCharsets.UTF_8);
            set = new Gson().fromJson(contents, SetUp.class);
        } catch (IOException ignored) {
            try {
                Utils.writeToString("JMCL.properties"
                        , new GsonBuilder().setPrettyPrinting().create().toJson(getInstance()));
            } catch (IOException ignored1) {
            }
        }
    }

    int threadQuantity = 0;
    int maxThreadsQuantity = 1024;
    boolean ifMultiThreadedDownloadAFile = true;
    int multiThreadedDownloadAFileSegmentSize = 5 * 1024 * 1024;
    int downloadRetries = -1;
    int downloadConnectTimeout = 1000;
    int downloadReadTimeout = 1000;
    boolean ifCheckFileSha1BeforeDownloading = true;
    boolean ifDownloadAssetIndexCopy = true;
    boolean ifUseMcbbsDownloadSource = false;
    boolean ifUseBmclapiDownloadSource = true;
    boolean ifUseOfficialDownloadSource = true;

    SetUp() {
    }

    ;

    public static SetUp getInstance() {
        return set;
    }

    public static void threadWait() {
        SetUp set = getInstance();
        for (; ; ) {
            if (set.threadQuantity < set.maxThreadsQuantity || set.maxThreadsQuantity == 0) {
                set.threadQuantity++;
                return;
            }
        }
    }
}
