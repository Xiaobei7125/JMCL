import JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.MinecraftVersionManifestObject;
import JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.MinecraftVersionObject;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;


public class MinecraftDownloadsUtils {
    static int end = 0;
    static int error = 0;

    private static void downloadVersionFile(MinecraftVersionObject MinecraftVersionObject, DownloadURL.DownloadSource downloadSource, MinecraftAttribute MinecraftAttribute) throws Exception {
        if (downloadSource.ifUse) {
            MinecraftDownload.downloadVersionFile(DownloadURL.versionJarFileURL(MinecraftVersionObject, downloadSource), MinecraftAttribute.mainPath, MinecraftAttribute.id, MinecraftDownload.VersionFile.jar);
        } else {
            throw new Exception();
        }
    }

    private static void downloadVersionJson(MinecraftVersionManifestObject MinecraftVersionManifestObject, DownloadURL.DownloadSource downloadSource, MinecraftAttribute MinecraftAttribute) throws Exception {
        if (downloadSource.ifUse) {
            MinecraftDownload.downloadVersionFile(DownloadURL.versionJsonFileURL(MinecraftVersionManifestObject, MinecraftAttribute.id, downloadSource), MinecraftAttribute.mainPath, MinecraftAttribute.id, MinecraftDownload.VersionFile.json);
        } else {
            throw new Exception();
        }
    }

    private static void downloadNativesDllLibraries(MinecraftVersionObject MinecraftVersionObject, DownloadURL.DownloadSource downloadSource, MinecraftAttribute MinecraftAttribute, int i) throws Exception {
        if (downloadSource.ifUse) {
            MinecraftDownload.downloadNativesDllLibraries(DownloadURL.nativesJarURL(MinecraftVersionObject, downloadSource, i), MinecraftAttribute.mainPath, MinecraftAttribute.id, MinecraftAttribute.runPath);
        } else {
            throw new Exception();
        }
    }

    private static void downloadOtherJarLibraries(MinecraftVersionObject MinecraftVersionObject, DownloadURL.DownloadSource downloadSource, MinecraftAttribute MinecraftAttribute, int i) throws Exception {
        if (downloadSource.ifUse) {
            MinecraftDownload.downloadOtherLibraries(DownloadURL.otherJarLibrariesURL(MinecraftVersionObject, downloadSource, i), MinecraftAttribute.mainPath, MinecraftVersionObject.getLibraries()[i].getDownloads().getArtifact().getPath());
        } else {
            throw new Exception();
        }
    }

    private static void downloadLog4jFile(MinecraftVersionObject MinecraftVersionObject, DownloadURL.DownloadSource downloadSource, MinecraftAttribute MinecraftAttribute) throws Exception {
        if (downloadSource.ifUse) {
            MinecraftDownload.downloadLog4jFile(DownloadURL.Log4jFileURL(MinecraftVersionObject, downloadSource), MinecraftAttribute.mainPath, MinecraftVersionObject.getLogging().getClient().getFile().getId());
        } else {
            throw new Exception();
        }
    }

    private static void downloadAssetIndexJson(MinecraftVersionObject MinecraftVersionObject, DownloadURL.DownloadSource downloadSource, MinecraftAttribute MinecraftAttribute) throws Exception {
        if (downloadSource.ifUse) {
            MinecraftDownload.downloadAssetIndexJson(DownloadURL.assetIndexJsonURL(MinecraftVersionObject, downloadSource), MinecraftAttribute.mainPath, MinecraftVersionObject.getAssetIndex().getId());
        } else {
            throw new Exception();
        }
    }

    private static void downloadAssetIndexFile(MinecraftAttribute MinecraftAttribute, DownloadURL.DownloadSource downloadSource, String hash) throws Exception {
        if (downloadSource.ifUse) {
            MinecraftDownload.downloadAssetIndexFile(DownloadURL.assetIndexFileURL(hash, downloadSource), MinecraftAttribute.mainPath, hash);
        } else {
            throw new Exception();
        }
    }

    private static void downloadAssetIndexCopyFile(MinecraftAttribute MinecraftAttribute, DownloadURL.DownloadSource downloadSource, String path, String hash) throws Exception {
        if (downloadSource.ifUse) {
            MinecraftDownload.downloadAssetIndexCopyFile(DownloadURL.assetIndexFileURL(hash, downloadSource), MinecraftAttribute.mainPath, path);
        } else {
            throw new Exception();
        }
    }

