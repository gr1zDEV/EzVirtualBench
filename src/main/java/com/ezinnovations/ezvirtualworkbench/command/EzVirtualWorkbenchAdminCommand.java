package com.ezinnovations.ezvirtualworkbench.command;

import com.ezinnovations.ezvirtualworkbench.command.subcommand.ReloadSubcommand;
import com.ezinnovations.ezvirtualworkbench.util.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class EzVirtualWorkbenchAdminCommand implements CommandExecutor {
    private static final String RELOAD_PERMISSION = "ezvirtualworkbench.admin.reload";

    private final ReloadSubcommand reloadSubcommand;
    private final MessageUtil messageUtil;

    public EzVirtualWorkbenchAdminCommand(ReloadSubcommand reloadSubcommand, MessageUtil messageUtil) {
        this.reloadSubcommand = reloadSubcommand;
        this.messageUtil = messageUtil;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 || !args[0].equalsIgnoreCase("reload")) {
            messageUtil.sendPrefixed(sender, "&eUsage: /" + label + " reload");
            return true;
        }

        if (!sender.hasPermission(RELOAD_PERMISSION)) {
            messageUtil.sendConfigMessage(sender, "no-permission", "&cYou do not have permission.");
            return true;
        }

        reloadSubcommand.execute(sender);
        return true;
    }
}
