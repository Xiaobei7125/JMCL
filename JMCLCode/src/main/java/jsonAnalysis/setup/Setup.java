package jsonAnalysis.setup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import utils.Utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Setup {
    private static Setup set = new Setup();

    static {
        try {
            String contents = new String(Utils.readToString("JMCL.properties"), StandardCharsets.UTF_8);
            set = new Gson().fromJson(contents, Setup.class);
            if (set == null) {
                throw new IOException();
            }
        } catch (IOException ignored) {
            try {
                byte[] setup = Utils.writeToString("JMCL.properties"
                        , new GsonBuilder().setPrettyPrinting().create().toJson(getSetupInstance()));
            } catch (IOException ignored1) {
            }
        }
    }

    public final Download download = new Download();

    public Setup() {
    }

    public static Setup getSetupInstance() {
        return set;
    }

    public static class Download {
        public final Threads threads = new Threads();
        public final Source source = new Source();
        public int downloadRetries = -1;
        public int downloadConnectTimeout = 1000;
        public int downloadReadTimeout = 1000;
        public boolean ifCheckFileBeforeDownloading = true;
        public boolean ifDownloadAssetIndexCopy = true;
        public boolean ifUsesNewURLDownloadingVersionJson = true;

        public static class Threads {
            public final MultiThreadedDownload multiThreadedDownload = new MultiThreadedDownload();
            public int maxThreadsQuantity = 1024;

            public static class MultiThreadedDownload {
                public boolean ifMultiThreadedDownloadAFile = false;
                public int multiThreadedDownloadAFileSegmentSize = 5 * 1024;
            }
        }

        public static class Source {
            public boolean ifUseMcbbsDownloadSource = true;
            public boolean ifUseBmclapiDownloadSource = true;
            public boolean ifUseOfficialDownloadSource = true;
        }
    }
}