    public static void downloadsVersionFileUtils(MinecraftVersionObject MinecraftVersionObject, MinecraftAttribute MinecraftAttribute) {
        IThreadManagement iThreadManagement = () -> {
            String name = MinecraftAttribute.id + "." + MinecraftDownload.VersionFile.jar;
            PublicVariable.executorService.execute(() -> {
                //下载路径
                String path = MinecraftAttribute.mainPath + "versions\\" + MinecraftAttribute.id + "\\";
                //从下载处获得的sha1
                String standardSha1 = MinecraftVersionObject.getDownloads().getClient().getSha1();
                int standardSize = MinecraftVersionObject.getDownloads().getClient().getSize();
                File file = new File(path + name);
                try {
                    if (Objects.equals(standardSha1, Utils.fileSha1(file)) && standardSize == file.length() && SetUp.ifCheckFileSha1BeforeDownloading) {
                        System.out.println("DL-VF '" + name + "' File already exists and SHA-1 is the same");
                        end++;
                        SetUp.threadQuantity--;
                        return;
                    } else {
                        for (int j = 0; j != SetUp.downloadRetries; j++) {
                            try {
                                System.out.println("DL-VF-0-" + j + " downloading '" + name + "'");
                                try {
                                    downloadVersionFile(MinecraftVersionObject, DownloadURL.DownloadSource.official, MinecraftAttribute);
                                } catch (Exception e) {
                                    try {
                                        downloadVersionFile(MinecraftVersionObject, DownloadURL.DownloadSource.bmclapi, MinecraftAttribute);
                                    } catch (Exception f) {
                                        downloadVersionFile(MinecraftVersionObject, DownloadURL.DownloadSource.mcbbs, MinecraftAttribute);
                                    }
                                }
                                System.out.println(standardSha1 + "," + Utils.fileSha1(file));
                                if (Objects.equals(standardSha1, Utils.fileSha1(file)) & file.length() == standardSize) {
                                    System.out.println("DL-VF" + " Download '" + name + "' end");
                                    end++;
                                    SetUp.threadQuantity--;
                                    return;
                                }
                            } catch (Exception e) {
                                System.out.println("DL-VF-0-" + j + " Download '" + name + "' error");
                            }
                        }
                    }
                    System.out.println("DL-VF-0" + " Download '" + name + "' error");
                    error++;
                    SetUp.threadQuantity--;
                } catch (NoSuchAlgorithmException | IOException ignored) {
                }
            });
        };
        iThreadManagement.run();
    }

