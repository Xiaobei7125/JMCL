import JsonAnalysis.MicrosoftLoginJsonAnalysis.MinecraftInformationObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
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
        ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
        FileOutputStream fileOutputStream = new FileOutputStream(path+name);
        FileChannel fileChannel = fileOutputStream.getChannel();
        fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        fileChannel.close();
    }

    public static StringBuilder httpHandle(URL url) throws IOException {
        URLConnection connection = url.openConnection();
        HttpURLConnection httpConnection = null;
        if (connection instanceof HttpURLConnection) {
            httpConnection = (HttpURLConnection) connection;
        } else {
            System.out.println("The URL is null");
        }
        assert httpConnection != null;
        BufferedReader is = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
        StringBuilder body = new StringBuilder();
        String a;
        while ((a = is.readLine()) != null) {
            body.append(a);
        }
        return body;
    }

    public static String regReplace(String content, String pattern, String newString) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(content);
        return m.replaceAll(newString);
    }

    public static StringBuilder httpsHandle(URL url) throws IOException {
        URLConnection connection = url.openConnection();
        HttpsURLConnection httpsConnection = null;
        if (connection instanceof HttpsURLConnection) {
            httpsConnection = (HttpsURLConnection) connection;
        } else {
            System.out.println("The URL is null");
        }
        assert httpsConnection != null;
        BufferedReader is = new BufferedReader(new InputStreamReader(httpsConnection.getInputStream()));
        StringBuilder body = new StringBuilder();
        String a;
        while ((a = is.readLine()) != null) {
            body.append(a);
        }
        return body;
    }

    public static void microsoftLogin() throws IOException, URISyntaxException, InterruptedException {
        String MicrosoftLoginCode = HTTPOperation.requestMicrosoftLoginCode();
        String MicrosoftLoginTokenBody = HTTPOperation.requestMicrosoftLogin(MicrosoftLoginCode);
        String MicrosoftAccessToken = Extract.getMicrosoftAccessToken(MicrosoftLoginTokenBody);
        String microsoftRefreshToken = Extract.getMicrosoftRefreshToken(MicrosoftLoginTokenBody);
        String XBLAuthenticationBody = HTTPOperation.requestXboxLiveAuthentication(MicrosoftAccessToken, xboxLiveType.XBL);
        String XBLAuthenticationToken = Extract.getXboxLiveAuthenticationToken(XBLAuthenticationBody);
        String XBLAuthenticationUserHash = Extract.getXboxLiveAuthenticationUserHash(XBLAuthenticationBody);
        String XSTSAuthenticationBody = HTTPOperation.requestXboxLiveAuthentication(XBLAuthenticationToken, xboxLiveType.XSTS);
        String XSTSAuthenticationToken = Extract.getXboxLiveAuthenticationToken(XSTSAuthenticationBody);
        String XSTSAuthenticationUserHash = Extract.getXboxLiveAuthenticationUserHash(XSTSAuthenticationBody);
        if (XBLAuthenticationUserHash.equals(XSTSAuthenticationUserHash)) {
            String MinecraftAuthenticationBody = HTTPOperation.requestMinecraftAuthentication(XSTSAuthenticationToken, XSTSAuthenticationUserHash);
            String MinecraftAuthenticationToken = Extract.getMinecraftAuthenticationToken(MinecraftAuthenticationBody);

            String MinecraftOwnershipBody = HTTPOperation.checkMinecraftOwnership(MinecraftAuthenticationToken);
            boolean ifMinecraftOwnership = Extract.ifMinecraftOwnership(MinecraftOwnershipBody);
            if (ifMinecraftOwnership) {
                String MinecraftInformationBody = HTTPOperation.receiveMinecraftInformation(MinecraftAuthenticationToken);
                MinecraftInformationObject MinecraftAuthenticationObject = Extract.getMinecraftInformationObject(MinecraftInformationBody);
                System.out.println(MinecraftAuthenticationObject.getId());
                System.out.println(MinecraftAuthenticationObject.getName());
            } else {
                System.out.println("You not have the Minecraft.");
            }
        } else {
            System.out.println("Please try again.");
        }
    }
    enum xboxLiveType{
        XBL,XSTS
    }
}
