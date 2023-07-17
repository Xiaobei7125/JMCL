package minecraft.download;

import jsonAnalysis.setup.Setup;
import utils.Utils;
import utils.Zip;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Download {

    public static void downloadVersionFile(URL url, String mainPath, String id, VersionFile versionFile) throws IOException {
        String incompletePath = mainPath + "versions\\" + id + "\\";
        String name = id + "." + versionFile;
        if (Setup.getSetupInstance().download.threads.multiThreadedDownload.ifMultiThreadedDownloadAFile) {
            utils.Download.MultiThreadedDownloadAFile(url, new File(incompletePath + name));
        } else {
            utils.Download.downloadAFile(url, new File(incompletePath + name));
        }
    }

    public static boolean downloadNativesDllLibraries(URL url, String mainPath, String id, String path) throws Exception {
        String name = Utils.regexReplace(String.valueOf(url), "[a-zA-Z]+://[\\w\\d./-]+/", "");
        if (Setup.getSetupInstance().download.threads.multiThreadedDownload.ifMultiThreadedDownloadAFile) {
            utils.Download.MultiThreadedDownloadAFile(url, new File(path + "\\natives\\" + name));
        } else {
            utils.Download.downloadAFile(url, new File(path + "\\natives\\" + name));
        }
        System.out.println(path + "\\natives\\" + name + " " + mainPath + "versions\\" + id + "\\natives\\");
        for (; ; ) {
            if (Zip.unzip(path + "\\natives\\" + name, mainPath + "versions\\" + id + "\\natives\\")) break;
        }
        File[] array = new File(mainPath + "versions\\" + id + "\\natives\\").listFiles();
        assert array != null;
        for (File file : array) {
            if (!Utils.regexReplace(file.getName(), "[\\w\\d-.]+\\.", "").equals("dll")) {
                if (file.isDirectory()) {
                    Utils.deleteDirectory(file.getPath());
                }
                if (file.delete()) return false;
            }
        }
        return true;
    }

    public static void downloadOtherLibraries(URL url, String mainPath, String path) throws IOException {
        String name = Utils.regexReplace(String.valueOf(url), "[a-zA-Z]+://[\\w\\d./-]+/", "");
        if (Setup.getSetupInstance().download.threads.multiThreadedDownload.ifMultiThreadedDownloadAFile) {
            utils.Download.MultiThreadedDownloadAFile(url, new File(mainPath + "libraries\\" + path));
        } else {
            utils.Download.downloadAFile(url, new File(mainPath + "libraries\\" + path));
        }
    }

    public static void downloadLog4jFile(URL url, String mainPath, String fileId) throws IOException {
        if (Setup.getSetupInstance().download.threads.multiThreadedDownload.ifMultiThreadedDownloadAFile) {
            utils.Download.MultiThreadedDownloadAFile(url, new File(mainPath + "assets\\log_configs\\" + fileId));
        } else {
            utils.Download.downloadAFile(url, new File(mainPath + "assets\\log_configs\\" + fileId));
        }

    }

    public static void downloadAssetIndexJson(URL url, String mainPath, String id) throws IOException {
        if (Setup.getSetupInstance().download.threads.multiThreadedDownload.ifMultiThreadedDownloadAFile) {
            utils.Download.MultiThreadedDownloadAFile(url, new File(mainPath + "assets\\indexes\\" + id + ".json"));
        } else {
            utils.Download.downloadAFile(url, new File(mainPath + "assets\\indexes\\" + id + ".json"));
        }
    }

    public static void downloadAssetIndexFile(URL url, String mainPath, String hash) throws IOException {
        File file = new File(mainPath + "assets\\objects\\" + hash.substring(0, 2) + "\\" + hash);
        if (Setup.getSetupInstance().download.threads.multiThreadedDownload.ifMultiThreadedDownloadAFile) {
            utils.Download.MultiThreadedDownloadAFile(url, file);
        } else {
            utils.Download.downloadAFile(url, file);
        }
    }

    public static void downloadAssetIndexCopyFile(URL url, String mainPath, String path) throws IOException {
        String name = Utils.regexReplace(path, "[\\w/]+/", "");
        String incompletePath = Utils.regexReplace(path, name, "");
        if (Setup.getSetupInstance().download.threads.multiThreadedDownload.ifMultiThreadedDownloadAFile) {
            utils.Download.MultiThreadedDownloadAFile(url, new File(mainPath + "assets\\virtual\\legacy\\" + path));
        } else {
            utils.Download.downloadAFile(url, new File(mainPath + "assets\\virtual\\legacy\\" + path));
        }
    }

    enum VersionFile {
        jar, json
    }
}
