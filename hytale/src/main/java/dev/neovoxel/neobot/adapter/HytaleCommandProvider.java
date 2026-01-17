package dev.neovoxel.neobot.adapter;

import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import dev.neovoxel.neobot.command.CommandProvider;
import dev.neovoxel.neobot.NeoBotHytale;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class HytaleCommandProvider extends CommandProvider {
    private final NeoBotHytale plugin;

    public HytaleCommandProvider(NeoBotHytale plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public void registerCommand() {
        plugin.getCommandRegistry().registerCommand(new NHytaleCommand(this));
    }

    public class NHytaleCommand extends AbstractCommand {
        private HytaleCommandProvider provider;
        protected NHytaleCommand(HytaleCommandProvider provider) {
            super("neobot", "Commands for NeoBot");
            this.provider = provider;
        }

        @Override
        protected @Nullable CompletableFuture<Void> execute(@NotNull CommandContext ctx) {
            provider.onCommand(new HytaleCommandSender(ctx.sender()), ctx.getInputString().replaceFirst("/neobot ", "").split(" "));
            return null;
        }
    }

}
