package minecraft.download;


import jsonAnalysis.download.minecraft.library.VersionJson;
import jsonAnalysis.download.minecraft.library.VersionManifest;
import jsonAnalysis.setup.Setup;
import minecraft.information.Attribute;
import minecraft.information.DownloadSource;
import minecraft.information.VersionFileType;
import other.IThreadManagement;
import other.Output;
import other.PublicVariable;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


public class DownloadsUtils {
    public static int end = 0;
    public static int error = 0;

    private static void downloadFile(URL url, DownloadSource downloadSource, File file) throws Exception {
        if (downloadSource.getIfUse()) {
            if (Setup.getSetupInstance().download.threads.multiThreadedDownload.ifMultiThreadedDownloadAFile) {
                utils.Download.MultiThreadedDownloadAFile(url, file);
            } else {
                utils.Download.downloadAFile(url, file);
            }
        } else {
            throw new Exception();
        }
    }


    private static void downloadVersionFile(VersionJson VersionJson, DownloadSource downloadSource, Attribute Attribute) throws Exception {
        if (downloadSource.getIfUse()) {
            Download.downloadVersionFile(Url.versionJarFileURL(VersionJson, downloadSource), Attribute.getMainPath(), Attribute.getId(), VersionFileType.jar);
        } else {
            throw new Exception();
        }
    }

    private static void downloadVersionJson(VersionManifest VersionManifest, DownloadSource downloadSource, Attribute Attribute) throws Exception {
        if (downloadSource.getIfUse()) {
            Download.downloadVersionFile(Url.versionJsonFileURL(VersionManifest, Attribute.getId(), downloadSource), Attribute.getMainPath(), Attribute.getId(), VersionFileType.json);
        } else {
            throw new Exception();
        }
    }

    private static void downloadNativesDllLibraries(VersionJson VersionJson, DownloadSource downloadSource, Attribute Attribute, int i) throws Exception {
        if (downloadSource.getIfUse()) {
            Download.downloadNativesDllLibraries(Url.nativesJarURL(VersionJson, downloadSource, i), Attribute.getMainPath(), Attribute.getId(), Attribute.getRunPath());
        } else {
            throw new Exception();
        }
    }

    private static void downloadOtherJarLibraries(VersionJson VersionJson, DownloadSource downloadSource, Attribute Attribute, int i) throws Exception {
        if (downloadSource.getIfUse()) {
            Download.downloadOtherLibraries(Url.otherJarLibrariesURL(VersionJson, downloadSource, i), Attribute.getMainPath(), VersionJson.getLibraries()[i].getDownloads().getArtifact().getPath());
        } else {
            throw new Exception();
        }
    }

    private static void downloadLog4jFile(VersionJson VersionJson, DownloadSource downloadSource, Attribute Attribute) throws Exception {
        if (downloadSource.getIfUse()) {
            Download.downloadLog4jFile(Url.Log4jFileURL(VersionJson, downloadSource), Attribute.getMainPath(), VersionJson.getLogging().getClient().getFile().getId());
        } else {
            throw new Exception();
        }
    }

    private static void downloadAssetIndexJson(VersionJson VersionJson, DownloadSource downloadSource, Attribute Attribute) throws Exception {
        if (downloadSource.getIfUse()) {
            Download.downloadAssetIndexJson(Url.assetIndexJsonURL(VersionJson, downloadSource), Attribute.getMainPath(), VersionJson.getAssetIndex().getId());
        } else {
            throw new Exception();
        }
    }

    private static void downloadAssetIndexFile(Attribute Attribute, DownloadSource downloadSource, String hash) throws Exception {
        if (downloadSource.getIfUse()) {
            Download.downloadAssetIndexFile(Url.assetIndexFileURL(hash, downloadSource), Attribute.getMainPath(), hash);
        } else {
            throw new Exception();
        }
    }

    private static void downloadAssetIndexCopyFile(Attribute Attribute, DownloadSource downloadSource, String path, String hash) throws Exception {
        if (downloadSource.getIfUse()) {
            Download.downloadAssetIndexCopyFile(Url.assetIndexFileURL(hash, downloadSource), Attribute.getMainPath(), path);
        } else {
            throw new Exception();
        }
    }

