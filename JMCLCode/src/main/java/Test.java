import JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.*;
import com.google.gson.*;


import java.net.*;
import java.nio.file.Paths;
import java.util.regex.*;


import static JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.MinecraftVersionJsonFileObject.*;
import static com.google.gson.JsonParser.parseString;


public class Test {
    public static void main(String[] ages) throws Exception {
        //microsoftLogin();
        String mainPath = "E:\\JavaTest\\JMCL\\.minecraft\\";
        String id = "1.17";
        String path = Paths.get("").toAbsolutePath().toString();
        System.out.println(path);
        Gson gson = new GsonBuilder().setVersion(2).create();
        String MinecraftVersionManifestJson = String.valueOf(Util.httpHandle(new URL("http://launchermeta.mojang.com/mc/game/version_manifest_v2.json")));
        MinecraftVersionManifestObject MinecraftVersionManifestObject = gson.fromJson(MinecraftVersionManifestJson,
                JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.MinecraftVersionManifestObject.class);
        if(MinecraftVersionProcessing.isId(MinecraftVersionManifestObject,id)){
            String MinecraftVersionJsonFileJson = String.valueOf(Util.httpHandle(MinecraftVersionProcessing.getUrl(MinecraftVersionManifestObject,id)));
            MinecraftVersionJsonFileObject MinecraftVersionJsonFileObject = getGsonObject().fromJson(MinecraftVersionJsonFileJson,
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
            MinecraftDownloads.downloadsLog4jFile(MinecraftVersionJsonFileObject.getLogging().getClient().getFile().getUrl(),mainPath,MinecraftVersionJsonFileObject.getLogging().getClient().getFile().getId());
            MinecraftDownloads.downloadsAssetIndexFile(MinecraftVersionJsonFileObject.getAssetIndex().getUrl(),mainPath,MinecraftVersionJsonFileObject.getAssetIndex().getId());
            String MinecraftVersionAssetIndexJson = String.valueOf(Util.httpsHandle(MinecraftVersionJsonFileObject.getAssetIndex().getUrl()));
            JsonElement MinecraftVersionAssetIndexJsonElement = parseString(MinecraftVersionAssetIndexJson);
            Pattern pattern = Pattern.compile(MinecraftVersionAssetIndexJson);
            Matcher matcher = pattern.matcher("[\\w\\d-.]{40}");
            matcher.find();
            System.out.println(matcher.group());
            for (int i = 0;i < MinecraftVersionAssetIndexJsonElement.getAsJsonObject().get("objects").getAsJsonArray().size();i++){
                String body = String.valueOf(MinecraftVersionAssetIndexJsonElement.getAsJsonObject().get("objects").getAsJsonArray().get(i).getAsJsonObject());
                System.out.println(body);
            }
        }else {
            System.out.println("This Minecraft version does not exist.");
        }

    }

}