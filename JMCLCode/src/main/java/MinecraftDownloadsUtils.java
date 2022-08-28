import JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.MinecraftVersionManifestObject;
import JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.MinecraftVersionObject;

import java.io.IOException;
import java.net.MalformedURLException;

public class MinecraftDownloadsUtils {
    public static void downloadsVersionFileUtils(MinecraftVersionObject MinecraftVersionObject, MinecraftAttribute MinecraftAttribute) throws IOException {
        MinecraftDownload.downloadVersionFile(DownloadURL.versionJarFileURL(MinecraftVersionObject,MinecraftAttribute.downloadSource),MinecraftAttribute.mainPath,MinecraftAttribute.id, MinecraftDownload.VersionFile.jar);
    }
    public static void downloadsVersionFileJsonUtils(MinecraftVersionManifestObject MinecraftVersionManifestObject, MinecraftAttribute MinecraftAttribute) throws IOException {
        MinecraftDownload.downloadVersionFile(DownloadURL.versionJsonFileURL(MinecraftVersionManifestObject,MinecraftAttribute.id,MinecraftAttribute.downloadSource),MinecraftAttribute.mainPath,MinecraftAttribute.id, MinecraftDownload.VersionFile.json);
    }
    public static void downloadsNativesDllLibrariesUtils(MinecraftVersionObject MinecraftVersionObject,MinecraftAttribute MinecraftAttribute,int i) throws Exception {
        MinecraftDownload.downloadNativesDllLibraries(DownloadURL.nativesJarURL(MinecraftVersionObject,MinecraftAttribute.downloadSource,i),MinecraftAttribute.mainPath,MinecraftAttribute.id,MinecraftAttribute.runPath);
    }
    public static void downloadOtherJarLibrariesUtils(MinecraftVersionObject MinecraftVersionObject,MinecraftAttribute MinecraftAttribute,int i) throws IOException {
        MinecraftDownload.downloadOtherLibraries(DownloadURL.otherJarLibrariesURL(MinecraftVersionObject,MinecraftAttribute.downloadSource,i),MinecraftAttribute.mainPath,MinecraftVersionObject.getLibraries()[i].getDownloads().getArtifact().getPath());
    }
    public static void downloadLog4jFileUtils(MinecraftVersionObject MinecraftVersionObject,MinecraftAttribute MinecraftAttribute) throws IOException {
        MinecraftDownload.downloadLog4jFile(DownloadURL.Log4jFileURL(MinecraftVersionObject, MinecraftAttribute.downloadSource),MinecraftAttribute.mainPath,MinecraftVersionObject.getLogging().getClient().getFile().getId());
    }
    public static void downloadAssetIndexJsonUtils(MinecraftVersionObject MinecraftVersionObject,MinecraftAttribute MinecraftAttribute) throws IOException {
        MinecraftDownload.downloadAssetIndexJson(DownloadURL.assetIndexJsonURL(MinecraftVersionObject,MinecraftAttribute.downloadSource),MinecraftAttribute.mainPath,MinecraftVersionObject.getAssetIndex().getId());
    }
    public static void downloadAssetIndexFileUtils(MinecraftAttribute MinecraftAttribute,String hash) throws IOException {
        MinecraftDownload.downloadAssetIndexFile(DownloadURL.assetIndexFileURL(hash, MinecraftAttribute.downloadSource),MinecraftAttribute.mainPath,hash);
    }
    public static void downloadAssetIndexCopyFileUtils(MinecraftAttribute MinecraftAttribute,String path,String hash) throws IOException {
        MinecraftDownload.downloadAssetIndexCopyFile(DownloadURL.assetIndexFileURL(hash, MinecraftAttribute.downloadSource),MinecraftAttribute.mainPath,path);
    }
}
