import minecraft.Attribute;
import minecraft.MinecraftUtils;

public class Test {
    public static void main(String[] ages) throws Exception {
        //BufferedReader mainPath = new BufferedReader(new InputStreamReader(System.in));
        //BufferedReader id = new BufferedReader(new InputStreamReader(System.in));
        String mainPath = "E:\\JavaTest\\JMCL\\.minecraft\\";
        String id = "1.19.3";
        //microsoftLogin();
        //minecraft.MinecraftUtils.downloadMinecraft(new minecraft.Attribute(mainPath.readLine(), id.readLine()));
        MinecraftUtils.downloadMinecraft(new Attribute(mainPath, id));

    }
}
//"E:\\JavaTest\\JMCL\\.minecraft\\"
//"1.17"