import java.io.IOException;
import java.net.URL;

public class MinecraftDownloads {
    public static void downloadsVersionFile(URL url, String mainPath, String id, versionFile versionFile) throws IOException {
        Util.fileOutput(url,mainPath+"versions\\"+id+"\\",id+"."+versionFile);
    }
    enum versionFile{
        jar,json
    }
}
