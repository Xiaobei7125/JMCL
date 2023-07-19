package minecraft.download;


import jsonAnalysis.download.minecraft.library.VersionJson;
import jsonAnalysis.download.minecraft.library.VersionManifest;
import minecraft.VersionProcessing;
import minecraft.information.DownloadSource;
import minecraft.information.VersionJsonManifest;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import utils.Utils;

import java.net.MalformedURLException;
import java.net.URL;

import static minecraft.information.VersionJsonManifest.v1;
import static minecraft.information.VersionJsonManifest.v2;

public class Url {
    static final String bmclapi = "https://bmclapi2.bangbang93.com/";
    static final String mcbbs = "https://download.mcbbs.net/";

    @Contract("_, _ -> new")
    public static @NotNull URL versionManifestJsonURL(VersionJsonManifest versionJsonManifest, DownloadSource downloadSource) throws MalformedURLException {
        if (downloadSource == DownloadSource.official) {
            if (versionJsonManifest == v1) {
                return new URL("https://launchermeta.mojang.com/" + v1);
            } else {
                return new URL("https://launchermeta.mojang.com/" + v2);
            }
        } else if (downloadSource == DownloadSource.bmclapi) {
            if (versionJsonManifest == v1) {
                return new URL(bmclapi + v1);
            } else {
                return new URL(bmclapi + v2);
            }
        } else {
            if (versionJsonManifest == v1) {
                return new URL(mcbbs + v1);
            } else {
                return new URL(mcbbs + v2);
            }
        }
    }

    @Contract("_, _, _ -> new")
    public static @NotNull URL versionJsonFileURL(VersionManifest VersionManifest, String id, DownloadSource downloadSource) throws MalformedURLException {
        String url = String.valueOf(VersionProcessing.getUrl(VersionManifest, id));
        if (VersionProcessing.getIdCount(VersionManifest, "1.17.1")
                < VersionProcessing.getIdCount(VersionManifest, id)) {
            if (downloadSource == DownloadSource.official) {
                return new URL(url);
            } else if (downloadSource == DownloadSource.bmclapi) {
                return new URL(Utils.regexReplace(url, "https://piston-meta.mojang.com/", bmclapi));
            } else {
                return new URL(Utils.regexReplace(url, "https://piston-meta.mojang.com/", mcbbs));
            }
        } else {
            if (downloadSource == DownloadSource.official) {
                return new URL(url);
            } else if (downloadSource == DownloadSource.bmclapi) {
                return new URL(Utils.regexReplace(url, "https://launchermeta.mojang.com/", bmclapi));
            } else {
                return new URL(Utils.regexReplace(url, "https://launchermeta.mojang.com/", mcbbs));
            }
        }
    }

    @Contract("_, _ -> new")
    public static @NotNull URL versionJarFileURL(@NotNull VersionJson VersionJson, DownloadSource downloadSource) throws MalformedURLException {
        String url = String.valueOf(VersionJson.getDownloads().getClient().getUrl());
        if (downloadSource == DownloadSource.official) {
            return new URL(url);
        } else if (downloadSource == DownloadSource.bmclapi) {
            return new URL(Utils.regexReplace(url, "https://launcher.mojang.com/", bmclapi));
        } else {
            return new URL(Utils.regexReplace(url, "https://launcher.mojang.com/", mcbbs));
        }
    }

    public static @NotNull URL nativesJarURL(@NotNull VersionJson VersionJson, DownloadSource downloadSource, int i) throws MalformedURLException {
        String url = String.valueOf(VersionJson.getLibraries()[i].getDownloads().getClassifiers().getNativesWindows().getUrl());
        return getLibrariesURL(downloadSource, url);
    }

    public static @NotNull URL otherJarLibrariesURL(@NotNull VersionJson VersionJson, DownloadSource downloadSource, int i) throws MalformedURLException {
        String url = String.valueOf(VersionJson.getLibraries()[i].getDownloads().getArtifact().getUrl());
        return getLibrariesURL(downloadSource, url);
    }

    @Contract("_, _ -> new")
    private static @NotNull URL getLibrariesURL(DownloadSource downloadSource, String url) throws MalformedURLException {
        if (downloadSource == DownloadSource.official) {
            return new URL(url);
        } else if (downloadSource == DownloadSource.bmclapi) {
            return new URL(Utils.regexReplace(url, "https://libraries.minecraft.net/", bmclapi + "maven/"));
        } else {
            return new URL(Utils.regexReplace(url, "https://libraries.minecraft.net/", mcbbs + "maven/"));
        }
    }

    @Contract("_, _ -> new")
    public static @NotNull URL Log4jFileURL(@NotNull VersionJson VersionJson, DownloadSource downloadSource) throws MalformedURLException {
        String url = String.valueOf(VersionJson.getLogging().getClient().getFile().getUrl());
        if (downloadSource == DownloadSource.official) {
            return new URL(url);
        } else if (downloadSource == DownloadSource.bmclapi) {
            return new URL(Utils.regexReplace(url, "https://launcher.mojang.com/", bmclapi));
        } else {
            return new URL(Utils.regexReplace(url, "https://launcher.mojang.com/", mcbbs));
        }
    }

    @Contract("_, _ -> new")
    public static @NotNull URL assetIndexJsonURL(@NotNull VersionJson VersionJson, DownloadSource downloadSource) throws MalformedURLException {
        String url = String.valueOf(VersionJson.getAssetIndex().getUrl());
        if (downloadSource == DownloadSource.official) {
            return new URL(url);
        } else if (downloadSource == DownloadSource.bmclapi) {
            return new URL(Utils.regexReplace(url, "https://launchermeta.mojang.com/", bmclapi));
        } else {
            return new URL(Utils.regexReplace(url, "https://launchermeta.mojang.com/", mcbbs));
        }
    }

    @Contract("_, _ -> new")
    public static @NotNull URL assetIndexFileURL(@NotNull String hash, DownloadSource downloadSource) throws MalformedURLException {
        String url = hash.substring(0, 2) + "/" + hash;
        if (downloadSource == DownloadSource.official) {
            return new URL("https://resources.download.minecraft.net/" + url);
        } else if (downloadSource == DownloadSource.bmclapi) {
            return new URL(bmclapi + "assets/" + url);
        } else {
            return new URL(mcbbs + "assets/" + url);
        }
    }

    //    public enum MinecraftFileOfficialURL{
//        versionManifestJson,
//    }
}

