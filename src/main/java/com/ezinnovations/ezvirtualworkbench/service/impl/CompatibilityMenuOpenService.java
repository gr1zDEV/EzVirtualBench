package com.ezinnovations.ezvirtualworkbench.service.impl;

import com.ezinnovations.ezvirtualworkbench.model.MenuType;
import com.ezinnovations.ezvirtualworkbench.service.MenuOpenService;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CompatibilityMenuOpenService implements MenuOpenService {
    private final JavaPlugin plugin;

    public CompatibilityMenuOpenService(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean openMenu(Player player, MenuType menuType) {
        switch (menuType) {
            case CRAFT:
                return openByBooleanMethod(player, "openWorkbench")
                    || openByInventoryType(player, "WORKBENCH");
            case ENDER_CHEST:
                // Explicitly opens the player's real Ender Chest inventory, not custom storage.
                player.openInventory(player.getEnderChest());
                return true;
            case ANVIL:
                return openByBooleanMethod(player, "openAnvil")
                    || openByInventoryType(player, "ANVIL");
            case SMITHING:
                return openByBooleanMethod(player, "openSmithing")
                    || openByInventoryType(player, "SMITHING");
            case GRINDSTONE:
                return openByBooleanMethod(player, "openGrindstone")
                    || openByInventoryType(player, "GRINDSTONE");
            case LOOM:
                return openByBooleanMethod(player, "openLoom")
                    || openByInventoryType(player, "LOOM");
            case STONECUTTER:
                return openByBooleanMethod(player, "openStonecutter")
                    || openByInventoryType(player, "STONECUTTER");
            case CARTOGRAPHY:
                return openByBooleanMethod(player, "openCartography")
                    || openByInventoryType(player, "CARTOGRAPHY");
            default:
                return false;
        }
    }

    /**
     * Compatibility path for 1.14+ where openX(Location, boolean) methods were common across versions.
     * These methods are deprecated on modern versions but still reliable as a wide-range bridge.
     */
    private boolean openByBooleanMethod(Player player, String methodName) {
        try {
            Method method = HumanEntity.class.getMethod(methodName, Location.class, boolean.class);
            method.invoke(player, player.getLocation(), true);
            return true;
        } catch (NoSuchMethodException ignored) {
            return false;
        } catch (IllegalAccessException | InvocationTargetException ex) {
            plugin.getLogger().warning("Failed to open menu via method " + methodName + ": " + ex.getMessage());
            return false;
        }
    }

    /**
     * Fallback path for versions/platforms where a dedicated openX method isn't available.
     * Uses true utility container types instead of fake chest GUIs.
     */
    private boolean openByInventoryType(Player player, String inventoryTypeName) {
        InventoryType inventoryType;
        try {
            inventoryType = InventoryType.valueOf(inventoryTypeName);
        } catch (IllegalArgumentException ex) {
            return false;
        }

        try {
            Inventory inventory = Bukkit.createInventory(player, inventoryType);
            player.openInventory(inventory);
            return true;
        } catch (IllegalArgumentException ex) {
            plugin.getLogger().warning("Inventory type not supported on this server: " + inventoryTypeName);
            return false;
        }
    }
}
