public class Test {
    public static void main(String[] ages) throws Exception {
        //BufferedReader mainPath = new BufferedReader(new InputStreamReader(System.in));
        //BufferedReader id = new BufferedReader(new InputStreamReader(System.in));
        String mainPath = "E:\\JavaTest\\JMCL\\.minecraft\\";
        String id = "1.18.2";
        //microsoftLogin();
        //MinecraftUtils.downloadMinecraft(new MinecraftAttribute(mainPath.readLine(), id.readLine()));
        MinecraftUtils.downloadMinecraft(new MinecraftAttribute(mainPath, id));

    }
}
//"E:\\JavaTest\\JMCL\\.minecraft\\"
//"1.17"