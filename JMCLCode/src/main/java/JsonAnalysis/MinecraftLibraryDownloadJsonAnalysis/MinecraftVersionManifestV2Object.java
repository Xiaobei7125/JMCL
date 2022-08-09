package JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis;

public class MinecraftVersionManifestV2Object {
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
        private String sha1;
        private String complianceLevel;
    }

    public String[] getId(){
        String[] id = new String[versions.length];
        for (int i = 0; i <= versions.length; i++){
            id[i] = versions[i].id;
        }
        return id;
    }

    public String[] getType(){
        String[] type = new String[versions.length];
        for (int i = 0; i <= versions.length; i++){
            type[i] = versions[i].type;
        }
        return type;
    }

    public String[] getUrl(){
        String[] url = new String[versions.length];
        for (int i = 0; i <= versions.length; i++){
            url[i] = versions[i].url;
        }
        return url;
    }

    public String[] getSha1(){
        String[] sha1 = new String[versions.length];
        for (int i = 0; i <= versions.length; i++){
            sha1[i] = versions[i].sha1;
        }
        return sha1;
    }

    public String getRelease(){
        return latest.release;
    }

    public String getSnapshot(){
        return latest.snapshot;
    }
}
