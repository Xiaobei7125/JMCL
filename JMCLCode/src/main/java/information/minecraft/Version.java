package information.minecraft;

public record Version(String version) {
    @Override
    public String version() {
        return version;
    }
}
