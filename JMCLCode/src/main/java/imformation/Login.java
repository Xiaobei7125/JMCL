package imformation;

public record Login(String UUID, String name, String accessToken) {
    @Override
    public String name() {
        return name;
    }

    @Override
    public String UUID() {
        return UUID;
    }

    @Override
    public String accessToken() {
        return accessToken;
    }
}
