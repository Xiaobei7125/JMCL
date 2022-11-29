import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static void deleteDirector(String path){
        File[] array = new File(path).listFiles();
        assert array != null;
        for (File file : array) {
            if (file.isDirectory()) {
                deleteDirector(file.getPath());
            }
            file.delete();
        }
    }
    public static void downloadAFile(URL url, String incompletePath, String name) throws IOException {
        File file = new File(incompletePath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
        if(!file.canWrite()) file.setWritable(true);
        //ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setConnectTimeout(1000);
        httpURLConnection.setReadTimeout(1000);
        if (httpURLConnection.getResponseCode() == 200) {
            InputStream in = httpURLConnection.getInputStream();
            ReadableByteChannel readableByteChannel = Channels.newChannel(in);
            FileOutputStream fileOutputStream = new FileOutputStream(incompletePath + name);
            FileChannel fileChannel = fileOutputStream.getChannel();
            fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            fileChannel.close();
        }
    }
    public static void MultiThreadedDownloadAFile(URL url, String path) throws IOException {
        int totalSize = Objects.requireNonNull(getFileContent(url)).toString().getBytes().length;
        //new FileInputStream(new File(new URL(url).toURI())).getChannel();
        long numberOfSegments = (long) Math.ceil(totalSize/SetUp.multiThreadedDownloadAFileSegmentSize);
        for (int i = 0;i<=numberOfSegments;i++) {
            int finalI = i;
            PublicVariable.executorService.execute(() -> {
                try {
                    RandomAccessFile randomAccessFile = new RandomAccessFile(path, "rwd");
                    randomAccessFile.seek((long) SetUp.multiThreadedDownloadAFileSegmentSize * finalI);
                    byte[] bytes = new byte[SetUp.multiThreadedDownloadAFileSegmentSize];
                    randomAccessFile.read(bytes);
                    randomAccessFile.close();
                    randomAccessFile = new RandomAccessFile(path, "rwd");
                    randomAccessFile.seek((long) SetUp.multiThreadedDownloadAFileSegmentSize * finalI);
                    randomAccessFile.write(bytes);
                    randomAccessFile.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
    public static String regexReplace(String input, String regex, String replacement) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        return m.replaceAll(replacement);
    }
    public static String[] regexMatching(String input, String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        String[] StringArray = new String[10000];
        int c = 0 ;
        while (matcher.find()){
            StringArray[c] = matcher.group();
            c++;
        }
        return Utils.removeNull(StringArray);
    }
    public static StringBuilder getFileContent(URL url) throws IOException {
        URLConnection connection = url.openConnection();
        if (connection instanceof HttpsURLConnection httpsConnection) {
            httpsConnection.setRequestMethod("GET");
            httpsConnection.setDoOutput(true);
            httpsConnection.setDoInput(true);
            httpsConnection.setUseCaches(false);
            httpsConnection.setConnectTimeout(1000);
            httpsConnection.setReadTimeout(1000);
            BufferedReader is = new BufferedReader(new InputStreamReader(httpsConnection.getInputStream()));
            StringBuilder body = new StringBuilder();
            String a;
            while ((a = is.readLine()) != null) {
                body.append(a);
            }
            return body;
        } else if (connection instanceof HttpURLConnection httpConnection) {
            httpConnection.setRequestMethod("GET");
            httpConnection.setDoOutput(true);
            httpConnection.setDoInput(true);
            httpConnection.setUseCaches(false);
            httpConnection.setConnectTimeout(1000);
            httpConnection.setReadTimeout(1000);
            BufferedReader is = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            StringBuilder body = new StringBuilder();
            String a;
            while ((a = is.readLine()) != null) {
                body.append(a);
            }
            return body;
        } else {
            System.out.println("The URL is null");
        }
        return null;
    }
    public static StringBuilder getPartOfTheFileContent(URL url,int start,int end) throws IOException {
        URLConnection connection = url.openConnection();
        if (connection instanceof HttpsURLConnection httpsConnection) {
            httpsConnection.setRequestMethod("GET");
            httpsConnection.setDoOutput(true);
            httpsConnection.setDoInput(true);
            httpsConnection.setUseCaches(false);
            httpsConnection.setConnectTimeout(1000);
            httpsConnection.setReadTimeout(1000);
            httpsConnection.setRequestProperty("Range", "bytes="+start+"-"+end);
            BufferedReader is = new BufferedReader(new InputStreamReader(httpsConnection.getInputStream()));
            StringBuilder body = new StringBuilder();
            String a;
            while ((a = is.readLine()) != null) {
                body.append(a);
            }
            return body;
        } else if (connection instanceof HttpURLConnection httpConnection) {
            httpConnection.setRequestMethod("GET");
            httpConnection.setDoOutput(true);
            httpConnection.setDoInput(true);
            httpConnection.setUseCaches(false);
            httpConnection.setConnectTimeout(1000);
            httpConnection.setReadTimeout(1000);
            httpConnection.setRequestProperty("Range", "bytes="+start+"-"+end);
            BufferedReader is = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            StringBuilder body = new StringBuilder();
            String a;
            while ((a = is.readLine()) != null) {
                body.append(a);
            }
            return body;
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
    public static String fileSha1(File file) throws NoSuchAlgorithmException {
        /*BufferedReader in = new BufferedReader(new FileReader(file), 5 * 1024 * 1024);
        String input = "";
        String i;
        while ((i = in.readLine()) != null) input = input + i;
        in.close();
        */
        if(!file.exists()){
            return null;
        }else {
            byte[] input = readToString(String.valueOf(file));
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(input);
            BigInteger no = new BigInteger(1, messageDigest);
            StringBuilder hashText = new StringBuilder(no.toString(16));
            while (hashText.length() < 32) hashText.insert(0, "0");
            return hashText.toString();
        }
    }
    public static byte[] readToString(String fileName) {
        File file = new File(fileName);
        long fileLength = file.length();
        byte[] fileContent = new byte[(int) fileLength];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(fileContent);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }
    enum xboxLiveType{
        XBL,XSTS
    }
}
