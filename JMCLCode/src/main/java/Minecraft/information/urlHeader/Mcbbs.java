package minecraft.information.urlHeader;

public enum Mcbbs {
    versionManifestJson("https://download.mcbbs.net/"),
    VersionJson("https://download.mcbbs.net/"),
    versionJar("https://download.mcbbs.net/"),
    Log4jXml("https://download.mcbbs.net/"),
    assetIndexJson("https://download.mcbbs.net/"),
    LibrariesJar("https://download.mcbbs.net/maven/"),
    assetIndexFile("https://download.mcbbs.net/assets/");
    final String urlHost;

    Mcbbs(String urlHost) {
        this.urlHost = urlHost;
    }

    public String getUrlHost() {
        return urlHost;
    }
}
