package information.minecraft.download;

public enum versionManifestUrlEndingVersion {
    v1("mc/game/version_manifest.json"), v2("mc/game/version_manifest_v2.json");
    final String urlPath;

    versionManifestUrlEndingVersion(String urlPath) {
        this.urlPath = urlPath;
    }

    public String getUrlPath() {
        return urlPath;
    }
}
