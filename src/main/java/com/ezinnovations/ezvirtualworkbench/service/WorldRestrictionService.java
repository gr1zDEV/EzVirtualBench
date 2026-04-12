package com.ezinnovations.ezvirtualworkbench.service;

import org.bukkit.entity.Player;

public interface WorldRestrictionService {
    boolean isBlocked(Player player);
}