    public static void downloadsVersionFileUtils(VersionJson VersionJson, Attribute Attribute) throws MalformedURLException {
        String name = Attribute.getId() + "." + VersionFileType.jar;
        //下载路径
        String path = Attribute.getMainPath() + "versions\\" + Attribute.getId() + "\\";
        //从下载处获得的sha1
        String standardSha1 = VersionJson.getDownloads().getClient().getSha1();
        int standardSize = VersionJson.getDownloads().getClient().getSize();
        File file = new File(path + name);
        downloadUtils(UrlArray.versionJarFileURL(VersionJson), "DL-VF", standardSha1, file, standardSize);
//        IThreadManagement iThreadManagement = () -> {
//            String name = Attribute.getId() + "." + Download.VersionFile.jar;
//            //下载路径
//            String path = Attribute.getMainPath() + "versions\\" + Attribute.getId() + "\\";
//            //从下载处获得的sha1
//            String standardSha1 = VersionJson.getDownloads().getClient().getSha1();
//            int standardSize = VersionJson.getDownloads().getClient().getSize();
//            File file = new File(path + name);
//            AtomicBoolean r = new AtomicBoolean();
//            PublicVariable.executorService.execute(() -> {
//                try {
//                    if (Objects.equals(standardSha1, Utils.fileSha1(file)) && standardSize == file.length() && Setup.getSetupInstance().download.ifCheckFileSha1BeforeDownloading) {
//                        System.out.println("DL-VF '" + name + "' File already exists and SHA-1 is the same");
//                        end++;
//                        PublicVariable.threadQuantity--;
//                        r.set(true);
//                        return;
//                    } else {
//                        for (int j = 0; j != Setup.getSetupInstance().download.downloadRetries; j++) {
//                            try {
//                                System.out.println("DL-VF-0-" + j + " downloading '" + name + "'");
//                                try {
//                                    downloadVersionFile(VersionJson, Url.DownloadSource.official, Attribute);
//                                } catch (Exception e) {
//                                    try {
//                                        downloadVersionFile(VersionJson, Url.DownloadSource.bmclapi, Attribute);
//                                    } catch (Exception f) {
//                                        downloadVersionFile(VersionJson, Url.DownloadSource.mcbbs, Attribute);
//                                    }
//                                }
//                                System.out.println(standardSha1 + "," + Utils.fileSha1(file));
//                                System.out.println(VersionJson.getDownloads().getClient().getSize() + "," + file.length());
//                                if (Objects.equals(standardSha1, Utils.fileSha1(file)) & file.length() == standardSize) {
//                                    System.out.println("DL-VF" + " Download '" + name + "' end");
//                                    end++;
//                                    PublicVariable.threadQuantity--;
//                                    r.set(true);
//                                    return;
//                                }
//                            } catch (Exception e) {
//                                System.out.println("DL-VF-0-" + j + " Download '" + name + "' error");
//                            }
//                        }
//                    }
//                    System.out.println("DL-VF-0" + " Download '" + name + "' error");
//                    error++;
//                    PublicVariable.threadQuantity--;
//                } catch (NoSuchAlgorithmException | IOException ignored) {
//                }
//            });
//            return r.get();
//        };
//        iThreadManagement.run();
    }

