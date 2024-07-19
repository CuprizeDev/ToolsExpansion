package com.golfing8.expansiontools.util;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatUtil {
    public static String color(String string) {
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher match = pattern.matcher(string);
        while (match.find()) {
            String color = string.substring(match.start(), match.end());
            string = string.replace(color, ChatColor.of(color) + "");
            match = pattern.matcher(string);
        }
        return org.bukkit.ChatColor.translateAlternateColorCodes('&', string);
    }
}