



import JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.*;
import com.google.gson.*;


import java.net.*;
import java.io.*;
import java.nio.file.Paths;


import static JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.MinecraftVersionJsonFileObject.*;


public class Test {
    public static void main(String[] ages) throws Exception {
        //microsoftLogin();
        String mainPath = "E:\\JavaTest\\JMCL\\.minecraft\\";
        String id = "1.17";
        String path = Paths.get("").toAbsolutePath().toString();
        System.out.println(path);
        Gson gson = new GsonBuilder().setVersion(2).create();
        String MinecraftVersionManifestBody = Util.httpHandle(new URL("http://launchermeta.mojang.com/mc/game/version_manifest_v2.json")).toString();
        MinecraftVersionManifestObject MinecraftVersionManifestObject = gson.fromJson(MinecraftVersionManifestBody,
                JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.MinecraftVersionManifestObject.class);
        if(MinecraftVersionProcessing.isId(MinecraftVersionManifestObject,id)){
            String MinecraftVersionJsonFileBody = String.valueOf(Util.httpHandle(MinecraftVersionProcessing.getUrl(MinecraftVersionManifestObject,id)));
            MinecraftVersionJsonFileObject MinecraftVersionJsonFileObject = getGsonObject().fromJson(MinecraftVersionJsonFileBody,
                    JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.MinecraftVersionJsonFileObject.class);
            MinecraftDownloads.downloadsVersionFile(MinecraftVersionProcessing.getUrl(MinecraftVersionManifestObject,id),mainPath,id, MinecraftDownloads.VersionFile.json);
            MinecraftDownloads.downloadsVersionFile(MinecraftVersionJsonFileObject.getDownloads().getClient().getUrl(),mainPath,id, MinecraftDownloads.VersionFile.jar);
            for (int i = 0;i < MinecraftVersionJsonFileObject.getLibraries().length;i++){
                if (MinecraftVersionJsonFileObject.getLibraries()[i].getDownloads().getClassifiers() != null && MinecraftVersionJsonFileObject.getLibraries()[i].getDownloads().getClassifiers().getNativesWindows() != null){
                    MinecraftDownloads.downloadsNativesLibraries(MinecraftVersionJsonFileObject.getLibraries()[i].getDownloads().getClassifiers().getNativesWindows().getUrl(),mainPath,id,path);
                } else if (MinecraftVersionJsonFileObject.getLibraries()[i].getDownloads().getClassifiers() == null) {
                    MinecraftDownloads.downloadsOtherLibraries(MinecraftVersionJsonFileObject.getLibraries()[i].getDownloads().getArtifact().getUrl(),mainPath,MinecraftVersionJsonFileObject.getLibraries()[i].getDownloads().getArtifact().getPath());
                }
            }
        }else {
            System.out.println("This Minecraft version does not exist.");
        }

    }

}