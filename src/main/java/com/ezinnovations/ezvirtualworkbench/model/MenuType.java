package com.ezinnovations.ezvirtualworkbench.model;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

public enum MenuType {
    CRAFT("craft", "ezvirtualworkbench.craft"),
    ENDER_CHEST("ec", "ezvirtualworkbench.enderchest"),
    ANVIL("anvil", "ezvirtualworkbench.anvil"),
    SMITHING("smithing", "ezvirtualworkbench.smithing"),
    GRINDSTONE("grindstone", "ezvirtualworkbench.grindstone"),
    LOOM("loom", "ezvirtualworkbench.loom"),
    STONECUTTER("stonecutter", "ezvirtualworkbench.stonecutter"),
    CARTOGRAPHY("cartography", "ezvirtualworkbench.cartography");

    private final String commandKey;
    private final String permission;

    MenuType(String commandKey, String permission) {
        this.commandKey = commandKey;
        this.permission = permission;
    }

    public String getCommandKey() {
        return commandKey;
    }

    public String getPermission() {
        return permission;
    }

    public static Optional<MenuType> fromCommandLabel(String commandLabel) {
        String normalized = commandLabel.toLowerCase(Locale.ROOT);
        if (normalized.equals("workbench") || normalized.equals("wb")) {
            return Optional.of(CRAFT);
        }
        if (normalized.equals("enderchest")) {
            return Optional.of(ENDER_CHEST);
        }

        return Arrays.stream(values())
            .filter(type -> type.commandKey.equals(normalized))
            .findFirst();
    }
}
