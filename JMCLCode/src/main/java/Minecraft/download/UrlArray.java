package minecraft.download;


import jsonAnalysis.download.minecraft.library.VersionJson;
import jsonAnalysis.download.minecraft.library.VersionManifest;
import jsonAnalysis.setup.Setup;
import minecraft.VersionProcessing;
import minecraft.information.VersionJsonManifest;
import org.jetbrains.annotations.NotNull;
import utils.Utils;

import java.net.MalformedURLException;
import java.net.URL;

import static minecraft.download.Url.bmclapi;
import static minecraft.download.Url.mcbbs;
import static minecraft.information.VersionJsonManifest.v1;
import static minecraft.information.VersionJsonManifest.v2;

public class UrlArray {
    public static @NotNull URL[] versionManifestJsonURL(VersionJsonManifest versionJsonManifest) throws MalformedURLException, MalformedURLException {
        if (versionJsonManifest == v1) {
            return new URL[]{new URL("https://launchermeta.mojang.com/" + v1),
                    new URL(bmclapi + v1),
                    new URL(mcbbs + v1)};
        } else {
            return new URL[]{new URL("https://launchermeta.mojang.com/" + v2),
                    new URL(bmclapi + v2),
                    new URL(mcbbs + v2)};
        }
    }

    public static @NotNull URL[] versionJsonFileURL(VersionManifest VersionManifest, String id) throws MalformedURLException {
        String url = String.valueOf(VersionProcessing.getUrl(VersionManifest, id));
        if (Setup.getSetupInstance().download.ifUsesNewURLDownloadingVersionJson) {
            return new URL[]{new URL(url),
                    new URL(Utils.regexReplace(url, "https://piston-meta.mojang.com/", bmclapi)),
                    new URL(Utils.regexReplace(url, "https://piston-meta.mojang.com/", mcbbs))};
        } else {
            return new URL[]{new URL(url),
                    new URL(Utils.regexReplace(url, "https://launchermeta.mojang.com/", bmclapi)),
                    new URL(Utils.regexReplace(url, "https://launchermeta.mojang.com/", mcbbs))};
        }
    }

    public static @NotNull URL[] versionJarFileURL(@NotNull VersionJson VersionJson) throws MalformedURLException {
        String url = String.valueOf(VersionJson.getDownloads().getClient().getUrl());
        return new URL[]{new URL(url),
                new URL(Utils.regexReplace(url, "https://launcher.mojang.com/", bmclapi)),
                new URL(Utils.regexReplace(url, "https://launcher.mojang.com/", mcbbs))};
    }

    public static @NotNull URL[] Log4jFileURL(@NotNull VersionJson VersionJson) throws MalformedURLException {
        String url = String.valueOf(VersionJson.getLogging().getClient().getFile().getUrl());
        return new URL[]{new URL(url),
                new URL(Utils.regexReplace(url, "https://launcher.mojang.com/", bmclapi)),
                new URL(Utils.regexReplace(url, "https://launcher.mojang.com/", mcbbs))};
    }

    public static @NotNull URL[] nativesJarURL(@NotNull VersionJson VersionJson, int i) throws MalformedURLException {
        String url = String.valueOf(VersionJson.getLibraries()[i].getDownloads().getClassifiers().getNativesWindows().getUrl());
        return getLibrariesURL(url);
    }

    public static @NotNull URL[] otherJarLibrariesURL(@NotNull VersionJson VersionJson, int i) throws MalformedURLException {
        String url = String.valueOf(VersionJson.getLibraries()[i].getDownloads().getArtifact().getUrl());
        return getLibrariesURL(url);
    }

    private static @NotNull URL[] getLibrariesURL(String url) throws MalformedURLException {
        return new URL[]{
                new URL(url),
                new URL(Utils.regexReplace(url, "https://libraries.minecraft.net/", bmclapi + "maven/")),
                new URL(Utils.regexReplace(url, "https://libraries.minecraft.net/", mcbbs + "maven/"))
        };
    }

    public static @NotNull URL[] assetIndexJsonURL(@NotNull VersionJson VersionJson) throws MalformedURLException {
        String url = String.valueOf(VersionJson.getAssetIndex().getUrl());
        return new URL[]{
                new URL(url),
                new URL(Utils.regexReplace(url, "https://launchermeta.mojang.com/", bmclapi)),
                new URL(Utils.regexReplace(url, "https://launchermeta.mojang.com/", mcbbs))
        };
    }

    public static @NotNull URL[] assetIndexFileURL(@NotNull String hash) throws MalformedURLException {
        String incompletePath = hash.substring(0, 2) + "/" + hash;
        return new URL[]{
                new URL("https://resources.download.minecraft.net/" + incompletePath),
                new URL(bmclapi + "assets/" + incompletePath),
                new URL(mcbbs + "assets/" + incompletePath)
        };
    }
}
