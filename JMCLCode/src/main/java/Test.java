import minecraft.Attribute;
import minecraft.Utils;

import java.io.IOException;

public class Test {
    public static void main(String[] ages) throws IOException {
        //BufferedReader mainPath = new BufferedReader(new InputStreamReader(System.in));
        //BufferedReader id = new BufferedReader(new InputStreamReader(System.in));
        String mainPath = "E:\\JavaTest\\JMCL\\.minecraft\\";
        String id = "1.19.3";
        //microsoftLogin();
        //minecraft.Utils.downloadMinecraft(new minecraft.Attribute(mainPath.readLine(), id.readLine()));
        Utils.downloadMinecraft(new Attribute(mainPath, id));
        //VersionManifest MinecraftVersionManifestObject = Request.getMinecraftVersionManifestObject();
        //DownloadsUtils.downloadsVersionFileUtils(Request.getMinecraftVersionObject(MinecraftVersionManifestObject, new Attribute(mainPath,id)),new Attribute(mainPath,id));
        //PublicVariable.executorService.shutdown();
    }
}
//"E:\\JavaTest\\JMCL\\.minecraft\\"
//"1.17"