    public static void downloadsVersionJsonUtils(MinecraftVersionManifestObject MinecraftVersionManifestObject, MinecraftAttribute MinecraftAttribute) {
        IThreadManagement iThreadManagement = () -> {
            String name = MinecraftAttribute.id + "." + MinecraftDownload.VersionFile.json;
            PublicVariable.executorService.execute(() -> {
                String path = MinecraftAttribute.mainPath + "versions\\" + MinecraftAttribute.id + "\\";
                String standardSha1 = MinecraftVersionManifestObject.getSha1(MinecraftAttribute.id);
                File file = new File(path + name);
                try {
                    if (Objects.equals(standardSha1, Utils.fileSha1(file)) && SetUp.ifCheckFileSha1BeforeDownloading) {
                        System.out.println("DL-VJ '" + name + "' File already exists and SHA-1 is the same");
                        end++;
                        SetUp.threadQuantity--;
                        return;
                    } else {
                        for (int j = 0; j != SetUp.downloadRetries; j++) {
                            try {
                                System.out.println("DL-VJ-0-" + j + " downloading '" + name + "'");
                                try {
                                    downloadVersionJson(MinecraftVersionManifestObject, DownloadURL.DownloadSource.official, MinecraftAttribute);
                                } catch (Exception e) {
                                    try {
                                        downloadVersionJson(MinecraftVersionManifestObject, DownloadURL.DownloadSource.bmclapi, MinecraftAttribute);
                                    } catch (Exception f) {
                                        downloadVersionJson(MinecraftVersionManifestObject, DownloadURL.DownloadSource.mcbbs, MinecraftAttribute);
                                    }
                                }
                                if (Objects.equals(standardSha1, Utils.fileSha1(file))) {
                                    System.out.println("DL-VJ" + " Download '" + name + "' end");
                                    end++;
                                    SetUp.threadQuantity--;
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
                SetUp.threadQuantity--;
            });
        };
        iThreadManagement.run();
    }

    public static void downloadLog4jFileUtils(MinecraftVersionObject MinecraftVersionObject, MinecraftAttribute MinecraftAttribute) {
        IThreadManagement iThreadManagement = () -> {
            if (MinecraftVersionObject.getLogging() == null) {
                return;
            }
            String name = MinecraftVersionObject.getLogging().getClient().getFile().getId();
            PublicVariable.executorService.execute(() -> {
                String path = MinecraftAttribute.mainPath + "assets\\log_configs\\";
                String standardSha1 = MinecraftVersionObject.getLogging().getClient().getFile().getSha1();
                int standardSize = MinecraftVersionObject.getLogging().getClient().getFile().getSize();
                File file = new File(path + name);
                try {
                    if (Objects.equals(standardSha1, Utils.fileSha1(new File(path + name))) && standardSize == file.length() && SetUp.ifCheckFileSha1BeforeDownloading) {
                        System.out.println("DL-LF '" + name + "' File already exists and SHA-1 is the same");
                        end++;
                        SetUp.threadQuantity--;
                        return;
                    } else {
                        for (int j = 0; j != SetUp.downloadRetries; j++) {
                            try {
                                System.out.println("DL-LF-" + j + " downloading: '" + name + "'");
                                try {
                                    downloadLog4jFile(MinecraftVersionObject, DownloadURL.DownloadSource.official, MinecraftAttribute);
                                } catch (Exception e) {
                                    try {
                                        downloadLog4jFile(MinecraftVersionObject, DownloadURL.DownloadSource.bmclapi, MinecraftAttribute);
                                    } catch (Exception f) {
                                        downloadLog4jFile(MinecraftVersionObject, DownloadURL.DownloadSource.mcbbs, MinecraftAttribute);
                                    }
                                }
                                if (Objects.equals(standardSha1, Utils.fileSha1(file)) && standardSize == file.length() && SetUp.ifCheckFileSha1BeforeDownloading) {
                                    System.out.println("DL-LF" + " Download '" + name + "' end");
                                    end++;
                                    SetUp.threadQuantity--;
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
                SetUp.threadQuantity--;
            });
        };
        iThreadManagement.run();
    }

    public static void downloadAssetIndexJsonUtils(MinecraftVersionObject MinecraftVersionObject, MinecraftAttribute MinecraftAttribute) {
        IThreadManagement iThreadManagement = () -> {
            String name = MinecraftVersionObject.getAssetIndex().getId() + ".json";
            PublicVariable.executorService.execute(() -> {
                String path = MinecraftAttribute.mainPath + "assets\\indexes\\";
                String standardSha1 = MinecraftVersionObject.getAssetIndex().getSha1();
                int standardSize = MinecraftVersionObject.getAssetIndex().getSize();
                File file = new File(path + name);
                try {
                    if (Objects.equals(standardSha1, Utils.fileSha1(file)) && standardSize == file.length() && SetUp.ifCheckFileSha1BeforeDownloading) {
                        System.out.println("DL-AJ '" + name + "' File already exists and SHA-1 is the same");
                        end++;
                        SetUp.threadQuantity--;
                        return;
                    } else {
                        for (int j = 0; j != SetUp.downloadRetries; j++) {
                            try {
                                System.out.println("DL-AJ-" + j + " downloading '" + name + "'");
                                try {
                                    downloadAssetIndexJson(MinecraftVersionObject, DownloadURL.DownloadSource.official, MinecraftAttribute);
                                } catch (Exception e) {
                                    try {
                                        downloadAssetIndexJson(MinecraftVersionObject, DownloadURL.DownloadSource.bmclapi, MinecraftAttribute);
                                    } catch (Exception f) {
                                        downloadAssetIndexJson(MinecraftVersionObject, DownloadURL.DownloadSource.mcbbs, MinecraftAttribute);
                                    }
                                }
                                System.out.println(standardSha1 + "," + Utils.fileSha1(file));
                                if (Objects.equals(standardSha1, Utils.fileSha1(file)) & file.length() == standardSize) {
                                    System.out.println("DL-AJ" + " Download '" + name + "' end");
                                    end++;
                                    SetUp.threadQuantity--;
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
                SetUp.threadQuantity--;
            });
        };
        iThreadManagement.run();
    }

    public static void downloadsLibrariesUtils(MinecraftVersionObject MinecraftVersionObject, MinecraftAttribute MinecraftAttribute) throws Exception {
        AtomicInteger DLNDEnd = new AtomicInteger();
        AtomicInteger DLOJEnd = new AtomicInteger();
        AtomicInteger DLNDError = new AtomicInteger();
        AtomicInteger DLOJError = new AtomicInteger();
        AtomicInteger DLNDadd = new AtomicInteger();
        AtomicInteger DLOJadd = new AtomicInteger();
        for (int i = 0; i < MinecraftVersionObject.getLibraries().length; i++) {
            int finalI = i;
            if (MinecraftVersionObject.getLibraries()[i].getDownloads().getClassifiers() != null &&
                    MinecraftVersionObject.getLibraries()[i].getDownloads().getClassifiers().getNativesWindows() != null) {
                String name = new File(DownloadURL.nativesJarURL(MinecraftVersionObject, DownloadURL.DownloadSource.official, i).getPath()).getName();
                IThreadManagement iThreadManagement = () -> PublicVariable.executorService.execute(() -> {
                    for (int j = 0; j != SetUp.downloadRetries; j++) {
                        try {
                            System.out.println("DL-ND-" + finalI + "-" + j + " downloading '" + name + "'");
                            try {
                                downloadNativesDllLibraries(MinecraftVersionObject, DownloadURL.DownloadSource.official, MinecraftAttribute, finalI);
                            } catch (Exception e) {
                                try {
                                    downloadNativesDllLibraries(MinecraftVersionObject, DownloadURL.DownloadSource.bmclapi, MinecraftAttribute, finalI);
                                } catch (Exception f) {
                                    downloadNativesDllLibraries(MinecraftVersionObject, DownloadURL.DownloadSource.mcbbs, MinecraftAttribute, finalI);
                                }
                            }
                            System.out.println("DL-ND-" + finalI + " Download '" + name + "' end");
                            end++;
                            synchronized (DLNDEnd) {
                                synchronized ((DLNDError)) {
                                    synchronized ((DLNDadd)) {
                                        DLNDEnd.addAndGet(1);
                                        DLNDadd.set(DLNDEnd.get() + DLNDError.get());
                                        System.out.println("DL-ND " + DLNDEnd + "/" + DLNDadd + "/" + MinecraftVersionObject.getLibraries().length);
                                    }
                                }
                            }
                            SetUp.threadQuantity--;
                            return;
                        } catch (Exception e) {
                            System.out.println("DL-ND-" + finalI + "-" + j + " Download '" + name + "' error");
                        }
                    }
                    //}
                    System.out.println("DL-ND-" + finalI + " Download '" + name + "' error");
                    error++;
                    synchronized (DLNDEnd) {
                        synchronized ((DLNDError)) {
                            synchronized ((DLNDadd)) {
                                DLNDError.addAndGet(1);
                                DLNDadd.set(DLNDEnd.get() + DLNDError.get());
                                System.out.println("DL-ND " + DLNDError + "/" + DLNDadd + "/" + " error");
                            }
                        }
                    }
                    SetUp.threadQuantity--;
                });
                iThreadManagement.run();
                /*
                new Thread(()-> {
                    try {
                        System.out.println("DL-ND-" + finalI + " downloading AssetIndex: '" + name + "'");
                        MinecraftDownloadsUtils.downloadsNativesDllLibrariesUtils(MinecraftVersionObject, MinecraftAttribute, finalI);
                        System.out.println("DL-ND-" + finalI + " Download '" + name + "' end");
                        synchronized (DLNDEnd) {
                            System.out.println("DL-ND " + DLNDEnd.addAndGet(1) + "/" + MinecraftVersionObject.getLibraries().length);
                        }
                    } catch (Exception e) {
                        System.out.println("DL-ND-" + finalI + " Download '" + name + "' error");
                        synchronized (DLNDError) {
                            System.out.println("DL-ND " + DLNDError.addAndGet(1));
                        }
                    }
                },"DL-ND-"+i).start();
                */
            } else if (MinecraftVersionObject.getLibraries()[i].getDownloads().getClassifiers() == null) {
                String name = new File(DownloadURL.otherJarLibrariesURL(MinecraftVersionObject, DownloadURL.DownloadSource.official, i).getPath()).getName();
                IThreadManagement iThreadManagement = () -> {
                    PublicVariable.executorService.execute(() -> {
                        String path = MinecraftAttribute.mainPath + "libraries\\" + Utils.regexReplace(MinecraftVersionObject.getLibraries()[finalI].getDownloads().getArtifact().getPath(), name, "");
                        String standardSha1 = MinecraftVersionObject.getLibraries()[finalI].getDownloads().getArtifact().getSha1();
                        int standardSize = MinecraftVersionObject.getLibraries()[finalI].getDownloads().getArtifact().getSize();
                        File file = new File(path + name);
                        try {
                            if (Objects.equals(standardSha1, Utils.fileSha1(file)) && standardSize == file.length() && SetUp.ifCheckFileSha1BeforeDownloading) {
                                System.out.println("DL-OJ '" + name + "' File already exists and SHA-1 is the same");
                                end++;
                                synchronized (DLOJEnd) {
                                    synchronized ((DLOJError)) {
                                        synchronized ((DLOJadd)) {
                                            DLOJEnd.addAndGet(1);
                                            DLOJadd.set(DLOJEnd.get() + DLOJError.get());
                                            System.out.println("DL-OJ " + DLOJEnd + "/" + DLOJadd + "/" + MinecraftVersionObject.getLibraries().length);
                                        }
                                    }
                                }
                                SetUp.threadQuantity--;
                                return;
                            } else {
                                for (int j = 0; j != SetUp.downloadRetries; j++) {
                                    try {
                                        System.out.println("DL-OJ-" + finalI + "-" + j + " downloading '" + name + "'");
                                        try {
                                            downloadOtherJarLibraries(MinecraftVersionObject, DownloadURL.DownloadSource.official, MinecraftAttribute, finalI);
                                        } catch (Exception e) {
                                            try {
                                                downloadOtherJarLibraries(MinecraftVersionObject, DownloadURL.DownloadSource.bmclapi, MinecraftAttribute, finalI);
                                            } catch (Exception f) {
                                                downloadOtherJarLibraries(MinecraftVersionObject, DownloadURL.DownloadSource.mcbbs, MinecraftAttribute, finalI);
                                            }
                                        }
                                        if (Objects.equals(standardSha1, Utils.fileSha1(file)) & file.length() == standardSize) {
                                            System.out.println("DL-OJ-" + finalI + " Download '" + name + "' end");
                                            end++;
                                            synchronized (DLOJEnd) {
                                                synchronized ((DLOJError)) {
                                                    synchronized ((DLOJadd)) {
                                                        DLOJEnd.addAndGet(1);
                                                        DLOJadd.set(DLOJEnd.get() + DLOJError.get());
                                                        System.out.println("DL-OJ " + DLOJEnd + "/" + DLOJadd + "/" + MinecraftVersionObject.getLibraries().length);
                                                    }
                                                }
                                            }
                                            SetUp.threadQuantity--;
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
                        synchronized (DLOJEnd) {
                            synchronized ((DLOJError)) {
                                synchronized ((DLOJadd)) {
                                    DLOJError.addAndGet(1);
                                    DLOJadd.set(DLOJEnd.get() + DLOJError.get());
                                    System.out.println("DL-OJ " + DLOJError + "/" + DLOJadd + "/" + " error");
                                }
                            }
                        }
                        SetUp.threadQuantity--;
                    });
                /*
                new Thread(()-> {
                    try {
                        System.out.println("DL-OJ-" + finalI + " downloading AssetIndex: '" + name + "'");
                        MinecraftDownloadsUtils.downloadOtherJarLibrariesUtils(MinecraftVersionObject,MinecraftAttribute, finalI);
                        System.out.println("DL-OJ-" + finalI + " Download '" + name + "' end");
                        synchronized (DLOJEnd) {
                            System.out.println("DL-OJ " + DLOJEnd.addAndGet(1) + "/" + MinecraftVersionObject.getLibraries().length);
                        }
                    } catch (Exception e) {
                        System.out.println("DL-OJ-" + finalI + " Download '" + name + "' error");
                        synchronized (DLOJError) {
                            System.out.println("DL-OJ " + DLOJError.addAndGet(1));
                        }
                    }
                },"DL-OJ-"+i).start();
                */
                };
                iThreadManagement.run();
            }
        }
    }

    public static void downloadsAssetIndexUtils(MinecraftVersionObject MinecraftVersionObject, MinecraftAttribute MinecraftAttribute) throws IOException {
        String b = String.valueOf(Utils.deleteSymbol(MinecraftRequest.getMinecraftVersionAssetIndexJson(MinecraftVersionObject), "{"));
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
            IThreadManagement iThreadManagement = () -> PublicVariable.executorService.execute(() -> {
                String path1 = MinecraftAttribute.mainPath + "assets\\objects\\" + hash.substring(0, 2) + "\\";
                File file = new File(path1 + hash);
                try {
                    if (Objects.equals(hash, Utils.fileSha1(file)) && size == file.length() && SetUp.ifCheckFileSha1BeforeDownloading) {
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
                        SetUp.threadQuantity--;
                        return;
                    } else {
                        for (int j = 0; j != SetUp.downloadRetries; j++) {
                            try {
                                System.out.println("DA-AI-" + finalI + "-" + j + " downloading '" + hash + "'");
                                try {
                                    downloadAssetIndexFile(MinecraftAttribute, DownloadURL.DownloadSource.official, hash);
                                } catch (Exception e) {
                                    try {
                                        downloadAssetIndexFile(MinecraftAttribute, DownloadURL.DownloadSource.bmclapi, hash);
                                    } catch (Exception f) {
                                        downloadAssetIndexFile(MinecraftAttribute, DownloadURL.DownloadSource.mcbbs, hash);
                                    }
                                }
                                if (Objects.equals(hash, Utils.fileSha1(file)) & file.length() == size) {
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
                                    SetUp.threadQuantity--;
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
                SetUp.threadQuantity--;
            });
            iThreadManagement.run();
            iThreadManagement = () -> PublicVariable.executorService.execute(() -> {
                if (!SetUp.ifDownloadAssetIndexCopy) {
                    return;
                }
                String path1 = MinecraftAttribute.mainPath + "assets\\virtual\\legacy\\" + Utils.regexReplace(path, Utils.regexReplace(path, "[\\w/]+/", ""), "");
                File file = new File(path1 + Utils.regexReplace(path, "[\\w/]+/", ""));
                try {
                    if (Objects.equals(hash, Utils.fileSha1(file)) && size == file.length() && SetUp.ifCheckFileSha1BeforeDownloading) {
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
                        SetUp.threadQuantity--;
                        return;
                    } else {
                        for (int j = 0; j != SetUp.downloadRetries; j++) {
                            try {
                                System.out.println("DA-AC-" + finalI + "-" + j + " downloading '" + path + "'");
                                try {
                                    downloadAssetIndexCopyFile(MinecraftAttribute, DownloadURL.DownloadSource.official, path, hash);
                                } catch (Exception e) {
                                    try {
                                        downloadAssetIndexCopyFile(MinecraftAttribute, DownloadURL.DownloadSource.bmclapi, path, hash);
                                    } catch (Exception f) {
                                        downloadAssetIndexCopyFile(MinecraftAttribute, DownloadURL.DownloadSource.mcbbs, path, hash);
                                    }
                                }
                                if (Objects.equals(hash, Utils.fileSha1(file)) & file.length() == size) {
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
                                    SetUp.threadQuantity--;
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
                SetUp.threadQuantity--;
            });
            iThreadManagement.run();
            /*new Thread(()-> {
                try {
                    System.out.println("DL-AI-"+ finalI +" downloading AssetIndex:"+hash+"'");
                    MinecraftDownloadsUtils.downloadAssetIndexFileUtils(MinecraftAttribute,hash);
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
                    MinecraftDownloadsUtils.downloadAssetIndexCopyFileUtils(MinecraftAttribute,path,hash);
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
}

