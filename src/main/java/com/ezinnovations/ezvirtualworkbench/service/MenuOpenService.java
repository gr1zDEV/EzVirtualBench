package com.ezinnovations.ezvirtualworkbench.service;

import com.ezinnovations.ezvirtualworkbench.model.MenuType;
import org.bukkit.entity.Player;

public interface MenuOpenService {
    boolean openMenu(Player player, MenuType menuType);
}
