package utils;


import jsonAnalysis.setup.Setup;

import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static boolean deleteDirectory(String path) {
        File[] array = new File(path).listFiles();
        assert array != null;
        for (File file : array) {
            if (file.isDirectory()) {
                deleteDirectory(file.getPath());
            } else if (!file.delete()) {
                return false;
            }
        }
        return true;
    }


    public static byte[] getData(InputStream is) throws IOException{
        ByteArrayOutputStream bops = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = is.read(buffer)) != -1) {
            bops.write(buffer, 0, len);
        }
        byte[] data = bops.toByteArray();
        bops.flush();
        bops.close();
        is.close();
        return data;
    }

    public static String regexReplace(String input, String regex, String replacement) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        return m.replaceAll(replacement);
    }

    public static String regularExpressionsReplaceNull(String input, String... replaceValue) {
        for (String value : replaceValue) {
            input = regexReplace(input, value, "");
        }
        return input;
    }

    public static String[] regexMatching(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        String[] StringArray = new String[10000];
        int c = 0;
        while (matcher.find()) {
            StringArray[c] = matcher.group();
            c++;
        }
        return Utils.removeNull(StringArray);
    }

    public static StringBuilder getFileContent(URL url) throws IOException {
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
            for (int j = 0; j != Setup.getSetupInstance().download.downloadRetries; j++) {
                try {
                    if (httpConnection.getResponseCode() == 206) {
                        //System.out.println(Thread.currentThread().getName() + httpConnection.getResponseCode());
                        return getData(httpConnection.getInputStream());
                    }
                    //System.out.println(Thread.currentThread().getName() + httpConnection.getResponseCode());
                    //System.out.println(Thread.currentThread().getName() + "Request failed");
                } catch (Throwable e) {
                    //System.out.println(Thread.currentThread().getName()+e);
                }
            }
        } else {
            System.out.println("The URL is null");
        }
        return null;
    }

    public static String[] removeNull(String[] array){
        int c = 0;
        for (String a : array) {
            if (a != null) c++;
        }
        String[] newArray = new String[c];
        System.arraycopy(array, 0, newArray, 0, c);
        return newArray;
    }

    public static StringBuilder deleteSymbol(String s, String symbol){
        StringBuilder newString = new StringBuilder();
        for (String i:s.split("")){
            if (i.equals(symbol)){
                continue;
            }
            newString.append(i);
        }
        return newString;
    }

    public static String fileSha1(File file) throws NoSuchAlgorithmException, IOException {
        if (!file.exists() && !file.isDirectory()) {
            return null;
        }else {
            if (!file.canWrite())
                if (!file.setWritable(true))
                    return null;
            byte[] input = readToString(String.valueOf(file));
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(input);
            BigInteger no = new BigInteger(1, messageDigest);
            StringBuilder hashText = new StringBuilder(no.toString(16));
            while (hashText.length() < 40) hashText.insert(0, "0");
            return hashText.toString();
        }
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

    public enum xboxLiveType {
        XBL, XSTS
    }
}
