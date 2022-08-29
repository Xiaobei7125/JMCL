import JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.MinecraftVersionManifestObject;
import JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.MinecraftVersionObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.*;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.*;

import static JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.MinecraftVersionObject.getGsonObject;


public class Test {
    public static void main(String[] ages) throws Exception {
        //microsoftLogin();
        MinecraftUtils.downloadMinecraft(new MinecraftAttribute("E:\\JavaTest\\JMCL\\.minecraft\\", "1.17", DownloadURL.DownloadSource.bmclapi));
    }
}