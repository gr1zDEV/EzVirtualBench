package com.ezinnovations.ezvirtualworkbench.config;

import com.ezinnovations.ezvirtualworkbench.model.MenuType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class ConfigManager {
    private final JavaPlugin plugin;
    private FileConfiguration config;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void load() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        this.config = plugin.getConfig();
    }

    public void reload() {
        plugin.reloadConfig();
        this.config = plugin.getConfig();
    }

    public String getPrefix() {
        return config.getString("prefix", "&8[&bEzVirtualWorkbench&8]&r ");
    }

    public String getMessage(String key, String fallback) {
        return config.getString("messages." + key, fallback);
    }

    public boolean isCommandEnabled(MenuType menuType) {
        return config.getBoolean("commands." + menuType.getCommandKey() + ".enabled", true);
    }

    public long getCooldownSeconds(MenuType menuType) {
        return config.getLong("commands." + menuType.getCommandKey() + ".cooldown-seconds", 0L);
    }

    public boolean isSoundEnabled() {
        return config.getBoolean("sounds.enabled", true);
    }

    public String getOpenSound() {
        return config.getString("sounds.open-sound", "BLOCK_CHEST_OPEN");
    }

    public float getOpenSoundVolume() {
        return (float) config.getDouble("sounds.volume", 0.8D);
    }

    public float getOpenSoundPitch() {
        return (float) config.getDouble("sounds.pitch", 1.0D);
    }

    public Set<String> getDisabledWorlds() {
        List<String> worlds = config.getStringList("disabled-worlds");
        if (worlds == null || worlds.isEmpty()) {
            return Collections.emptySet();
        }

        Set<String> normalized = new HashSet<>();
        for (String world : worlds) {
            normalized.add(world.toLowerCase(Locale.ROOT));
        }
        return normalized;
    }

    public boolean showSuccessMessage() {
        return config.getBoolean("settings.show-success-message", false);
    }
}
