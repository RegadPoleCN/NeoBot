package dev.neovoxel.neobot.adapter;

import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class OfflinePlayer {
    private final String name;
    private final UUID uuid;

    protected OfflinePlayer(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    public abstract boolean isOnline();
}
