package com.ezinnovations.ezvirtualworkbench.command;

import com.ezinnovations.ezvirtualworkbench.config.ConfigManager;
import com.ezinnovations.ezvirtualworkbench.model.MenuType;
import com.ezinnovations.ezvirtualworkbench.service.CooldownService;
import com.ezinnovations.ezvirtualworkbench.service.MenuOpenService;
import com.ezinnovations.ezvirtualworkbench.service.WorldRestrictionService;
import com.ezinnovations.ezvirtualworkbench.util.MessageUtil;
import com.ezinnovations.ezvirtualworkbench.util.SoundHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class UtilityCommandExecutor implements CommandExecutor {
    private final ConfigManager configManager;
    private final MessageUtil messageUtil;
    private final MenuOpenService menuOpenService;
    private final CooldownService cooldownService;
    private final WorldRestrictionService worldRestrictionService;
    private final SoundHelper soundHelper;

    public UtilityCommandExecutor(
        ConfigManager configManager,
        MessageUtil messageUtil,
        MenuOpenService menuOpenService,
        CooldownService cooldownService,
        WorldRestrictionService worldRestrictionService,
        SoundHelper soundHelper
    ) {
        this.configManager = configManager;
        this.messageUtil = messageUtil;
        this.menuOpenService = menuOpenService;
        this.cooldownService = cooldownService;
        this.worldRestrictionService = worldRestrictionService;
        this.soundHelper = soundHelper;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Optional<MenuType> menuTypeOptional = MenuType.fromCommandLabel(command.getName());
        if (menuTypeOptional.isEmpty()) {
            return false;
        }

        if (!(sender instanceof Player player)) {
            messageUtil.sendConfigMessage(sender, "player-only", "&cOnly players can use this command.");
            return true;
        }

        MenuType menuType = menuTypeOptional.get();
        if (!player.hasPermission(menuType.getPermission())) {
            messageUtil.sendConfigMessage(player, "no-permission", "&cYou do not have permission.");
            return true;
        }

        if (!configManager.isCommandEnabled(menuType)) {
            messageUtil.sendConfigMessage(player, "command-disabled", "&cThat command is currently disabled.");
            return true;
        }

        if (worldRestrictionService.isBlocked(player)) {
            messageUtil.sendConfigMessage(player, "disabled-world", "&cYou cannot use that here.");
            return true;
        }

        if (!player.hasPermission("ezvirtualworkbench.bypass.cooldown")) {
            long cooldownSeconds = configManager.getCooldownSeconds(menuType);
            long remaining = cooldownService.getRemainingSeconds(player, menuType, cooldownSeconds);
            if (remaining > 0) {
                String message = configManager
                    .getMessage("cooldown", "&eYou must wait %seconds%s before using this again.")
                    .replace("%seconds%", String.valueOf(remaining));
                messageUtil.sendPrefixed(player, message);
                return true;
            }
        }

        boolean opened = menuOpenService.openMenu(player, menuType);
        if (!opened) {
            messageUtil.sendConfigMessage(player, "unsupported-menu", "&cThis menu is not supported on your server version.");
            return true;
        }

        cooldownService.markUsed(player, menuType);
        soundHelper.playOpenSound(player);

        if (configManager.showSuccessMessage()) {
            String message = configManager
                .getMessage("opened", "&aOpened %menu%.")
                .replace("%menu%", menuType.getCommandKey());
            messageUtil.sendPrefixed(player, message);
        }

        return true;
    }
}
