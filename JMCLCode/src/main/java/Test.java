

import JsonAnalysis.MicrosoftLoginJsonAnalysis.MinecraftInformationObject;
import JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.MinecraftVersionJsonFileObject;
import JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.MinecraftVersionManifestObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.net.ssl.*;
import java.net.*;
import java.io.*;
import java.nio.channels.*;
import java.util.Arrays;
import java.util.regex.*;


public class Test {
    public static void main(String[] ages) throws IOException, URISyntaxException, InterruptedException {
        //microsoftLogin();
        /*String id = "1.19";
        Gson gson = new GsonBuilder().setVersion(2).create();
        String MinecraftVersionManifestBody = httpHandle(new URL("http://launchermeta.mojang.com/mc/game/version_manifest_v2.json")).toString();
        MinecraftVersionManifestObject MinecraftVersionManifestObject = gson.fromJson(MinecraftVersionManifestBody,
                JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.MinecraftVersionManifestObject.class);
        if(MinecraftVersionProcessing.ifId(MinecraftVersionManifestObject,id)){
            fileOutput(new URL(MinecraftVersionProcessing.getUrl(MinecraftVersionManifestObject,id)));
        }else {
            System.out.println("This Minecraft version does not exist.");
        }*/
        String json = "{\"game\": [\"--username\", \"${auth_player_name}\", \"--version\", \"${version_name}\", \"--gameDir\", \"${game_directory}\", \"--assetsDir\", \"${assets_root}\", \"--assetIndex\", \"${assets_index_name}\", \"--uuid\", \"${auth_uuid}\", \"--accessToken\", \"${auth_access_token}\", \"--clientId\", \"${clientid}\", \"--xuid\", \"${auth_xuid}\", \"--userType\", \"${user_type}\", \"--versionType\", \"${version_type}\", {\"rules\": [{\"action\": \"allow\", \"features\": {\"is_demo_user\": true}}], \"value\": \"--demo\"}, {\"rules\": [{\"action\": \"allow\", \"features\": {\"has_custom_resolution\": true}}], \"value\": [\"--width\", \"${resolution_width}\", \"--height\", \"${resolution_height}\"]}], \"jvm\": [{\"rules\": [{\"action\": \"allow\", \"os\": {\"name\": \"osx\"}}], \"value\": [\"-XstartOnFirstThread\"]}, {\"rules\": [{\"action\": \"allow\", \"os\": {\"name\": \"windows\"}}], \"value\": \"-XX:HeapDumpPath=MojangTricksIntelDriversForPerformance_javaw.exe_minecraft.exe.heapdump\"}, {\"rules\": [{\"action\": \"allow\", \"os\": {\"name\": \"windows\", \"version\": \"^10\\\\.\"}}], \"value\": [\"-Dos.name=Windows 10\", \"-Dos.version=10.0\"]}, {\"rules\": [{\"action\": \"allow\", \"os\": {\"arch\": \"x86\"}}], \"value\": \"-Xss1M\"}, \"-Djava.library.path=${natives_directory}\", \"-Dminecraft.launcher.brand=${launcher_name}\", \"-Dminecraft.launcher.version=${launcher_version}\", \"-cp\", \"${classpath}\"]}";
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(MinecraftVersionJsonFileObject.Arguments.Game.class,new MinecraftVersionJsonFileObject.GameDeserializer())
                .registerTypeAdapter(MinecraftVersionJsonFileObject.Arguments.Jvm.class,new MinecraftVersionJsonFileObject.JvmDeserializer())
                .create();
        MinecraftVersionJsonFileObject.Arguments.Game game = gson.fromJson(json, MinecraftVersionJsonFileObject.Arguments.Game.class);

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