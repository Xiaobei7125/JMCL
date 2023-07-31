package main;

import information.minecraft.Version;
import information.minecraft.download.DownloadBasicInformation;
import minecraft.Utils;

import java.io.IOException;
import java.net.URISyntaxException;

public class Test {
    public static void main(String[] ages) throws IOException, URISyntaxException, InterruptedException {
        //BufferedReader mainPath = new BufferedReader(new InputStreamReader(System.in));
        //BufferedReader id = new BufferedReader(new InputStreamReader(System.in));
        String mainPath = "F:\\JavaTest\\JMCL\\.minecraft\\";
        String version = "1.20.1";
        //Output.theNameOfTheCallingMethod();
//        long i = 0;
//        Output.output(Output.OutputLevel.main.Test,"\33[3;35;44m91222");
//        while (true) {
//            System.out.print("\33[4;5;6;7;8;9;39;49m91222\r???" + i);
//            Thread.sleep(1000);
//            i = i +1;
//        }
//        GetInformation login = microsoftLogin();
//        if (login != null) {
//            Output.output(Output.OutputLevel.main.Test, login.name() + login.accessToken() + login.UUID());
//        }
        //minecraft.Utils.downloadMinecraft(new minecraft.minecraft.DownloadBasicInformation(mainPath.readLine(), id.readLine()));
        //Output.output(Output.OutputLevel.Test, String.valueOf(v1));
        Utils.downloadMinecraft(new DownloadBasicInformation(mainPath, new Version(version)));
        //VersionManifest MinecraftVersionManifestObject = Request.getMinecraftVersionManifestObject();
        //Libraries.downloadsVersionFileUtils(Request.getMinecraftVersionObject(MinecraftVersionManifestObject, new DownloadBasicInformation(mainPath,id)),new DownloadBasicInformation(mainPath,id));
        //PublicVariable.multiThreadedDownloadExecutorService.shutdown();

    }
}
//"E:\\JavaTest\\JMCL\\.minecraft\\"
//"1.17"