    public static void downloadsVersionJsonUtils(VersionManifest VersionManifest, Attribute Attribute) throws MalformedURLException {
        String name = Attribute.getId() + "." + VersionFileType.json;
        String path = Attribute.getMainPath() + "versions\\" + Attribute.getId() + "\\";
        String standardSha1 = VersionManifest.getSha1(Attribute.getId());
        File file = new File(path + name);
        downloadUtils(UrlArray.versionJsonFileURL(VersionManifest, Attribute.getId()), "DL-VJ", standardSha1, file, 0);
//        IThreadManagement iThreadManagement = () -> {
//            String name = Attribute.getId() + "." + Download.VersionFile.json;
//            String path = Attribute.getMainPath() + "versions\\" + Attribute.getId() + "\\";
//            String standardSha1 = VersionManifest.getSha1(Attribute.getId());
//            File file = new File(path + name);
//            AtomicBoolean r = new AtomicBoolean();
//            PublicVariable.executorService.execute(() -> {
//                try {
//                    if (Objects.equals(standardSha1, Utils.fileSha1(file)) && Setup.getSetupInstance().download.ifCheckFileSha1BeforeDownloading) {
//                        System.out.println("DL-VJ '" + name + "' File already exists and SHA-1 is the same");
//                        end++;
//                        PublicVariable.threadQuantity--;
//                        r.set(true);
//                        return;
//                    } else {
//                        for (int j = 0; j != Setup.getSetupInstance().download.downloadRetries; j++) {
//                            try {
//                                System.out.println("DL-VJ-0-" + j + " downloading '" + name + "'");
//                                try {
//                                    downloadVersionJson(VersionManifest, Url.DownloadSource.official, Attribute);
//                                } catch (Exception e) {
//                                    try {
//                                        downloadVersionJson(VersionManifest, Url.DownloadSource.bmclapi, Attribute);
//                                    } catch (Exception f) {
//                                        downloadVersionJson(VersionManifest, Url.DownloadSource.mcbbs, Attribute);
//                                    }
//                                }
//                                if (Objects.equals(standardSha1, Utils.fileSha1(file))) {
//                                    System.out.println("DL-VJ" + " Download '" + name + "' end");
//                                    end++;
//                                    PublicVariable.threadQuantity--;
//                                    r.set(true);
//                                    return;
//                                }
//                            } catch (Exception e) {
//                                System.out.println("DL-VJ-" + j + " Download '" + name + "' error");
//                            }
//                        }
//                    }
//                } catch (NoSuchAlgorithmException | IOException ignored) {
//                }
//                System.out.println("DL-VJ" + " Download '" + name + "' error");
//                error++;
//                PublicVariable.threadQuantity--;
//            });
//            return r.get();
//        };
//        iThreadManagement.run();
    }

    public static void downloadLog4jFileUtils(VersionJson VersionJson, Attribute Attribute) throws MalformedURLException {
        String name = VersionJson.getLogging().getClient().getFile().getId();
        String path = Attribute.getMainPath() + "assets\\log_configs\\";
        String standardSha1 = VersionJson.getLogging().getClient().getFile().getSha1();
        int standardSize = VersionJson.getLogging().getClient().getFile().getSize();
        File file = new File(path + name);
        downloadUtils(UrlArray.Log4jFileURL(VersionJson), "DL-LF", standardSha1, file, standardSize);
//        IThreadManagement iThreadManagement = () -> {
//            if (VersionJson.getLogging() == null) {
//                return false;
//            }
//            String name = VersionJson.getLogging().getClient().getFile().getId();
//            String path = Attribute.getMainPath() + "assets\\log_configs\\";
//            String standardSha1 = VersionJson.getLogging().getClient().getFile().getSha1();
//            int standardSize = VersionJson.getLogging().getClient().getFile().getSize();
//            File file = new File(path + name);
//            AtomicBoolean r = new AtomicBoolean();
//            PublicVariable.executorService.execute(() -> {
//                try {
//                    if (Objects.equals(standardSha1, Utils.fileSha1(new File(path + name))) && standardSize == file.length() && Setup.getSetupInstance().download.ifCheckFileSha1BeforeDownloading) {
//                        System.out.println("DL-LF '" + name + "' File already exists and SHA-1 is the same");
//                        end++;
//                        PublicVariable.threadQuantity--;
//                        r.set(true);
//                        return;
//                    } else {
//                        for (int j = 0; j != Setup.getSetupInstance().download.downloadRetries; j++) {
//                            try {
//                                System.out.println("DL-LF-" + j + " downloading: '" + name + "'");
//                                try {
//                                    downloadLog4jFile(VersionJson, Url.DownloadSource.official, Attribute);
//                                } catch (Exception e) {
//                                    try {
//                                        downloadLog4jFile(VersionJson, Url.DownloadSource.bmclapi, Attribute);
//                                    } catch (Exception f) {
//                                        downloadLog4jFile(VersionJson, Url.DownloadSource.mcbbs, Attribute);
//                                    }
//                                }
//                                if (Objects.equals(standardSha1, Utils.fileSha1(file)) && standardSize == file.length() && Setup.getSetupInstance().download.ifCheckFileSha1BeforeDownloading) {
//                                    System.out.println("DL-LF" + " Download '" + name + "' end");
//                                    end++;
//                                    PublicVariable.threadQuantity--;
//                                    r.set(true);
//                                    return;
//                                }
//                            } catch (Exception e) {
//                                System.out.println("DL-LF-" + j + " Download '" + name + "' error");
//                            }
//                        }
//
//                    }
//                } catch (NoSuchAlgorithmException | IOException ignored) {
//                }
//                System.out.println("DL-LF" + " Download '" + name + "' error");
//                error++;
//                PublicVariable.threadQuantity--;
//            });
//            return r.get();
//        };
//        iThreadManagement.run();
    }

