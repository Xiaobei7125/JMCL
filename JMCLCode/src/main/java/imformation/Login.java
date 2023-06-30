package imformation;

public class Login {
    public final String UUID;
    public final String name;
    public final String accessToken;

    public Login(String UUID, String name, String accessToken) {
        this.accessToken = accessToken;
        this.UUID = UUID;
        this.name = name;
    }
}
