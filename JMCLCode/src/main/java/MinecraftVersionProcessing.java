import JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.MinecraftVersionManifestObject;

public class MinecraftVersionProcessing {
    /** 这个方法输入MC版本列表变量,版本id;
     *  返回版本的type;
     */
    public static String getType(MinecraftVersionManifestObject MinecraftVersionManifestObject, String id){
        String[] idArray = MinecraftVersionManifestObject.getIdArray();
        String[] typeArray = MinecraftVersionManifestObject.getTypeArray();
        int i;
        for (i = 0;i < idArray.length;i++){
            if(idArray[i].equals(id)){
                return typeArray[i];
            }
        }
        return "null";
    }
    /** 这个方法输入MC版本列表变量,release或snapshot版本;
     *  返回release或snapshot版本数组;
     */
    public static String[] getTypeArray(MinecraftVersionManifestObject MinecraftVersionManifestObject,Type type){
        String[] idArray = MinecraftVersionManifestObject.getIdArray();
        String[] typeArray = new String[getTypeArrayCount(MinecraftVersionManifestObject,type)];
        int b = 0;
        for (int i = 0;i < idArray.length;i++){
            if(getType(MinecraftVersionManifestObject,idArray[i]).equals(type.toString())){
                typeArray[b] = idArray[i];
                b++;
            }
        }
        return typeArray;
    }
    /** 这个方法输入MC版本列表变量,release或snapshot版本;
     *  返回release或snapshot版本数组长度;
     */
    public static int getTypeArrayCount(MinecraftVersionManifestObject MinecraftVersionManifestObject, Type type){
        String[] idArray = MinecraftVersionManifestObject.getIdArray();
        int typeArrayCount = 0;
        for (int i = 0;i < idArray.length;i++){
            if(getType(MinecraftVersionManifestObject,idArray[i]).equals(type.toString())){
                typeArrayCount++;
            }
        }
        return typeArrayCount;
    }
    /** 这个方法输入MC版本列表变量,版本id;
     *  返回版本的url;
     */
    public static String getUrl(MinecraftVersionManifestObject MinecraftVersionManifestObject, String id){
        String[] idArray = MinecraftVersionManifestObject.getIdArray();
        String[] urlArray = MinecraftVersionManifestObject.getUrlArray();
        int i;
        for (i = 0;i < idArray.length;i++){
            if(idArray[i].equals(id)){
                return urlArray[i];
            }
        }
        return "null";
    }
    /** 这个方法输入MC版本列表变量,版本id ;
     *  返回此版本是否存在 ;
     */
    public static boolean ifId(MinecraftVersionManifestObject MinecraftVersionManifestObject, String id){
        String[] idArray = MinecraftVersionManifestObject.getIdArray();
        String[] typeArray = MinecraftVersionManifestObject.getTypeArray();
        int i;
        for (i = 0;i < idArray.length;i++){
            if(idArray[i].equals(id)){
                return true;
            }
        }
        return false;
    }
    enum Type{
        release,snapshot,old_beta,old_alpha
    }
}

