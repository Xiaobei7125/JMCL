package minecraft.download;


import information.minecraft.Version;
import information.minecraft.download.urlHeader.Bmclapi;
import information.minecraft.download.urlHeader.Mcbbs;
import information.minecraft.download.urlHeader.Official;
import information.minecraft.download.versionManifestUrlEndingVersion;
import jsonProcessing.download.minecraft.library.VersionJson;
import jsonProcessing.download.minecraft.library.VersionManifest;
import jsonProcessing.setup.Setup;
import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;

import static information.minecraft.download.versionManifestUrlEndingVersion.v1;
import static information.minecraft.download.versionManifestUrlEndingVersion.v2;

public class UrlArray {
    public static @NotNull URL[] versionManifestJsonURL(versionManifestUrlEndingVersion versionManifestVersion) throws MalformedURLException {
        if (versionManifestVersion == v1) {
            return new URL[]{new URL("https://launchermeta.mojang.com/" + v1.getUrlPath()),
                    new URL(Bmclapi.versionManifestJson.getUrlHost() + v1.getUrlPath()),
                    new URL(Mcbbs.versionManifestJson.getUrlHost() + v1.getUrlPath())};
        } else {
            return new URL[]{new URL("https://launchermeta.mojang.com/" + v2.getUrlPath()),
                    new URL(Bmclapi.versionManifestJson.getUrlHost() + v2.getUrlPath()),
                    new URL(Mcbbs.versionManifestJson.getUrlHost() + v2.getUrlPath())};
        }
    }

    public static @NotNull URL[] versionJsonFileURL(VersionManifest VersionManifest, Version version) throws MalformedURLException {
        URL url = VersionManifestProcessing.getVersionJsonUrl(VersionManifest, version.version());
        assert url != null;
        if (Setup.getSetupInstance().download.ifUsesNewURLDownloadingVersionJson) {
            return new URL[]{url,
                    new URL(Bmclapi.VersionJson.getUrlHost() + url.getPath()),
                    new URL(Mcbbs.VersionJson.getUrlHost() + url.getPath())
            };
        } else {
            return new URL[]{new URL(Official.oldVersionJson.getUrlHost() + url.getPath()),
                    new URL(Bmclapi.VersionJson.getUrlHost() + url.getPath()),
                    new URL(Mcbbs.VersionJson.getUrlHost() + url.getPath())
            };
        }
    }

    public static @NotNull URL[] versionJarFileURL(@NotNull VersionJson VersionJson) throws MalformedURLException {
        URL url = VersionJson.getDownloads().getClient().getUrl();
        return new URL[]{
                url,
                new URL(Bmclapi.versionJar.getUrlHost() + url.getPath()),
                new URL(Mcbbs.versionJar.getUrlHost() + url.getPath())};
    }

    public static @NotNull URL[] Log4jFileURL(@NotNull VersionJson VersionJson) throws MalformedURLException {
        URL url = VersionJson.getLogging().getClient().getFile().getUrl();
        return new URL[]{
                url,
                new URL(Bmclapi.Log4jXml.getUrlHost() + url.getPath()),
                new URL(Mcbbs.Log4jXml.getUrlHost() + url.getPath())};
    }

    public static @NotNull URL[] nativesJarURL(@NotNull VersionJson VersionJson, int member) throws MalformedURLException {
        URL url = VersionJson.getLibraries()[member].getDownloads().getClassifiers().getNativesWindows().getUrl();
        return LibrariesURL(url);
    }

    public static @NotNull URL[] otherJarLibrariesURL(@NotNull VersionJson VersionJson, int member) throws MalformedURLException {
        URL url = VersionJson.getLibraries()[member].getDownloads().getArtifact().getUrl();
        return LibrariesURL(url);
    }

    private static @NotNull URL[] LibrariesURL(URL url) throws MalformedURLException {
        return new URL[]{
                url,
                new URL(Bmclapi.LibrariesJar.getUrlHost() + url.getPath()),
                new URL(Mcbbs.LibrariesJar.getUrlHost() + url.getPath())};
    }

    public static @NotNull URL[] assetIndexJsonURL(@NotNull VersionJson VersionJson) throws MalformedURLException {
        URL url = VersionJson.getAssetIndex().getUrl();
        return new URL[]{
                url,
                new URL(Bmclapi.assetIndexJson.getUrlHost() + url.getPath()),
                new URL(Mcbbs.assetIndexJson.getUrlHost() + url.getPath())};
    }

    public static @NotNull URL[] assetIndexFileURL(@NotNull String hash) throws MalformedURLException {
        String incompletePath = hash.substring(0, 2) + "/" + hash;
        return new URL[]{
                new URL(Official.assetIndexFile.getUrlHost() + incompletePath),
                new URL(Bmclapi.assetIndexFile.getUrlHost() + incompletePath),
                new URL(Bmclapi.assetIndexFile.getUrlHost() + incompletePath)
        };
    }
}
