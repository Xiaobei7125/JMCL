package jsonProcessing.download.minecraft.library;

import com.google.gson.annotations.Since;
import information.minecraft.Version;

import java.net.URL;
import java.util.Objects;

public class VersionManifest {
    @Since(1)
    private Latest latest;
    @Since(1)
    private Versions[] versions;

    public String[] getVersionArray() {
        String[] idArray = new String[versions.length];
        for (int i = 0; i < versions.length; i++) {
            idArray[i] = versions[i].id;
        }
        return idArray;
    }

    public String[] getVersionTypeArray() {
        String[] typeArray = new String[versions.length];
        for (int i = 0; i < versions.length; i++) {
            typeArray[i] = versions[i].type;
        }
        return typeArray;
    }

    public URL[] getVersionJsonUrlArray() {
        URL[] urlArray = new URL[versions.length];
        for (int i = 0; i < versions.length; i++) {
            urlArray[i] = versions[i].url;
        }
        return urlArray;
    }

    public String[] getVersionJsonSha1Array() {
        String[] sha1Array = new String[versions.length];
        for (int i = 0; i < versions.length; i++) {
            sha1Array[i] = versions[i].sha1;
        }
        return sha1Array;
    }

    public String getVersionJsonSha1(Version version) {
        String[] versionArray = getVersionArray();
        String sha1;
        for (int i = 0; i < versionArray.length; i++) {
            if (Objects.equals(versionArray[i], version.version())) {
                return getVersionJsonSha1Array()[i];
            }
        }
        return "";
    }

    protected static class Versions {
        @Since(1)
        private String id;
        @Since(1)
        private String type;
        @Since(1)
        private URL url;
        @Since(1)
        private String time;
        @Since(1)
        private String releaseTime;
        @Since(2)
        private String sha1;
        @Since(2)
        private String complianceLevel;
    }

    protected static class Latest {
        @Since(1)
        private String release;
        @Since(1)
        private String snapshot;

    }

    public String getRelease(){
        return latest.release;
    }

    public String getSnapshot(){
        return latest.snapshot;
    }
}
