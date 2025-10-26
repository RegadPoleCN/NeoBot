package dev.neovoxel.neobot.game.event;

import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class LoginEvent {
    private final String name;
    private final UUID uuid;

    protected LoginEvent(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public abstract void disallow(String reason);
}
