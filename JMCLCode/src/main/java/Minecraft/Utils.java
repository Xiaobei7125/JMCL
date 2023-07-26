package minecraft;

import information.Account;
import information.minecraft.download.DownloadBasicInformation;
import information.minecraft.download.DownloadSource;
import information.minecraft.download.MinecraftTask;
import jsonProcessing.download.minecraft.library.VersionJson;
import jsonProcessing.download.minecraft.library.VersionManifest;
import jsonProcessing.login.microsoft.MinecraftInformationObject;
import minecraft.download.DownloadsUtils;
import minecraft.download.Request;
import minecraft.download.Url;
import minecraft.download.VersionManifestProcessing;
import minecraft.login.GetInformation;
import minecraft.login.Login;
import utils.Output;
import utils.Stream;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import static jsonProcessing.download.minecraft.library.VersionJson.getGsonObject;

public class Utils {
    public static Account microsoftLogin() throws IOException, URISyntaxException, InterruptedException {
//        String MicrosoftLoginCode = Account.requestMicrosoftLoginCode();
//        Output.output(Output.OutputLevel.main.Test, MicrosoftLoginCode);
//        String MicrosoftLoginTokenBody = Account.requestMicrosoftLogin(MicrosoftLoginCode);
//        Output.output(Output.OutputLevel.main.Test, MicrosoftLoginTokenBody);
//        String MicrosoftAccessToken = GetInformation.getMicrosoftAccessToken(MicrosoftLoginTokenBody);
//        String microsoftRefreshToken = GetInformation.getMicrosoftRefreshToken(MicrosoftLoginTokenBody);
        String MicrosoftAccessToken = Login.requestMicrosoftLoginCode();
        Output.output(Output.OutputLevel.Test, MicrosoftAccessToken);
        String XBLAuthenticationBody = Login.requestXboxLiveAuthentication(MicrosoftAccessToken, utils.Utils.xboxLiveType.XBL);
        Output.output(Output.OutputLevel.Test, XBLAuthenticationBody);
        String XBLAuthenticationUserHash = GetInformation.getXboxLiveAuthenticationUserHash(XBLAuthenticationBody);
        String XBLAuthenticationToken = GetInformation.getXboxLiveAuthenticationToken(XBLAuthenticationBody);
        String XSTSAuthenticationBody = Login.requestXboxLiveAuthentication(XBLAuthenticationToken, utils.Utils.xboxLiveType.XSTS);
        Output.output(Output.OutputLevel.Test, XSTSAuthenticationBody);
        String XSTSAuthenticationToken = GetInformation.getXboxLiveAuthenticationToken(XSTSAuthenticationBody);
        Output.output(Output.OutputLevel.Test, XSTSAuthenticationToken);
        if (XSTSAuthenticationToken == null) {
            Output.output(Output.OutputLevel.Warning, "There is a problem with your Xbox account");
            return null;
        }
        String XSTSAuthenticationUserHash = GetInformation.getXboxLiveAuthenticationUserHash(XSTSAuthenticationBody);
        if (!XBLAuthenticationUserHash.equals(XSTSAuthenticationUserHash)) {
            Output.output(Output.OutputLevel.Warning, "There was an error logging in,please try again.");
            return null;
        }
        String MinecraftAuthenticationBody = Login.requestMinecraftAuthentication(XSTSAuthenticationToken, XSTSAuthenticationUserHash);
        String MinecraftAuthenticationToken = GetInformation.getMinecraftAuthenticationToken(MinecraftAuthenticationBody);
        String MinecraftOwnershipBody = Login.checkMinecraftOwnership(MinecraftAuthenticationToken);
        if (!GetInformation.ifMinecraftOwnership(MinecraftOwnershipBody)) {
            Output.output(Output.OutputLevel.Warning, "You don't have Minecraft");
            return null;
        }
        String MinecraftInformationBody = Login.receiveMinecraftInformation(MinecraftAuthenticationToken);
        MinecraftInformationObject MinecraftAuthenticationObject = GetInformation.getMinecraftInformationObject(MinecraftInformationBody);
        return new Account(MinecraftAuthenticationObject.getId(), MinecraftAuthenticationObject.getName(), MinecraftAuthenticationToken);
    }

