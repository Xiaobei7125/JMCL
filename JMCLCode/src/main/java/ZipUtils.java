import java.io.*;
import java.util.zip.*;

public class ZipUtils {
    public static void unzip(String zipFilePath, String desDirectory) throws Exception {

        File desDir = new File(desDirectory);
        if (!desDir.exists()) {
            desDir.mkdir();
        }
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        File file;
        while (zipEntry != null) {
            if (zipEntry.isDirectory()) {
                String unzipFilePath = desDirectory + File.separator + zipEntry.getName();
                mkdir(new File(unzipFilePath));
            } else {
                String unzipFilePath = desDirectory + File.separator + zipEntry.getName();
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

    }
    private static void mkdir(File file){
        if (null == file || file.exists()) {
            return;
        }
        mkdir(file.getParentFile());
        file.mkdir();
    }
}
