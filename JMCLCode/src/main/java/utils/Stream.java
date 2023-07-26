package utils;

import jsonProcessing.setup.Setup;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class Stream {
    public static byte[] getData(InputStream is) throws IOException {
        ByteArrayOutputStream bops = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = is.read(buffer)) != -1) {
            bops.write(buffer, 0, len);
        }
        byte[] data = bops.toByteArray();
        bops.flush();
        bops.close();
        return data;
    }

    public static StringBuilder getUrlBody(URL url) throws IOException {
        URLConnection connection = url.openConnection();
        if (connection instanceof HttpURLConnection httpConnection) {
            httpConnection.setRequestMethod("GET");
            httpConnection.setDoOutput(true);
            httpConnection.setDoInput(true);
            httpConnection.setUseCaches(false);
            httpConnection.setConnectTimeout(Setup.getSetupInstance().download.downloadConnectTimeout);
            httpConnection.setReadTimeout(Setup.getSetupInstance().download.downloadReadTimeout);
            BufferedReader is = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder body = new StringBuilder();
            String a;
            while ((a = is.readLine()) != null) {
                body.append(a);
            }
            is.close();
            return body;
        } else {
            System.out.println("The URL is null");
        }
        return null;
    }

    public static long getFileSize(URL[] urls) {
        long size = 0;
        for (URL url : urls) {
            try {
                size = url.openConnection().getContentLengthLong();
            } catch (IOException ignored) {
            }
        }
        return size;
    }

    public static byte[] getPartOfTheFileContent(URL url, int start, long end) throws IOException {
        URLConnection connection = url.openConnection();
        if (connection instanceof HttpURLConnection httpConnection) {
            httpConnection.setRequestMethod("GET");
            httpConnection.setDoOutput(true);
            httpConnection.setDoInput(true);
            httpConnection.setUseCaches(false);
            httpConnection.setConnectTimeout(Setup.getSetupInstance().download.downloadConnectTimeout);
            httpConnection.setReadTimeout(Setup.getSetupInstance().download.downloadReadTimeout);
            httpConnection.setRequestProperty("Range", "bytes=" + start + "-" + end);
            httpConnection.connect();
            for (int j = 0; j != Setup.getSetupInstance().download.downloadRetries; j++) {
                try {
                    if (httpConnection.getResponseCode() == 206) {
                        //System.out.println(Thread.currentThread().getName() + httpConnection.getResponseCode());
                        InputStream inputStream = httpConnection.getInputStream();
                        byte[] r = getData(inputStream);
                        inputStream.close();
                        return r;
                    }
                    //Output.output(Output.OutputLevel.Test, String.valueOf(httpConnection.getResponseCode()));
                    //System.out.println(Thread.currentThread().getName() + "Request failed");
                } catch (Throwable e) {
                    //e.printStackTrace();
                }
            }
        } else {
            System.out.println("The URL is null");
        }
        return null;
    }

    public static byte[] readToString(String fileName) throws IOException {
        File file = new File(fileName);
        long fileLength = file.length();
        byte[] fileContent = new byte[(int) fileLength];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(fileContent);
            in.close();
        } catch (IOException e) {
            throw new IOException(e);
        }
        return fileContent;
    }

    public static byte[] writeToString(String fileName, String contents) throws IOException {
        File file = new File(fileName);
        long fileLength = contents.length();
        byte[] fileContent = new byte[(int) fileLength];
        try {
            FileOutputStream out = new FileOutputStream(file);
            out.write(contents.getBytes(StandardCharsets.UTF_8));
            out.close();
        } catch (IOException e) {
            throw new IOException(e);
        }
        return fileContent;
    }
}
