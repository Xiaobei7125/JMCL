public class Test {
    public static void main(String[] ages) throws Exception {
        //BufferedReader mainPath = new BufferedReader(new InputStreamReader(System.in));
        //BufferedReader id = new BufferedReader(new InputStreamReader(System.in));
        String mainPath = "E:\\JavaTest\\JMCL\\.minecraft\\";
        String id = "rd-132211";
        //microsoftLogin();
        //MinecraftUtils.downloadMinecraft(new MinecraftAttribute(mainPath.readLine(), id.readLine()));
        MinecraftUtils.downloadMinecraft(new MinecraftAttribute(mainPath, id));
        /*
        //MinecraftAttribute MinecraftAttribute = new MinecraftAttribute(mainPath.readLine(), id.readLine());
        MinecraftAttribute MinecraftAttribute = new MinecraftAttribute(mainPath, id);
        MinecraftVersionManifestObject MinecraftVersionManifestObject = MinecraftRequest.getMinecraftVersionManifestObject();
        MinecraftVersionObject MinecraftVersionObject = MinecraftRequest
                .getMinecraftVersionObject(MinecraftVersionManifestObject,MinecraftAttribute);
        String name = MinecraftAttribute.id+"." + MinecraftDownload.VersionFile.jar;
        String path = MinecraftAttribute.mainPath+"versions\\"+ MinecraftAttribute.id+"\\";
        String i;
        boolean b;
        System.out.println(b = Objects.equals(MinecraftVersionObject.getDownloads().getClient().getSha1(), i = Utils.fileSha1(new File(path + name))));
        System.out.println(MinecraftVersionObject.getDownloads().getClient().getSha1());
        System.out.println(i);
        /*if (!b){
            MinecraftDownloadsUtils.downloadsVersionFileUtils(MinecraftVersionObject,MinecraftAttribute);
        }

         */
        //MinecraftDownloadsUtils.downloadsVersionFileUtils(MinecraftVersionObject,MinecraftAttribute);
    }
}
//"E:\\JavaTest\\JMCL\\.minecraft\\"
//"1.17"