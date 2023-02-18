public class LoginInformation {
    String UUID;
    String name;
    String accessToken;

    LoginInformation(String UUID, String name, String accessToken) {
        this.accessToken = accessToken;
        this.UUID = UUID;
        this.name = name;
    }
}
