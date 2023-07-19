import joptsimple.OptionParser;
import joptsimple.OptionSet;
import jsonAnalysis.setup.Setup;
import minecraft.Utils;
import minecraft.information.Attribute;

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
            String[] startOption = {"st", "start"};
            optionParser.acceptsAll(Arrays.asList(startOption), "Start minecraft");
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
            Utils.downloadMinecraft(new Attribute((String) optionSet.valueOf("p"), (String) optionSet.valueOf("id")));
        } else if (optionSet.has("l")) {
            Utils.microsoftLogin();
        } else if (optionSet.has("st")) {
            Utils.startMinecraft(new Attribute((String) optionSet.valueOf("p"), (String) optionSet.valueOf("id")));
        } else if (optionSet.has("s")) {
            String name;
            if (optionSet.has(name = "maxThreadsQuantity")) {
                Setup.getSetupInstance().download.threads.maxThreadsQuantity = (int) optionSet.valueOf(name);
                export(name, optionSet.valueOf(name));
            }
            if (optionSet.has(name = "ifMultiThreadedDownloadAFile")) {
                Setup.getSetupInstance().download.threads.multiThreadedDownload.ifMultiThreadedDownloadAFile = (boolean) optionSet.valueOf(name);
                export(name, optionSet.valueOf(name));
            }
            if (optionSet.has(name = "multiThreadedDownloadAFileSegmentSize")) {
                Setup.getSetupInstance().download.threads.multiThreadedDownload.multiThreadedDownloadAFileSegmentSize = (int) optionSet.valueOf(name);
                export(name, optionSet.valueOf(name));
            }
            if (optionSet.has(name = "downloadRetries")) {
                Setup.getSetupInstance().download.downloadRetries = (int) optionSet.valueOf(name);
                export(name, optionSet.valueOf(name));
            }
            if (optionSet.has(name = "downloadConnectTimeout")) {
                Setup.getSetupInstance().download.downloadConnectTimeout = (int) optionSet.valueOf(name);
                export(name, optionSet.valueOf(name));
            }
            if (optionSet.has(name = "downloadReadTimeout")) {
                Setup.getSetupInstance().download.downloadReadTimeout = (int) optionSet.valueOf(name);
                export(name, optionSet.valueOf(name));
            }
            if (optionSet.has(name = "ifCheckFileSha1BeforeDownloading")) {
                Setup.getSetupInstance().download.ifCheckFileSha1BeforeDownloading = (boolean) optionSet.valueOf(name);
                export(name, optionSet.valueOf(name));
            }
            if (optionSet.has(name = "ifDownloadAssetIndexCopy")) {
                Setup.getSetupInstance().download.ifDownloadAssetIndexCopy = (boolean) optionSet.valueOf(name);
                export(name, optionSet.valueOf(name));
            }
            if (optionSet.has(name = "ifUseOfficialDownloadSource")) {
                Setup.getSetupInstance().download.source.ifUseOfficialDownloadSource = (boolean) optionSet.valueOf(name);
                export(name, optionSet.valueOf(name));
            }
            if (optionSet.has(name = "ifUseBmclapiDownloadSource")) {
                Setup.getSetupInstance().download.source.ifUseBmclapiDownloadSource = (boolean) optionSet.valueOf(name);
                export(name, optionSet.valueOf(name));
            }
            if (optionSet.has(name = "ifUseMcbbsDownloadSource")) {
                Setup.getSetupInstance().download.source.ifUseMcbbsDownloadSource = (boolean) optionSet.valueOf(name);
                export(name, optionSet.valueOf(name));
            }
        }

    }

    public static <T> void export(String name, T to) {
        System.out.println("set " + name + " to " + to + " success");
    }
}
