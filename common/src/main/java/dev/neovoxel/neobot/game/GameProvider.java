package dev.neovoxel.neobot.game;

import dev.neovoxel.neobot.adapter.OfflinePlayer;
import dev.neovoxel.neobot.adapter.Player;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public interface GameProvider {
    Player getOnlinePlayer(String name);

    Player[] getOnlinePlayers();

    OfflinePlayer getOfflinePlayer(String name);

    void broadcast(String message);

    String externalParsePlaceholder(String message, OfflinePlayer player); // it supports Player (online)

    default String parsePlaceholder(String message, OfflinePlayer player) {
        String regex = "%internal_neobot_player_list_([^%]*)%";

        List<String> players = Arrays.stream(getOnlinePlayers()).map(Player::getName).collect(Collectors.toList());

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);

        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            String extracted = matcher.group(1);
            String processed = String.join(extracted, players);
            matcher.appendReplacement(result, processed);
        }
        matcher.appendTail(result);

        return externalParsePlaceholder(result.toString(), player);
    }

    default String parsePlaceholder(String message, String playerName) {
        return parsePlaceholder(message, getOfflinePlayer(playerName));
    }
}
