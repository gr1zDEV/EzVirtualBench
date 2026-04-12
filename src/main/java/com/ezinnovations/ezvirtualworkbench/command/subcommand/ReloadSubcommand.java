package com.ezinnovations.ezvirtualworkbench.command.subcommand;

import com.ezinnovations.ezvirtualworkbench.config.ConfigManager;
import com.ezinnovations.ezvirtualworkbench.util.MessageUtil;
import org.bukkit.command.CommandSender;

public class ReloadSubcommand {
    private final ConfigManager configManager;
    private final MessageUtil messageUtil;

    public ReloadSubcommand(ConfigManager configManager, MessageUtil messageUtil) {
        this.configManager = configManager;
        this.messageUtil = messageUtil;
    }

    public void execute(CommandSender sender) {
        configManager.reload();
        messageUtil.sendConfigMessage(sender, "reload", "&aEzVirtualWorkbench configuration reloaded.");
    }
}
