package minecraft.download;


import com.google.gson.GsonBuilder;
import jsonAnalysis.download.minecraft.library.VersionJson;
import jsonAnalysis.download.minecraft.library.VersionManifest;
import minecraft.information.Attribute;
import minecraft.information.DownloadSource;
import minecraft.information.VersionJsonManifestVersion;
import utils.Utils;

import java.io.IOException;
import java.util.Objects;

import static jsonAnalysis.download.minecraft.library.VersionJson.getGsonObject;


public class Request {
    private static VersionManifest getMinecraftVersionManifestObject(DownloadSource downloadSource) throws IOException {
        String MinecraftVersionManifestJson = String.valueOf(Utils.getFileContent(Url.versionManifestJsonURL(VersionJsonManifestVersion.v2, downloadSource)));
        return new GsonBuilder().setVersion(2).create().fromJson(MinecraftVersionManifestJson,
                VersionManifest.class);
    }

    private static VersionJson getMinecraftVersionObject(VersionManifest VersionManifest, String id, DownloadSource downloadSource) throws IOException {
        String MinecraftVersionJson = String.valueOf(Utils.getFileContent(Url.versionJsonFileURL(VersionManifest, id, downloadSource)));
        return getGsonObject().fromJson(MinecraftVersionJson,
                VersionJson.class);
    }

    private static String getMinecraftVersionAssetIndexJson(VersionJson VersionJson, DownloadSource downloadSource) throws IOException {
        return String.valueOf(Utils.getFileContent(Objects.requireNonNull(Url.assetIndexJsonURL(VersionJson, downloadSource))));
    }

    public static String getMinecraftVersionAssetIndexJson(VersionJson VersionJson) throws IOException {
        try {
            return getMinecraftVersionAssetIndexJson(VersionJson, DownloadSource.official);
        } catch (IOException e) {
            try {
                return getMinecraftVersionAssetIndexJson(VersionJson, DownloadSource.bmclapi);
            } catch (IOException f) {
                return getMinecraftVersionAssetIndexJson(VersionJson, DownloadSource.mcbbs);
            }
        }
    }

    public static VersionJson getMinecraftVersionObject(VersionManifest VersionManifest, Attribute Attribute) throws IOException {
        try {
            return getMinecraftVersionObject(VersionManifest, Attribute.getId(), DownloadSource.official);
        } catch (IOException e) {
            try {
                return getMinecraftVersionObject(VersionManifest, Attribute.getId(), DownloadSource.bmclapi);
            } catch (IOException f) {
                return getMinecraftVersionObject(VersionManifest, Attribute.getId(), DownloadSource.mcbbs);
            }
        }
    }

    public static VersionManifest getMinecraftVersionManifestObject() throws IOException {
        try {
            return getMinecraftVersionManifestObject(DownloadSource.official);
        } catch (IOException e) {
            try {
                return getMinecraftVersionManifestObject(DownloadSource.bmclapi);
            } catch (IOException f) {
                return getMinecraftVersionManifestObject(DownloadSource.mcbbs);
            }
        }
    }
}
