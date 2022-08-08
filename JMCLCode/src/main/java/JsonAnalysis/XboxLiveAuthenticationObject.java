package JsonAnalysis;

public class XboxLiveAuthenticationObject {
    private String IssueInstant;
    private String NotAfter;
    private String Token;
    private DisplayClaims DisplayClaims;
    public class DisplayClaims{
        private Xui[] xui;

        public Xui[] getXui() {
            return xui;
        }
    }
    public class Xui{
        private String uhs;

        public String getUserHash() {
            return uhs;
        }
    }

    public String getToken() {
        return Token;
    }

    public XboxLiveAuthenticationObject.DisplayClaims getDisplayClaims() {
        return DisplayClaims;
    }
}
