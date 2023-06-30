package Other;


import Utils.Utils;
import jsonAnalysis.download.minecraft.library.VersionJson;
import jsonAnalysis.download.minecraft.library.VersionManifest;
import jsonAnalysis.setup.Setup;
import minecraft.MinecraftVersionProcessing;

import java.net.MalformedURLException;
import java.net.URL;

public class DownloadURL {
    static final String bmclapi = "https://bmclapi2.bangbang93.com/";
    static final String mcbbs = "https://download.mcbbs.net/";

    public static URL versionManifestJsonURL(VersionJsonManifest versionJsonManifest, DownloadSource downloadSource) throws MalformedURLException {
        String v1 = "mc/game/version_manifest.json";
        String v2 = "mc/game/version_manifest_v2.json";
        if (downloadSource == DownloadSource.official) {
            if (versionJsonManifest == VersionJsonManifest.v1) {
                return new URL("http://launchermeta.mojang.com/" + v1);
            } else {
                return new URL("http://launchermeta.mojang.com/" + v2);
            }
        } else if (downloadSource == DownloadSource.bmclapi) {
            if (versionJsonManifest == VersionJsonManifest.v1) {
                return new URL(bmclapi + v1);
            } else {
                return new URL(bmclapi + v2);
            }
        } else {
            if (versionJsonManifest == VersionJsonManifest.v1) {
                return new URL(mcbbs + v1);
            } else {
                return new URL(mcbbs + v2);
            }
        }
    }

    public static URL versionJsonFileURL(VersionManifest VersionManifest, String id, DownloadSource downloadSource) throws MalformedURLException {
        String url = String.valueOf(MinecraftVersionProcessing.getUrl(VersionManifest, id));
        if (MinecraftVersionProcessing.getIdCount(VersionManifest, "1.17.1")
                < MinecraftVersionProcessing.getIdCount(VersionManifest, id)) {
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

    public static URL versionJarFileURL(VersionJson VersionJson, DownloadSource downloadSource) throws MalformedURLException {
        String url = String.valueOf(VersionJson.getDownloads().getClient().getUrl());
        if (downloadSource == DownloadSource.official) {
            return new URL(url);
        } else if (downloadSource == DownloadSource.bmclapi) {
            return new URL(Utils.regexReplace(url, "https://launcher.mojang.com/", bmclapi));
        } else {
            return new URL(Utils.regexReplace(url, "https://launcher.mojang.com/", mcbbs));
        }
    }

    public static URL nativesJarURL(VersionJson VersionJson, DownloadSource downloadSource, int i) throws MalformedURLException {
        String url = String.valueOf(VersionJson.getLibraries()[i].getDownloads().getClassifiers().getNativesWindows().getUrl());
        return getLibrariesUrl(downloadSource, url);
    }

    public static URL otherJarLibrariesURL(VersionJson VersionJson, DownloadSource downloadSource, int i) throws MalformedURLException {
        String url = String.valueOf(VersionJson.getLibraries()[i].getDownloads().getArtifact().getUrl());
        return getLibrariesUrl(downloadSource, url);
    }

    private static URL getLibrariesUrl(DownloadSource downloadSource, String url) throws MalformedURLException {
        if (downloadSource == DownloadSource.official) {
            return new URL(url);
        } else if (downloadSource == DownloadSource.bmclapi) {
            return new URL(Utils.regexReplace(url, "https://libraries.minecraft.net/", bmclapi + "maven/"));
        } else {
            return new URL(Utils.regexReplace(url, "https://libraries.minecraft.net/", mcbbs + "maven/"));
        }
    }

    public static URL Log4jFileURL(VersionJson VersionJson, DownloadSource downloadSource) throws MalformedURLException {
        String url = String.valueOf(VersionJson.getLogging().getClient().getFile().getUrl());
        if (downloadSource == DownloadSource.official) {
            return new URL(url);
        } else if (downloadSource == DownloadSource.bmclapi) {
            return new URL(Utils.regexReplace(url, "https://launcher.mojang.com/", bmclapi));
        } else {
            return new URL(Utils.regexReplace(url, "https://launcher.mojang.com/", mcbbs));
        }
    }

    public static URL assetIndexJsonURL(VersionJson VersionJson, DownloadSource downloadSource) throws MalformedURLException {
        String url = String.valueOf(VersionJson.getAssetIndex().getUrl());
        if (downloadSource == DownloadSource.official) {
            return new URL(url);
        } else if (downloadSource == DownloadSource.bmclapi) {
            return new URL(Utils.regexReplace(url, "https://launchermeta.mojang.com/", bmclapi));
        } else {
            return new URL(Utils.regexReplace(url, "https://launchermeta.mojang.com/", mcbbs));
        }
    }

    public static URL assetIndexFileURL(String hash, DownloadSource downloadSource) throws MalformedURLException {
        String url = hash.substring(0, 2) + "/" + hash;
        if (downloadSource == DownloadSource.official) {
            return new URL("http://resources.download.minecraft.net/" + url);
        } else if (downloadSource == DownloadSource.bmclapi) {
            return new URL(bmclapi + url);
        } else {
            return new URL(mcbbs + url);
        }
    }

    public enum DownloadSource {

        official(Setup.getSetupInstance().download.source.ifUseOfficialDownloadSource), bmclapi(Setup.getSetupInstance().download.source.ifUseBmclapiDownloadSource), mcbbs(Setup.getSetupInstance().download.source.ifUseMcbbsDownloadSource);
        public final boolean ifUse;

        DownloadSource(boolean ifUseDownloadSource) {
            this.ifUse = ifUseDownloadSource;
        }
    }

    public enum VersionJsonManifest {
        v1, v2

    }
}

