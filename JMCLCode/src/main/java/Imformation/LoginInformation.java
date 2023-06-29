package Imformation;

public class LoginInformation {
    public String UUID;
    public String name;
    public String accessToken;

    public LoginInformation(String UUID, String name, String accessToken) {
        this.accessToken = accessToken;
        this.UUID = UUID;
        this.name = name;
    }
}
