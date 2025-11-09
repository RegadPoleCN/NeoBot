package dev.neovoxel.neobot.adapter;

import lombok.Getter;

@Getter
public abstract class CommandSender {
    private final String name;

    protected CommandSender(String name) {
        this.name = name;
    }

    public abstract void sendMessage(String message);

    public abstract boolean hasPermission(String node);
}
