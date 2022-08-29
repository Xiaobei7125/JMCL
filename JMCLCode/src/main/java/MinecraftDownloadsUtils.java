import JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.MinecraftVersionManifestObject;
import JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.MinecraftVersionObject;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public class MinecraftDownloadsUtils {
    static ExecutorService executorService =  Executors.newCachedThreadPool();
    private static void downloadVersionFile(MinecraftVersionObject MinecraftVersionObject, DownloadURL.DownloadSource downloadSource, MinecraftAttribute MinecraftAttribute) throws Exception {
        MinecraftDownload.downloadVersionFile(DownloadURL.versionJarFileURL(MinecraftVersionObject,downloadSource),MinecraftAttribute.mainPath,MinecraftAttribute.id, MinecraftDownload.VersionFile.jar);
    }
    private static void downloadVersionJson(MinecraftVersionManifestObject MinecraftVersionManifestObject,DownloadURL.DownloadSource downloadSource, MinecraftAttribute MinecraftAttribute) throws Exception {
        MinecraftDownload.downloadVersionFile(DownloadURL.versionJsonFileURL(MinecraftVersionManifestObject,MinecraftAttribute.id,downloadSource),MinecraftAttribute.mainPath,MinecraftAttribute.id, MinecraftDownload.VersionFile.json);
    }
    private static void downloadNativesDllLibraries(MinecraftVersionObject MinecraftVersionObject,DownloadURL.DownloadSource downloadSource,MinecraftAttribute MinecraftAttribute,int i) throws Exception {
        MinecraftDownload.downloadNativesDllLibraries(DownloadURL.nativesJarURL(MinecraftVersionObject,downloadSource,i),MinecraftAttribute.mainPath,MinecraftAttribute.id,MinecraftAttribute.runPath);
    }
    private static void downloadOtherJarLibraries(MinecraftVersionObject MinecraftVersionObject,DownloadURL.DownloadSource downloadSource,MinecraftAttribute MinecraftAttribute,int i) throws Exception {
        MinecraftDownload.downloadOtherLibraries(DownloadURL.otherJarLibrariesURL(MinecraftVersionObject,downloadSource,i),MinecraftAttribute.mainPath,MinecraftVersionObject.getLibraries()[i].getDownloads().getArtifact().getPath());
    }
    private static void downloadLog4jFile(MinecraftVersionObject MinecraftVersionObject,DownloadURL.DownloadSource downloadSource, MinecraftAttribute MinecraftAttribute) throws Exception {
        MinecraftDownload.downloadLog4jFile(DownloadURL.Log4jFileURL(MinecraftVersionObject, downloadSource),MinecraftAttribute.mainPath,MinecraftVersionObject.getLogging().getClient().getFile().getId());
    }
    private static void downloadAssetIndexJson(MinecraftVersionObject MinecraftVersionObject,DownloadURL.DownloadSource downloadSource,MinecraftAttribute MinecraftAttribute) throws Exception {
        MinecraftDownload.downloadAssetIndexJson(DownloadURL.assetIndexJsonURL(MinecraftVersionObject,downloadSource),MinecraftAttribute.mainPath,MinecraftVersionObject.getAssetIndex().getId());
    }
    private static void downloadAssetIndexFile(MinecraftAttribute MinecraftAttribute,DownloadURL.DownloadSource downloadSource,String hash) throws Exception {
        MinecraftDownload.downloadAssetIndexFile(DownloadURL.assetIndexFileURL(hash, downloadSource),MinecraftAttribute.mainPath,hash);
    }
    private static void downloadAssetIndexCopyFile(MinecraftAttribute MinecraftAttribute,DownloadURL.DownloadSource downloadSource,String path,String hash) throws Exception {
        MinecraftDownload.downloadAssetIndexCopyFile(DownloadURL.assetIndexFileURL(hash, downloadSource),MinecraftAttribute.mainPath,path);
    }
    private static void downloadVersionFileUtil(MinecraftVersionObject MinecraftVersionObject, MinecraftAttribute MinecraftAttribute) throws Exception {
        try {
            downloadVersionFile(MinecraftVersionObject, DownloadURL.DownloadSource.official,MinecraftAttribute);
        }catch (Exception e) {
            try {
                downloadVersionFile(MinecraftVersionObject, DownloadURL.DownloadSource.bmclapi,MinecraftAttribute);
            }catch (Exception f){
                downloadVersionFile(MinecraftVersionObject, DownloadURL.DownloadSource.mcbbs,MinecraftAttribute);
            }
        }
    }
    private static void downloadVersionJsonUtil(MinecraftVersionManifestObject MinecraftVersionManifestObject, MinecraftAttribute MinecraftAttribute) throws Exception {
        try {
            downloadVersionJson(MinecraftVersionManifestObject, DownloadURL.DownloadSource.official,MinecraftAttribute);
        }catch (Exception e) {
            try {
                downloadVersionJson(MinecraftVersionManifestObject, DownloadURL.DownloadSource.bmclapi,MinecraftAttribute);
            }catch (Exception f){
                downloadVersionJson(MinecraftVersionManifestObject, DownloadURL.DownloadSource.mcbbs,MinecraftAttribute);
            }
        }
    }
    private static void downloadNativesDllLibrariesUtil(MinecraftVersionObject MinecraftVersionObject,MinecraftAttribute MinecraftAttribute,int i) throws Exception {
        try {
            downloadNativesDllLibraries(MinecraftVersionObject, DownloadURL.DownloadSource.official,MinecraftAttribute,i);
        }catch (Exception e) {
            try {
                downloadNativesDllLibraries(MinecraftVersionObject, DownloadURL.DownloadSource.bmclapi,MinecraftAttribute,i);
            }catch (Exception f){
                downloadNativesDllLibraries(MinecraftVersionObject, DownloadURL.DownloadSource.mcbbs,MinecraftAttribute,i);
            }
        }
    }
    private static void downloadOtherJarLibrariesUtil(MinecraftVersionObject MinecraftVersionObject,MinecraftAttribute MinecraftAttribute,int i) throws Exception {
        try {
            downloadOtherJarLibraries(MinecraftVersionObject, DownloadURL.DownloadSource.official,MinecraftAttribute,i);
        }catch (Exception e) {
            try {
                downloadOtherJarLibraries(MinecraftVersionObject, DownloadURL.DownloadSource.bmclapi,MinecraftAttribute,i);
            }catch (Exception f){
                downloadOtherJarLibraries(MinecraftVersionObject, DownloadURL.DownloadSource.mcbbs,MinecraftAttribute,i);
            }
        }
    }
    private static void downloadLog4jFileUtil(MinecraftVersionObject MinecraftVersionObject, MinecraftAttribute MinecraftAttribute) throws Exception {
        try {
            downloadLog4jFile(MinecraftVersionObject, DownloadURL.DownloadSource.official,MinecraftAttribute);
        }catch (Exception e) {
            try {
                downloadLog4jFile(MinecraftVersionObject, DownloadURL.DownloadSource.bmclapi,MinecraftAttribute);
            }catch (Exception f){
                downloadLog4jFile(MinecraftVersionObject, DownloadURL.DownloadSource.mcbbs,MinecraftAttribute);
            }
        }
    }
    private static void downloadAssetIndexJsonUtil(MinecraftVersionObject MinecraftVersionObject,MinecraftAttribute MinecraftAttribute) throws Exception{
        try {
            downloadAssetIndexJson(MinecraftVersionObject, DownloadURL.DownloadSource.official,MinecraftAttribute);
        }catch (Exception e) {
            try {
                downloadAssetIndexJson(MinecraftVersionObject, DownloadURL.DownloadSource.bmclapi,MinecraftAttribute);
            }catch (Exception f){
                downloadAssetIndexJson(MinecraftVersionObject, DownloadURL.DownloadSource.mcbbs,MinecraftAttribute);
            }
        }
    }
    private static void downloadAssetIndexFileUtil(MinecraftAttribute MinecraftAttribute,String hash) throws Exception{
        try {
            downloadAssetIndexFile(MinecraftAttribute, DownloadURL.DownloadSource.official,hash);
        }catch (Exception e) {
            try {
                downloadAssetIndexFile(MinecraftAttribute, DownloadURL.DownloadSource.bmclapi,hash);
            }catch (Exception f){
                downloadAssetIndexFile(MinecraftAttribute, DownloadURL.DownloadSource.mcbbs,hash);
            }
        }
    }
    private static void downloadAssetIndexCopyFileUtil(MinecraftAttribute MinecraftAttribute,String path,String hash) throws Exception{
        try {
            downloadAssetIndexCopyFile(MinecraftAttribute, DownloadURL.DownloadSource.official,path,hash);
        }catch (Exception e) {
            try {
                downloadAssetIndexCopyFile(MinecraftAttribute, DownloadURL.DownloadSource.bmclapi,path,hash);
            }catch (Exception f){
                downloadAssetIndexCopyFile(MinecraftAttribute, DownloadURL.DownloadSource.mcbbs,path,hash);
            }
        }
    }
    public static void downloadsVersionFileUtils(MinecraftVersionObject MinecraftVersionObject, MinecraftAttribute MinecraftAttribute){
        String name = MinecraftAttribute.id + ".json";
        executorService.execute(()-> {
            try {
                System.out.println("DL-VF" + " downloading AssetIndex: '" + name + "'");
                MinecraftDownloadsUtils.downloadVersionFileUtil(MinecraftVersionObject,MinecraftAttribute);
                System.out.println("DL-VF" + " Download '" + name + "' end");
            } catch (Exception e) {
                System.out.println("DL-VF" + " Download '" + name + "' error");
            }
        });
    }
    public static void downloadsVersionJsonUtils(MinecraftVersionManifestObject MinecraftVersionManifestObject, MinecraftAttribute MinecraftAttribute){
        String name = MinecraftAttribute.id + ".jar";
        executorService.execute(()-> {
            try {
                System.out.println("DL-VJ" + " downloading AssetIndex: '" + name + "'");
                MinecraftDownloadsUtils.downloadVersionJsonUtil(MinecraftVersionManifestObject,MinecraftAttribute);
                System.out.println("DL-VJ" + " Download '" + name + "' end");
            } catch (Exception e) {
                System.out.println("DL-VJ" + " Download '" + name + "' error");
            }
        });
    }
    public static void downloadLog4jFileUtils(MinecraftVersionObject MinecraftVersionObject, MinecraftAttribute MinecraftAttribute){
        String name = MinecraftVersionObject.getLogging().getClient().getFile().getId();
        executorService.execute(()-> {
            try {
                System.out.println("DL-LF" + " downloading AssetIndex: '" + name + "'");
                MinecraftDownloadsUtils.downloadLog4jFileUtil(MinecraftVersionObject,MinecraftAttribute);
                System.out.println("DL-LF" + " Download '" + name + "' end");
            } catch (Exception e) {
                System.out.println("DL-LF" + " Download '" + name + "' error");
            }
        });
    }
    public static void downloadAssetIndexJsonUtils(MinecraftVersionObject MinecraftVersionObject,MinecraftAttribute MinecraftAttribute){
        String name = MinecraftVersionObject.getAssetIndex().getId();
        executorService.execute(()-> {
            try {
                System.out.println("DL-AJ" + " downloading AssetIndex: '" + name + "'");
                MinecraftDownloadsUtils.downloadAssetIndexJsonUtil(MinecraftVersionObject,MinecraftAttribute);
                System.out.println("DL-AJ" + " Download '" + name + "' end");
            } catch (Exception e) {
                System.out.println("DL-AJ" + " Download '" + name + "' error");
            }
        });
    }
    public static void downloadsLibrariesUtils(MinecraftVersionObject MinecraftVersionObject,MinecraftAttribute MinecraftAttribute) throws Exception {
        AtomicInteger DNDLEnd = new AtomicInteger();
        AtomicInteger DOJLEnd = new AtomicInteger();
        AtomicInteger DNDLError = new AtomicInteger();
        AtomicInteger DOJLError = new AtomicInteger();
        for (int i = 0;i < MinecraftVersionObject.getLibraries().length;i++){
            int finalI = i;
            if (MinecraftVersionObject.getLibraries()[i].getDownloads().getClassifiers() != null && MinecraftVersionObject.getLibraries()[i].getDownloads().getClassifiers().getNativesWindows() != null){
                String name = new File(DownloadURL.nativesJarURL(MinecraftVersionObject, DownloadURL.DownloadSource.official,i).getPath()).getName();
                executorService.execute(()-> {
                    try {
                        System.out.println("DL-ND-" + finalI + " downloading AssetIndex: '" + name + "'");
                        MinecraftDownloadsUtils.downloadNativesDllLibrariesUtil(MinecraftVersionObject, MinecraftAttribute, finalI);
                        System.out.println("DL-ND-" + finalI + " Download '" + name + "' end");
                        synchronized (DNDLEnd) {
                            System.out.println("DL-ND " + DNDLEnd.addAndGet(1) + "/" + MinecraftVersionObject.getLibraries().length);
                        }
                    } catch (Exception e) {
                        System.out.println("DL-ND-" + finalI + " Download '" + name + "' error");
                        synchronized (DNDLError) {
                            System.out.println("DL-ND " + DNDLError.addAndGet(1));
                        }
                    }
                });
                /*
                new Thread(()-> {
                    try {
                        System.out.println("DL-ND-" + finalI + " downloading AssetIndex: '" + name + "'");
                        MinecraftDownloadsUtils.downloadsNativesDllLibrariesUtils(MinecraftVersionObject, MinecraftAttribute, finalI);
                        System.out.println("DL-ND-" + finalI + " Download '" + name + "' end");
                        synchronized (DNDLEnd) {
                            System.out.println("DL-ND " + DNDLEnd.addAndGet(1) + "/" + MinecraftVersionObject.getLibraries().length);
                        }
                    } catch (Exception e) {
                        System.out.println("DL-ND-" + finalI + " Download '" + name + "' error");
                        synchronized (DNDLError) {
                            System.out.println("DL-ND " + DNDLError.addAndGet(1));
                        }
                    }
                },"DL-ND-"+i).start();
                */
            } else if (MinecraftVersionObject.getLibraries()[i].getDownloads().getClassifiers() == null) {
                String name = new File(DownloadURL.otherJarLibrariesURL(MinecraftVersionObject, DownloadURL.DownloadSource.official,i).getPath()).getName();
                executorService.execute(()-> {
                    try {
                        System.out.println("DL-OJ-" + finalI + " downloading AssetIndex: '" + name + "'");
                        MinecraftDownloadsUtils.downloadOtherJarLibrariesUtil(MinecraftVersionObject,MinecraftAttribute, finalI);
                        System.out.println("DL-OJ-" + finalI + " Download '" + name + "' end");
                        synchronized (DOJLEnd) {
                            System.out.println("DL-OJ " + DOJLEnd.addAndGet(1) + "/" + MinecraftVersionObject.getLibraries().length);
                        }
                    } catch (Exception e) {
                        System.out.println("DL-OJ-" + finalI + " Download '" + name + "' error");
                        synchronized (DOJLError) {
                            System.out.println("DL-OJ " + DOJLError.addAndGet(1));
                        }
                    }
                });
                /*
                new Thread(()-> {
                    try {
                        System.out.println("DL-OJ-" + finalI + " downloading AssetIndex: '" + name + "'");
                        MinecraftDownloadsUtils.downloadOtherJarLibrariesUtils(MinecraftVersionObject,MinecraftAttribute, finalI);
                        System.out.println("DL-OJ-" + finalI + " Download '" + name + "' end");
                        synchronized (DOJLEnd) {
                            System.out.println("DL-OJ " + DOJLEnd.addAndGet(1) + "/" + MinecraftVersionObject.getLibraries().length);
                        }
                    } catch (Exception e) {
                        System.out.println("DL-OJ-" + finalI + " Download '" + name + "' error");
                        synchronized (DOJLError) {
                            System.out.println("DL-OJ " + DOJLError.addAndGet(1));
                        }
                    }
                },"DL-OJ-"+i).start();
                */
            }
        }
    }
    public static void downloadsAssetIndexUtils(MinecraftVersionObject MinecraftVersionObject,MinecraftAttribute MinecraftAttribute) throws IOException {
        String b = String.valueOf(Utils.deleteSymbol(MinecraftRequest.getMinecraftVersionAssetIndexJson(MinecraftVersionObject),"{"));
        String[] hashArray = Utils.regexMatching(b,"\\w{40}");
        String[] pathArray = Utils.regexMatching(b,"[\\w/]+[.]{1}\\w+");
        AtomicInteger DAILEnd = new AtomicInteger();
        AtomicInteger DACLEnd = new AtomicInteger();
        AtomicInteger DAILError = new AtomicInteger();
        AtomicInteger DACLError = new AtomicInteger();
        for (int i = 0 ;i < hashArray.length;i++) {
            String hash = hashArray[i];
            String path = pathArray[i];
            int finalI = i;
            executorService.execute(() ->{
                try {
                    System.out.println("DA-AI-"+ finalI +" downloading AssetIndex:"+hash+"'");
                    MinecraftDownloadsUtils.downloadAssetIndexFileUtil(MinecraftAttribute,hash);
                    System.out.println("DA-AI-"+ finalI +" Download '"+ hash + "' end");
                    synchronized (DAILEnd) {
                        System.out.println("DA-AI " + DAILEnd.addAndGet(1) + "/" + hashArray.length);
                    }
                } catch (Exception e) {
                    System.out.println("DL-AI-"+ finalI +" Download '"+ hash + "' error");
                    synchronized (DAILError) {
                        System.out.println("DA-AI " + DAILError.addAndGet(1));
                    }
                }
            });
            executorService.execute(()-> {
                try {
                    System.out.println("DA-AC-"+ finalI +" downloading AssetIndex: '"+path+"'");
                    MinecraftDownloadsUtils.downloadAssetIndexCopyFileUtil(MinecraftAttribute,path,hash);
                    System.out.println("DA-AC-"+ finalI +" Download '"+ path + "' end");
                    synchronized (DACLEnd) {
                        System.out.println("DA-AC " + DACLEnd.addAndGet(1) + "/" + pathArray.length);
                    }
                } catch (Exception e) {
                    System.out.println("DA-AC-"+ finalI +" Download '"+ hash + "' error");
                    synchronized (DACLError) {
                        System.out.println("DA-AC " + DACLError.addAndGet(1));
                    }
                }
            });
            /*new Thread(()-> {
                try {
                    System.out.println("DL-AI-"+ finalI +" downloading AssetIndex:"+hash+"'");
                    MinecraftDownloadsUtils.downloadAssetIndexFileUtils(MinecraftAttribute,hash);
                    System.out.println("DL-AI-"+ finalI +" Download '"+ hash + "' end");
                    synchronized (DAILEnd) {
                        System.out.println("DL-AI " + DAILEnd.addAndGet(1) + "/" + hashArray.length);
                    }
                } catch (Exception e) {
                    System.out.println("DL-AI-"+ finalI +" Download '"+ hash + "' error");
                    synchronized (DAILError) {
                        System.out.println("DL-AI " + DAILError.addAndGet(1));
                    }
                }
            },"DL-AI-"+i).start();
            new Thread(()-> {
                try {
                    System.out.println("DACL-"+ finalI +" downloading AssetIndex: '"+path+"'");
                    MinecraftDownloadsUtils.downloadAssetIndexCopyFileUtils(MinecraftAttribute,path,hash);
                    System.out.println("DACL-"+ finalI +" Download '"+ path + "' end");
                    synchronized (DACLEnd) {
                        System.out.println("DACL " + DACLEnd.addAndGet(1) + "/" + pathArray.length);
                    }
                } catch (Exception e) {
                    System.out.println("DACF-"+ finalI +" Download '"+ hash + "' error");
                    synchronized (DACLError) {
                        System.out.println("DACF " + DACLError.addAndGet(1));
                    }
                }
            },"DACF-"+i).start();
            */
        }
    }
}
/*      try {
            return
        }catch (Exception e) {
            try {
                return
            }catch (Exception f){
                return
            }
        }
 */
