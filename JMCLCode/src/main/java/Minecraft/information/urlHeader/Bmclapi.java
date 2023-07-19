package minecraft.information.urlHeader;

public enum Bmclapi {
    versionManifestJson("https://bmclapi2.bangbang93.com/"),
    VersionJson("https://bmclapi2.bangbang93.com/"),
    versionJar("https://bmclapi2.bangbang93.com/"),
    Log4jXml("https://bmclapi2.bangbang93.com/"),
    assetIndexJson("https://bmclapi2.bangbang93.com/"),
    LibrariesJar("https://bmclapi2.bangbang93.com/maven/"),
    assetIndexFile("https://bmclapi2.bangbang93.com/assets/");
    final String urlHost;

    Bmclapi(String urlHost) {
        this.urlHost = urlHost;
    }

    public String getUrlHost() {
        return urlHost;
    }
}
