import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

public class HTTPOperation {
    public static String requestMicrosoftLoginCode() throws IOException, URISyntaxException {
        URI uri = new URI("https://login.live.com/oauth20_authorize.srf?client_id=2119e5ac-e85d-467a-82bf-b5e8227cb900&scope=XboxLive.signin%20offline_access&redirect_uri=https://127.0.0.1&response_type=code");
        Desktop.getDesktop().browse(uri);
        System.out.println("Please enter redirection URL");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String redirection = bufferedReader.readLine();
        return Utils.regReplace(redirection, "https://127\\.0\\.0\\.1/\\?code=", "");
    }

    public static String requestMicrosoftLogin(String code) throws URISyntaxException, InterruptedException, IOException {
        URI uri = new URI("https://login.live.com/oauth20_token.srf");
        String toBody = "client_id=" + "2119e5ac-e85d-467a-82bf-b5e8227cb900" +
                "&client_secret=" + "8bg8Q~~t8cc9I7GQO5y3oDG4zH7nm48A2w3Oha6j" +
                "&code=" + code +
                "&grant_type=authorization_code" +
                "&redirect_uri=" + "https://127.0.0.1";
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .version(HttpClient.Version.HTTP_2)
                .timeout(Duration.of(100, SECONDS))
                .POST(HttpRequest.BodyPublishers.ofString(toBody))
                .build();
        HttpClient httpClient = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return httpResponse.body();
    }

    public static String refreshMicrosoftAccess(String refresh_token) throws InterruptedException, URISyntaxException, IOException {
        URI uri = new URI("https://login.live.com/oauth20_token.srf");
        String toBody = "client_id=" + "2119e5ac-e85d-467a-82bf-b5e8227cb900" +
                "&client_secret=" + "8bg8Q~~t8cc9I7GQO5y3oDG4zH7nm48A2w3Oha6j" +
                "&refresh_token=" + refresh_token +
                "&grant_type=refresh_token" +
                "&redirect_uri=" + "https://127.0.0.1";
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .version(HttpClient.Version.HTTP_2)
                .timeout(Duration.of(100, SECONDS))
                .POST(HttpRequest.BodyPublishers.ofString(toBody))
                .build();
        HttpClient httpClient = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return httpResponse.body();
    }

    public static String requestXboxLiveAuthentication(String xboxLiveToken, Utils.xboxLiveType type) throws URISyntaxException, IOException, InterruptedException {
        URI uri = null;
        String body = null;
        if (type.equals(Utils.xboxLiveType.XBL)) {
            uri = new URI("https://user.auth.xboxlive.com/user/authenticate");
            body = "{\"Properties\":{\"AuthMethod\":\"RPS\",\"SiteName\":\"user.auth.xboxlive.com\",\"RpsTicket\":\"d=" +
                    xboxLiveToken + "\"},\"RelyingParty\":\"http://auth.xboxlive.com\",\"TokenType\":\"JWT\"}";
        }
        if (type.equals(Utils.xboxLiveType.XSTS)) {
            uri = new URI("https://xsts.auth.xboxlive.com/xsts/authorize");
            body = "{\"Properties\":{\"SandboxId\":\"RETAIL\",\"UserTokens\":[\"" + xboxLiveToken +
                    "\"]},\"RelyingParty\":\"rp://api.minecraftservices.com/\",\"TokenType\":\"JWT\"}";
        }
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .version(HttpClient.Version.HTTP_2)
                .timeout(Duration.of(100, SECONDS))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        HttpClient httpClient = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return httpResponse.body();
    }

    public static String requestMinecraftAuthentication(String XSTSAuthenticationToken, String XSTSAuthenticationUserHash) throws IOException, InterruptedException, URISyntaxException {
        URI uri = new URI("https://api.minecraftservices.com/authentication/login_with_xbox");
        String body = "{\"identityToken\": \"XBL3.0 x=" + XSTSAuthenticationUserHash + ";" + XSTSAuthenticationToken + "\"}";
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .version(HttpClient.Version.HTTP_2)
                .timeout(Duration.of(100, SECONDS))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        HttpClient httpClient = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return httpResponse.body();
    }
    public static String checkMinecraftOwnership(String MinecraftAuthenticationBody) throws IOException, InterruptedException, URISyntaxException {
        URI uri = new URI("https://api.minecraftservices.com/entitlements/mcstore");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .header("Authorization", "Bearer " + MinecraftAuthenticationBody)
                .version(HttpClient.Version.HTTP_2)
                .timeout(Duration.of(100, SECONDS))
                .GET()
                .build();
        HttpClient httpClient = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return httpResponse.body();
    }

    public static String receiveMinecraftInformation(String MinecraftAccessToken) throws IOException, InterruptedException, URISyntaxException {
        URI uri = new URI("https://api.minecraftservices.com/minecraft/profile");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .header("Authorization", "Bearer " + MinecraftAccessToken)
                .version(HttpClient.Version.HTTP_2)
                .timeout(Duration.of(100, SECONDS))
                .GET()
                .build();
        HttpClient httpClient = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return httpResponse.body();
    }
}
