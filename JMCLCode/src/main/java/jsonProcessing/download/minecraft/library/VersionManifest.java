package jsonProcessing.download.minecraft.library;

import com.google.gson.annotations.Since;

import java.net.URL;
import java.util.Objects;

public class VersionManifest {
    @Since(1)
    private Latest latest;
    @Since(1)
    private Versions[] versions;

    public String[] getIdArray() {
        String[] idArray = new String[versions.length];
        for (int i = 0; i < versions.length; i++) {
            idArray[i] = versions[i].id;
        }
        return idArray;
    }

    public String[] getTypeArray() {
        String[] typeArray = new String[versions.length];
        for (int i = 0; i < versions.length; i++) {
            typeArray[i] = versions[i].type;
        }
        return typeArray;
    }

    public URL[] getUrlArray() {
        URL[] urlArray = new URL[versions.length];
        for (int i = 0; i < versions.length; i++) {
            urlArray[i] = versions[i].url;
        }
        return urlArray;
    }

    public String[] getSha1Array() {
        String[] sha1Array = new String[versions.length];
        for (int i = 0; i < versions.length; i++) {
            sha1Array[i] = versions[i].sha1;
        }
        return sha1Array;
    }

    public String getSha1(String id) {
        String[] idArray = getIdArray();
        String sha1;
        for (int i = 0; i < idArray.length; i++){
            if (Objects.equals(idArray[i], id)){
                return getSha1Array()[i];
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
