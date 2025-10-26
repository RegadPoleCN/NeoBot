package dev.neovoxel.neobot.event;

import dev.neovoxel.neobot.game.event.LoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class BukkitLoginEvent extends LoginEvent {
    private final PlayerLoginEvent event;

    public BukkitLoginEvent(PlayerLoginEvent event) {
        super(event.getPlayer().getName(), event.getPlayer().getUniqueId());
        this.event = event;
    }

    @Override
    public void disallow(String reason) {
        event.disallow(PlayerLoginEvent.Result.KICK_OTHER, reason);
    }
}