    public static void downloadMinecraft(DownloadBasicInformation downloadBasicInformation) {
        try {
            VersionManifest MinecraftVersionManifestObject = Request.getMinecraftVersionManifestObject();
            if (VersionManifestProcessing.isId(MinecraftVersionManifestObject, downloadBasicInformation.getId())) {
                MinecraftTask mcDownloadsTask = new MinecraftTask();
                VersionJson MinecraftVersionObject = Request.getMinecraftVersionObject(MinecraftVersionManifestObject, downloadBasicInformation);
                DownloadsUtils.downloadsVersionJsonUtils(mcDownloadsTask, MinecraftVersionManifestObject, downloadBasicInformation);
                DownloadsUtils.downloadsVersionFileUtils(mcDownloadsTask, MinecraftVersionObject, downloadBasicInformation);
//                DownloadsUtils.downloadsLibrariesUtils(MinecraftVersionObject, downloadBasicInformation);
//                DownloadsUtils.downloadLog4jFileUtils(MinecraftVersionObject, downloadBasicInformation);
//                DownloadsUtils.downloadAssetIndexJsonUtils(MinecraftVersionObject, downloadBasicInformation);
//                DownloadsUtils.downloadsAssetIndexUtils(MinecraftVersionObject, downloadBasicInformation);
//                PublicVariable.executorService.shutdown();
//                for (; ; ) {
//                    if (PublicVariable.executorService.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
//                        PublicVariable.multiThreadedDownloadExecutorService.shutdownNow();
//                        System.out.println(PublicVariable.executorService.isTerminated());
//                        System.out.println(DownloadsUtils.end + "/" + DownloadsUtils.error + "/" +
//                                (DownloadsUtils.error + DownloadsUtils.end));
//                        break;
//                    }
//                }
            } else {
                System.out.println("This minecraft version does not exist.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startMinecraft(DownloadBasicInformation DownloadBasicInformation) throws IOException, URISyntaxException, InterruptedException {
        VersionManifest VersionManifest = Request.getMinecraftVersionManifestObject();
        VersionJson VersionJson = Request.getMinecraftVersionObject(VersionManifest, DownloadBasicInformation);
        Account account = Utils.microsoftLogin();
        {
            String javaPath = "\"" + System.getProperty("java.home") + "\\bin\\java.exe\"";
            String launcherBrand = " -Dminecraft.launcher.brand=" + information.Launcher.name;
            String launcherVersion = " -Dminecraft.launcher.version=" + information.Launcher.version;
            String jvm = " -Xmn1024m -Xmx1024m";
            String natives = " -Djava.library.path=\"" + DownloadBasicInformation.getMainPath() + "versions\\" + DownloadBasicInformation.getId() + "\\" + "natives" + "\"";
            String log4j = "";
            if (VersionJson.getLogging() != null) {
                log4j = " -Dlog4j.configurationFile=" + DownloadBasicInformation.getMainPath() + "assets\\log_configs\\" + VersionJson.getLogging().getClient().getFile().getId();
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
            StringBuilder classPath = new StringBuilder(" -cp ");
            getGsonObject().fromJson(new String(Stream.readToString(DownloadBasicInformation.getMainPath() + "versions\\" + DownloadBasicInformation.getId() + "\\" + DownloadBasicInformation.getId() + ".json"), StandardCharsets.UTF_8),
                    VersionJson.class);
            String a = "";
            for (int i = 0; i < VersionJson.getLibraries().length; i++) {
                if (VersionJson.getLibraries()[i].getDownloads().getClassifiers() == null) {
                    String name = new File(Url.otherJarLibrariesURL(VersionJson, DownloadSource.official, i).getPath()).getName();
                    String path = DownloadBasicInformation.getMainPath() + "libraries\\" + utils.Utils.regexReplace(VersionJson.getLibraries()[i].getDownloads().getArtifact().getPath(), name, "");
                    classPath.append(a).append(path).append(name);
                    a = ";";
                }
            }
            classPath.append(";").append(DownloadBasicInformation.getMainPath()).append("versions\\").append(DownloadBasicInformation.getId()).append("\\").append(DownloadBasicInformation.getId()).append(".jar");
            String mainClass = " " + VersionJson.getMainClass();
            assert account != null;
            String username = " --username " + account.name();
            String version = " --version " + DownloadBasicInformation.getId();
            String gameDir = " --gameDir " + DownloadBasicInformation.getMainPath();
            String assetsDir = " --assetsDir " + DownloadBasicInformation.getMainPath() + "assets";
            String assetIndex = " --assetIndex " + VersionJson.getAssetIndex().getId();
            String uuid = " --uuid " + account.UUID();
            String accessToken = " --accessToken " + account.accessToken();
            String userType = " --userType " + "Mojang";
            String versionType = " --versionType " + information.Launcher.name;
            String command = javaPath + launcherBrand + launcherVersion + jvm + natives + log4j + other + classPath
                    + mainClass + username + version + gameDir + assetsDir + assetIndex + uuid + accessToken + userType + versionType;
            Runtime.getRuntime().exec(command);
            //ProcessBuilder processBuilder = new ProcessBuilder(command);
            //processBuilder.command();
            System.out.println(command);
        }
    }
}
