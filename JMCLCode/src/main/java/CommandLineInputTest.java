import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

public class CommandLineInputTest {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        OptionParser optionParser = new OptionParser();
        {
            String[] helpOption = {"h", "?", "help"};
            optionParser.acceptsAll(Arrays.asList(helpOption), "Help").withOptionalArg().forHelp();
            String[] downloadOption = {"d", "download"};
            optionParser.acceptsAll(Arrays.asList(downloadOption), "Download");
            {
                String[] mainPathOption = {"p", "mainPath"};
                optionParser.acceptsAll(Arrays.asList(mainPathOption), "Main path").withOptionalArg().ofType(String.class);
                String[] idOption = {"n", "id", "name"};
                optionParser.acceptsAll(Arrays.asList(idOption), "Version id").withOptionalArg().ofType(String.class);
            }
            String[] setOption = {"s", "set", "setup"};
            optionParser.acceptsAll(Arrays.asList(setOption), "Set up").withOptionalArg().ofType(String.class);
            String[] loginOption = {"l", "login"};
            optionParser.acceptsAll(Arrays.asList(loginOption), "Login");
            {
                optionParser.accepts("maxThreadsQuantity").withOptionalArg().ofType(Integer.class);
                optionParser.accepts("ifMultiThreadedDownloadAFile").withOptionalArg().ofType(Boolean.class);
                optionParser.accepts("multiThreadedDownloadAFileSegmentSize").withOptionalArg().ofType(Integer.class);
                optionParser.accepts("downloadRetries").withOptionalArg().ofType(Integer.class);
                optionParser.accepts("downloadConnectTimeout").withOptionalArg().ofType(Integer.class);
                optionParser.accepts("downloadReadTimeout").withOptionalArg().ofType(Integer.class);
                optionParser.accepts("ifCheckFileSha1BeforeDownloading").withOptionalArg().ofType(Boolean.class);
                optionParser.accepts("ifDownloadAssetIndexCopy").withOptionalArg().ofType(Boolean.class);
                optionParser.accepts("ifUseOfficialDownloadSource").withOptionalArg().ofType(Boolean.class);
                optionParser.accepts("ifUseBmclapiDownloadSource").withOptionalArg().ofType(Boolean.class);
                optionParser.accepts("ifUseMcbbsDownloadSource").withOptionalArg().ofType(Boolean.class);
            }
        }
        OptionSet optionSet = optionParser.parse(args);
        if (optionSet.has("d")) {
            MinecraftUtils.downloadMinecraft(new MinecraftAttribute((String) optionSet.valueOf("p"), (String) optionSet.valueOf("id")));
        } else if (optionSet.has("l")) {
            MinecraftUtils.microsoftLogin();
        } else if (optionSet.has("s")) {
            String name;
            if (optionSet.has(name = "maxThreadsQuantity")) {
                SetUp.getInstance().maxThreadsQuantity = (int) optionSet.valueOf(name);
                export(name, optionSet.valueOf(name));
            }
            if (optionSet.has(name = "ifMultiThreadedDownloadAFile")) {
                SetUp.getInstance().ifMultiThreadedDownloadAFile = (boolean) optionSet.valueOf(name);
                export(name, optionSet.valueOf(name));
            }
            if (optionSet.has(name = "multiThreadedDownloadAFileSegmentSize")) {
                SetUp.getInstance().multiThreadedDownloadAFileSegmentSize = (int) optionSet.valueOf(name);
                export(name, optionSet.valueOf(name));
            }
            if (optionSet.has(name = "downloadRetries")) {
                SetUp.getInstance().downloadRetries = (int) optionSet.valueOf(name);
                export(name, optionSet.valueOf(name));
            }
            if (optionSet.has(name = "downloadConnectTimeout")) {
                SetUp.getInstance().downloadConnectTimeout = (int) optionSet.valueOf(name);
                export(name, optionSet.valueOf(name));
            }
            if (optionSet.has(name = "downloadReadTimeout")) {
                SetUp.getInstance().downloadReadTimeout = (int) optionSet.valueOf(name);
                export(name, optionSet.valueOf(name));
            }
            if (optionSet.has(name = "ifCheckFileSha1BeforeDownloading")) {
                SetUp.getInstance().ifCheckFileSha1BeforeDownloading = (boolean) optionSet.valueOf(name);
                export(name, optionSet.valueOf(name));
            }
            if (optionSet.has(name = "ifDownloadAssetIndexCopy")) {
                SetUp.getInstance().ifDownloadAssetIndexCopy = (boolean) optionSet.valueOf(name);
                export(name, optionSet.valueOf(name));
            }
            if (optionSet.has(name = "ifUseOfficialDownloadSource")) {
                SetUp.getInstance().ifUseOfficialDownloadSource = (boolean) optionSet.valueOf(name);
                export(name, optionSet.valueOf(name));
            }
            if (optionSet.has(name = "ifUseBmclapiDownloadSource")) {
                SetUp.getInstance().ifUseBmclapiDownloadSource = (boolean) optionSet.valueOf(name);
                export(name, optionSet.valueOf(name));
            }
            if (optionSet.has(name = "ifUseMcbbsDownloadSource")) {
                SetUp.getInstance().ifUseMcbbsDownloadSource = (boolean) optionSet.valueOf(name);
                export(name, optionSet.valueOf(name));
            }
        }

    }

    public static <T> void export(String name, T to) {
        System.out.println("set " + name + " to " + to + " success");
    }
}
