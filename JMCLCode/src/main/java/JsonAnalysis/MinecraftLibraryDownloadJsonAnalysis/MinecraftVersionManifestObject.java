package JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis;

import com.google.gson.annotations.Since;

public class MinecraftVersionManifestObject {
    @Since(1)
    private Latest latest;
    @Since(1)
    private Versions[] versions;
    protected class Latest{
        @Since(1)
        private String release;
        @Since(1)
        private String snapshot;

    }
    protected class Versions{
        @Since(1)
        private String id;
        @Since(1)
        private String type;
        @Since(1)
        private String url;
        @Since(1)
        private String time;
        @Since(1)
        private String releaseTime;
        @Since(2)
        private String sha1;
        @Since(2)
        private String complianceLevel;
    }
    public String[] getIdArray(){
        String[] idArray = new String[versions.length];
        for (int i = 0; i < versions.length; i++){
            idArray[i] = versions[i].id;
        }
        return idArray;
    }

    public String[] getTypeArray(){
        String[] typeArray = new String[versions.length];
        for (int i = 0; i < versions.length; i++){
            typeArray[i] = versions[i].type;
        }
        return typeArray;
    }

    public String[] getUrlArray(){
        String[] urlArray = new String[versions.length];
        for (int i = 0; i < versions.length; i++){
            urlArray[i] = versions[i].url;
        }
        return urlArray;
    }

    public String[] getSha1Array(){
        String[] sha1Array = new String[versions.length];
        for (int i = 0; i < versions.length; i++){
            sha1Array[i] = versions[i].sha1;
        }
        return sha1Array;
    }

    public String getRelease(){
        return latest.release;
    }

    public String getSnapshot(){
        return latest.snapshot;
    }
}
