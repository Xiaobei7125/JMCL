package information.minecraft.download;

import information.download.Task;

import java.util.ArrayList;

public class MinecraftTask extends Task {
    private ArrayList<MinecraftFile> fileArrayList;

    /**
     * 添加文件信息的同时添加文件总数和文件总大小
     */
    public MinecraftTask addFileArrayList(MinecraftFile file) {
        this.fileArrayList.add(file);
        MinecraftTask.super.addFilesTotalNumber(1);
        MinecraftTask.super.addTotalSize(file.getSize());
        return this;
    }
}
