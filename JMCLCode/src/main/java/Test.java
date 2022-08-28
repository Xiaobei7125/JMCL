import JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.MinecraftVersionManifestObject;
import JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.MinecraftVersionObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.*;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.*;

import static JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.MinecraftVersionObject.getGsonObject;


public class Test {
    public static void main(String[] ages) throws Exception {
        //microsoftLogin();
        MinecraftAttribute MinecraftAttribute = new MinecraftAttribute("E:\\JavaTest\\JMCL\\.minecraft\\","1.17",DownloadURL.DownloadSource.bmclapi);
        Gson gson = new GsonBuilder().setVersion(2).create();
        System.out.println(-3);
        String MinecraftVersionManifestJson = String.valueOf(Utils.httpHandle(DownloadURL.versionManifestJsonURL(DownloadURL.VersionManifest.v2, MinecraftAttribute.downloadSource)));
        MinecraftVersionManifestObject MinecraftVersionManifestObject = gson.fromJson(MinecraftVersionManifestJson,
                JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.MinecraftVersionManifestObject.class);
        if(MinecraftVersionProcessing.isId(MinecraftVersionManifestObject,MinecraftAttribute.id)){
            System.out.println(-2);
            String MinecraftVersionJson = String.valueOf(Utils.httpHandle(DownloadURL.versionJsonFileURL(MinecraftVersionManifestObject,MinecraftAttribute.id,MinecraftAttribute.downloadSource)));
            MinecraftVersionObject MinecraftVersionObject = getGsonObject().fromJson(MinecraftVersionJson,
                    JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.MinecraftVersionObject.class);
            System.out.println(-1);
            MinecraftDownloadsUtils.downloadsVersionFileUtils(MinecraftVersionObject,MinecraftAttribute);
            MinecraftDownloadsUtils.downloadsVersionFileJsonUtils(MinecraftVersionManifestObject,MinecraftAttribute);
            for (int i = 0;i < MinecraftVersionObject.getLibraries().length;i++){
                if (MinecraftVersionObject.getLibraries()[i].getDownloads().getClassifiers() != null && MinecraftVersionObject.getLibraries()[i].getDownloads().getClassifiers().getNativesWindows() != null){
                    MinecraftDownloadsUtils.downloadsNativesDllLibrariesUtils(MinecraftVersionObject,MinecraftAttribute,i);
                } else if (MinecraftVersionObject.getLibraries()[i].getDownloads().getClassifiers() == null) {
                    MinecraftDownloadsUtils.downloadOtherJarLibrariesUtils(MinecraftVersionObject,MinecraftAttribute,i);
                }
            }
            System.out.println(3);
            MinecraftDownloadsUtils.downloadLog4jFileUtils(MinecraftVersionObject,MinecraftAttribute);
            System.out.println(4);
            MinecraftDownloadsUtils.downloadAssetIndexJsonUtils(MinecraftVersionObject,MinecraftAttribute);
            System.out.println(5);
            String MinecraftVersionAssetIndexJson = String.valueOf(Utils.httpHandle(Objects.requireNonNull(DownloadURL.assetIndexJsonURL(MinecraftVersionObject, MinecraftAttribute.downloadSource))));

            String b = "";
            for (String i:MinecraftVersionAssetIndexJson.split("")){
                if (i.equals("{")){
                    continue;
                }
                b = b + i;
            }
            Pattern pattern = Pattern.compile("\\w{40}");
            Matcher matcher = pattern.matcher(b);
            String[] n = new String[5000];
            int c = 0 ;
            while (matcher.find()){
                n[c] = matcher.group();
                c++;
            }
            c = 0;
            for (String s : n) {
                if (s != null) c++;
            }
            String[] hashArray = new String[c];
            for (int i = 0; i < n.length; i++){
                if (n[i] != null){
                    hashArray[i] = n[i];
                }else {
                    break;
                }
            }
            Pattern pattern2 = Pattern.compile("[\\w/]+[.]\\w+");
            Matcher matcher2 = pattern2.matcher(b);
            String[] p = new String[5000];
            c = 0 ;
            while (matcher2.find()){
                p[c] = matcher2.group();
                c++;
            }
            c = 0;
            for (String s : p) {
                if (s != null) c++;
            }
            String[] pathArray = new String[c];
            for (int i = 0; i < p.length; i++){
                if (p[i] != null){
                    pathArray[i] = p[i];
                }else {
                    break;
                }
            }
            AtomicInteger DAIFEnd = new AtomicInteger();
            AtomicInteger DACFEnd = new AtomicInteger();
            AtomicInteger DAIFError = new AtomicInteger();
            AtomicInteger DACFError = new AtomicInteger();
            for (int i = 0 ;i < hashArray.length && i < pathArray.length;i++) {
                String hash = hashArray[i];
                String path = pathArray[i];
                URL url = DownloadURL.assetIndexFileURL(hash, MinecraftAttribute.downloadSource);
                int finalI = i;
                new Thread(()-> {
                    try {
                        System.out.println("DAIF-"+ finalI +" downloading AssetIndex:"+hash+"'");
                        MinecraftDownloadsUtils.downloadAssetIndexFileUtils(MinecraftAttribute,hash);
                        System.out.println("DAIF-"+ finalI +" Download '"+ hash + "' end");
                        System.out.println("DAIF "+ DAIFEnd.addAndGet(1)+"/"+hashArray.length);
                    } catch (IOException e) {
                        System.out.println("DAIF-"+ finalI +" Download '"+ hash + "' error");
                        System.out.println("DAIF "+ DAIFError.addAndGet(1));
                    }
                },"DAIF-"+i).start();
                new Thread(()-> {
                    try {
                        System.out.println("DACF-"+ finalI +" downloading AssetIndex: '"+path+"'");
                        MinecraftDownloadsUtils.downloadAssetIndexCopyFileUtils(MinecraftAttribute,path,hash);
                        System.out.println("DACF-"+ finalI +" Download '"+ path + "' end");
                        System.out.println("DACF "+ DACFEnd.addAndGet(1) +"/"+pathArray.length);
                    } catch (IOException e) {
                        System.out.println("DACF-"+ finalI +" Download '"+ hash + "' error");
                        System.out.println("DACF "+ DACFError.addAndGet(1));
                    }
                },"DACF-"+i).start();
            }
        }else {
            System.out.println("This Minecraft version does not exist.");
        }
    }
}