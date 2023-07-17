import imformation.Login;
import other.Output;

import java.io.IOException;
import java.net.URISyntaxException;

import static minecraft.Utils.microsoftLogin;

public class Test {
    public static void main(String[] ages) throws IOException, URISyntaxException, InterruptedException {
        //BufferedReader mainPath = new BufferedReader(new InputStreamReader(System.in));
        //BufferedReader id = new BufferedReader(new InputStreamReader(System.in));
        //String mainPath = "E:\\JavaTest\\JMCL\\.minecraft\\";
        //String id = "1.19.3";
//        long i = 0;
//        Output.output(Output.OutputLevel.Test,"\33[3;35;44m91222");
//        while (true) {
//            System.out.print("\33[4;5;6;7;8;9;39;49m91222\r???" + i);
//            Thread.sleep(1000);
//            i = i +1;
//        }
        Login login = microsoftLogin();
        if (login != null) {
            Output.output(Output.OutputLevel.Test, login.name() + login.accessToken() + login.UUID());
        }
        //minecraft.Utils.downloadMinecraft(new minecraft.imformation.Attribute(mainPath.readLine(), id.readLine()));
        //Utils.downloadMinecraft(new Attribute(mainPath, id));
        //VersionManifest MinecraftVersionManifestObject = Request.getMinecraftVersionManifestObject();
        //DownloadsUtils.downloadsVersionFileUtils(Request.getMinecraftVersionObject(MinecraftVersionManifestObject, new Attribute(mainPath,id)),new Attribute(mainPath,id));
        //PublicVariable.executorService.shutdown();

    }
}
//"E:\\JavaTest\\JMCL\\.minecraft\\"
//"1.17"