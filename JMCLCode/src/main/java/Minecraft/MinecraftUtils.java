package minecraft;

import Other.DownloadURL;
import Other.NetOperation;
import Other.PublicVariable;
import Utils.Utils;
import jsonAnalysis.download.minecraft.library.VersionJson;
import jsonAnalysis.download.minecraft.library.VersionManifest;
import jsonAnalysis.login.microsoft.MinecraftInformationObject;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import static jsonAnalysis.download.minecraft.library.VersionJson.getGsonObject;

public class MinecraftUtils {
    public static imformation.Login microsoftLogin() throws IOException, URISyntaxException, InterruptedException {
        String MicrosoftLoginCode = NetOperation.requestMicrosoftLoginCode();
        String MicrosoftLoginTokenBody = NetOperation.requestMicrosoftLogin(MicrosoftLoginCode);
        String MicrosoftAccessToken = Login.getMicrosoftAccessToken(MicrosoftLoginTokenBody);
        String microsoftRefreshToken = Login.getMicrosoftRefreshToken(MicrosoftLoginTokenBody);
        String XBLAuthenticationBody = NetOperation.requestXboxLiveAuthentication(MicrosoftAccessToken, Utils.xboxLiveType.XBL);
        String XBLAuthenticationUserHash = Login.getXboxLiveAuthenticationUserHash(XBLAuthenticationBody);
        String XBLAuthenticationToken = Login.getXboxLiveAuthenticationToken(XBLAuthenticationBody);
        String XSTSAuthenticationBody = NetOperation.requestXboxLiveAuthentication(XBLAuthenticationToken, Utils.xboxLiveType.XSTS);
        String XSTSAuthenticationToken = Login.getXboxLiveAuthenticationToken(XSTSAuthenticationBody);
        String XSTSAuthenticationUserHash = Login.getXboxLiveAuthenticationUserHash(XSTSAuthenticationBody);
        if (XBLAuthenticationUserHash.equals(XSTSAuthenticationUserHash)) {
            String MinecraftAuthenticationBody = NetOperation.requestMinecraftAuthentication(XSTSAuthenticationToken, XSTSAuthenticationUserHash);
            String MinecraftAuthenticationToken = Login.getMinecraftAuthenticationToken(MinecraftAuthenticationBody);
            String MinecraftOwnershipBody = NetOperation.checkMinecraftOwnership(MinecraftAuthenticationToken);
            if (Login.ifMinecraftOwnership(MinecraftOwnershipBody)) {
                String MinecraftInformationBody = NetOperation.receiveMinecraftInformation(MinecraftAuthenticationToken);
                MinecraftInformationObject MinecraftAuthenticationObject = Login.getMinecraftInformationObject(MinecraftInformationBody);
                return new imformation.Login(MinecraftAuthenticationObject.getId(), MinecraftAuthenticationObject.getName(), MinecraftAuthenticationToken);
            } else {
                System.out.println("You not have the minecraft.");
            }
        } else {
            System.out.println("Please try again.");
        }
        return null;
    }

    public static void downloadMinecraft(Attribute Attribute) {
        try {
            VersionManifest MinecraftVersionManifestObject = MinecraftRequest.getMinecraftVersionManifestObject();
            if (MinecraftVersionProcessing.isId(MinecraftVersionManifestObject, Attribute.id)) {
                VersionJson MinecraftVersionObject = MinecraftRequest.getMinecraftVersionObject(MinecraftVersionManifestObject, Attribute);
                MinecraftDownloadsUtils.downloadsVersionFileUtils(MinecraftVersionObject, Attribute);
                MinecraftDownloadsUtils.downloadsVersionJsonUtils(MinecraftVersionManifestObject, Attribute);
                MinecraftDownloadsUtils.downloadsLibrariesUtils(MinecraftVersionObject, Attribute);
                MinecraftDownloadsUtils.downloadLog4jFileUtils(MinecraftVersionObject, Attribute);
                MinecraftDownloadsUtils.downloadAssetIndexJsonUtils(MinecraftVersionObject, Attribute);
                MinecraftDownloadsUtils.downloadsAssetIndexUtils(MinecraftVersionObject, Attribute);
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
                System.out.println("This minecraft version does not exist.");
            }
        } catch (Exception ignored) {
        }
    }

    public static void startMinecraft(Attribute Attribute) throws IOException, URISyntaxException, InterruptedException {
        VersionManifest VersionManifest = MinecraftRequest.getMinecraftVersionManifestObject();
        VersionJson VersionJson = MinecraftRequest.getMinecraftVersionObject(VersionManifest, Attribute);
        imformation.Login login = MinecraftUtils.microsoftLogin();
        {
            String javaPath = "\"" + System.getProperty("java.home") + "\\bin\\java.exe\"";
            String launcherBrand = " -Dminecraft.launcher.brand=" + imformation.Launcher.name;
            String launcherVersion = " -Dminecraft.launcher.version=" + imformation.Launcher.version;
            String jvm = " -Xmn1024m -Xmx1024m";
            String natives = " -Djava.library.path=\"" + Attribute.mainPath + "versions\\" + Attribute.id + "\\" + "natives" + "\"";
            String log4j = "";
            if (VersionJson.getLogging() != null) {
                log4j = " -Dlog4j.configurationFile=" + Attribute.mainPath + "assets\\log_configs\\" + VersionJson.getLogging().getClient().getFile().getId();
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
            getGsonObject().fromJson(new String(Utils.readToString(Attribute.mainPath + "versions\\" + Attribute.id + "\\" + Attribute.id + ".json"), StandardCharsets.UTF_8),
                    VersionJson.class);
            String a = "";
            for (int i = 0; i < VersionJson.getLibraries().length; i++) {
                if (VersionJson.getLibraries()[i].getDownloads().getClassifiers() == null) {
                    String name = new File(DownloadURL.otherJarLibrariesURL(VersionJson, DownloadURL.DownloadSource.official, i).getPath()).getName();
                    String path = Attribute.mainPath + "libraries\\" + Utils.regexReplace(VersionJson.getLibraries()[i].getDownloads().getArtifact().getPath(), name, "");
                    classPath = classPath + a + path + name;
                    a = ";";
                }
            }
            classPath = classPath + ";" + Attribute.mainPath + "versions\\" + Attribute.id + "\\" + Attribute.id + ".jar";
            String mainClass = " " + VersionJson.getMainClass();
            assert login != null;
            String username = " --username " + login.name;
            String version = " --version " + Attribute.id;
            String gameDir = " --gameDir " + Attribute.mainPath;
            String assetsDir = " --assetsDir " + Attribute.mainPath + "assets";
            String assetIndex = " --assetIndex " + VersionJson.getAssetIndex().getId();
            String uuid = " --uuid " + login.UUID;
            String accessToken = " --accessToken " + login.accessToken;
            String userType = " --userType " + "Mojang";
            String versionType = " --versionType " + imformation.Launcher.name;
            Runtime.getRuntime().exec(javaPath + launcherBrand + launcherVersion + jvm + natives + log4j + other + classPath
                    + mainClass + username + version + gameDir + assetsDir + assetIndex + uuid + accessToken + userType + versionType);
            System.out.println(javaPath + launcherBrand + launcherVersion + jvm + natives + log4j + other + classPath
                    + mainClass + username + version + gameDir + assetsDir + assetIndex + uuid + accessToken + userType + versionType);
        }
    }
}
