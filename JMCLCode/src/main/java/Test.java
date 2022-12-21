import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] ages) throws Exception {
        //BufferedReader mainPath = new BufferedReader(new InputStreamReader(System.in));
        //BufferedReader id = new BufferedReader(new InputStreamReader(System.in));
        String mainPath = "E:\\JavaTest\\JMCL\\.minecraft\\";
        String id = "1.18.2";
        //microsoftLogin();
        //MinecraftUtils.downloadMinecraft(new MinecraftAttribute(mainPath.readLine(), id.readLine()));
        MinecraftUtils.downloadMinecraft(new MinecraftAttribute(mainPath, id));
        PublicVariable.executorService.shutdown();
        for (; ; ) {
            if (PublicVariable.executorService.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
                PublicVariable.multiThreadedDownloadExecutorService.shutdownNow();
                System.out.println(PublicVariable.executorService.isTerminated());
                System.out.println(MinecraftDownloadsUtils.end + "/" + MinecraftDownloadsUtils.error + "/" +
                        (MinecraftDownloadsUtils.error + MinecraftDownloadsUtils.end));
                break;
            }
        }
    }
}
//"E:\\JavaTest\\JMCL\\.minecraft\\"
//"1.17"