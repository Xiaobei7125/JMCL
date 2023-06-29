package JsonAnalysis.Login.Microsoft;

public class XboxLiveAuthenticationObject {
    private String IssueInstant;
    private String NotAfter;
    private String Token;
    private DisplayClaims DisplayClaims;
    protected class DisplayClaims{
        private Xui[] xui;

        public Xui[] getXui() {
            return xui;
        }
        protected class Xui{
            private String uhs;

            public String getUserHash() {
                return uhs;
            }
        }
    }

    public String getToken() {
        return Token;
    }

    public String getUserHash() {
        return DisplayClaims.xui[0].getUserHash();
    }
}
