import java.io.File;
import java.io.IOException;
import java.net.URL;

public class MinecraftDownload {
    public static void downloadVersionFile(URL url, String mainPath, String id, VersionFile versionFile) throws IOException {
        String incompletePath = mainPath + "versions\\" + id + "\\";
        String name = id + "." + versionFile;
        if (SetUp.ifMultiThreadedDownloadAFile){
            Utils.MultiThreadedDownloadAFile(url,incompletePath+name);
        }else {
            Utils.downloadAFile(url, incompletePath, name);
        }
    }
    public static boolean downloadNativesDllLibraries(URL url, String mainPath,String id,String path) throws Exception {
        String name = Utils.regexReplace(String.valueOf(url),"[a-zA-Z]+://[\\w\\d./-]+/","");
        if (SetUp.ifMultiThreadedDownloadAFile){
            Utils.MultiThreadedDownloadAFile(url,path+"\\natives\\"+name);
        }else {
            Utils.downloadAFile(url,path+"\\natives\\",name);
        }
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
        String incompletePath = Utils.regexReplace(path,name,"");
        if (SetUp.ifMultiThreadedDownloadAFile){
            Utils.MultiThreadedDownloadAFile(url,mainPath+"libraries\\"+path);
        }else {
            Utils.downloadAFile(url,mainPath+"libraries\\"+incompletePath,name);
        }
    }
    /*
    if (SetUp.ifMultiThreadedDownloadAFile){
        Utils.MultiThreadedDownloadAFile(url,);
    }else {

    }

     */
    public static void downloadLog4jFile(URL url, String mainPath,String fileId) throws IOException {
        if (SetUp.ifMultiThreadedDownloadAFile){
            Utils.MultiThreadedDownloadAFile(url,mainPath+"assets\\log_configs\\"+fileId);
        }else {
            Utils.downloadAFile(url,mainPath+"assets\\log_configs\\",fileId);
        }

    }
    public static void downloadAssetIndexJson(URL url, String mainPath,String id) throws IOException {
        if (SetUp.ifMultiThreadedDownloadAFile){
            Utils.MultiThreadedDownloadAFile(url,mainPath+"assets\\indexes\\"+id+".json");
        }else {
            Utils.downloadAFile(url,mainPath+"assets\\indexes\\",id+".json");
        }
    }
    public static void downloadAssetIndexFile(URL url, String mainPath,String hash) throws IOException {
        if (SetUp.ifMultiThreadedDownloadAFile){
            Utils.MultiThreadedDownloadAFile(url,mainPath+"assets\\objects\\"+hash.substring(0,2)+"\\"+hash);
        }else {
            Utils.downloadAFile(url,mainPath+"assets\\objects\\"+hash.substring(0,2)+"\\",hash);
        }
    }
    public static void downloadAssetIndexCopyFile(URL url, String mainPath,String path) throws IOException {
        String name = Utils.regexReplace(path,"[\\w/]+/","");
        String incompletePath = Utils.regexReplace(path,name,"");
        if (SetUp.ifMultiThreadedDownloadAFile){
            Utils.MultiThreadedDownloadAFile(url,mainPath+"assets\\virtual\\legacy\\"+path);
        }else {
            Utils.downloadAFile(url,mainPath+"assets\\virtual\\legacy\\"+incompletePath,name);
        }
    }
    enum VersionFile{
        jar,json
    }
}
