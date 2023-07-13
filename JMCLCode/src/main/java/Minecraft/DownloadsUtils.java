package minecraft;


import jsonAnalysis.download.minecraft.library.VersionJson;
import jsonAnalysis.download.minecraft.library.VersionManifest;
import jsonAnalysis.setup.Setup;
import other.IThreadManagement;
import other.Output;
import other.PublicVariable;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


public class DownloadsUtils {
    static int end = 0;
    static int error = 0;

    private static void downloadVersionFile(VersionJson VersionJson, DownloadURL.DownloadSource downloadSource, Attribute Attribute) throws Exception {
        if (downloadSource.ifUse) {
            Download.downloadVersionFile(DownloadURL.versionJarFileURL(VersionJson, downloadSource), Attribute.mainPath, Attribute.id, Download.VersionFile.jar);
        } else {
            throw new Exception();
        }
    }

    private static void downloadVersionJson(VersionManifest VersionManifest, DownloadURL.DownloadSource downloadSource, Attribute Attribute) throws Exception {
        if (downloadSource.ifUse) {
            Download.downloadVersionFile(DownloadURL.versionJsonFileURL(VersionManifest, Attribute.id, downloadSource), Attribute.mainPath, Attribute.id, Download.VersionFile.json);
        } else {
            throw new Exception();
        }
    }

    private static void downloadNativesDllLibraries(VersionJson VersionJson, DownloadURL.DownloadSource downloadSource, Attribute Attribute, int i) throws Exception {
        if (downloadSource.ifUse) {
            Download.downloadNativesDllLibraries(DownloadURL.nativesJarURL(VersionJson, downloadSource, i), Attribute.mainPath, Attribute.id, Attribute.runPath);
        } else {
            throw new Exception();
        }
    }

    private static void downloadOtherJarLibraries(VersionJson VersionJson, DownloadURL.DownloadSource downloadSource, Attribute Attribute, int i) throws Exception {
        if (downloadSource.ifUse) {
            Download.downloadOtherLibraries(DownloadURL.otherJarLibrariesURL(VersionJson, downloadSource, i), Attribute.mainPath, VersionJson.getLibraries()[i].getDownloads().getArtifact().getPath());
        } else {
            throw new Exception();
        }
    }

    private static void downloadLog4jFile(VersionJson VersionJson, DownloadURL.DownloadSource downloadSource, Attribute Attribute) throws Exception {
        if (downloadSource.ifUse) {
            Download.downloadLog4jFile(DownloadURL.Log4jFileURL(VersionJson, downloadSource), Attribute.mainPath, VersionJson.getLogging().getClient().getFile().getId());
        } else {
            throw new Exception();
        }
    }

    private static void downloadAssetIndexJson(VersionJson VersionJson, DownloadURL.DownloadSource downloadSource, Attribute Attribute) throws Exception {
        if (downloadSource.ifUse) {
            Download.downloadAssetIndexJson(DownloadURL.assetIndexJsonURL(VersionJson, downloadSource), Attribute.mainPath, VersionJson.getAssetIndex().getId());
        } else {
            throw new Exception();
        }
    }

    private static void downloadAssetIndexFile(Attribute Attribute, DownloadURL.DownloadSource downloadSource, String hash) throws Exception {
        if (downloadSource.ifUse) {
            Download.downloadAssetIndexFile(DownloadURL.assetIndexFileURL(hash, downloadSource), Attribute.mainPath, hash);
        } else {
            throw new Exception();
        }
    }

    private static void downloadAssetIndexCopyFile(Attribute Attribute, DownloadURL.DownloadSource downloadSource, String path, String hash) throws Exception {
        if (downloadSource.ifUse) {
            Download.downloadAssetIndexCopyFile(DownloadURL.assetIndexFileURL(hash, downloadSource), Attribute.mainPath, path);
        } else {
            throw new Exception();
        }
    }

