package minecraft.download;


import com.google.gson.GsonBuilder;
import jsonAnalysis.download.minecraft.library.VersionJson;
import jsonAnalysis.download.minecraft.library.VersionManifest;
import minecraft.imformation.Attribute;
import utils.Utils;

import java.io.IOException;
import java.util.Objects;

import static jsonAnalysis.download.minecraft.library.VersionJson.getGsonObject;


public class Request {
    private static VersionManifest getMinecraftVersionManifestObject(DownloadURL.DownloadSource downloadSource) throws IOException {
        String MinecraftVersionManifestJson = String.valueOf(Utils.getFileContent(DownloadURL.versionManifestJsonURL(DownloadURL.VersionJsonManifest.v2, downloadSource)));
        return new GsonBuilder().setVersion(2).create().fromJson(MinecraftVersionManifestJson,
                VersionManifest.class);
    }

    private static VersionJson getMinecraftVersionObject(VersionManifest VersionManifest, String id, DownloadURL.DownloadSource downloadSource) throws IOException {
        String MinecraftVersionJson = String.valueOf(Utils.getFileContent(DownloadURL.versionJsonFileURL(VersionManifest, id, downloadSource)));
        return getGsonObject().fromJson(MinecraftVersionJson,
                VersionJson.class);
    }

    private static String getMinecraftVersionAssetIndexJson(VersionJson VersionJson, DownloadURL.DownloadSource downloadSource) throws IOException {
        return String.valueOf(Utils.getFileContent(Objects.requireNonNull(DownloadURL.assetIndexJsonURL(VersionJson, downloadSource))));
    }

    public static String getMinecraftVersionAssetIndexJson(VersionJson VersionJson) throws IOException {
        try {
            return getMinecraftVersionAssetIndexJson(VersionJson, DownloadURL.DownloadSource.official);
        } catch (IOException e) {
            try {
                return getMinecraftVersionAssetIndexJson(VersionJson, DownloadURL.DownloadSource.bmclapi);
            } catch (IOException f) {
                return getMinecraftVersionAssetIndexJson(VersionJson, DownloadURL.DownloadSource.mcbbs);
            }
        }
    }

    public static VersionJson getMinecraftVersionObject(VersionManifest VersionManifest, Attribute Attribute) throws IOException {
        try {
            return getMinecraftVersionObject(VersionManifest, Attribute.getId(), DownloadURL.DownloadSource.official);
        } catch (IOException e) {
            try {
                return getMinecraftVersionObject(VersionManifest, Attribute.getId(), DownloadURL.DownloadSource.bmclapi);
            } catch (IOException f) {
                return getMinecraftVersionObject(VersionManifest, Attribute.getId(), DownloadURL.DownloadSource.mcbbs);
            }
        }
    }

    public static VersionManifest getMinecraftVersionManifestObject() throws IOException {
        try {
            return getMinecraftVersionManifestObject(DownloadURL.DownloadSource.official);
        } catch (IOException e) {
            try {
                return getMinecraftVersionManifestObject(DownloadURL.DownloadSource.bmclapi);
            } catch (IOException f) {
                return getMinecraftVersionManifestObject(DownloadURL.DownloadSource.mcbbs);
            }
        }
    }
}
