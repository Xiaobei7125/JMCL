package main;

import minecraft.Utils;
import minecraft.information.Attribute;

import java.io.IOException;
import java.net.URISyntaxException;

public class Test {
    public static void main(String[] ages) throws IOException, URISyntaxException, InterruptedException {
        //BufferedReader mainPath = new BufferedReader(new InputStreamReader(System.in));
        //BufferedReader id = new BufferedReader(new InputStreamReader(System.in));
        String mainPath = "F:\\JavaTest\\JMCL\\.minecraft\\";
        String id = "1.20.1";
        //Output.theNameOfTheCallingMethod();
//        long i = 0;
//        Output.output(Output.OutputLevel.main.Test,"\33[3;35;44m91222");
//        while (true) {
//            System.out.print("\33[4;5;6;7;8;9;39;49m91222\r???" + i);
//            Thread.sleep(1000);
//            i = i +1;
//        }
//        Login login = microsoftLogin();
//        if (login != null) {
//            Output.output(Output.OutputLevel.main.Test, login.name() + login.accessToken() + login.UUID());
//        }
        //minecraft.Utils.downloadMinecraft(new minecraft.information.Attribute(mainPath.readLine(), id.readLine()));
        //Output.output(Output.OutputLevel.Test, String.valueOf(v1));
        Utils.downloadMinecraft(new Attribute(mainPath, id));
        //VersionManifest MinecraftVersionManifestObject = Request.getMinecraftVersionManifestObject();
        //DownloadsUtils.downloadsVersionFileUtils(Request.getMinecraftVersionObject(MinecraftVersionManifestObject, new Attribute(mainPath,id)),new Attribute(mainPath,id));
        //PublicVariable.multiThreadedDownloadExecutorService.shutdown();

    }
}
//"E:\\JavaTest\\JMCL\\.minecraft\\"
//"1.17"