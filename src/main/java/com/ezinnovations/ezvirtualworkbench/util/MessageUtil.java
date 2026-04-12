package com.ezinnovations.ezvirtualworkbench.util;

import com.ezinnovations.ezvirtualworkbench.config.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MessageUtil {
    private final ConfigManager configManager;

    public MessageUtil(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public void sendPrefixed(CommandSender sender, String rawMessage) {
        if (rawMessage == null || rawMessage.isBlank()) {
            return;
        }
        sender.sendMessage(colorize(configManager.getPrefix() + rawMessage));
    }

    public void sendConfigMessage(CommandSender sender, String key, String fallback) {
        sendPrefixed(sender, configManager.getMessage(key, fallback));
    }

    public String colorize(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
