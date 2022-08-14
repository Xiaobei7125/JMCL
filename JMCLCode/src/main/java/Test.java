

import JsonAnalysis.MicrosoftLoginJsonAnalysis.MinecraftInformationObject;


import JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.*;
import com.google.gson.*;

import javax.net.ssl.*;
import java.net.*;
import java.io.*;
import java.nio.channels.*;
import java.util.regex.*;


import static JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.MinecraftVersionJsonFileObject.*;


public class Test {
    public static void main(String[] ages) throws IOException, URISyntaxException, InterruptedException {
        //microsoftLogin();
        String id = "1.19";
        Gson gson = new GsonBuilder().setVersion(2).create();
        String MinecraftVersionManifestBody = httpHandle(new URL("http://launchermeta.mojang.com/mc/game/version_manifest_v2.json")).toString();
        MinecraftVersionManifestObject MinecraftVersionManifestObject = gson.fromJson(MinecraftVersionManifestBody,
                JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.MinecraftVersionManifestObject.class);
        if(MinecraftVersionProcessing.isId(MinecraftVersionManifestObject,id)){
            String MinecraftVersionJsonFileBody = String.valueOf(httpHandle(new URL(MinecraftVersionProcessing.getUrl(MinecraftVersionManifestObject,id))));
            MinecraftVersionJsonFileObject MinecraftVersionJsonFileObject = getGsonObject().fromJson(MinecraftVersionJsonFileBody,JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.MinecraftVersionJsonFileObject.class);
        }else {
            System.out.println("This Minecraft version does not exist.");
        }

    }

    public static void fileOutput(URL url) throws IOException {
        String name = regReplace(url.toString(), "[a-zA-z$]+://[^\\s$]*/", "");
        System.out.println(name);
        ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
        FileOutputStream fileOutputStream = new FileOutputStream(name);
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
        StringBuilder b = new StringBuilder();
        String a;
        while ((a = is.readLine()) != null) {
            b.append(a);
        }
        return b;
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
        if(XBLAuthenticationUserHash.equals(XSTSAuthenticationUserHash)){
            String MinecraftAuthenticationBody = HTTPOperation.requestMinecraftAuthentication(XSTSAuthenticationToken, XSTSAuthenticationUserHash);
            String MinecraftAuthenticationToken = Extract.getMinecraftAuthenticationToken(MinecraftAuthenticationBody);

            String MinecraftOwnershipBody = HTTPOperation.checkMinecraftOwnership(MinecraftAuthenticationToken);
            boolean ifMinecraftOwnership = Extract.ifMinecraftOwnership(MinecraftOwnershipBody);
            if(ifMinecraftOwnership){
                String MinecraftInformationBody = HTTPOperation.receiveMinecraftInformation(MinecraftAuthenticationToken);
                MinecraftInformationObject MinecraftAuthenticationObject = Extract.getMinecraftInformationObject(MinecraftInformationBody);
                System.out.println(MinecraftAuthenticationObject.getId());
                System.out.println(MinecraftAuthenticationObject.getName());
            }else {
                System.out.println("You not have the Minecraft.");
            }
        }else {
            System.out.println("Please try again.");
        }


    }

    enum xboxLiveType{
        XBL,XSTS
    }
}