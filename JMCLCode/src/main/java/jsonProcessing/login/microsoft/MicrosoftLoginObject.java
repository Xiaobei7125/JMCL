package jsonProcessing.login.microsoft;

import com.google.gson.annotations.SerializedName;

public class MicrosoftLoginObject {
    @SerializedName("token_type")
    private String tokenType;
    private String scope;
    @SerializedName("expires_in")
    private int expiresIn;
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("refresh_token")
    private String refreshToken;
    @SerializedName("id_token")
    private String idToken;

    public String getAccessToken() {
        return accessToken;
    }
    public String getRefreshToken() {
        return refreshToken;
    }

}
