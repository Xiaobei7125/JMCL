package information.minecraft.download;

public enum VersionManifestVersion {
    v1("mc/game/version_manifest.json"), v2("mc/game/version_manifest_v2.json");
    final String urlPath;

    VersionManifestVersion(String urlPath) {
        this.urlPath = urlPath;
    }

    public String getUrlPath() {
        return urlPath;
    }
}
