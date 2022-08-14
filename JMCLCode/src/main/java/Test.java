



import JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.*;
import com.google.gson.*;


import java.net.*;
import java.io.*;



import static JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.MinecraftVersionJsonFileObject.*;


public class Test {
    public static void main(String[] ages) throws IOException {
        //microsoftLogin();
        String mainPath = "E:\\JavaTest\\JMCL\\.minecraft\\";
        String id = "1.19";
        Gson gson = new GsonBuilder().setVersion(2).create();
        String MinecraftVersionManifestBody = Util.httpHandle(new URL("http://launchermeta.mojang.com/mc/game/version_manifest_v2.json")).toString();
        MinecraftVersionManifestObject MinecraftVersionManifestObject = gson.fromJson(MinecraftVersionManifestBody,
                JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.MinecraftVersionManifestObject.class);
        if(MinecraftVersionProcessing.isId(MinecraftVersionManifestObject,id)){
            String MinecraftVersionJsonFileBody = String.valueOf(Util.httpHandle(MinecraftVersionProcessing.getUrl(MinecraftVersionManifestObject,id)));
            MinecraftVersionJsonFileObject MinecraftVersionJsonFileObject = getGsonObject().fromJson(MinecraftVersionJsonFileBody,
                    JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.MinecraftVersionJsonFileObject.class);
            MinecraftDownloads.downloadsVersionFile(MinecraftVersionJsonFileObject.getAssetIndex().getUrl(),mainPath,id, MinecraftDownloads.versionFile.json);
            MinecraftDownloads.downloadsVersionFile(MinecraftVersionJsonFileObject.getDownloads().getClient().getUrl(),mainPath,id, MinecraftDownloads.versionFile.jar);
        }else {
            System.out.println("This Minecraft version does not exist.");
        }

    }

}