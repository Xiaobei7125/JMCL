package JsonAnalysis.MicrosoftLoginJsonAnalysis;

public class MicrosoftLoginObject {
    private String token_type;
    private String scope;
    private int expires_in;
    private String access_token;
    private String refresh_token;
    private String id_token;

    public String getAccess_token() {
        return access_token;
    }
    public String getRefresh_token() {
        return refresh_token;
    }

}
