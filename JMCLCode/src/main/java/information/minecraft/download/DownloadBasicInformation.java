package information.minecraft.download;


import information.minecraft.Version;

import java.nio.file.Paths;

public class DownloadBasicInformation {
    String mainPath;
    Version version;
    String runPath = Paths.get("").toAbsolutePath().toString();


    public DownloadBasicInformation(String mainPath, Version version) {
        this.version = version;
        this.mainPath = mainPath;
    }

    public String getMainPath() {
        return mainPath;
    }

    public void setMainPath(String mainPath) {
        this.mainPath = mainPath;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public String getRunPath() {
        return runPath;
    }

    public void setRunPath(String runPath) {
        this.runPath = runPath;
    }
}
