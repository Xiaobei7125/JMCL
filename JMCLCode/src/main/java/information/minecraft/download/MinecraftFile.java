package information.minecraft.download;

import information.download.File;

import java.net.URL;

public class MinecraftFile extends File {
    /**
     * 下载完的额外步骤
     */
    ExtraActions extraActions;
    /**
     * 同一文件不同下载源的链接
     */
    URL[] urls;

    public MinecraftFile(String name, URL[] urls, long size, java.io.File file, ExtraActions extraActions) {
        super(name, size, file);
        this.extraActions = extraActions;
        this.urls = urls;
    }

    public MinecraftFile(String name, URL[] urls, long size, java.io.File file) {
        super(name, size, file);
        this.urls = urls;
    }

    interface ExtraActions {
        boolean actions() throws Exception;
    }
}
