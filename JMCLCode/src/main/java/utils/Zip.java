package utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Zip {
    public static boolean unzip(String zipFilePath, String unzipTheDirectory) throws Exception {
        File desDir = new File(unzipTheDirectory);
        if (!desDir.exists()) if (!desDir.mkdir()) return false;
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        File file;
        if (zipEntry == null) {
            return false;
        }
        while (zipEntry != null) {
            if (zipEntry.isDirectory()) {
                String unzipFilePath = unzipTheDirectory + File.separator + zipEntry.getName();
                mkdir(new File(unzipFilePath));
            } else {
                String unzipFilePath = unzipTheDirectory + File.separator + zipEntry.getName();
                file = new File(unzipFilePath);
                mkdir(file.getParentFile());
                BufferedOutputStream bufferedOutputStream =
                        new BufferedOutputStream(new FileOutputStream(unzipFilePath));
                byte[] bytes = new byte[1024];
                int readLen;
                while ((readLen = zipInputStream.read(bytes)) != -1) {
                    bufferedOutputStream.write(bytes, 0, readLen);
                }
                bufferedOutputStream.close();
            }
            zipInputStream.closeEntry();
            zipEntry = zipInputStream.getNextEntry();
        }
        zipInputStream.close();
        return true;

    }

    private static void mkdir(File file){
        if (null == file || file.exists()) {
            return;
        }
        mkdir(file.getParentFile());
        file.mkdir();
    }
}
