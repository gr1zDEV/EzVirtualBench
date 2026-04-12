package com.ezinnovations.ezvirtualworkbench.service.impl;

import com.ezinnovations.ezvirtualworkbench.model.MenuType;
import com.ezinnovations.ezvirtualworkbench.service.CooldownService;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleCooldownService implements CooldownService {
    private final Map<UUID, Map<MenuType, Long>> usageTimestamps = new ConcurrentHashMap<>();

    @Override
    public long getRemainingSeconds(Player player, MenuType menuType, long cooldownSeconds) {
        if (cooldownSeconds <= 0) {
            return 0;
        }
        Long lastUse = usageTimestamps
            .getOrDefault(player.getUniqueId(), Map.of())
            .get(menuType);

        if (lastUse == null) {
            return 0;
        }

        long elapsedSeconds = (System.currentTimeMillis() - lastUse) / 1000L;
        long remaining = cooldownSeconds - elapsedSeconds;
        return Math.max(remaining, 0L);
    }

    @Override
    public void markUsed(Player player, MenuType menuType) {
        usageTimestamps
            .computeIfAbsent(player.getUniqueId(), ignored -> new ConcurrentHashMap<>())
            .put(menuType, System.currentTimeMillis());
    }
}
