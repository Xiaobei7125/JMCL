package utils;

import jsonAnalysis.setup.Setup;
import other.IThreadManagement;
import other.PublicVariable;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.atomic.AtomicBoolean;

import static utils.Utils.getPartOfTheFileContent;

public class Download {
    public static boolean downloadAFile(URL url, File file) throws IOException {
        if (!file.exists() && !file.isDirectory()) if (!file.mkdir()) return false;
        if (!file.canWrite()) if (!file.setWritable(true)) return false;
        //ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setConnectTimeout(Setup.getSetupInstance().download.downloadConnectTimeout);
        httpURLConnection.setReadTimeout(Setup.getSetupInstance().download.downloadReadTimeout);
        if (httpURLConnection.getResponseCode() == 200) {
            InputStream in = httpURLConnection.getInputStream();
            ReadableByteChannel readableByteChannel = Channels.newChannel(in);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            FileChannel fileChannel = fileOutputStream.getChannel();
            fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            fileChannel.close();
        }
        if (file.length() == 0) return file.delete();
        return true;
    }

    public static boolean MultiThreadedDownloadAFile(URL url, File file) throws IOException {
        if (file.exists() && !file.isDirectory()) if (file.delete()) return false;
        URLConnection urlConnection = url.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setConnectTimeout(Setup.getSetupInstance().download.downloadConnectTimeout);
        httpURLConnection.setReadTimeout(Setup.getSetupInstance().download.downloadReadTimeout);
        int responseCode = httpURLConnection.getResponseCode();
        if (responseCode == 200) {
            int totalSize = urlConnection.getContentLength();
            long numberOfSegments = (long) Math.ceil((double) totalSize / Setup.getSetupInstance().download.threads.multiThreadedDownload.multiThreadedDownloadAFileSegmentSize);
            for (int i = 0; i < numberOfSegments; i++) {
                int finalI = i;
                IThreadManagement iThreadManagement = () -> {
                    AtomicBoolean r = new AtomicBoolean();
                    PublicVariable.multiThreadedDownloadExecutorService.execute(() -> {
                        try {
                            //如果父目录不存在，就创建;                     如果创建失败，就结束此进程;
                            if (!file.getParentFile().exists()) if (!file.getParentFile().mkdirs()) return;
                            //如果文件不存在或文件是文件夹，就创建;           如果创建失败，就结束此进程;
                            if (!file.exists() || file.isDirectory()) if (!file.createNewFile()) return;
                            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
                            randomAccessFile.setLength(totalSize);
                            randomAccessFile.seek((long) Setup.getSetupInstance().download.threads.multiThreadedDownload.multiThreadedDownloadAFileSegmentSize * finalI);
                            byte[] bytes;
                            int start = Setup.getSetupInstance().download.threads.multiThreadedDownload.multiThreadedDownloadAFileSegmentSize * finalI;
                            int end;
                            if (finalI != numberOfSegments) {
                                end = Setup.getSetupInstance().download.threads.multiThreadedDownload.multiThreadedDownloadAFileSegmentSize * (finalI + 1) - 1;
                            } else {
                                end = totalSize;
                            }
                            bytes = getPartOfTheFileContent(url, start, end);
                            //assert bytes != null;
                            randomAccessFile.write(bytes);
                            randomAccessFile.close();
                            r.set(true);
                        } catch (IOException ignored) {
                        }
                    });
                    return r.get();
                };
            }
        } else {
            System.out.println(responseCode);
            return false;
        }
        return false;
    }
}
