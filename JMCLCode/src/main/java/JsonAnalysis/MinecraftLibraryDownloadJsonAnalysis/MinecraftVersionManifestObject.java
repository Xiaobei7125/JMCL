package JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis;

public class MinecraftVersionManifestObject {
    private Latest latest;
    private Versions[] versions;
    protected class Latest{
        private String release;
        private String snapshot;
    }
    protected class Versions{
        private String id;
        private String type;
        private String url;
        private String time;
        private String releaseTime;
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


    public String getRelease(){
        return latest.release;
    }

    public String getSnapshot(){
        return latest.snapshot;
    }
}