    public static void downloadAssetIndexJsonUtils(VersionJson VersionJson, Attribute Attribute) throws MalformedURLException {
        String name = VersionJson.getAssetIndex().getId() + ".json";
        String path = Attribute.getMainPath() + "assets\\indexes\\";
        String standardSha1 = VersionJson.getAssetIndex().getSha1();
        int standardSize = VersionJson.getAssetIndex().getSize();
        File file = new File(path + name);
        downloadUtils(UrlArray.assetIndexJsonURL(VersionJson), "DL-AJ", standardSha1, file, standardSize);
//        IThreadManagement iThreadManagement = () -> {
//            String name = VersionJson.getAssetIndex().getId() + ".json";
//            String path = Attribute.getMainPath() + "assets\\indexes\\";
//            String standardSha1 = VersionJson.getAssetIndex().getSha1();
//            int standardSize = VersionJson.getAssetIndex().getSize();
//            File file = new File(path + name);
//            AtomicBoolean r = new AtomicBoolean();
//            PublicVariable.executorService.execute(() -> {
//                try {
//                    if (Objects.equals(standardSha1, Utils.fileSha1(file)) && standardSize == file.length() && Setup.getSetupInstance().download.ifCheckFileSha1BeforeDownloading) {
//                        System.out.println("DL-AJ '" + name + "' File already exists and SHA-1 is the same");
//                        end++;
//                        PublicVariable.threadQuantity--;
//                        r.set(true);
//                        return;
//                    } else {
//                        for (int j = 0; j != Setup.getSetupInstance().download.downloadRetries; j++) {
//                            try {
//                                System.out.println("DL-AJ-" + j + " downloading '" + name + "'");
//                                try {
//                                    downloadAssetIndexJson(VersionJson, Url.DownloadSource.official, Attribute);
//                                } catch (Exception e) {
//                                    try {
//                                        downloadAssetIndexJson(VersionJson, Url.DownloadSource.bmclapi, Attribute);
//                                    } catch (Exception f) {
//                                        downloadAssetIndexJson(VersionJson, Url.DownloadSource.mcbbs, Attribute);
//                                    }
//                                }
//                                System.out.println(standardSha1 + "," + Utils.fileSha1(file));
//                                if (Objects.equals(standardSha1, Utils.fileSha1(file)) & file.length() == standardSize) {
//                                    System.out.println("DL-AJ" + " Download '" + name + "' end");
//                                    end++;
//                                    PublicVariable.threadQuantity--;
//                                    r.set(true);
//                                    return;
//                                }
//                            } catch (Exception e) {
//                                System.out.println("DL-AJ-" + j + " Download '" + name + "' error");
//                            }
//                        }
//
//                    }
//                } catch (NoSuchAlgorithmException | IOException ignored) {
//                }
//                System.out.println("DL-AJ" + " Download '" + name + "' error");
//                error++;
//                PublicVariable.threadQuantity--;
//            });
//            return r.get();
//        };
//        iThreadManagement.run();
    }

