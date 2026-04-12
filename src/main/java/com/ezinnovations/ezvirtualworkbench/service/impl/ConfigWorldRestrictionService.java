package com.ezinnovations.ezvirtualworkbench.service.impl;

import com.ezinnovations.ezvirtualworkbench.config.ConfigManager;
import com.ezinnovations.ezvirtualworkbench.service.WorldRestrictionService;
import org.bukkit.entity.Player;

import java.util.Locale;

public class ConfigWorldRestrictionService implements WorldRestrictionService {
    private final ConfigManager configManager;

    public ConfigWorldRestrictionService(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public boolean isBlocked(Player player) {
        return configManager.getDisabledWorlds().contains(player.getWorld().getName().toLowerCase(Locale.ROOT));
    }
}