    public static void downloadsVersionFileUtils(VersionJson VersionJson, Attribute Attribute) {
        IThreadManagement iThreadManagement = () -> {
            String name = Attribute.id + "." + Download.VersionFile.jar;
            //下载路径
            String path = Attribute.mainPath + "versions\\" + Attribute.id + "\\";
            //从下载处获得的sha1
            String standardSha1 = VersionJson.getDownloads().getClient().getSha1();
            int standardSize = VersionJson.getDownloads().getClient().getSize();
            File file = new File(path + name);
            AtomicBoolean r = new AtomicBoolean();
            PublicVariable.executorService.execute(() -> {
                try {
                    if (Objects.equals(standardSha1, Utils.fileSha1(file)) && standardSize == file.length() && Setup.getSetupInstance().download.ifCheckFileSha1BeforeDownloading) {
                        System.out.println("DL-VF '" + name + "' File already exists and SHA-1 is the same");
                        end++;
                        PublicVariable.threadQuantity--;
                        r.set(true);
                        return;
                    } else {
                        for (int j = 0; j != Setup.getSetupInstance().download.downloadRetries; j++) {
                            try {
                                System.out.println("DL-VF-0-" + j + " downloading '" + name + "'");
                                try {
                                    downloadVersionFile(VersionJson, DownloadURL.DownloadSource.official, Attribute);
                                } catch (Exception e) {
                                    try {
                                        downloadVersionFile(VersionJson, DownloadURL.DownloadSource.bmclapi, Attribute);
                                    } catch (Exception f) {
                                        downloadVersionFile(VersionJson, DownloadURL.DownloadSource.mcbbs, Attribute);
                                    }
                                }
                                System.out.println(standardSha1 + "," + Utils.fileSha1(file));
                                System.out.println(VersionJson.getDownloads().getClient().getSize() + "," + file.length());
                                if (Objects.equals(standardSha1, Utils.fileSha1(file)) & file.length() == standardSize) {
                                    System.out.println("DL-VF" + " Download '" + name + "' end");
                                    end++;
                                    PublicVariable.threadQuantity--;
                                    r.set(true);
                                    return;
                                }
                            } catch (Exception e) {
                                System.out.println("DL-VF-0-" + j + " Download '" + name + "' error");
                            }
                        }
                    }
                    System.out.println("DL-VF-0" + " Download '" + name + "' error");
                    error++;
                    PublicVariable.threadQuantity--;
                } catch (NoSuchAlgorithmException | IOException ignored) {
                }
            });
            return r.get();
        };
        iThreadManagement.run();
    }

    public static void downloadsVersionJsonUtils(VersionManifest VersionManifest, Attribute Attribute) {
        IThreadManagement iThreadManagement = () -> {
            String name = Attribute.id + "." + Download.VersionFile.json;
            String path = Attribute.mainPath + "versions\\" + Attribute.id + "\\";
            String standardSha1 = VersionManifest.getSha1(Attribute.id);
            File file = new File(path + name);
            AtomicBoolean r = new AtomicBoolean();
            PublicVariable.executorService.execute(() -> {
                try {
                    if (Objects.equals(standardSha1, Utils.fileSha1(file)) && Setup.getSetupInstance().download.ifCheckFileSha1BeforeDownloading) {
                        System.out.println("DL-VJ '" + name + "' File already exists and SHA-1 is the same");
                        end++;
                        PublicVariable.threadQuantity--;
                        r.set(true);
                        return;
                    } else {
                        for (int j = 0; j != Setup.getSetupInstance().download.downloadRetries; j++) {
                            try {
                                System.out.println("DL-VJ-0-" + j + " downloading '" + name + "'");
                                try {
                                    downloadVersionJson(VersionManifest, DownloadURL.DownloadSource.official, Attribute);
                                } catch (Exception e) {
                                    try {
                                        downloadVersionJson(VersionManifest, DownloadURL.DownloadSource.bmclapi, Attribute);
                                    } catch (Exception f) {
                                        downloadVersionJson(VersionManifest, DownloadURL.DownloadSource.mcbbs, Attribute);
                                    }
                                }
                                if (Objects.equals(standardSha1, Utils.fileSha1(file))) {
                                    System.out.println("DL-VJ" + " Download '" + name + "' end");
                                    end++;
                                    PublicVariable.threadQuantity--;
                                    r.set(true);
                                    return;
                                }
                            } catch (Exception e) {
                                System.out.println("DL-VJ-" + j + " Download '" + name + "' error");
                            }
                        }
                    }
                } catch (NoSuchAlgorithmException | IOException ignored) {
                }
                System.out.println("DL-VJ" + " Download '" + name + "' error");
                error++;
                PublicVariable.threadQuantity--;
            });
            return r.get();
        };
        iThreadManagement.run();
    }

