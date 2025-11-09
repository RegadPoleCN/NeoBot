package dev.neovoxel.neobot.game.event;

import dev.neovoxel.neobot.adapter.Player;
import lombok.Getter;

@Getter
public abstract class ChatEvent {
    private final Player player;
    private final String message;

    protected ChatEvent(Player player, String message) {
        this.player = player;
        this.message = message;
    }

    public abstract void disallow();
}