    public static void downloadsLibrariesUtils(VersionJson VersionJson, Attribute Attribute) throws Exception {
        AtomicInteger DLEnd = new AtomicInteger();
        AtomicInteger DLError = new AtomicInteger();
        AtomicInteger DLAdd = new AtomicInteger();
        int sumOfDL = 0;
        for (int i = 0; i < VersionJson.getLibraries().length; i++) {
            if ((VersionJson.getLibraries()[i].getDownloads().getClassifiers() != null &&
                    VersionJson.getLibraries()[i].getDownloads().getClassifiers().getNativesWindows() != null)
                    || VersionJson.getLibraries()[i].getDownloads().getClassifiers() == null) sumOfDL++;
        }
        for (int i = 0; i < VersionJson.getLibraries().length; i++) {
            int finalI = i;
            if (VersionJson.getLibraries()[i].getDownloads().getClassifiers() != null &&
                    VersionJson.getLibraries()[i].getDownloads().getClassifiers().getNativesWindows() != null) {
                String name = new File(Url.nativesJarURL(VersionJson, DownloadSource.official, i).getPath()).getName();
                int finalSumOfDL = sumOfDL;
                IThreadManagement iThreadManagement = () -> {
                    AtomicBoolean r = new AtomicBoolean();
                    PublicVariable.executorService.execute(() -> {
                        for (int j = 0; j != Setup.getSetupInstance().download.downloadRetries; j++) {
                            try {
                                System.out.println("DL-ND-" + finalI + "-" + j + " downloading '" + name + "'");
                                try {
                                    downloadNativesDllLibraries(VersionJson, DownloadSource.official, Attribute, finalI);
                                } catch (Exception e) {
                                    try {
                                        downloadNativesDllLibraries(VersionJson, DownloadSource.bmclapi, Attribute, finalI);
                                    } catch (Exception f) {
                                        downloadNativesDllLibraries(VersionJson, DownloadSource.mcbbs, Attribute, finalI);
                                    }
                                }
                                System.out.println("DL-ND-" + finalI + " Download '" + name + "' end");
                                count("DL-ND", DLEnd, DLError, DLAdd, finalSumOfDL, r, true);
                                PublicVariable.threadQuantity--;
                                r.set(true);
                                return;
                            } catch (Exception e) {
                                System.out.println("DL-ND-" + finalI + "-" + j + " Download '" + name + "' error");
                            }
                        }
                        System.out.println("DL-ND-" + finalI + " Download '" + name + "' error");
                        count("DL-ND", DLEnd, DLError, DLAdd, finalSumOfDL, r, false);
                    });
                    return r.get();
                };
                iThreadManagement.run();
            } else if (VersionJson.getLibraries()[i].getDownloads().getClassifiers() == null) {
                String name = new File(Url.otherJarLibrariesURL(VersionJson, DownloadSource.official, i).getPath()).getName();
                String path = Attribute.getMainPath() + "libraries\\" + Utils.regexReplace(VersionJson.getLibraries()[finalI].getDownloads().getArtifact().getPath(), name, "");
                String standardSha1 = VersionJson.getLibraries()[finalI].getDownloads().getArtifact().getSha1();
                int standardSize = VersionJson.getLibraries()[finalI].getDownloads().getArtifact().getSize();
                File file = new File(path + name);
                int finalSumOfDL = sumOfDL;
                IThreadManagement iThreadManagement = () -> {
                    AtomicBoolean r = new AtomicBoolean();
                    PublicVariable.executorService.execute(() -> {
                        try {
                            if (Objects.equals(standardSha1, Utils.fileSha1(file)) && standardSize == file.length() && Setup.getSetupInstance().download.ifCheckFileSha1BeforeDownloading) {
                                System.out.println("DL-OJ '" + name + "' File already exists and SHA-1 is the same");
                                count("DL-OJ", DLEnd, DLError, DLAdd, finalSumOfDL, r, true);
                                return;
                            } else {
                                for (int j = 0; j != Setup.getSetupInstance().download.downloadRetries; j++) {
                                    try {
                                        System.out.println("DL-OJ-" + finalI + "-" + j + " downloading '" + name + "'");
                                        try {
                                            downloadOtherJarLibraries(VersionJson, DownloadSource.official, Attribute, finalI);
                                        } catch (Exception e) {
                                            try {
                                                downloadOtherJarLibraries(VersionJson, DownloadSource.bmclapi, Attribute, finalI);
                                            } catch (Exception f) {
                                                downloadOtherJarLibraries(VersionJson, DownloadSource.mcbbs, Attribute, finalI);
                                            }
                                        }
                                        if (Objects.equals(standardSha1, Utils.fileSha1(file)) & file.length() == standardSize) {
                                            System.out.println("DL-OJ-" + finalI + " Download '" + name + "' end");
                                            count("DL-OJ", DLEnd, DLError, DLAdd, finalSumOfDL, r, true);
                                            return;
                                        }
                                    } catch (Exception e) {
                                        System.out.println("DL-OJ-" + finalI + "-" + j + " Download '" + name + "' error");
                                    }
                                }
                            }
                        } catch (NoSuchAlgorithmException | IOException ignored) {
                        }
                        System.out.println("DL-OJ-" + finalI + " Download '" + name + "' error");
                        count("DL-OJ", DLEnd, DLError, DLAdd, finalSumOfDL, r, false);
                    });
                    return r.get();
                };
                iThreadManagement.run();
            }
        }
    }

