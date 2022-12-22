import JsonAnalysis.MicrosoftLoginJsonAnalysis.MinecraftInformationObject;
import JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.MinecraftVersionManifestObject;
import JsonAnalysis.MinecraftLibraryDownloadJsonAnalysis.MinecraftVersionObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

public class MinecraftUtils {
    public static void microsoftLogin() throws IOException, URISyntaxException, InterruptedException {
        String MicrosoftLoginCode = HTTPOperation.requestMicrosoftLoginCode();
        String MicrosoftLoginTokenBody = HTTPOperation.requestMicrosoftLogin(MicrosoftLoginCode);
        String MicrosoftAccessToken = MicrosoftLogin.getMicrosoftAccessToken(MicrosoftLoginTokenBody);
        String microsoftRefreshToken = MicrosoftLogin.getMicrosoftRefreshToken(MicrosoftLoginTokenBody);
        String XBLAuthenticationBody = HTTPOperation.requestXboxLiveAuthentication(MicrosoftAccessToken, Utils.xboxLiveType.XBL);
        String XBLAuthenticationUserHash = MicrosoftLogin.getXboxLiveAuthenticationUserHash(XBLAuthenticationBody);
        String XBLAuthenticationToken = MicrosoftLogin.getXboxLiveAuthenticationToken(XBLAuthenticationBody);
        String XSTSAuthenticationBody = HTTPOperation.requestXboxLiveAuthentication(XBLAuthenticationToken, Utils.xboxLiveType.XSTS);
        String XSTSAuthenticationToken = MicrosoftLogin.getXboxLiveAuthenticationToken(XSTSAuthenticationBody);
        String XSTSAuthenticationUserHash = MicrosoftLogin.getXboxLiveAuthenticationUserHash(XSTSAuthenticationBody);
        if (XBLAuthenticationUserHash.equals(XSTSAuthenticationUserHash)) {
            String MinecraftAuthenticationBody = HTTPOperation.requestMinecraftAuthentication(XSTSAuthenticationToken, XSTSAuthenticationUserHash);
            String MinecraftAuthenticationToken = MicrosoftLogin.getMinecraftAuthenticationToken(MinecraftAuthenticationBody);
            String MinecraftOwnershipBody = HTTPOperation.checkMinecraftOwnership(MinecraftAuthenticationToken);
            if (MicrosoftLogin.ifMinecraftOwnership(MinecraftOwnershipBody)) {
                String MinecraftInformationBody = HTTPOperation.receiveMinecraftInformation(MinecraftAuthenticationToken);
                MinecraftInformationObject MinecraftAuthenticationObject = MicrosoftLogin.getMinecraftInformationObject(MinecraftInformationBody);
                System.out.println(MinecraftAuthenticationObject.getId());
                System.out.println(MinecraftAuthenticationObject.getName());
            }else {
                System.out.println("You not have the Minecraft.");
            }
        } else {
            System.out.println("Please try again.");
        }
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
            }else {
                System.out.println("This Minecraft version does not exist.");
            }
        }catch (Exception ignored){}
    }
}
