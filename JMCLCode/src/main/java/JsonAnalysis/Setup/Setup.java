package JsonAnalysis.Setup;

import Utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Setup {
    private static Setup set = new Setup();

    static {
        try {
            String contents = new String(Utils.readToString("JMCL.properties"), StandardCharsets.UTF_8);
            set = new Gson().fromJson(contents, Setup.class);
        } catch (IOException ignored) {
            try {
                Utils.writeToString("JMCL.properties"
                        , new GsonBuilder().setPrettyPrinting().create().toJson(getSetupInstance()));
            } catch (IOException ignored1) {
            }
        }
    }

    public Download download = new Download();

    public Setup() {
    }

    public static Setup getSetupInstance() {
        return set;
    }

    public class Download {
        public Threads threads = new Threads();
        public Source source = new Source();
        public int downloadRetries = -1;
        public int downloadConnectTimeout = 1000;
        public int downloadReadTimeout = 1000;
        public boolean ifCheckFileSha1BeforeDownloading = true;
        public boolean ifDownloadAssetIndexCopy = true;

        public class Threads {
            public MultiThreadedDownload multiThreadedDownload = new MultiThreadedDownload();
            public int maxThreadsQuantity = 1024;

            public class MultiThreadedDownload {
                public boolean ifMultiThreadedDownloadAFile = true;
                public int multiThreadedDownloadAFileSegmentSize = 5 * 1024 * 1024;
            }
        }

        public class Source {
            public boolean ifUseMcbbsDownloadSource = false;
            public boolean ifUseBmclapiDownloadSource = true;
            public boolean ifUseOfficialDownloadSource = true;
        }
    }
}
