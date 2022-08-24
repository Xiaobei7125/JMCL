import JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.MinecraftVersionJsonFileObject;
import JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.MinecraftVersionManifestObject;

import java.net.MalformedURLException;
import java.net.URL;
public class DownloadURL {
    static String bmclapi = "https://bmclapi2.bangbang93.com/";
    static String mcbbs = "https://download.mcbbs.net/";
    public static URL versionManifestURL(VersionManifest versionManifest,DownloadSource downloadSource) throws MalformedURLException {
        String v1 = "mc/game/version_manifest.json";
        String v2 = "mc/game/version_manifest_v2.json";
        if (downloadSource == DownloadSource.official){
            if (versionManifest == VersionManifest.v1){
                return new URL("http://launchermeta.mojang.com/" + v1);
            } else if (versionManifest == VersionManifest.v2) {
                return new URL("http://launchermeta.mojang.com/" + v2);
            }
        } else if (downloadSource == DownloadSource.bmclapi) {
            if (versionManifest == VersionManifest.v1){
                return new URL(bmclapi + v1);
            } else if (versionManifest == VersionManifest.v2) {
                return new URL(bmclapi + v2);
            }
        }else if (downloadSource == DownloadSource.mcbbs){
            if (versionManifest == VersionManifest.v1){
                return new URL(mcbbs + v1);
            } else if (versionManifest == VersionManifest.v2) {
                return new URL(mcbbs + v2);
            }
        }
        return null;
    }
    public static URL versionJsonURL(MinecraftVersionManifestObject MinecraftVersionManifestObject, String id, DownloadSource downloadSource) throws MalformedURLException {
        String url = String.valueOf(MinecraftVersionProcessing.getUrl(MinecraftVersionManifestObject,id));
        if (downloadSource == DownloadSource.official){
            return new URL(url);
        } else if (downloadSource == DownloadSource.bmclapi) {
            return new URL(Util.regReplace(url,"https://piston-meta.mojang.com/",bmclapi));
        }else if (downloadSource == DownloadSource.mcbbs){
            return new URL(Util.regReplace(url,"https://piston-meta.mojang.com/",mcbbs));
        }
        return null;
    }
    public static URL versionFileURL(MinecraftVersionJsonFileObject MinecraftVersionJsonFileObject, DownloadSource downloadSource) throws MalformedURLException {
        String url = String.valueOf(MinecraftVersionJsonFileObject.getDownloads().getClient().getUrl());
        if (downloadSource == DownloadSource.official){
            return new URL(url);
        } else if (downloadSource == DownloadSource.bmclapi) {
            return new URL(Util.regReplace(url,"https://launcher.mojang.com/",bmclapi));
        }else if (downloadSource == DownloadSource.mcbbs){
            return new URL(Util.regReplace(url,"https://launcher.mojang.com/",mcbbs));
        }
        return null;
    }
    public static URL nativesLibrariesURL(MinecraftVersionJsonFileObject MinecraftVersionJsonFileObject, DownloadSource downloadSource,int i) throws MalformedURLException {
        String url = String.valueOf(MinecraftVersionJsonFileObject.getLibraries()[i].getDownloads().getClassifiers().getNativesWindows().getUrl());
        if (downloadSource == DownloadSource.official){
            return new URL(url);
        } else if (downloadSource == DownloadSource.bmclapi) {
            return new URL(Util.regReplace(url,"https://libraries.minecraft.net/",bmclapi + "maven/"));
        }else if (downloadSource == DownloadSource.mcbbs){
            return new URL(Util.regReplace(url,"https://libraries.minecraft.net/",mcbbs + "maven/"));
        }
        return null;
    }
    public static URL otherLibrariesURL(MinecraftVersionJsonFileObject MinecraftVersionJsonFileObject, DownloadSource downloadSource,int i) throws MalformedURLException {
        String url = String.valueOf(MinecraftVersionJsonFileObject.getLibraries()[i].getDownloads().getArtifact().getUrl());
        if (downloadSource == DownloadSource.official){
            return new URL(url);
        } else if (downloadSource == DownloadSource.bmclapi) {
            return new URL(Util.regReplace(url,"https://libraries.minecraft.net/",bmclapi + "maven/"));
        }else if (downloadSource == DownloadSource.mcbbs){
            return new URL(Util.regReplace(url,"https://libraries.minecraft.net/",mcbbs + "maven/"));
        }
        return null;
    }
    public static URL Log4jFileURL(MinecraftVersionJsonFileObject MinecraftVersionJsonFileObject, DownloadSource downloadSource) throws MalformedURLException {
        String url = String.valueOf(MinecraftVersionJsonFileObject.getLogging().getClient().getFile().getUrl());
        if (downloadSource == DownloadSource.official){
            return new URL(url);
        } else if (downloadSource == DownloadSource.bmclapi) {
            return new URL(Util.regReplace(url,"https://launcher.mojang.com/",bmclapi));
        }else if (downloadSource == DownloadSource.mcbbs){
            return new URL(Util.regReplace(url,"https://launcher.mojang.com/",mcbbs));
        }
        return null;
    }
    public static URL assetIndexFileURL(MinecraftVersionJsonFileObject MinecraftVersionJsonFileObject, DownloadSource downloadSource) throws MalformedURLException {
        String url = String.valueOf(MinecraftVersionJsonFileObject.getAssetIndex().getUrl());
        if (downloadSource == DownloadSource.official){
            return new URL(url);
        } else if (downloadSource == DownloadSource.bmclapi) {
            return new URL(Util.regReplace(url,"https://launchermeta.mojang.com/",bmclapi));
        }else if (downloadSource == DownloadSource.mcbbs){
            return new URL(Util.regReplace(url,"https://launchermeta.mojang.com/",mcbbs));
        }
        return null;
    }
    enum DownloadSource{
        official,bmclapi,mcbbs
    }
    enum VersionManifest{
        v1,v2;

    }
}
