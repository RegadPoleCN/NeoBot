package dev.neovoxel.neobot.adapter;

public class BukkitPlayer extends Player {
    private final org.bukkit.entity.Player player;

    public BukkitPlayer(org.bukkit.entity.Player player) {
        super(player.getName(), player.getUniqueId());
        this.player = player;
    }

    @Override
    public void sendMessage(String message) {
        player.sendMessage(message);
    }

    @Override
    public void kick(String message) {
        player.kickPlayer(message);
    }

    @Override
    public boolean hasPermission(String node) {
        return player.hasPermission(node);
    }
}
