package information.download;

import java.util.ArrayList;

public class Task {
    private long totalSize;
    private long downloadCompletionSize;
    private int filesTotalNumber;
    private int downloadCompletionFilesNumber;
    private ArrayList<File> fileArrayList;

    public Task(long totalSize, long downloadCompletionSize, int filesTotalNumber,
                int downloadCompletionFilesNumber, ArrayList<File> fileArrayList) {
        this.totalSize = totalSize;
        this.downloadCompletionSize = downloadCompletionSize;
        this.filesTotalNumber = filesTotalNumber;
        this.downloadCompletionFilesNumber = downloadCompletionFilesNumber;
        this.fileArrayList = fileArrayList;
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

    public ArrayList<File> getFileArrayList() {
        return fileArrayList;
    }

    public void setFileArrayList(ArrayList<File> fileArrayList) {
        this.fileArrayList = fileArrayList;
    }

    public int getFilesTotalNumber() {
        return filesTotalNumber;
    }

    public void setFilesTotalNumber(int filesTotalNumber) {
        this.filesTotalNumber = filesTotalNumber;
    }
}
