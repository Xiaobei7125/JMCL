package information.minecraft.download;

import jsonProcessing.setup.Setup;

public enum DownloadSource {

    official(Setup.getSetupInstance().download.source.ifUseOfficialDownloadSource),
    bmclapi(Setup.getSetupInstance().download.source.ifUseBmclapiDownloadSource),
    mcbbs(Setup.getSetupInstance().download.source.ifUseMcbbsDownloadSource);
    final boolean ifUse;

    DownloadSource(boolean ifUseDownloadSource) {
        this.ifUse = ifUseDownloadSource;
    }

    public boolean getIfUse() {
        return ifUse;
    }

}
