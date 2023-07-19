package minecraft.information.urlHeader;

public enum Official {
    versionManifestJson("https://launchermeta.mojang.com/"),
    oldVersionJson("https://launchermeta.mojang.com/"),
    newVersionJson("https://piston-meta.mojang.com/"),
    versionJar("https://launcher.mojang.com/"),
    Log4jXml("https://launcher.mojang.com/"),
    LibrariesJar("https://libraries.minecraft.net/"),
    assetIndexJson("https://launchermeta.mojang.com/"),
    assetIndexFile("https://resources.download.minecraft.net/");
    final String urlHost;

    Official(String urlHost) {
        this.urlHost = urlHost;
    }

    public String getUrlHost() {
        return urlHost;
    }
}
