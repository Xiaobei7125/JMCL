package information.download;

import java.net.URL;

public class File {
    String name;
    URL url;
    long size;
    File file;
    ExtraActions extraActions;

    public File(String name, URL url, long size, File file, ExtraActions extraActions) {
        this.name = name;
        this.url = url;
        this.size = size;
        this.file = file;
        this.extraActions = extraActions;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public ExtraActions getExtraActions() {
        return extraActions;
    }

    public void setExtraActions(ExtraActions extraActions) {
        this.extraActions = extraActions;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    interface ExtraActions {
        boolean actions() throws Exception;
    }

}
