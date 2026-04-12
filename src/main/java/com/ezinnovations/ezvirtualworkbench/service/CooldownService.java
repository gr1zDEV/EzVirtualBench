package com.ezinnovations.ezvirtualworkbench.service;

import com.ezinnovations.ezvirtualworkbench.model.MenuType;
import org.bukkit.entity.Player;

public interface CooldownService {
    long getRemainingSeconds(Player player, MenuType menuType, long cooldownSeconds);

    void markUsed(Player player, MenuType menuType);
}
