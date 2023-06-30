package Utils;

import Other.PublicVariable;
import jsonAnalysis.setup.Setup;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

import static Utils.Utils.getPartOfTheFileContent;

public class Download {
    public static void downloadAFile(URL url, String incompletePath, String name) throws IOException {
        File file = new File(incompletePath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
        if (!file.canWrite()) file.setWritable(true);
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
            FileOutputStream fileOutputStream = new FileOutputStream(incompletePath + name);
            FileChannel fileChannel = fileOutputStream.getChannel();
            fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            fileChannel.close();
        }
        if (file.length() == 0) {
            file.delete();
        }
    }

    public static void MultiThreadedDownloadAFile(URL url, String path) throws IOException {
        File file = new File(path);
        if (file.exists() && !file.isDirectory()) file.delete();
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
            long numberOfSegments = (long) Math.ceil(totalSize / Setup.getSetupInstance().download.threads.multiThreadedDownload.multiThreadedDownloadAFileSegmentSize);
            for (int i = 0; i <= numberOfSegments; i++) {
                int finalI = i;
                PublicVariable.multiThreadedDownloadExecutorService.execute(() -> {
                    try {
                        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
                        if (!file.exists() && !file.isDirectory()) file.createNewFile();
                        RandomAccessFile randomAccessFile = new RandomAccessFile(path, "rwd");
                        randomAccessFile.setLength(totalSize);
                        randomAccessFile.seek((long) Setup.getSetupInstance().download.threads.multiThreadedDownload.multiThreadedDownloadAFileSegmentSize * finalI);
                        byte[] bytes;
                        for (int j = 0; j != Setup.getSetupInstance().download.downloadRetries; j++) {
                            try {
                                int start = Setup.getSetupInstance().download.threads.multiThreadedDownload.multiThreadedDownloadAFileSegmentSize * finalI;
                                int end;
                                if (finalI != numberOfSegments) {
                                    end = Setup.getSetupInstance().download.threads.multiThreadedDownload.multiThreadedDownloadAFileSegmentSize * (finalI + 1) - 1;
                                } else {
                                    end = totalSize;
                                }
                                bytes = getPartOfTheFileContent(url, start, end);
                                assert bytes != null;
                                randomAccessFile.write(bytes);
                                randomAccessFile.close();
                                if (file.length() == 0) {
                                    file.delete();
                                }
                                break;
                            } catch (Throwable ignored) {
                            }
                        }
                        randomAccessFile.close();
                    } catch (IOException ignored) {
                    }
                });
            }
        }
        if (file.length() == 0) {
            file.delete();
        }
    }
}
