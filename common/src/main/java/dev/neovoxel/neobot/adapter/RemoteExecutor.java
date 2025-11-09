package dev.neovoxel.neobot.adapter;

public interface RemoteExecutor {
    boolean init();
    void execute(String command);
    String getResult();
}
