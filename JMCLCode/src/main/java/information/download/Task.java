package information.download;

public class Task {
    /**
     * 全部文件大小
     */
    private long totalSize;
    /** 经过检查已经下载的文件大小*/
    private long downloadCompletionSize;
    /** 全部文件的数量*/
    private int filesTotalNumber;
    /** 经过检查已经下载的文件的数量*/
    private int downloadCompletionFilesNumber;

    public Task(long totalSize, long downloadCompletionSize, int filesTotalNumber,
                int downloadCompletionFilesNumber) {
        this.totalSize = totalSize;
        this.downloadCompletionSize = downloadCompletionSize;
        this.filesTotalNumber = filesTotalNumber;
        this.downloadCompletionFilesNumber = downloadCompletionFilesNumber;
    }

    public Task() {
        this.totalSize = 0;
        this.downloadCompletionSize = 0;
        this.filesTotalNumber = 0;
        this.downloadCompletionFilesNumber = 0;
    }


    public long getDownloadCompletionSize() {
        return downloadCompletionSize;
    }

    public void setDownloadCompletionSize(long downloadCompletionSize) {
        this.downloadCompletionSize = downloadCompletionSize;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public int getDownloadCompletionFilesNumber() {
        return downloadCompletionFilesNumber;
    }

    public void setDownloadCompletionFilesNumber(int downloadCompletionFilesNumber) {
        this.downloadCompletionFilesNumber = downloadCompletionFilesNumber;
    }

    public int getFilesTotalNumber() {
        return filesTotalNumber;
    }

    public void setFilesTotalNumber(int filesTotalNumber) {
        this.filesTotalNumber = filesTotalNumber;
    }

    public Task addTotalSize(long add) {
        this.totalSize = this.totalSize + add;
        return this;
    }

    public Task addDownloadCompletionFilesNumber(int add) {
        this.downloadCompletionFilesNumber = this.downloadCompletionFilesNumber + add;
        return this;
    }

    public Task addFilesTotalNumber(int add) {
        this.filesTotalNumber = this.filesTotalNumber + add;
        return this;
    }

    public Task addDownloadCompletionSize(long add) {
        this.downloadCompletionSize = this.downloadCompletionSize + add;
        return this;
    }
}