    public static void downloadLog4jFileUtils(VersionJson VersionJson, Attribute Attribute) {
        IThreadManagement iThreadManagement = () -> {
            if (VersionJson.getLogging() == null) {
                return false;
            }
            String name = VersionJson.getLogging().getClient().getFile().getId();
            String path = Attribute.mainPath + "assets\\log_configs\\";
            String standardSha1 = VersionJson.getLogging().getClient().getFile().getSha1();
            int standardSize = VersionJson.getLogging().getClient().getFile().getSize();
            File file = new File(path + name);
            AtomicBoolean r = new AtomicBoolean();
            PublicVariable.executorService.execute(() -> {
                try {
                    if (Objects.equals(standardSha1, Utils.fileSha1(new File(path + name))) && standardSize == file.length() && Setup.getSetupInstance().download.ifCheckFileSha1BeforeDownloading) {
                        System.out.println("DL-LF '" + name + "' File already exists and SHA-1 is the same");
                        end++;
                        PublicVariable.threadQuantity--;
                        r.set(true);
                        return;
                    } else {
                        for (int j = 0; j != Setup.getSetupInstance().download.downloadRetries; j++) {
                            try {
                                System.out.println("DL-LF-" + j + " downloading: '" + name + "'");
                                try {
                                    downloadLog4jFile(VersionJson, DownloadURL.DownloadSource.official, Attribute);
                                } catch (Exception e) {
                                    try {
                                        downloadLog4jFile(VersionJson, DownloadURL.DownloadSource.bmclapi, Attribute);
                                    } catch (Exception f) {
                                        downloadLog4jFile(VersionJson, DownloadURL.DownloadSource.mcbbs, Attribute);
                                    }
                                }
                                if (Objects.equals(standardSha1, Utils.fileSha1(file)) && standardSize == file.length() && Setup.getSetupInstance().download.ifCheckFileSha1BeforeDownloading) {
                                    System.out.println("DL-LF" + " Download '" + name + "' end");
                                    end++;
                                    PublicVariable.threadQuantity--;
                                    r.set(true);
                                    return;
                                }
                            } catch (Exception e) {
                                System.out.println("DL-LF-" + j + " Download '" + name + "' error");
                            }
                        }

                    }
                } catch (NoSuchAlgorithmException | IOException ignored) {
                }
                System.out.println("DL-LF" + " Download '" + name + "' error");
                error++;
                PublicVariable.threadQuantity--;
            });
            return r.get();
        };
        iThreadManagement.run();
    }

