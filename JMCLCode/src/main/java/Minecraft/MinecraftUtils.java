package Minecraft;

import Imformation.LauncherInformation;
import Imformation.LoginInformation;
import JsonAnalysis.Download.Minecraft.Library.MinecraftVersionManifestObject;
import JsonAnalysis.Download.Minecraft.Library.MinecraftVersionObject;
import JsonAnalysis.Login.Microsoft.MinecraftInformationObject;
import Other.NetOperation;
import Other.PublicVariable;
import Utils.Utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import static JsonAnalysis.Download.Minecraft.Library.MinecraftVersionObject.getGsonObject;

public class MinecraftUtils {
    static NetOperation netOperation = new NetOperation();
    public static LoginInformation microsoftLogin() throws IOException, URISyntaxException, InterruptedException {
        String MicrosoftLoginCode = netOperation.requestMicrosoftLoginCode();
        String MicrosoftLoginTokenBody = netOperation.requestMicrosoftLogin(MicrosoftLoginCode);
        String MicrosoftAccessToken = MicrosoftLogin.getMicrosoftAccessToken(MicrosoftLoginTokenBody);
        String microsoftRefreshToken = MicrosoftLogin.getMicrosoftRefreshToken(MicrosoftLoginTokenBody);
        String XBLAuthenticationBody = netOperation.requestXboxLiveAuthentication(MicrosoftAccessToken, Utils.xboxLiveType.XBL);
        String XBLAuthenticationUserHash = MicrosoftLogin.getXboxLiveAuthenticationUserHash(XBLAuthenticationBody);
        String XBLAuthenticationToken = MicrosoftLogin.getXboxLiveAuthenticationToken(XBLAuthenticationBody);
        String XSTSAuthenticationBody = netOperation.requestXboxLiveAuthentication(XBLAuthenticationToken, Utils.xboxLiveType.XSTS);
        String XSTSAuthenticationToken = MicrosoftLogin.getXboxLiveAuthenticationToken(XSTSAuthenticationBody);
        String XSTSAuthenticationUserHash = MicrosoftLogin.getXboxLiveAuthenticationUserHash(XSTSAuthenticationBody);
        if (XBLAuthenticationUserHash.equals(XSTSAuthenticationUserHash)) {
            String MinecraftAuthenticationBody = netOperation.requestMinecraftAuthentication(XSTSAuthenticationToken, XSTSAuthenticationUserHash);
            String MinecraftAuthenticationToken = MicrosoftLogin.getMinecraftAuthenticationToken(MinecraftAuthenticationBody);
            String MinecraftOwnershipBody = netOperation.checkMinecraftOwnership(MinecraftAuthenticationToken);
            if (MicrosoftLogin.ifMinecraftOwnership(MinecraftOwnershipBody)) {
                String MinecraftInformationBody = netOperation.receiveMinecraftInformation(MinecraftAuthenticationToken);
                MinecraftInformationObject MinecraftAuthenticationObject = MicrosoftLogin.getMinecraftInformationObject(MinecraftInformationBody);
                return new LoginInformation(MinecraftAuthenticationObject.getId(), MinecraftAuthenticationObject.getName(), MinecraftAuthenticationToken);
            }else {
                System.out.println("You not have the Minecraft.");
            }
        } else {
            System.out.println("Please try again.");
        }
        return null;
    }
    public static void downloadMinecraft(MinecraftAttribute MinecraftAttribute) {
        try {
            MinecraftVersionManifestObject MinecraftVersionManifestObject = MinecraftRequest.getMinecraftVersionManifestObject();
            if(MinecraftVersionProcessing.isId(MinecraftVersionManifestObject,MinecraftAttribute.id)) {
                MinecraftVersionObject MinecraftVersionObject = MinecraftRequest.getMinecraftVersionObject(MinecraftVersionManifestObject, MinecraftAttribute);
                MinecraftDownloadsUtils.downloadsVersionFileUtils(MinecraftVersionObject, MinecraftAttribute);
                MinecraftDownloadsUtils.downloadsVersionJsonUtils(MinecraftVersionManifestObject, MinecraftAttribute);
                MinecraftDownloadsUtils.downloadsLibrariesUtils(MinecraftVersionObject, MinecraftAttribute);
                MinecraftDownloadsUtils.downloadLog4jFileUtils(MinecraftVersionObject, MinecraftAttribute);
                MinecraftDownloadsUtils.downloadAssetIndexJsonUtils(MinecraftVersionObject, MinecraftAttribute);
                MinecraftDownloadsUtils.downloadsAssetIndexUtils(MinecraftVersionObject, MinecraftAttribute);
                PublicVariable.executorService.shutdown();
                for (; ; ) {
                    if (PublicVariable.executorService.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
                        PublicVariable.multiThreadedDownloadExecutorService.shutdownNow();
                        System.out.println(PublicVariable.executorService.isTerminated());
                        System.out.println(MinecraftDownloadsUtils.end + "/" + MinecraftDownloadsUtils.error + "/" +
                                (MinecraftDownloadsUtils.error + MinecraftDownloadsUtils.end));
                        break;
                    }
                }
            } else {
                System.out.println("This Minecraft version does not exist.");
            }
        } catch (Exception ignored) {
        }
    }

