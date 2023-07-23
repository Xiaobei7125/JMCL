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
import utils.Zip;

import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;


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

    public static void downloadsVersionFileUtils(VersionJson VersionJson, Attribute Attribute) throws Exception {
        IThreadManagement iThreadManagement = () -> {
            AtomicBoolean outcome = new AtomicBoolean(false);
            PublicVariable.executorService.execute(() -> {
                String name = Attribute.getId() + "." + VersionFileType.jar;
                //下载路径
                String incompletePath = Attribute.getMainPath() + "versions\\" + Attribute.getId() + "\\";
                //从下载处获得的sha1
                String standardSha1 = VersionJson.getDownloads().getClient().getSha1();
                int standardSize = VersionJson.getDownloads().getClient().getSize();
                File file = new File(incompletePath + name);
                try {
                    outcome.set(download(UrlArray.versionJarFileURL(VersionJson), "DL-VF", standardSha1, file,
                            standardSize, 0, new Outcome(new AtomicInteger(), new AtomicInteger(), new AtomicInteger(), 1)));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            return outcome.get();
        };
        iThreadManagement.run();
    }

    public static void downloadsVersionJsonUtils(VersionManifest VersionManifest, Attribute Attribute) throws Exception {
        IThreadManagement iThreadManagement = () -> {
            AtomicBoolean outcome = new AtomicBoolean(false);
            PublicVariable.executorService.execute(() -> {
                String name = Attribute.getId() + "." + VersionFileType.json;
                String incompletePath = Attribute.getMainPath() + "versions\\" + Attribute.getId() + "\\";
                String standardSha1 = VersionManifest.getSha1(Attribute.getId());
                File file = new File(incompletePath + name);
                try {
                    outcome.set(download(UrlArray.versionJsonFileURL(VersionManifest, Attribute.getId()), "DL-VJ",
                            standardSha1, file, 0, 0, new Outcome(new AtomicInteger(), new AtomicInteger(), new AtomicInteger(), 1)));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            return outcome.get();
        };
        iThreadManagement.run();
    }

    public static void downloadLog4jFileUtils(VersionJson VersionJson, Attribute Attribute) throws Exception {
        IThreadManagement iThreadManagement = () -> {
            AtomicBoolean outcome = new AtomicBoolean(false);
            PublicVariable.executorService.execute(() -> {
                String name = VersionJson.getLogging().getClient().getFile().getId();
                String incompletePath = Attribute.getMainPath() + "assets\\log_configs\\";
                String standardSha1 = VersionJson.getLogging().getClient().getFile().getSha1();
                int standardSize = VersionJson.getLogging().getClient().getFile().getSize();
                File file = new File(incompletePath + name);
                try {
                    outcome.set(download(UrlArray.Log4jFileURL(VersionJson), "DL-LF", standardSha1, file,
                            standardSize, 0, new Outcome(new AtomicInteger(), new AtomicInteger(), new AtomicInteger(), 1)));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            return outcome.get();
        };
        iThreadManagement.run();
    }

    public static void downloadAssetIndexJsonUtils(VersionJson VersionJson, Attribute Attribute) throws Exception {
        IThreadManagement iThreadManagement = () -> {
            AtomicBoolean outcome = new AtomicBoolean(false);
            PublicVariable.executorService.execute(() -> {
                String name = VersionJson.getAssetIndex().getId() + ".json";
                String incompletePath = Attribute.getMainPath() + "assets\\indexes\\";
                String standardSha1 = VersionJson.getAssetIndex().getSha1();
                int standardSize = VersionJson.getAssetIndex().getSize();
                File file = new File(incompletePath + name);
                try {
                    outcome.set(download(UrlArray.assetIndexJsonURL(VersionJson), "DL-AJ", standardSha1, file,
                            standardSize, 0, new Outcome(new AtomicInteger(), new AtomicInteger(), new AtomicInteger(), 1)));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            return outcome.get();
        };
        iThreadManagement.run();
    }

    public static void downloadsLibrariesUtils(VersionJson VersionJson, Attribute Attribute) throws Exception {
        AtomicInteger DLEnd = new AtomicInteger();
        AtomicInteger DLError = new AtomicInteger();
        AtomicInteger DLAdd = new AtomicInteger();
        int sumOfDL = (int) IntStream.range(0, VersionJson.getLibraries().length).filter(i ->
                (VersionJson.getLibraries()[i].getDownloads().getClassifiers() != null &&
                        VersionJson.getLibraries()[i].getDownloads().getClassifiers().getNativesWindows() != null)
                        || VersionJson.getLibraries()[i].getDownloads().getClassifiers() == null).count();
        for (int i = 0; i < VersionJson.getLibraries().length; i++) {
            int finalI = i;
            if (VersionJson.getLibraries()[i].getDownloads().getClassifiers() != null &&
                    VersionJson.getLibraries()[i].getDownloads().getClassifiers().getNativesWindows() != null) {
                int finalI1 = i;
                IThreadManagement iThreadManagement = () -> {
                    AtomicBoolean outcome = new AtomicBoolean(false);
                    PublicVariable.executorService.execute(() -> {
                        try {
                            String name = new File(Url.nativesJarURL(VersionJson, DownloadSource.official, finalI1).getPath()).getName();
                            //String name = Utils.regexReplace(VersionJson.getLibraries()[finalI].getDownloads().getArtifact().getPath(), "[\\w\\d./-]+/", "");
                            int standardSize = VersionJson.getLibraries()[finalI].getDownloads().getClassifiers().getNativesWindows().getSize();
                            String standardSha1 = VersionJson.getLibraries()[finalI].getDownloads().getClassifiers().getNativesWindows().getSha1();
                            File file = new File(Attribute.getRunPath() + "\\natives\\" + name);
                            outcome.set(download(UrlArray.nativesJarURL(VersionJson, finalI1), "DL-ND", standardSha1, file,
                                    standardSize, finalI1, new Outcome(DLEnd, DLError, DLAdd, sumOfDL)));
                            String unzipTheDirectory = Attribute.getMainPath() + "versions\\" + Attribute.getId() + "\\natives\\";
                            for (; ; ) {
                                if (Zip.unzip(file.getPath(), unzipTheDirectory)) break;
                            }
                            File[] array = new File(unzipTheDirectory).listFiles();
                            assert array != null;
                            for (File unzipFile : array) {
                                if (!Utils.regexReplace(unzipFile.getName(), "[\\w\\d-.]+\\.", "").equals("dll")) {
                                    if (unzipFile.isDirectory()) {
                                        Utils.deleteDirectory(unzipFile.getPath());
                                    }
                                    unzipFile.delete();
                                }
                            }

                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                    return outcome.get();
                };
                iThreadManagement.run();
            } else if (VersionJson.getLibraries()[i].getDownloads().getClassifiers() == null) {
                IThreadManagement iThreadManagement = () -> {
                    AtomicBoolean outcome = new AtomicBoolean(false);
                    PublicVariable.executorService.execute(() -> {
                        try {
                            String name = new File(Url.otherJarLibrariesURL(VersionJson, DownloadSource.official, finalI).getPath()).getName();
                            String path = Attribute.getMainPath() + "libraries\\" + Utils.regexReplace(VersionJson.getLibraries()[finalI].getDownloads().getArtifact().getPath(), name, "");
                            String standardSha1 = VersionJson.getLibraries()[finalI].getDownloads().getArtifact().getSha1();
                            int standardSize = VersionJson.getLibraries()[finalI].getDownloads().getArtifact().getSize();
                            File file = new File(path + name);
                            outcome.set(download(UrlArray.otherJarLibrariesURL(VersionJson, finalI), "DL-OJ",
                                    standardSha1, file, standardSize, finalI, new Outcome(DLEnd, DLError, DLAdd, sumOfDL)));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                    return outcome.get();
                };
                iThreadManagement.run();
            }
        }
    }

    public static void downloadsAssetIndexUtils(VersionJson VersionJson, Attribute Attribute) throws Exception {
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
            IThreadManagement iThreadManagement = () -> {
                AtomicBoolean outcome = new AtomicBoolean(false);
                PublicVariable.executorService.execute(() -> {
                    String path1 = Attribute.getMainPath() + "assets\\objects\\" + hash.substring(0, 2) + "\\";
                    File file = new File(path1 + hash);
                    try {
                        outcome.set(download(UrlArray.assetIndexFileURL(hash), "DA-AI", hash, file,
                                size, finalI, new Outcome(DAIFEnd, DAIFError, DAIFadd, hashArray.length)));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
                return outcome.get();
            };
            iThreadManagement.run();
            IThreadManagement iThreadManagement2 = () -> {
                AtomicBoolean outcome = new AtomicBoolean(false);
                PublicVariable.executorService.execute(() -> {
                    String path1 = Attribute.getMainPath() + "assets\\virtual\\legacy\\" + Utils.regexReplace(path, Utils.regexReplace(path, "[\\w/]+/", ""), "");
                    File file = new File(path1 + Utils.regexReplace(path, "[\\w/]+/", ""));
                    try {
                        outcome.set(download(UrlArray.assetIndexFileURL(hash), "DA-AC", hash, file,
                                size, finalI, new Outcome(DACFEnd, DACFError, DACFadd, hashArray.length)));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
                return outcome.get();
            };
            iThreadManagement2.run();
        }
    }

    private static synchronized void count(String ThreadName, Outcome outcome, boolean ifSucceed) {
        if (ifSucceed) {
            end++;
            outcome.end.addAndGet(1);
        } else {
            error++;
            outcome.error.addAndGet(1);
        }
        outcome.and.set(outcome.end.get() + outcome.error.get());
        Output.output(Output.OutputLevel.Debug, ThreadName, outcome.end + "/" + outcome.and + "/" + outcome.maximum);
        PublicVariable.threadQuantity--;
    }

    public static boolean download(URL[] url, String ThreadName, String theoreticalFileHash, File file, int theoreticalFileSize, int count, Outcome outcome) throws Exception {
        PublicVariable.threadQuantity++;
        String name = file.getName();
        try {
            if (!(Setup.getSetupInstance().download.ifCheckFileBeforeDownloading && (Objects.equals(theoreticalFileHash, Utils.fileSha1(file)) && (theoreticalFileSize == file.length() || theoreticalFileSize == 0)))) {
                int k = 0;
                for (int j = 0; j != Setup.getSetupInstance().download.downloadRetries; k++) {
                    if (k == 0) {
                        Output.output(Output.OutputLevel.Ordinary, ThreadName + "-" + count, "The " + j + " attempt to download the file downloading '" + name + "'");
                    }
                    try {
                        downloadFile(url[k], DownloadSource.values()[k], file);
                        if (Objects.equals(theoreticalFileHash, Utils.fileSha1(file)) && (theoreticalFileSize == file.length() || theoreticalFileSize == 0)) {
                            Output.output(Output.OutputLevel.Debug, ThreadName + "-" + count, " Download '" + name + "' succeed");
                            count(ThreadName, outcome, true);
                            return true;
                        } else {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        if (k == 2) {
                            k = 0;
                            j++;
                            Output.output(Output.OutputLevel.Ordinary, ThreadName + "-" + count, "In the " + j + " attempt , download '" + name + "' fail");
                        }
                    }
                }
            } else {
                Output.output(Output.OutputLevel.Debug, ThreadName + "-" + count, "'" + name + "' File already exists and SHA-1 is the same");
                count(ThreadName, outcome, true);
                return true;
            }
        } catch (Exception ignored) {
        }
        Output.output(Output.OutputLevel.Debug, ThreadName + "-" + count, " Download '" + file.getName() + "' fail");
        count(ThreadName, outcome, false);
        return false;
    }

    static class Outcome {
        AtomicInteger and;
        AtomicInteger error;
        AtomicInteger end;
        int maximum;

        Outcome(AtomicInteger end, AtomicInteger error, AtomicInteger and, int maximum) {
            this.and = and;
            this.end = end;
            this.error = error;
            this.maximum = maximum;
        }
    }
}