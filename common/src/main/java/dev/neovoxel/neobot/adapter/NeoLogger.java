package dev.neovoxel.neobot.adapter;

public interface NeoLogger {
    void info(String message);
    void warn(String message);
    void error(String message);
    void error(String message, Throwable throwable);
    void debug(String message);
    void trace(String message);
}