    public static void startMinecraft(MinecraftAttribute MinecraftAttribute) throws IOException, URISyntaxException, InterruptedException {
        MinecraftVersionManifestObject MinecraftVersionManifestObject = MinecraftRequest.getMinecraftVersionManifestObject();
        MinecraftVersionObject MinecraftVersionObject = MinecraftRequest.getMinecraftVersionObject(MinecraftVersionManifestObject, MinecraftAttribute);
        LoginInformation loginInformation = MinecraftUtils.microsoftLogin();
        {
            String javaPath = "\"" + System.getProperty("java.home") + "\\bin\\java.exe\"";
            String launcherBrand = " -Dminecraft.launcher.brand=" + LauncherInformation.name;
            String launcherVersion = " -Dminecraft.launcher.version=" + LauncherInformation.version;
            String jvm = " -Xmn1024m -Xmx1024m";
            String natives = " -Djava.library.path=\"" + MinecraftAttribute.mainPath + "versions\\" + MinecraftAttribute.id + "\\" + "natives" + "\"";
            String log4j = "";
            if (MinecraftVersionObject.getLogging() != null) {
                log4j = " -Dlog4j.configurationFile=" + MinecraftAttribute.mainPath + "assets\\log_configs\\" + MinecraftVersionObject.getLogging().getClient().getFile().getId();
            }
            String other =
                    " -XX:+UnlockExperimentalVMOptions" +
                            " -XX:HeapDumpPath=MojangTricksIntelDriversForPerformance_javaw.exe_minecraft.exe.heapdump" +
                            " \"-Dos.name=Windows 10\"" +
                            " -Dos.version=10.0" +
                            " -Xss1M" +
                            " -XX:+UseG1GC" +
                            " -XX:G1NewSizePercent=20" +
                            " -XX:G1ReservePercent=20" +
                            " -XX:MaxGCPauseMillis=50" +
                            " -XX:G1HeapRegionSize=32M" +
                            " -XX:-UseAdaptiveSizePolicy" +
                            " -XX:-OmitStackTraceInFastThrow";
            String classPath = " -cp ";
            getGsonObject().fromJson(new String(Utils.readToString(MinecraftAttribute.mainPath + "versions\\" + MinecraftAttribute.id + "\\" + MinecraftAttribute.id + ".json"), StandardCharsets.UTF_8),
                    JsonAnalysis.Download.Minecraft.Library.MinecraftVersionObject.class);
            String a = "";
            for (int i = 0; i < MinecraftVersionObject.getLibraries().length; i++) {
                if (MinecraftVersionObject.getLibraries()[i].getDownloads().getClassifiers() == null) {
                    String name = new File(DownloadURL.otherJarLibrariesURL(MinecraftVersionObject, DownloadURL.DownloadSource.official, i).getPath()).getName();
                    String path = MinecraftAttribute.mainPath + "libraries\\" + Utils.regexReplace(MinecraftVersionObject.getLibraries()[i].getDownloads().getArtifact().getPath(), name, "");
                    classPath = classPath + a + path + name;
                    a = ";";
                }
            }
            classPath = classPath + ";" + MinecraftAttribute.mainPath + "versions\\" + MinecraftAttribute.id + "\\" + MinecraftAttribute.id + ".jar";
            String mainClass = " " + MinecraftVersionObject.getMainClass();
            assert loginInformation != null;
            String username = " --username " + loginInformation.name;
            String version = " --version " + MinecraftAttribute.id;
            String gameDir = " --gameDir " + MinecraftAttribute.mainPath;
            String assetsDir = " --assetsDir " + MinecraftAttribute.mainPath + "assets";
            String assetIndex = " --assetIndex " + MinecraftVersionObject.getAssetIndex().getId();
            String uuid = " --uuid " + loginInformation.UUID;
            String accessToken = " --accessToken " + loginInformation.accessToken;
            String userType = " --userType " + "Mojang";
            String versionType = " --versionType " + LauncherInformation.name;
            Runtime.getRuntime().exec(javaPath + launcherBrand + launcherVersion + jvm + natives + log4j + other + classPath
                    + mainClass + username + version + gameDir + assetsDir + assetIndex + uuid + accessToken + userType + versionType);
            System.out.println(javaPath + launcherBrand + launcherVersion + jvm + natives + log4j + other + classPath
                    + mainClass + username + version + gameDir + assetsDir + assetIndex + uuid + accessToken + userType + versionType);
        }
    }
}
