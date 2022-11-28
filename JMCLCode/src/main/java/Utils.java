import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static void deleteDirector(String path){
        File[] array = new File(path).listFiles();
        for (File file : array) {
            if (file.isDirectory()) {
                deleteDirector(file.getPath());
            }
            file.delete();
        }
    }
    public static void fileOutput(URL url, String path,String name) throws IOException {
        File folder = new File(path);
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs();
        }
        if(!folder.canWrite()) folder.setWritable(true);
        //ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setConnectTimeout(1000);
        httpURLConnection.setReadTimeout(1000);
        InputStream in = httpURLConnection.getInputStream();
        ReadableByteChannel readableByteChannel = Channels.newChannel(in);
        FileOutputStream fileOutputStream = new FileOutputStream(path+name);
        FileChannel fileChannel = fileOutputStream.getChannel();
        fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        fileChannel.close();
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
    public static StringBuilder httpHandle(URL url) throws IOException {
        URLConnection connection = url.openConnection();
        if (connection instanceof HttpsURLConnection httpsConnection) {
            BufferedReader is = new BufferedReader(new InputStreamReader(httpsConnection.getInputStream()));
            StringBuilder body = new StringBuilder();
            String a;
            while ((a = is.readLine()) != null) {
                body.append(a);
            }
            return body;
        } else if (connection instanceof HttpURLConnection httpConnection) {
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
        for (int i = 0; i < c; i++){
            newArray[i] = array[i];
        }
        return newArray;
    }
    public static StringBuilder deleteSymbol(String s, String symbol){
        StringBuilder newString = new StringBuilder();
        for (String i:s.split("")){
            if (i.equals("{")){
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
            String hashText = no.toString(16);
            while (hashText.length() < 32) hashText = "0" + hashText;
            return hashText;
        }
    }
    public static long fileSize(File file){
        return file.length();
    }
    public static byte[] readToString(String fileName) {
        String encoding = "UTF-8";
        File file = new File(fileName);
        Long fileLength = file.length();
        byte[] fileContent = new byte[fileLength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(fileContent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }
    enum xboxLiveType{
        XBL,XSTS
    }
}
