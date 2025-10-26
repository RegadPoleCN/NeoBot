package dev.neovoxel.neobot.bot.types;

import dev.neovoxel.nbapi.event.message.GroupMessageEvent;
import dev.neovoxel.nbapi.event.message.GroupMessageType;
import org.graalvm.polyglot.HostAccess;
import org.json.JSONArray;

public class NGroupMessageEvent extends GroupMessageEvent {
    public NGroupMessageEvent(long time, long selfId, int messageId, long senderId, JSONArray message, String rawMessage, GroupMessageType subType, long groupId, long anonymousId, @org.jetbrains.annotations.Nullable String anonymousName) {
        super(time, selfId, messageId, senderId, message, rawMessage, subType, groupId, anonymousId, anonymousName);
    }

    public NGroupMessageEvent(GroupMessageEvent event) {
        super(event.getTime(), event.getSelfId(), event.getMessageId(), event.getSenderId(), event.getMessage(), event.getRawMessage(), event.getSubType(), event.getGroupId(), event.getAnonymousId(), event.getAnonymousName());
    }

    @HostAccess.Export
    public String getJsonMessage() {
        return getMessage().toString();
    }
}
