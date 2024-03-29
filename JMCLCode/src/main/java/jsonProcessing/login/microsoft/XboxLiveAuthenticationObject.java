package jsonProcessing.login.microsoft;

public class XboxLiveAuthenticationObject {
    private String IssueInstant;
    private String NotAfter;
    private String Token;
    private DisplayClaims DisplayClaims;

    protected static class DisplayClaims {
        private Xui[] xui;

        public Xui[] getXui() {
            return xui;
        }

        protected static class Xui {
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
