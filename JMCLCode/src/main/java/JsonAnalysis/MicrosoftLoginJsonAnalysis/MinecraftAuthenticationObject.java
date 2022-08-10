package JsonAnalysis.MicrosoftLoginJsonAnalysis;

import com.google.gson.annotations.SerializedName;

import java.io.Serial;

public class MinecraftAuthenticationObject {
    private String username;
    private String[] roles;
    @SerializedName("access_token")
    private String accessToken;
    private String token_type;
    private int expires_in;

    public String getAccessToken() {
        return accessToken;
    }
}
