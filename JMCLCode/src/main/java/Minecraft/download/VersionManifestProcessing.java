package minecraft.download;


import jsonProcessing.download.minecraft.library.VersionManifest;

import java.net.URL;

public class VersionManifestProcessing {
    /**
     * 这个方法输入MC版本列表变量,版本id;
     * 返回版本的type;
     */
    public static String getVersionType(VersionManifest VersionManifest, String id) {
        String[] idArray = VersionManifest.getIdArray();
        String[] typeArray = VersionManifest.getTypeArray();
        int i;
        for (i = 0; i < idArray.length; i++) {
            if (idArray[i].equals(id)) {
                return typeArray[i];
            }
        }
        return "null";
    }

    /**
     * 这个方法输入MC版本列表变量,release或snapshot版本;
     * 返回release或snapshot版本数组;
     */
    public static String[] getTypeArray(VersionManifest VersionManifest, Type type) {
        String[] idArray = VersionManifest.getIdArray();
        String[] typeArray = new String[getTypeArrayCount(VersionManifest, type)];
        int b = 0;
        for (String s : idArray) {
            if (getVersionType(VersionManifest, s).equals(type.toString())) {
                typeArray[b] = s;
                b++;
            }
        }
        return typeArray;
    }

    /**
     * 这个方法输入MC版本列表变量,release或snapshot版本;
     * 返回release或snapshot版本数组长度;
     */
    public static int getTypeArrayCount(VersionManifest VersionManifest, Type type) {
        String[] idArray = VersionManifest.getIdArray();
        int typeArrayCount = 0;
        for (String s : idArray) {
            if (getVersionType(VersionManifest, s).equals(type.toString())) {
                typeArrayCount++;
            }
        }
        return typeArrayCount;
    }

    /**
     * 这个方法输入MC版本列表变量,版本id;
     * 返回版本的url;
     */
    public static URL getVersionJsonUrl(VersionManifest VersionManifest, String id) {
        String[] idArray = VersionManifest.getIdArray();
        URL[] urlArray = VersionManifest.getUrlArray();
        int i;
        for (i = 0; i < idArray.length; i++) {
            if (idArray[i].equals(id)) {
                return urlArray[i];
            }
        }
        return null;
    }

    /**
     * 这个方法输入MC版本列表变量,版本id;
     * 返回是否有此版本;
     */
    public static boolean isId(VersionManifest VersionManifest, String id) {
        String[] idArray = VersionManifest.getIdArray();
        String[] typeArray = VersionManifest.getTypeArray();
        int i;
        for (i = 0; i < idArray.length; i++) {
            if (idArray[i].equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 这个方法输入MC版本列表变量,版本id ;
     * 返回此版本id在版本id数组的位置 ;
     */
    public static int getIdCount(VersionManifest VersionManifest, String id) {
        String[] idArray = VersionManifest.getIdArray();
        int i;
        for (i = 0; i < idArray.length; i++) {
            if (idArray[i].equals(id)) {
                return i;
            }
        }
        return -1;
    }

    enum Type {
        release, snapshot, old_beta, old_alpha
    }
}