    private static synchronized void count(String ThreadName, AtomicInteger End, AtomicInteger Error, AtomicInteger Add, int totalQuantity, AtomicBoolean r, boolean ifSucceed) {
        if (ifSucceed) {
            end++;
            End.addAndGet(1);
        } else {
            error++;
            Error.addAndGet(1);
        }
        Add.set(End.get() + Error.get());
        Output.output(Output.OutputLevel.Debug, ThreadName, End + "/" + Add + "/" + totalQuantity);
        PublicVariable.threadQuantity--;
        r.set(ifSucceed);
    }

    public static void downloadsAssetIndexUtils(VersionJson VersionJson, Attribute Attribute) throws IOException {
        String b = String.valueOf(Utils.deleteSymbol(Request.getMinecraftVersionAssetIndexJson(VersionJson), "{"));
        String[] hashArray = Utils.regexMatching(b, "\\w{40}");
        String[] pathArray = Utils.regexMatching(b, "[\\w/]+[.]{1}\\w+");
        String[] sizeArray = Utils.regexMatching(b, "\"size\": \\w+");
        for (int i = 0; i < sizeArray.length; i++) {
            sizeArray[i] = Utils.regexReplace(sizeArray[i], "\"size\": ", "");
        }
        AtomicInteger DAIFEnd = new AtomicInteger();
        AtomicInteger DACFEnd = new AtomicInteger();
        AtomicInteger DAIFError = new AtomicInteger();
        AtomicInteger DACFError = new AtomicInteger();
        AtomicInteger DAIFadd = new AtomicInteger();
        AtomicInteger DACFadd = new AtomicInteger();
        for (int i = 0; i < hashArray.length; i++) {
            String hash = hashArray[i];
            String path = pathArray[i];
            int size = Integer.parseInt(sizeArray[i]);
            int finalI = i;
            String path1 = Attribute.getMainPath() + "assets\\objects\\" + hash.substring(0, 2) + "\\";
            File file = new File(path1 + hash);
            File finalFile = file;
            IThreadManagement iThreadManagement = () -> {
                AtomicBoolean r = new AtomicBoolean();
                PublicVariable.executorService.execute(() -> {
                    try {
                        if (Objects.equals(hash, Utils.fileSha1(finalFile)) && size == finalFile.length() && Setup.getSetupInstance().download.ifCheckFileSha1BeforeDownloading) {
                            System.out.println("DA-AI '" + hash + "' File already exists and SHA-1 is the same");
                            count("DA-AI", DAIFEnd, DAIFError, DAIFadd, hashArray.length, r, true);
                            return;
                        } else {
                            for (int j = 0; j != Setup.getSetupInstance().download.downloadRetries; j++) {
                                try {
                                    System.out.println("DA-AI-" + finalI + "-" + j + " downloading '" + hash + "'");
                                    try {
                                        downloadAssetIndexFile(Attribute, DownloadSource.official, hash);
                                    } catch (Exception e) {
                                        try {
                                            downloadAssetIndexFile(Attribute, DownloadSource.bmclapi, hash);
                                        } catch (Exception f) {
                                            downloadAssetIndexFile(Attribute, DownloadSource.mcbbs, hash);
                                        }
                                    }
                                    if (Objects.equals(hash, Utils.fileSha1(finalFile)) & finalFile.length() == size) {
                                        System.out.println("DA-AI-" + finalI + " Download '" + hash + "' end");
                                        count("DA-AI", DAIFEnd, DAIFError, DAIFadd, hashArray.length, r, true);
                                        return;
                                    }
                                } catch (Exception e) {
                                    System.out.println("DA-AI-" + finalI + "-" + j + " Download '" + hash + "' error");
                                }
                            }
                        }
                    } catch (NoSuchAlgorithmException | IOException ignored) {
                    }
                    System.out.println("DA-AI-" + finalI + " Download '" + hash + "' error");
                    count("DA-AI", DAIFEnd, DAIFError, DAIFadd, hashArray.length, r, false);
                });
                return r.get();
            };
            iThreadManagement.run();
            path1 = Attribute.getMainPath() + "assets\\virtual\\legacy\\" + Utils.regexReplace(path, Utils.regexReplace(path, "[\\w/]+/", ""), "");
            file = new File(path1 + Utils.regexReplace(path, "[\\w/]+/", ""));
            iThreadManagement = () -> {
                AtomicBoolean r = new AtomicBoolean();
                PublicVariable.executorService.execute(() -> {
                    if (!Setup.getSetupInstance().download.ifDownloadAssetIndexCopy) {
                        return;
                    }
                    try {
                        if (Objects.equals(hash, Utils.fileSha1(finalFile)) && size == finalFile.length() && Setup.getSetupInstance().download.ifCheckFileSha1BeforeDownloading) {
                            System.out.println("DA-AC '" + hash + "' File already exists and SHA-1 is the same");
                            count("DA-AC", DACFEnd, DACFError, DACFadd, hashArray.length, r, true);
                            return;
                        } else {
                            for (int j = 0; j != Setup.getSetupInstance().download.downloadRetries; j++) {
                                try {
                                    System.out.println("DA-AC-" + finalI + "-" + j + " downloading '" + path + "'");
                                    try {
                                        downloadAssetIndexCopyFile(Attribute, DownloadSource.official, path, hash);
                                    } catch (Exception e) {
                                        try {
                                            downloadAssetIndexCopyFile(Attribute, DownloadSource.bmclapi, path, hash);
                                        } catch (Exception f) {
                                            downloadAssetIndexCopyFile(Attribute, DownloadSource.mcbbs, path, hash);
                                        }
                                    }
                                    if (Objects.equals(hash, Utils.fileSha1(finalFile)) & finalFile.length() == size) {
                                        System.out.println("DA-AC-" + finalI + " Download '" + path + "' end");
                                        count("DA-AC", DACFEnd, DACFError, DACFadd, hashArray.length, r, true);
                                        return;
                                    }
                                } catch (Exception e) {
                                    System.out.println("DA-AC-" + finalI + "-" + j + " Download '" + hash + "' error");
                                }
                            }
                        }
                    } catch (NoSuchAlgorithmException | IOException ignored) {
                    }
                    System.out.println("DA-AC-" + finalI + " Download '" + path + "' error");
                    count("DA-AC", DACFEnd, DACFError, DACFadd, hashArray.length, r, false);
                });
                return r.get();
            };
            iThreadManagement.run();
        }
    }

