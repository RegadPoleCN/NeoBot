package dev.neovoxel.neobot.adapter;

import dev.neovoxel.neobot.scheduler.ScheduledTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class BukkitScheduledTask implements ScheduledTask {
    private final BukkitTask task;

    public BukkitScheduledTask(BukkitTask task) {
        this.task = task;
    }

    @Override
    public void cancel() {
        task.cancel();
    }

    public static void cancelAll(Plugin plugin) {
        Bukkit.getScheduler().cancelTasks(plugin);
    }
}
