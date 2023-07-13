package other;

import utils.Utils;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Base64;

import static java.time.temporal.ChronoUnit.SECONDS;

public class NetOperation {
    public NetOperation() {
    }

    public static String requestMicrosoftLoginCode() throws IOException, URISyntaxException {
        SecureRandom sr = new SecureRandom();
        byte[] code = new byte[32];
        sr.nextBytes(code);
        String verifier = Base64.getUrlEncoder().withoutPadding().encodeToString(code);
        URI uri = new URI(
                "https://login.microsoftonline.com/consumers" +
                        "/oauth2/v2.0/authorize?" +
                        "client_id=00000000402b5328" +
                        "&response_type=code" +
                        "&redirect_uri=https%3A%2F%2Flogin.live.com%2Foauth20_desktop.srf" +
                        "&scope=service%3A%3Auser.auth.xboxlive.com%3A%3AMBI_SSL"

                /*"https://login.live.com/oauth20_authorize.srf" +
                "?client_id=00000000402b5328" +
                "&response_type=code" +
                "&scope=XboxLive.signin%20offline_access" +
                "&redirect_uri=https%3A%2F%2Flogin.live.com%2Foauth20_desktop.srf"*/);

                /*"https://login.live.com/oauth20_authorize.srf" +
                "?client_id=2119e5ac-e85d-467a-82bf-b5e8227cb900" +
                "&scope=XboxLive.signin%20offline_access" // "service%3A%3Auser.auth.xboxlive.com%3A%3AMBI_SSL"
                "&redirect_uri=https://127.0.0.1" +
                "&response_type=code"
                 */
        Desktop.getDesktop().browse(uri);
        System.out.println("Please enter redirection URL");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        String redirection = bufferedReader.readLine();
        //return Utils.regexReplace(redirection, "https://127\\.0\\.0\\.1/\\?code=", "");
        return Utils.regexReplace(
                Utils.regexReplace(redirection, "https://login\\.live\\.com/oauth20_desktop\\.srf\\?code=", ""),
                "&lc=[\\d]+", "");
    }

    public static String requestMicrosoftLogin(String code) throws URISyntaxException, InterruptedException, IOException {
        URI uri = new URI("https://login.live.com/oauth20_token.srf");
        String toBody = "client_id=00000000402b5328" +
                "&code=" + code +
                "&grant_type=authorization_code" +
                "&redirect_uri=https%3A%2F%2Flogin.live.com%2Foauth20_desktop.srf" +
                "&scope=service%3A%3Auser.auth.xboxlive.com%3A%3AMBI_SSL";/*"client_id=" + "2119e5ac-e85d-467a-82bf-b5e8227cb900" +
                "&client_secret=" + "8bg8Q~~t8cc9I7GQO5y3oDG4zH7nm48A2w3Oha6j" +
                "&code=" + code +
                "&grant_type=authorization_code" +
                "&redirect_uri=" + "https://127.0.0.1";*/
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
            body = "{" +
                    "\"Properties\":{" +
                    "\"AuthMethod\":\"RPS\"," +
                    "\"SiteName\":\"user.auth.xboxlive.com\"," +
                    "\"RpsTicket\":\"" + xboxLiveToken + "\"" +
                    "}," +
                    "\"RelyingParty\":\"http://auth.xboxlive.com\"," +
                    "\"TokenType\":\"JWT\"" +
                    "}";
        }
        Output.output(Output.OutputLevel.Test, body);
        if (type.equals(Utils.xboxLiveType.XSTS)) {
            uri = new URI("https://xsts.auth.xboxlive.com/xsts/authorize");
            body = "{\"Properties\":{\"SandboxId\":\"RETAIL\",\"UserTokens\":[\"" + xboxLiveToken +
                    "\"]},\"RelyingParty\":\"rp://api.minecraftservices.com/\",\"TokenType\":\"JWT\"}";
        }
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .version(HttpClient.Version.HTTP_1_1)
                .timeout(Duration.of(100, SECONDS))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        HttpClient httpClient = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Output.output(Output.OutputLevel.Test, String.valueOf(httpResponse.statusCode()));
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
