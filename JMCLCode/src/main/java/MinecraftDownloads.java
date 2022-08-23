import java.io.File;
import java.io.IOException;
import java.net.URL;

public class MinecraftDownloads {
    public static void downloadsVersionFile(URL url, String mainPath, String id, VersionFile versionFile) throws IOException {
        Util.fileOutput(url,mainPath+"versions\\"+id+"\\",id+"."+versionFile);
    }
    public static void downloadsNativesLibraries(URL url, String mainPath,String id,String path) throws Exception {
        String name = Util.regReplace(String.valueOf(url),"[a-zA-Z]+://[\\w\\d./-]+/","");
        Util.fileOutput(url,path+"\\natives\\",name);
        ZipUtils.unzip(path+"\\natives\\"+name,mainPath+"versions\\"+id+"\\natives\\");
        File[] array = new File(mainPath+"versions\\"+id+"\\natives\\").listFiles();
        for (File file : array) {
            if (!Util.regReplace(file.getName(), "[\\w\\d-.]+\\.", "").equals("dll")) {
                if (file.isDirectory()) {
                    Util.deleteDirector(file.getPath());
                }
                file.delete();
            }
        }
    }
    public static void downloadsOtherLibraries(URL url, String mainPath,String path) throws IOException {
        String name = Util.regReplace(String.valueOf(url),"[a-zA-Z]+://[\\w\\d./-]+/","");
        path = Util.regReplace(path,name,"");
        Util.fileOutput(url,mainPath+"libraries\\"+path,name);
    }
    public static void downloadsLog4jFile(URL url, String mainPath,String fileId) throws IOException {
        Util.fileOutput(url,mainPath+"assets\\log_configs\\",fileId);
    }
    public static void downloadsAssetIndexFile(URL url, String mainPath,String id) throws IOException {
        Util.fileOutput(url,mainPath+"assets\\indexes\\",id+".json");
    }
    enum VersionFile{
        jar,json
    }
}
