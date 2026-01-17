package dev.neovoxel.neobot;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.NameMatching;
import com.hypixel.hytale.server.core.event.events.player.PlayerChatEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerDisconnectEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerSetupConnectEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.plugin.PluginBase;
import com.hypixel.hytale.server.core.plugin.PluginManager;
import com.hypixel.hytale.server.core.task.TaskRegistration;
import com.hypixel.hytale.server.core.universe.Universe;
import dev.neovoxel.neobot.adapter.*;
import dev.neovoxel.neobot.bot.BotProvider;
import dev.neovoxel.neobot.command.CommandProvider;
import dev.neovoxel.neobot.config.EnhancedConfig;
import dev.neovoxel.neobot.config.ScriptConfig;
import dev.neovoxel.neobot.event.HytaleEventManager;
import dev.neovoxel.neobot.game.GameEventListener;
import dev.neovoxel.neobot.scheduler.ScheduledTask;
import dev.neovoxel.neobot.script.ScriptProvider;
import dev.neovoxel.neobot.script.ScriptScheduler;
import dev.neovoxel.neobot.storage.StorageProvider;
import lombok.Getter;
import lombok.Setter;
import org.graalvm.polyglot.HostAccess;

import java.io.File;
import java.time.Duration;
import java.util.UUID;

public class NeoBotHytale extends JavaPlugin implements NeoBot {

    @Getter
    private GameEventListener gameEventListener;

    @Getter
    @Setter
    private BotProvider botProvider;

    @Getter
    @Setter
    private ScriptProvider scriptProvider;

    @Getter(onMethod_ = {@HostAccess.Export})
    @Setter
    private StorageProvider storageProvider;

    @Getter(onMethod_ = {@HostAccess.Export})
    @Setter
    private ScriptScheduler scriptScheduler;

    @Getter(onMethod_ = {@HostAccess.Export})
    @Setter
    private EnhancedConfig messageConfig;

    @Getter(onMethod_ = {@HostAccess.Export})
    @Setter
    private EnhancedConfig generalConfig;

    @Getter(onMethod_ = {@HostAccess.Export})
    @Setter
    private ScriptConfig scriptConfig;

    @Getter(onMethod_ = {@HostAccess.Export})
    @Setter
    private String storageType;

    @Getter
    @Setter
    private CommandProvider commandProvider;

    public NeoBotHytale(JavaPluginInit init) {
        super(init);
    }

    @Override
    public void start() {
        this.enable();
    }

    @HostAccess.Export
    @Override
    public NeoLogger getNeoLogger() {
        return new NHytaleLogger(this);
    }

    @Override
    public File getDataFolder() {
        return this.getDataFolder();
    }

    @Override
    public void setGameEventListener(GameEventListener listener) {
        HytaleEventManager manager = new HytaleEventManager(this);
        getEventRegistry().registerGlobal(PlayerSetupConnectEvent.class, manager::onLogin);
        getEventRegistry().registerGlobal(PlayerReadyEvent.class, manager::onJoin);
        getEventRegistry().registerGlobal(PlayerDisconnectEvent.class, manager::onQuit);
        getEventRegistry().registerGlobal(PlayerChatEvent.class, manager::onChat);
        this.gameEventListener = listener;
    }

    @Override
    public void registerCommands() {
        HytaleCommandProvider commandProvider1 = new HytaleCommandProvider(this);
        commandProvider1.registerCommand();
        setCommandProvider(commandProvider1);
    }

    @HostAccess.Export
    @Override
    public String getPlatform() {
        return "Hytale";
    }

    @Override
    public boolean isPluginLoaded(String name) {
        boolean has = false;
        for (PluginBase plugin: PluginManager.get().getPlugins()) {
            String plname = plugin.getManifest().getName();
            if (plname.equals(name)) has = true;
        }
        return has;
    }

    @HostAccess.Export
    @Override
    public RemoteExecutor getExecutorByName(String name) {
        return null;
    }

    @HostAccess.Export
    @Override
    public Player getOnlinePlayer(String name) {
        return new HytalePlayer(Universe.get().getPlayer(name, NameMatching.EXACT));
    }

    @HostAccess.Export
    @Override
    public Player[] getOnlinePlayers() {
        return Universe.get().getPlayers().stream().map(HytalePlayer::new).toArray(HytalePlayer[]::new);
    }

    @HostAccess.Export
    @Override
    public OfflinePlayer getOfflinePlayer(String name) {
        UUID uuid = Universe.get().getPlayer(name, NameMatching.EXACT).getUuid();
        return new HytaleOfflinePlayer(name, uuid);
    }

    @HostAccess.Export
    @Override
    public void broadcast(String message) {
        Universe.get().sendMessage(Message.parse(message));
    }

    @HostAccess.Export
    @Override
    public String externalParsePlaceholder(String message, OfflinePlayer player) {
        return message;
    }

    private TaskRegistration runTask(Runnable task) {

    }

    @Override
    public ScheduledTask submit(Runnable task) {
        Universe.get().getDefaultWorld().execute();
        return new HytaleSchedulerTask();
    }

    @Override
    public ScheduledTask submitAsync(Runnable task) {
        return submit(task);
    }

    @Override
    public ScheduledTask submit(Runnable task, long delay) {
        return new HytaleSchedulerTask(proxyServer.getScheduler().buildTask(this, task)
                .delay(Duration.ofSeconds(delay)).schedule());
    }

    @Override
    public ScheduledTask submitAsync(Runnable task, long delay) {
        return submit(task, delay);
    }

    @Override
    public ScheduledTask submit(Runnable task, long delay, long period) {
        return new HytaleSchedulerTask(proxyServer.getScheduler().buildTask(this, task)
                .delay(Duration.ofSeconds(delay)).repeat(Duration.ofSeconds(period)).schedule());
    }

    @Override
    public ScheduledTask submitAsync(Runnable task, long delay, long period) {
        return submit(task, delay, period);
    }

    @Override
    public void cancelAllTasks() {
        HytaleSchedulerTask.cancelAllTasks();
    }
}
