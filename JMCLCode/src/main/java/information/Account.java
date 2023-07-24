package information;

public record Account(String UUID, String name, String accessToken) {
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
