package Other;

import JsonAnalysis.Download.Minecraft.Library.MinecraftVersionManifestObject;
import JsonAnalysis.Download.Minecraft.Library.MinecraftVersionObject;
import JsonAnalysis.Setup.Setup;
import Minecraft.MinecraftVersionProcessing;
import Utils.Utils;

import java.net.MalformedURLException;
import java.net.URL;

public class DownloadURL {
    static String bmclapi = "https://bmclapi2.bangbang93.com/";
    static String mcbbs = "https://download.mcbbs.net/";

    public static URL versionManifestJsonURL(VersionManifest versionManifest, DownloadSource downloadSource) throws MalformedURLException {
        String v1 = "mc/game/version_manifest.json";
        String v2 = "mc/game/version_manifest_v2.json";
        if (downloadSource == DownloadSource.official) {
            if (versionManifest == VersionManifest.v1){
                return new URL("http://launchermeta.mojang.com/" + v1);
            }else{
                return new URL("http://launchermeta.mojang.com/" + v2);
            }
        } else if (downloadSource == DownloadSource.bmclapi) {
            if (versionManifest == VersionManifest.v1){
                return new URL(bmclapi + v1);
            }else{
                return new URL(bmclapi + v2);
            }
        }else{
            if (versionManifest == VersionManifest.v1){
                return new URL(mcbbs + v1);
            }else{
                return new URL(mcbbs + v2);
            }
        }
    }
    public static URL versionJsonFileURL(MinecraftVersionManifestObject MinecraftVersionManifestObject, String id, DownloadSource downloadSource) throws MalformedURLException {
        String url = String.valueOf(MinecraftVersionProcessing.getUrl(MinecraftVersionManifestObject, id));
        if (MinecraftVersionProcessing.getIdCount(MinecraftVersionManifestObject, "1.17.1")
                < MinecraftVersionProcessing.getIdCount(MinecraftVersionManifestObject, id)) {
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
    public static URL versionJarFileURL(MinecraftVersionObject MinecraftVersionObject, DownloadSource downloadSource) throws MalformedURLException {
        String url = String.valueOf(MinecraftVersionObject.getDownloads().getClient().getUrl());
        if (downloadSource == DownloadSource.official){
            return new URL(url);
        } else if (downloadSource == DownloadSource.bmclapi) {
            return new URL(Utils.regexReplace(url,"https://launcher.mojang.com/",bmclapi));
        }else{
            return new URL(Utils.regexReplace(url,"https://launcher.mojang.com/",mcbbs));
        }
    }
    public static URL nativesJarURL(MinecraftVersionObject MinecraftVersionObject, DownloadSource downloadSource, int i) throws MalformedURLException {
        String url = String.valueOf(MinecraftVersionObject.getLibraries()[i].getDownloads().getClassifiers().getNativesWindows().getUrl());
        if (downloadSource == DownloadSource.official){
            return new URL(url);
        } else if (downloadSource == DownloadSource.bmclapi) {
            return new URL(Utils.regexReplace(url,"https://libraries.minecraft.net/",bmclapi + "maven/"));
        }else{
            return new URL(Utils.regexReplace(url,"https://libraries.minecraft.net/",mcbbs + "maven/"));
        }
    }
    public static URL otherJarLibrariesURL(MinecraftVersionObject MinecraftVersionObject, DownloadSource downloadSource, int i) throws MalformedURLException {
        String url = String.valueOf(MinecraftVersionObject.getLibraries()[i].getDownloads().getArtifact().getUrl());
        if (downloadSource == DownloadSource.official){
            return new URL(url);
        } else if (downloadSource == DownloadSource.bmclapi) {
            return new URL(Utils.regexReplace(url,"https://libraries.minecraft.net/",bmclapi + "maven/"));
        }else{
            return new URL(Utils.regexReplace(url,"https://libraries.minecraft.net/",mcbbs + "maven/"));
        }
    }
    public static URL Log4jFileURL(MinecraftVersionObject MinecraftVersionObject, DownloadSource downloadSource) throws MalformedURLException {
        String url = String.valueOf(MinecraftVersionObject.getLogging().getClient().getFile().getUrl());
        if (downloadSource == DownloadSource.official){
            return new URL(url);
        } else if (downloadSource == DownloadSource.bmclapi) {
            return new URL(Utils.regexReplace(url,"https://launcher.mojang.com/",bmclapi));
        }else{
            return new URL(Utils.regexReplace(url,"https://launcher.mojang.com/",mcbbs));
        }
    }
    public static URL assetIndexJsonURL(MinecraftVersionObject MinecraftVersionObject, DownloadSource downloadSource) throws MalformedURLException {
        String url = String.valueOf(MinecraftVersionObject.getAssetIndex().getUrl());
        if (downloadSource == DownloadSource.official){
            return new URL(url);
        } else if (downloadSource == DownloadSource.bmclapi) {
            return new URL(Utils.regexReplace(url,"https://launchermeta.mojang.com/",bmclapi));
        }else{
            return new URL(Utils.regexReplace(url,"https://launchermeta.mojang.com/",mcbbs));
        }
    }
    public static URL assetIndexFileURL(String hash,DownloadSource downloadSource) throws MalformedURLException {
        String url = hash.substring(0,2)+"/"+hash;
        if (downloadSource == DownloadSource.official){
            return new URL("http://resources.download.minecraft.net/"+url);
        } else if (downloadSource == DownloadSource.bmclapi) {
            return new URL(bmclapi+url);
        }else{
            return new URL(mcbbs+url);
        }
    }

    public enum DownloadSource {

        official(Setup.getSetupInstance().download.source.ifUseOfficialDownloadSource), bmclapi(Setup.getSetupInstance().download.source.ifUseBmclapiDownloadSource), mcbbs(Setup.getSetupInstance().download.source.ifUseMcbbsDownloadSource);
        final boolean ifUse;

        DownloadSource(boolean ifUseDownloadSource) {
            this.ifUse = ifUseDownloadSource;
        }
    }

    public enum VersionManifest {
        v1, v2

    }
}

