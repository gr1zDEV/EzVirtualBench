package com.ezinnovations.ezvirtualworkbench.util;

import com.ezinnovations.ezvirtualworkbench.config.ConfigManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundHelper {
    private final ConfigManager configManager;

    public SoundHelper(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public void playOpenSound(Player player) {
        if (!configManager.isSoundEnabled()) {
            return;
        }

        String configuredName = configManager.getOpenSound();
        if (configuredName == null || configuredName.isBlank()) {
            return;
        }

        try {
            Sound sound = Sound.valueOf(configuredName.toUpperCase());
            player.playSound(player.getLocation(), sound, configManager.getOpenSoundVolume(), configManager.getOpenSoundPitch());
        } catch (IllegalArgumentException ignored) {
            // Intentionally ignore invalid sound names for cross-version safety.
        }
    }
}
