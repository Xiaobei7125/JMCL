package minecraft.download;


import com.google.gson.GsonBuilder;
import information.minecraft.download.DownloadBasicInformation;
import information.minecraft.download.DownloadSource;
import information.minecraft.download.VersionManifestVersion;
import jsonProcessing.download.minecraft.library.VersionJson;
import jsonProcessing.download.minecraft.library.VersionManifest;
import utils.Stream;

import java.io.IOException;
import java.util.Objects;

import static jsonProcessing.download.minecraft.library.VersionJson.getGsonObject;


public class Request {
    private static VersionManifest getMinecraftVersionManifestObject(DownloadSource downloadSource) throws IOException {
        String MinecraftVersionManifestJson = String.valueOf(Stream.getUrlBody(Url.versionManifestJsonURL(VersionManifestVersion.v2, downloadSource)));
        return new GsonBuilder().setVersion(2).create().fromJson(MinecraftVersionManifestJson,
                VersionManifest.class);
    }

    private static VersionJson getMinecraftVersionObject(VersionManifest VersionManifest, String id, DownloadSource downloadSource) throws IOException {
        String MinecraftVersionJson = String.valueOf(Stream.getUrlBody(Url.versionJsonFileURL(VersionManifest, id, downloadSource)));
        return getGsonObject().fromJson(MinecraftVersionJson,
                VersionJson.class);
    }

    private static String getMinecraftVersionAssetIndexJson(VersionJson VersionJson, DownloadSource downloadSource) throws IOException {
        return String.valueOf(Stream.getUrlBody(Objects.requireNonNull(Url.assetIndexJsonURL(VersionJson, downloadSource))));
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

    public static VersionJson getMinecraftVersionObject(VersionManifest VersionManifest, DownloadBasicInformation DownloadBasicInformation) throws IOException {
        try {
            return getMinecraftVersionObject(VersionManifest, DownloadBasicInformation.getId(), DownloadSource.official);
        } catch (IOException e) {
            try {
                return getMinecraftVersionObject(VersionManifest, DownloadBasicInformation.getId(), DownloadSource.bmclapi);
            } catch (IOException f) {
                return getMinecraftVersionObject(VersionManifest, DownloadBasicInformation.getId(), DownloadSource.mcbbs);
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
