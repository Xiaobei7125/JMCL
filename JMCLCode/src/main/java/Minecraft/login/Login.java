package minecraft.login;

import com.google.gson.Gson;
import jsonAnalysis.login.microsoft.*;

public class Login {
    public static String getMicrosoftAccessToken(String microsoftLoginBody) {
        Gson gson = new Gson();
        MicrosoftLoginObject microsoftLoginObject = gson.fromJson(microsoftLoginBody, MicrosoftLoginObject.class);
        return microsoftLoginObject.getAccessToken();
    }

    public static String getMicrosoftRefreshToken(String microsoftLoginBody) {
        Gson gson = new Gson();
        MicrosoftLoginObject microsoftLoginObject = gson.fromJson(microsoftLoginBody, MicrosoftLoginObject.class);
        return microsoftLoginObject.getRefreshToken();
    }

    public static String getXboxLiveAuthenticationToken(String xboxLiveAuthenticationBody) {
        Gson gson = new Gson();
        XboxLiveAuthenticationObject xboxLiveAuthenticationObject = gson.fromJson(xboxLiveAuthenticationBody, XboxLiveAuthenticationObject.class);
        return xboxLiveAuthenticationObject.getToken();
    }

    public static String getXboxLiveAuthenticationUserHash(String xboxLiveAuthenticationBody) {
        Gson gson = new Gson();

        XboxLiveAuthenticationObject xboxLiveAuthenticationObject = gson.fromJson(xboxLiveAuthenticationBody, XboxLiveAuthenticationObject.class);
        return xboxLiveAuthenticationObject.getUserHash();
    }

    public static boolean ifMinecraftOwnership(String checkMinecraftOwnershipBody) {
        Gson gson = new Gson();
        MinecraftOwnershipObject MinecraftOwnershipObject = gson.fromJson(checkMinecraftOwnershipBody, MinecraftOwnershipObject.class);
        return MinecraftOwnershipObject.getItemsLength() != 0;
    }

    public static MinecraftInformationObject getMinecraftInformationObject(String MinecraftInformationBody) {
        Gson gson = new Gson();
        return gson.fromJson(MinecraftInformationBody, MinecraftInformationObject.class);
    }

    public static String getMinecraftAuthenticationToken(String MinecraftAuthenticationBody) {
        Gson gson = new Gson();
        MinecraftAuthenticationObject MinecraftAuthenticationObject = gson.fromJson(MinecraftAuthenticationBody, MinecraftAuthenticationObject.class);
        return MinecraftAuthenticationObject.getAccessToken();
    }
}
