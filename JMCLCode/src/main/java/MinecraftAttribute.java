import java.nio.file.Paths;

public class MinecraftAttribute {
    String mainPath;
    String id;
    DownloadURL.DownloadSource downloadSource;
    String runPath = Paths.get("").toAbsolutePath().toString();
    public MinecraftAttribute(String mainPath,String id,DownloadURL.DownloadSource downloadSource){
        this.downloadSource = downloadSource;
        this.id = id;
        this.mainPath = mainPath;
    }

    public String getMainPath() {
        return mainPath;
    }

    public String getId() {
        return id;
    }

    public DownloadURL.DownloadSource getDownloadSource() {
        return downloadSource;
    }

    public String getRunPath() {
        return runPath;
    }

    public void setDownloadSource(DownloadURL.DownloadSource downloadSource) {
        this.downloadSource = downloadSource;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMainPath(String mainPath) {
        this.mainPath = mainPath;
    }

    public void setRunPath(String runPath) {
        this.runPath = runPath;
    }
}
