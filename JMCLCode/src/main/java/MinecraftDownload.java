import java.io.File;
import java.io.IOException;
import java.net.URL;

public class MinecraftDownload {
    public static void downloadVersionFile(URL url, String mainPath, String id, VersionFile versionFile) throws IOException {
        Utils.downloadAFile(url,mainPath+"versions\\"+id+"\\",id+"."+versionFile);
    }
    public static boolean downloadNativesDllLibraries(URL url, String mainPath,String id,String path) throws Exception {
        String name = Utils.regexReplace(String.valueOf(url),"[a-zA-Z]+://[\\w\\d./-]+/","");
        Utils.downloadAFile(url,path+"\\natives\\",name);
        ZipUtils.unzip(path+"\\natives\\"+name,mainPath+"versions\\"+id+"\\natives\\");
        File[] array = new File(mainPath+"versions\\"+id+"\\natives\\").listFiles();
        assert array != null;
        for (File file : array) {
            if (!Utils.regexReplace(file.getName(), "[\\w\\d-.]+\\.", "").equals("dll")) {
                if (file.isDirectory()) {
                    Utils.deleteDirector(file.getPath());
                }
                if (file.delete()) return false;
            }
        }
        return true;
    }
    public static void downloadOtherLibraries(URL url, String mainPath,String path) throws IOException {
        String name = Utils.regexReplace(String.valueOf(url),"[a-zA-Z]+://[\\w\\d./-]+/","");
        path = Utils.regexReplace(path,name,"");
        Utils.downloadAFile(url,mainPath+"libraries\\"+path,name);
    }
    public static void downloadLog4jFile(URL url, String mainPath,String fileId) throws IOException {
        Utils.downloadAFile(url,mainPath+"assets\\log_configs\\",fileId);
    }
    public static void downloadAssetIndexJson(URL url, String mainPath,String id) throws IOException {
        Utils.downloadAFile(url,mainPath+"assets\\indexes\\",id+".json");
    }
    public static void downloadAssetIndexFile(URL url, String mainPath,String hash) throws IOException {
        Utils.downloadAFile(url,mainPath+"assets\\objects\\"+hash.substring(0,2)+"\\",hash);
    }
    public static void downloadAssetIndexCopyFile(URL url, String mainPath,String path) throws IOException {
        String name = Utils.regexReplace(path,"[\\w/]+/","");
        path = Utils.regexReplace(path,name,"");
        Utils.downloadAFile(url,mainPath+"assets\\virtual\\legacy\\"+path,name);
    }
    enum VersionFile{
        jar,json
    }
}
