package jsonAnalysis.login.microsoft;

import com.google.gson.annotations.SerializedName;

public class MinecraftAuthenticationObject {
    private String username;
    private String[] roles;
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("token_type")
    private String tokenType;
    @SerializedName("expires_in")
    private int expiresIn;

    public String getAccessToken() {
        return accessToken;
    }
}
