package dev.neovoxel.neobot.adapter.executor;

import dev.neovoxel.neobot.adapter.RemoteExecutor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class NativeExecutor implements RemoteExecutor {

    private CommandSender commandSender;
    private List<String> messages = new ArrayList<>();

    public boolean init() {
        try {
            Method method = Class.forName("org.bukkit.Bukkit").getMethod("createCommandSender", Consumer.class);
            commandSender = (CommandSender) method.invoke(null, (Consumer<Component>) s -> {
                messages.add(LegacyComponentSerializer.legacySection().serialize(s));
            });
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    @Override
    public void execute(String command) {
        Bukkit.dispatchCommand(commandSender, command);
    }

    @Override
    public String getResult() {
        return String.join("\n", messages);
    }
}