    public static void downloadAssetIndexJsonUtils(VersionJson VersionJson, Attribute Attribute) {
        IThreadManagement iThreadManagement = () -> {
            String name = VersionJson.getAssetIndex().getId() + ".json";
            String path = Attribute.mainPath + "assets\\indexes\\";
            String standardSha1 = VersionJson.getAssetIndex().getSha1();
            int standardSize = VersionJson.getAssetIndex().getSize();
            File file = new File(path + name);
            AtomicBoolean r = new AtomicBoolean();
            PublicVariable.executorService.execute(() -> {
                try {
                    if (Objects.equals(standardSha1, Utils.fileSha1(file)) && standardSize == file.length() && Setup.getSetupInstance().download.ifCheckFileSha1BeforeDownloading) {
                        System.out.println("DL-AJ '" + name + "' File already exists and SHA-1 is the same");
                        end++;
                        PublicVariable.threadQuantity--;
                        r.set(true);
                        return;
                    } else {
                        for (int j = 0; j != Setup.getSetupInstance().download.downloadRetries; j++) {
                            try {
                                System.out.println("DL-AJ-" + j + " downloading '" + name + "'");
                                try {
                                    downloadAssetIndexJson(VersionJson, DownloadURL.DownloadSource.official, Attribute);
                                } catch (Exception e) {
                                    try {
                                        downloadAssetIndexJson(VersionJson, DownloadURL.DownloadSource.bmclapi, Attribute);
                                    } catch (Exception f) {
                                        downloadAssetIndexJson(VersionJson, DownloadURL.DownloadSource.mcbbs, Attribute);
                                    }
                                }
                                System.out.println(standardSha1 + "," + Utils.fileSha1(file));
                                if (Objects.equals(standardSha1, Utils.fileSha1(file)) & file.length() == standardSize) {
                                    System.out.println("DL-AJ" + " Download '" + name + "' end");
                                    end++;
                                    PublicVariable.threadQuantity--;
                                    r.set(true);
                                    return;
                                }
                            } catch (Exception e) {
                                System.out.println("DL-AJ-" + j + " Download '" + name + "' error");
                            }
                        }

                    }
                } catch (NoSuchAlgorithmException | IOException ignored) {
                }
                System.out.println("DL-AJ" + " Download '" + name + "' error");
                error++;
                PublicVariable.threadQuantity--;
            });
            return r.get();
        };
        iThreadManagement.run();
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
                String name = new File(DownloadURL.nativesJarURL(VersionJson, DownloadURL.DownloadSource.official, i).getPath()).getName();
                int finalSumOfDL = sumOfDL;
                IThreadManagement iThreadManagement = () -> {
                    AtomicBoolean r = new AtomicBoolean();
                    PublicVariable.executorService.execute(() -> {
                        for (int j = 0; j != Setup.getSetupInstance().download.downloadRetries; j++) {
                            try {
                                System.out.println("DL-ND-" + finalI + "-" + j + " downloading '" + name + "'");
                                try {
                                    downloadNativesDllLibraries(VersionJson, DownloadURL.DownloadSource.official, Attribute, finalI);
                                } catch (Exception e) {
                                    try {
                                        downloadNativesDllLibraries(VersionJson, DownloadURL.DownloadSource.bmclapi, Attribute, finalI);
                                    } catch (Exception f) {
                                        downloadNativesDllLibraries(VersionJson, DownloadURL.DownloadSource.mcbbs, Attribute, finalI);
                                    }
                                }
                                System.out.println("DL-ND-" + finalI + " Download '" + name + "' end");
                                end++;
                                synchronized (DLEnd) {
                                    synchronized ((DLError)) {
                                        synchronized ((DLAdd)) {
                                            DLEnd.addAndGet(1);
                                            DLAdd.set(DLEnd.get() + DLError.get());
                                            System.out.println("DL-ND " + DLEnd + "/" + DLAdd + "/" + finalSumOfDL);
                                        }
                                    }
                                }
                                PublicVariable.threadQuantity--;
                                r.set(true);
                                return;
                            } catch (Exception e) {
                                System.out.println("DL-ND-" + finalI + "-" + j + " Download '" + name + "' error");
                            }
                        }
                        //}
                        System.out.println("DL-ND-" + finalI + " Download '" + name + "' error");
                        error++;
                        synchronized (DLEnd) {
                            synchronized ((DLError)) {
                                synchronized ((DLAdd)) {
                                    DLError.addAndGet(1);
                                    DLAdd.set(DLEnd.get() + DLError.get());
                                    System.out.println("DL-ND " + DLError + "/" + DLAdd + "/" + " error");
                                }
                            }
                        }
                        PublicVariable.threadQuantity--;
                    });
                    return r.get();
                };
                iThreadManagement.run();
                /*
                new Thread(()-> {
                    try {
                        System.out.println("DL-ND-" + finalI + " downloading AssetIndex: '" + name + "'");
                        minecraft.DownloadsUtils.downloadsNativesDllLibrariesUtils(VersionJson, minecraft.Attribute, finalI);
                        System.out.println("DL-ND-" + finalI + " Download '" + name + "' end");
                        synchronized (DLNDEnd) {
                            System.out.println("DL-ND " + DLNDEnd.addAndGet(1) + "/" + sumOfDL);
                        }
                    } catch (Exception e) {
                        System.out.println("DL-ND-" + finalI + " Download '" + name + "' error");
                        synchronized (DLNDError) {
                            System.out.println("DL-ND " + DLNDError.addAndGet(1));
                        }
                    }
                },"DL-ND-"+i).start();
                */
            } else if (VersionJson.getLibraries()[i].getDownloads().getClassifiers() == null) {
                String name = new File(DownloadURL.otherJarLibrariesURL(VersionJson, DownloadURL.DownloadSource.official, i).getPath()).getName();
                String path = Attribute.mainPath + "libraries\\" + Utils.regexReplace(VersionJson.getLibraries()[finalI].getDownloads().getArtifact().getPath(), name, "");
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
                                end++;
                                synchronized (DLEnd) {
                                    synchronized ((DLError)) {
                                        synchronized ((DLAdd)) {
                                            DLEnd.addAndGet(1);
                                            DLAdd.set(DLEnd.get() + DLError.get());
                                            System.out.println("DL-OJ " + DLEnd + "/" + DLAdd + "/" + finalSumOfDL);
                                        }
                                    }
                                }
                                PublicVariable.threadQuantity--;
                                r.set(true);
                                return;
                            } else {
                                for (int j = 0; j != Setup.getSetupInstance().download.downloadRetries; j++) {
                                    try {
                                        System.out.println("DL-OJ-" + finalI + "-" + j + " downloading '" + name + "'");
                                        try {
                                            downloadOtherJarLibraries(VersionJson, DownloadURL.DownloadSource.official, Attribute, finalI);
                                        } catch (Exception e) {
                                            try {
                                                downloadOtherJarLibraries(VersionJson, DownloadURL.DownloadSource.bmclapi, Attribute, finalI);
                                            } catch (Exception f) {
                                                downloadOtherJarLibraries(VersionJson, DownloadURL.DownloadSource.mcbbs, Attribute, finalI);
                                            }
                                        }
                                        if (Objects.equals(standardSha1, Utils.fileSha1(file)) & file.length() == standardSize) {
                                            System.out.println("DL-OJ-" + finalI + " Download '" + name + "' end");
                                            end++;
                                            synchronized (DLEnd) {
                                                synchronized ((DLError)) {
                                                    synchronized ((DLAdd)) {
                                                        DLEnd.addAndGet(1);
                                                        DLAdd.set(DLEnd.get() + DLError.get());
                                                        System.out.println("DL-OJ " + DLEnd + "/" + DLAdd + "/" + finalSumOfDL);
                                                    }
                                                }
                                            }
                                            PublicVariable.threadQuantity--;
                                            r.set(true);
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
                        error++;
                        synchronized (DLEnd) {
                            synchronized ((DLError)) {
                                synchronized ((DLAdd)) {
                                    DLError.addAndGet(1);
                                    DLAdd.set(DLEnd.get() + DLError.get());
                                    System.out.println("DL-OJ " + DLError + "/" + DLAdd + "/" + " error");
                                }
                            }
                        }
                        PublicVariable.threadQuantity--;
                    });
                /*
                new Thread(()-> {
                    try {
                        System.out.println("DL-OJ-" + finalI + " downloading AssetIndex: '" + name + "'");
                        minecraft.DownloadsUtils.downloadOtherJarLibrariesUtils(VersionJson,minecraft.Attribute, finalI);
                        System.out.println("DL-OJ-" + finalI + " Download '" + name + "' end");
                        synchronized (DLOJEnd) {
                            System.out.println("DL-OJ " + DLOJEnd.addAndGet(1) + "/" + sumOfDL);
                        }
                    } catch (Exception e) {
                        System.out.println("DL-OJ-" + finalI + " Download '" + name + "' error");
                        synchronized (DLOJError) {
                            System.out.println("DL-OJ " + DLOJError.addAndGet(1));
                        }
                    }
                },"DL-OJ-"+i).start();
                */
                    return r.get();
                };
                iThreadManagement.run();
            }
        }
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
            String path1 = Attribute.mainPath + "assets\\objects\\" + hash.substring(0, 2) + "\\";
            File file = new File(path1 + hash);
            File finalFile = file;
            IThreadManagement iThreadManagement = () -> {
                AtomicBoolean r = new AtomicBoolean();
                PublicVariable.executorService.execute(() -> {
                    try {
                        if (Objects.equals(hash, Utils.fileSha1(finalFile)) && size == finalFile.length() && Setup.getSetupInstance().download.ifCheckFileSha1BeforeDownloading) {
                            System.out.println("DA-AI '" + hash + "' File already exists and SHA-1 is the same");
                            end++;
                            synchronized (DAIFEnd) {
                                synchronized ((DAIFError)) {
                                    synchronized ((DAIFadd)) {
                                        DAIFEnd.addAndGet(1);
                                        DAIFadd.set(DAIFEnd.get() + DAIFError.get());
                                        System.out.println("DA-AI " + DAIFEnd + "/" + DAIFadd + "/" + hashArray.length);
                                    }
                                }
                            }
                            PublicVariable.threadQuantity--;
                            r.set(true);
                            return;
                        } else {
                            for (int j = 0; j != Setup.getSetupInstance().download.downloadRetries; j++) {
                                try {
                                    System.out.println("DA-AI-" + finalI + "-" + j + " downloading '" + hash + "'");
                                    try {
                                        downloadAssetIndexFile(Attribute, DownloadURL.DownloadSource.official, hash);
                                    } catch (Exception e) {
                                        try {
                                            downloadAssetIndexFile(Attribute, DownloadURL.DownloadSource.bmclapi, hash);
                                        } catch (Exception f) {
                                            downloadAssetIndexFile(Attribute, DownloadURL.DownloadSource.mcbbs, hash);
                                        }
                                    }
                                    if (Objects.equals(hash, Utils.fileSha1(finalFile)) & finalFile.length() == size) {
                                        System.out.println("DA-AI-" + finalI + " Download '" + hash + "' end");
                                        end++;
                                        synchronized (DAIFEnd) {
                                            synchronized ((DAIFError)) {
                                                synchronized ((DAIFadd)) {
                                                    DAIFEnd.addAndGet(1);
                                                    DAIFadd.set(DAIFEnd.get() + DAIFError.get());
                                                    System.out.println("DA-AI " + DAIFEnd + "/" + DAIFadd + "/" + hashArray.length);
                                                }
                                            }
                                        }
                                        PublicVariable.threadQuantity--;
                                        r.set(true);
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
                    error++;
                    synchronized (DAIFEnd) {
                        synchronized ((DAIFError)) {
                            synchronized ((DAIFadd)) {
                                DAIFError.addAndGet(1);
                                DAIFadd.set(DAIFEnd.get() + DAIFError.get());
                                System.out.println("DA-AI " + DAIFError + "/" + DAIFadd + "/" + " error");
                            }
                        }
                    }
                    PublicVariable.threadQuantity--;
                });
                return r.get();
            };
            iThreadManagement.run();
            path1 = Attribute.mainPath + "assets\\virtual\\legacy\\" + Utils.regexReplace(path, Utils.regexReplace(path, "[\\w/]+/", ""), "");
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
                            end++;
                            synchronized (DACFEnd) {
                                synchronized ((DACFError)) {
                                    synchronized ((DACFadd)) {
                                        DACFEnd.addAndGet(1);
                                        DACFadd.set(DACFEnd.get() + DACFError.get());
                                        System.out.println("DA-AC " + DACFEnd + "/" + DACFadd + "/" + hashArray.length);
                                    }
                                }
                            }
                            PublicVariable.threadQuantity--;
                            r.set(true);
                            return;
                        } else {
                            for (int j = 0; j != Setup.getSetupInstance().download.downloadRetries; j++) {
                                try {
                                    System.out.println("DA-AC-" + finalI + "-" + j + " downloading '" + path + "'");
                                    try {
                                        downloadAssetIndexCopyFile(Attribute, DownloadURL.DownloadSource.official, path, hash);
                                    } catch (Exception e) {
                                        try {
                                            downloadAssetIndexCopyFile(Attribute, DownloadURL.DownloadSource.bmclapi, path, hash);
                                        } catch (Exception f) {
                                            downloadAssetIndexCopyFile(Attribute, DownloadURL.DownloadSource.mcbbs, path, hash);
                                        }
                                    }
                                    if (Objects.equals(hash, Utils.fileSha1(finalFile)) & finalFile.length() == size) {
                                        System.out.println("DA-AC-" + finalI + " Download '" + path + "' end");
                                        end++;
                                        synchronized (DACFEnd) {
                                            synchronized ((DACFError)) {
                                                synchronized ((DACFadd)) {
                                                    DACFEnd.addAndGet(1);
                                                    DACFadd.set(DACFEnd.get() + DACFError.get());
                                                    System.out.println("DA-AC " + DACFEnd + "/" + DACFadd + "/" + hashArray.length);
                                                }
                                            }
                                        }
                                        PublicVariable.threadQuantity--;
                                        r.set(true);
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
                    error++;
                    synchronized (DACFEnd) {
                        synchronized ((DACFError)) {
                            synchronized ((DACFadd)) {
                                DACFError.addAndGet(1);
                                DACFadd.set(DACFEnd.get() + DACFError.get());
                                System.out.println("DA-AC " + DACFError + "/" + DACFadd + "/" + " error");
                            }
                        }
                    }
                    PublicVariable.threadQuantity--;
                });
                return r.get();
            };
            iThreadManagement.run();
            /*new Thread(()-> {
                try {
                    System.out.println("DL-AI-"+ finalI +" downloading AssetIndex:"+hash+"'");
                    minecraft.DownloadsUtils.downloadAssetIndexFileUtils(minecraft.Attribute,hash);
                    System.out.println("DL-AI-"+ finalI +" Download '"+ hash + "' end");
                    synchronized (DAIFEnd) {
                        System.out.println("DL-AI " + DAIFEnd.addAndGet(1) + "/" + hashArray.length);
                    }
                } catch (Exception e) {
                    System.out.println("DL-AI-"+ finalI +" Download '"+ hash + "' error");
                    synchronized (DAIFError) {
                        System.out.println("DL-AI " + DAIFError.addAndGet(1));
                    }
                }
            },"DL-AI-"+i).start();
            new Thread(()-> {
                try {
                    System.out.println("DACL-"+ finalI +" downloading AssetIndex: '"+path+"'");
                    minecraft.DownloadsUtils.downloadAssetIndexCopyFileUtils(minecraft.Attribute,path,hash);
                    System.out.println("DACL-"+ finalI +" Download '"+ path + "' end");
                    synchronized (DACFEnd) {
                        System.out.println("DACL " + DACFEnd.addAndGet(1) + "/" + pathArray.length);
                    }
                } catch (Exception e) {
                    System.out.println("DACF-"+ finalI +" Download '"+ hash + "' error");
                    synchronized (DACFError) {
                        System.out.println("DACF " + DACFError.addAndGet(1));
                    }
                }
            },"DACF-"+i).start();
            */
        }
    }

    public static void downloadTestUtils(URL url, String ThreadName, String theoreticalFileHash, File file, int theoreticalFileSize, Attribute Attribute) {
        IThreadManagement iThreadManagement = () -> {
            AtomicBoolean r = new AtomicBoolean();
            PublicVariable.executorService.execute(() -> {
                PublicVariable.threadQuantity++;
                String name = file.getName();
                try {
                    if (Objects.equals(theoreticalFileHash, Utils.fileSha1(file)) && theoreticalFileSize == file.length() && Setup.getSetupInstance().download.ifCheckFileSha1BeforeDownloading) {
                        Output.output(Output.OutputLevel.Debug, ThreadName, "'" + name + "' File already exists and SHA-1 is the same");
                        end++;
                        PublicVariable.threadQuantity--;
                        r.set(true);
                        return;
                    } else {
                        int k = 0;
                        for (int j = 0; j != Setup.getSetupInstance().download.downloadRetries; k++) {
                            if (k == 0) {
                                Output.output(Output.OutputLevel.Ordinary, ThreadName, "The " + j + " attempt to download the file downloading '" + name + "'");
                            }
                            try {
                                downloadTestFile(url, DownloadURL.DownloadSource.values()[k], file);
                                if (Objects.equals(theoreticalFileHash, Utils.fileSha1(file)) & file.length() == theoreticalFileSize) {
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
                    }
                } catch (Exception ignored) {
                }
                Output.output(Output.OutputLevel.Debug, ThreadName, " Download '" + file.getName() + "' fail");
                error++;
                PublicVariable.threadQuantity--;
            });
            return r.get();
        };
        iThreadManagement.run();
    }

    private static void downloadTestFile(URL url, DownloadURL.DownloadSource downloadSource, File file) throws Exception {
        if (downloadSource.ifUse) {
            if (Setup.getSetupInstance().download.threads.multiThreadedDownload.ifMultiThreadedDownloadAFile) {
                utils.Download.MultiThreadedDownloadAFile(url, file);
            } else {
                utils.Download.downloadAFile(url, file);
            }
        } else {
            throw new Exception();
        }
    }
}