    public static boolean downloadUtils(URL[] url, String ThreadName, String theoreticalFileHash, File file, int theoreticalFileSize) {
        IThreadManagement iThreadManagement = () -> {
            AtomicBoolean r = new AtomicBoolean();
            PublicVariable.executorService.execute(() -> {
                PublicVariable.threadQuantity++;
                String name = file.getName();
                try {
                    if (!(Objects.equals(theoreticalFileHash, Utils.fileSha1(file)) && (theoreticalFileSize == file.length() || theoreticalFileSize == 0) && Setup.getSetupInstance().download.ifCheckFileSha1BeforeDownloading)) {
                        int k = 0;
                        for (int j = 0; j != Setup.getSetupInstance().download.downloadRetries; k++) {
                            if (k == 0) {
                                Output.output(Output.OutputLevel.Ordinary, ThreadName, "The " + j + " attempt to download the file downloading '" + name + "'");
                            }
                            try {
                                downloadFile(url[k], DownloadSource.values()[k], file);
                                if (Objects.equals(theoreticalFileHash, Utils.fileSha1(file)) && (theoreticalFileSize == file.length() || theoreticalFileSize == 0)) {
                                    Output.output(Output.OutputLevel.Debug, ThreadName, " Download '" + name + "' succeed");
                                    end++;
                                    PublicVariable.threadQuantity--;
                                    r.set(true);
                                    return;
                                } else {
                                    throw new Exception();
                                }
                            } catch (Exception e) {
                                if (k == 2) {
                                    k = 0;
                                    j++;
                                    Output.output(Output.OutputLevel.Ordinary, ThreadName, "In the " + j + " attempt , download '" + name + "' fail");
                                }
                            }
                        }
                    } else {
                        Output.output(Output.OutputLevel.Debug, ThreadName, "'" + name + "' File already exists and SHA-1 is the same");
                        end++;
                        PublicVariable.threadQuantity--;
                        r.set(true);
                        return;
                    }
                } catch (Exception ignored) {
                }
                Output.output(Output.OutputLevel.Debug, ThreadName, " Download '" + file.getName() + "' fail");
                error++;
                PublicVariable.threadQuantity--;
            });
            return r.get();
        };
        return iThreadManagement.run();
    }
}

