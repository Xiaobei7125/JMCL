package Minecraft;

import JsonAnalysis.Download.Minecraft.Library.MinecraftVersionManifestObject;
import JsonAnalysis.Download.Minecraft.Library.MinecraftVersionObject;
import Other.DownloadURL;
import Utils.Utils;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Objects;

import static JsonAnalysis.Download.Minecraft.Library.MinecraftVersionObject.getGsonObject;

public class MinecraftRequest {
    private static MinecraftVersionManifestObject getMinecraftVersionManifestObject(DownloadURL.DownloadSource downloadSource) throws IOException {
        String MinecraftVersionManifestJson = String.valueOf(Utils.getFileContent(DownloadURL.versionManifestJsonURL(DownloadURL.VersionManifest.v2, downloadSource)));
        return new GsonBuilder().setVersion(2).create().fromJson(MinecraftVersionManifestJson,
                MinecraftVersionManifestObject.class);
    }
    private static MinecraftVersionObject getMinecraftVersionObject(MinecraftVersionManifestObject MinecraftVersionManifestObject, String id, DownloadURL.DownloadSource downloadSource) throws IOException {
        String MinecraftVersionJson = String.valueOf(Utils.getFileContent(DownloadURL.versionJsonFileURL(MinecraftVersionManifestObject,id,downloadSource)));
        return getGsonObject().fromJson(MinecraftVersionJson,
                MinecraftVersionObject.class);
    }
    private static String getMinecraftVersionAssetIndexJson(MinecraftVersionObject MinecraftVersionObject, DownloadURL.DownloadSource downloadSource) throws IOException {
        return String.valueOf(Utils.getFileContent(Objects.requireNonNull(DownloadURL.assetIndexJsonURL(MinecraftVersionObject, downloadSource))));
    }
    public static String getMinecraftVersionAssetIndexJson(MinecraftVersionObject MinecraftVersionObject) throws IOException {
        try {
            return getMinecraftVersionAssetIndexJson(MinecraftVersionObject, DownloadURL.DownloadSource.official);
        } catch (IOException e) {
            try {
                return getMinecraftVersionAssetIndexJson(MinecraftVersionObject, DownloadURL.DownloadSource.bmclapi);
            } catch (IOException f) {
                return getMinecraftVersionAssetIndexJson(MinecraftVersionObject, DownloadURL.DownloadSource.mcbbs);
            }
        }
    }

    public static MinecraftVersionObject getMinecraftVersionObject(MinecraftVersionManifestObject MinecraftVersionManifestObject, MinecraftAttribute MinecraftAttribute) throws IOException {
        try {
            return getMinecraftVersionObject(MinecraftVersionManifestObject, MinecraftAttribute.id, DownloadURL.DownloadSource.official);
        } catch (IOException e) {
            try {
                return getMinecraftVersionObject(MinecraftVersionManifestObject, MinecraftAttribute.id, DownloadURL.DownloadSource.bmclapi);
            } catch (IOException f) {
                return getMinecraftVersionObject(MinecraftVersionManifestObject, MinecraftAttribute.id, DownloadURL.DownloadSource.mcbbs);
            }
        }
    }
    public static MinecraftVersionManifestObject getMinecraftVersionManifestObject() throws IOException {
        try {
            return getMinecraftVersionManifestObject(DownloadURL.DownloadSource.official);
        }catch (IOException e) {
            try {
                return getMinecraftVersionManifestObject(DownloadURL.DownloadSource.bmclapi);
            }catch (IOException f){
                return getMinecraftVersionManifestObject(DownloadURL.DownloadSource.mcbbs);
            }
        }
    }
}
