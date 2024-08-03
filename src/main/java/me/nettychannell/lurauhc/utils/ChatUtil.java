package me.nettychannell.lurauhc.utils;

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class ChatUtil {

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> color(List<String> messages) {
        List<String> coloredMessages = new ArrayList<>(messages);
        for (int i = 0; i < coloredMessages.size(); i++) {
            coloredMessages.set(i, color(coloredMessages.get(i)));
        }
        return coloredMessages;
    }

    public static Component colorComponent(String message) {
        return Component.text(color(message));
    }
}
