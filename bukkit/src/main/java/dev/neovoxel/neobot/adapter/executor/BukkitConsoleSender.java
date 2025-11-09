package dev.neovoxel.neobot.adapter.executor;

import dev.neovoxel.neobot.adapter.RemoteExecutor;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BukkitConsoleSender implements ConsoleCommandSender, RemoteExecutor {
    private final List<String> messageList = new ArrayList<>();

    @Override
    public boolean isOp() {
        return true;
    }

    @Override
    public void setOp(boolean p0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isPermissionSet(String p0) {
        return false;
    }

    @Override
    public boolean isPermissionSet(Permission p0) {
        return false;
    }

    @Override
    public boolean hasPermission(String p0) {
        return true;
    }

    @Override
    public boolean hasPermission(Permission p0) {
        return true;
    }

    @Override
    public PermissionAttachment addAttachment(Plugin p0, String p1, boolean p2) {
        messageList.add(p1);
        throw new UnsupportedOperationException();
    }

    @Override
    public PermissionAttachment addAttachment(Plugin p0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public PermissionAttachment addAttachment(Plugin p0, String p1, boolean p2, int p3) {
        messageList.add(p1);
        throw new UnsupportedOperationException();
    }

    @Override
    public PermissionAttachment addAttachment(Plugin p0, int p1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeAttachment(PermissionAttachment p0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void recalculatePermissions() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sendMessage(String p0) {
        messageList.add(p0);
    }

    @Override
    public void sendMessage(String... p0) {
        for (String s : p0) {
            sendMessage(s);
        }
    }

    @Override
    public void sendMessage(UUID p0, String p1) {
        messageList.add(p1);
    }

    @Override
    public void sendMessage(UUID p0, String... p1) {
        for (String s : p1) {
            sendMessage(p0, s);
        }
    }

    @Override
    public Server getServer() {
        return Bukkit.getServer();
    }

    @Override
    public String getName() {
        return "CONSOLE";
    }

    @Override
    public Spigot spigot() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isConversing() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void acceptConversationInput(String p0) {
        messageList.add(p0);
    }

    @Override
    public boolean beginConversation(Conversation p0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void abandonConversation(Conversation p0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void abandonConversation(Conversation p0, ConversationAbandonedEvent p1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sendRawMessage(String p0) {
        messageList.add(p0);
    }

    @Override
    public void sendRawMessage(UUID p0, String p1) {
        messageList.add(p1);
    }

    @Override
    public boolean init() {
        return true;
    }

    @Override
    public void execute(String command) {
        Bukkit.dispatchCommand(this, command);
    }

    @Override
    public String getResult() {
        return String.join("\n", messageList);
    }
}
