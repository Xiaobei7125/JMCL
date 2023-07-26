package information.download;

import java.net.URL;

public class File {
    String name;
    URL url;
    long size;
    java.io.File file;

    public File(String name, URL url, long size, java.io.File file) {
        this.name = name;
        this.url = url;
        this.size = size;
        this.file = file;
    }

    public File(String name, long size, java.io.File file) {
        this.name = name;
        this.size = size;
        this.file = file;
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

    public java.io.File getFile() {
        return file;
    }

    public void setFile(java.io.File file) {
        this.file = file;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }


}
