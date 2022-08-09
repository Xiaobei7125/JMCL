package JsonAnalysis.MicrosoftLoginJsonAnalysis;

public class MinecraftAuthenticationObject {
    private String username;
    private String[] roles;
    private String access_token;
    private String token_type;
    private int expires_in;

    public String getAccess_token() {
        return access_token;
    }
}
