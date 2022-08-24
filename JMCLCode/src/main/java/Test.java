import JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.*;
import com.google.gson.*;


import java.net.*;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.regex.*;


import static JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.MinecraftVersionJsonFileObject.*;
import static com.google.gson.JsonParser.parseString;


public class Test {
    public static void main(String[] ages) throws Exception {
        //microsoftLogin();
        String mainPath = "E:\\JavaTest\\JMCL\\.minecraft\\";
        String id = "1.17";
        DownloadURL.DownloadSource downloadSource = DownloadURL.DownloadSource.official;
        String path = Paths.get("").toAbsolutePath().toString();
        System.out.println(path);
        Gson gson = new GsonBuilder().setVersion(2).create();
        System.out.println(-3);
        String MinecraftVersionManifestJson = String.valueOf(Util.httpHandle(Objects.requireNonNull(DownloadURL.versionManifestURL(DownloadURL.VersionManifest.v2, downloadSource))));
        MinecraftVersionManifestObject MinecraftVersionManifestObject = gson.fromJson(MinecraftVersionManifestJson,
                JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.MinecraftVersionManifestObject.class);
        if(MinecraftVersionProcessing.isId(MinecraftVersionManifestObject,id)){
            System.out.println(-2);
            String MinecraftVersionJsonFileJson = String.valueOf(Util.httpHandle(Objects.requireNonNull(DownloadURL.versionJsonURL(MinecraftVersionManifestObject,id,downloadSource))));
            MinecraftVersionJsonFileObject MinecraftVersionJsonFileObject = getGsonObject().fromJson(MinecraftVersionJsonFileJson,
                    JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.MinecraftVersionJsonFileObject.class);
            System.out.println(-1);
            MinecraftDownloads.downloadsVersionFile(DownloadURL.versionJsonURL(MinecraftVersionManifestObject,id,downloadSource),mainPath,id, MinecraftDownloads.VersionFile.json);
            System.out.println(0);
            MinecraftDownloads.downloadsVersionFile(DownloadURL.versionFileURL(MinecraftVersionJsonFileObject,downloadSource),mainPath,id, MinecraftDownloads.VersionFile.jar);
            for (int i = 0;i < MinecraftVersionJsonFileObject.getLibraries().length;i++){
                if (MinecraftVersionJsonFileObject.getLibraries()[i].getDownloads().getClassifiers() != null && MinecraftVersionJsonFileObject.getLibraries()[i].getDownloads().getClassifiers().getNativesWindows() != null){
                    MinecraftDownloads.downloadsNativesLibraries(DownloadURL.nativesLibrariesURL(MinecraftVersionJsonFileObject,downloadSource,i),mainPath,id,path);
                    System.out.println(1);
                } else if (MinecraftVersionJsonFileObject.getLibraries()[i].getDownloads().getClassifiers() == null) {
                    System.out.println(2);
                    MinecraftDownloads.downloadsOtherLibraries(DownloadURL.otherLibrariesURL(MinecraftVersionJsonFileObject,downloadSource,i),mainPath,MinecraftVersionJsonFileObject.getLibraries()[i].getDownloads().getArtifact().getPath());
                }
            }
            System.out.println(3);
            MinecraftDownloads.downloadsLog4jFile(DownloadURL.Log4jFileURL(MinecraftVersionJsonFileObject,downloadSource),mainPath,MinecraftVersionJsonFileObject.getLogging().getClient().getFile().getId());
            System.out.println(4);
            MinecraftDownloads.downloadsAssetIndexFile(DownloadURL.assetIndexFileURL(MinecraftVersionJsonFileObject,downloadSource),mainPath,MinecraftVersionJsonFileObject.getAssetIndex().getId());
            System.out.println(5);
            String MinecraftVersionAssetIndexJson = String.valueOf(Util.httpHandle(MinecraftVersionJsonFileObject.getAssetIndex().getUrl()));
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