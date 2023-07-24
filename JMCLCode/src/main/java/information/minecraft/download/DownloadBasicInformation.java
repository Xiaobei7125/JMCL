package information.minecraft.download;


import java.nio.file.Paths;

public class DownloadBasicInformation {
    String mainPath;
    String id;
    String runPath = Paths.get("").toAbsolutePath().toString();


    public DownloadBasicInformation(String mainPath, String id) {
        this.id = id;
        this.mainPath = mainPath;
    }

    public String getMainPath() {
        return mainPath;
    }

    public void setMainPath(String mainPath) {
        this.mainPath = mainPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRunPath() {
        return runPath;
    }

    public void setRunPath(String runPath) {
        this.runPath = runPath;
    }
}
