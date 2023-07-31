package minecraft.download;


import information.minecraft.Version;
import information.minecraft.download.DownloadSource;
import information.minecraft.download.versionManifestUrlEndingVersion;
import jsonProcessing.download.minecraft.library.VersionJson;
import jsonProcessing.download.minecraft.library.VersionManifest;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;

public class Url {
//    static final String bmclapi = "https://bmclapi2.bangbang93.com/";
//    static final String mcbbs = "https://download.mcbbs.net/";

    @Contract("_, _ -> new")
    public static @NotNull URL versionManifestJsonURL(versionManifestUrlEndingVersion versionManifestVersion, DownloadSource downloadSource) throws MalformedURLException {
        return UrlArray.versionManifestJsonURL(versionManifestVersion)[downloadSource.ordinal()];
    }

    @Contract("_, _, _ -> new")
    public static @NotNull URL versionJsonFileURL(VersionManifest VersionManifest, Version version, DownloadSource downloadSource) throws MalformedURLException {
        return UrlArray.versionJsonFileURL(VersionManifest, version)[downloadSource.ordinal()];

    }

    @Contract("_, _ -> new")
    public static @NotNull URL versionJarFileURL(@NotNull VersionJson VersionJson, DownloadSource downloadSource) throws MalformedURLException {
        return UrlArray.versionJarFileURL(VersionJson)[downloadSource.ordinal()];

    }

    public static @NotNull URL nativesJarURL(@NotNull VersionJson VersionJson, DownloadSource downloadSource, int i) throws MalformedURLException {
        return UrlArray.nativesJarURL(VersionJson, i)[downloadSource.ordinal()];
    }

    public static @NotNull URL otherJarLibrariesURL(@NotNull VersionJson VersionJson, DownloadSource downloadSource, int i) throws MalformedURLException {
        return UrlArray.otherJarLibrariesURL(VersionJson, i)[downloadSource.ordinal()];
    }

//    @Contract("_, _ -> new")
//    private static @NotNull URL getLibrariesURL(DownloadSource downloadSource, String url) throws MalformedURLException {
//        if (downloadSource == DownloadSource.official) {
//            return new URL(url);
//        } else if (downloadSource == DownloadSource.bmclapi) {
//            return new URL(Utils.regexReplace(url, "https://libraries.minecraft.net/", bmclapi + "maven/"));
//        } else {
//            return new URL(Utils.regexReplace(url, "https://libraries.minecraft.net/", mcbbs + "maven/"));
//        }
//    }

    @Contract("_, _ -> new")
    public static @NotNull URL Log4jFileURL(@NotNull VersionJson VersionJson, DownloadSource downloadSource) throws MalformedURLException {
        return UrlArray.Log4jFileURL(VersionJson)[downloadSource.ordinal()];
    }

    @Contract("_, _ -> new")
    public static @NotNull URL assetIndexJsonURL(@NotNull VersionJson VersionJson, DownloadSource downloadSource) throws MalformedURLException {
        return UrlArray.assetIndexJsonURL(VersionJson)[downloadSource.ordinal()];
    }

    @Contract("_, _ -> new")
    public static @NotNull URL assetIndexFileURL(@NotNull String hash, DownloadSource downloadSource) throws MalformedURLException {
        return UrlArray.assetIndexFileURL(hash)[downloadSource.ordinal()];
    }
}