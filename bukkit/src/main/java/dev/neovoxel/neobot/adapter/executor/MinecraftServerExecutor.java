package dev.neovoxel.neobot.adapter.executor;

import dev.neovoxel.neobot.adapter.RemoteExecutor;
import dev.neovoxel.neobot.util.ReflectionUtil;
import org.bukkit.Bukkit;
import org.bukkit.Server;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;

public class MinecraftServerExecutor implements RemoteExecutor {
    private Object minecraftServer;
    private Method method;
    private Object rconConsoleSource;
    private String message;

    @Override
    public boolean init() {
        Server server = Bukkit.getServer();
        minecraftServer = ReflectionUtil.findFieldByType(server, "MinecraftServer");
        if (minecraftServer == null) return false;
        try {
            method = minecraftServer.getClass().getMethod("runCommand", getRconConsoleSourceClassPath(), String.class);
            Constructor<?> rconConsoleSourceConstructor = getRconConsoleSourceClassPath().getConstructors()[0];
            rconConsoleSource = rconConsoleSourceConstructor.newInstance(minecraftServer,
                    InetSocketAddress.createUnresolved("", 0));
            return true;
        } catch (Exception e) {
            try {
                method = minecraftServer.getClass().getMethod("executeRemoteCommand", String.class);
                return true;
            } catch (Exception e1) {
                return false;
            }
        }
    }

    @Override
    public void execute(String command) {
        try {
            if (method.getName().equals("runCommand")) {
                message = (String) method.invoke(minecraftServer, command);
            } else {
                message = (String) method.invoke(minecraftServer, rconConsoleSource, command);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getResult() {
        return message;
    }

    private Class<?> getRconConsoleSourceClassPath() {
        try {
            return Class.forName("net.minecraft.server.rcon.RconConsoleSource");
        } catch (ClassNotFoundException classNotFoundException) {
            try {
                return Class.forName("net.minecraft.server.rcon.RemoteControlCommandListener");
            } catch (ClassNotFoundException classNotFoundException2) {
                throw new RuntimeException("Can not find RconConsoleSource class path");
            }
        }
    }
}
