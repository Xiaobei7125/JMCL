package minecraft.information;

import jsonAnalysis.setup.Setup;

public enum DownloadSource {

    official(Setup.getSetupInstance().download.source.ifUseOfficialDownloadSource),
    bmclapi(Setup.getSetupInstance().download.source.ifUseBmclapiDownloadSource),
    mcbbs(Setup.getSetupInstance().download.source.ifUseMcbbsDownloadSource);
    public final boolean ifUse;

    DownloadSource(boolean ifUseDownloadSource) {
        this.ifUse = ifUseDownloadSource;
    }
}
