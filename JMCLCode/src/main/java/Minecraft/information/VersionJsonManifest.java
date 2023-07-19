package minecraft.information;

public enum VersionJsonManifest {
    v1("mc/game/version_manifest.json"), v2("mc/game/version_manifest_v2.json ");
    final String urlPath;

    VersionJsonManifest(String urlPath) {
        this.urlPath = urlPath;
    }
}
