import JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.MinecraftVersionManifestV2Object;

public class MinecraftVersionProcessing {
    /** 这个方法输入MC版本列表变量,版本id;
     *  返回版本的type;
     */
    public static String getType(MinecraftVersionManifestV2Object MinecraftVersionManifestV2Object, String id){
        String[] idArray = MinecraftVersionManifestV2Object.getIdArray();
        String[] typeArray = MinecraftVersionManifestV2Object.getTypeArray();
        int i;
        for (i = 0;i < idArray.length;i++){
            if(idArray[i].equals(id)){
                break;
            }
        }
        return typeArray[i];
    }
    /** 这个方法输入MC版本列表变量,release或snapshot版本;
     *  返回release或snapshot版本数组;
     */
    public static String[] getTypeArray(MinecraftVersionManifestV2Object MinecraftVersionManifestV2Object,Type type){
        String[] idArray = MinecraftVersionManifestV2Object.getIdArray();
        String[] typeArray = new String[getTypeArrayCount(MinecraftVersionManifestV2Object,type)];
        int b = 0;
        for (int i = 0;i < idArray.length;i++){
            if(getType(MinecraftVersionManifestV2Object,idArray[i]).equals(type.toString())){
                typeArray[b] = idArray[i];
                b++;
            }
        }
        return typeArray;
    }
    /** 这个方法输入MC版本列表变量,release或snapshot版本;
     *  返回release或snapshot版本数组长度;
     */
    public static int getTypeArrayCount(MinecraftVersionManifestV2Object MinecraftVersionManifestV2Object, Type type){
        String[] idArray = MinecraftVersionManifestV2Object.getIdArray();
        int typeArrayCount = 0;
        for (int i = 0;i < idArray.length;i++){
            if(getType(MinecraftVersionManifestV2Object,idArray[i]).equals(type.toString())){
                typeArrayCount++;
            }
        }
        return typeArrayCount;
    }
    enum Type{
        release,snapshot
    }
}
