package com.ezinnovations.ezvirtualworkbench;

import com.ezinnovations.ezvirtualworkbench.command.CommandRegistrar;
import com.ezinnovations.ezvirtualworkbench.command.EzVirtualWorkbenchAdminCommand;
import com.ezinnovations.ezvirtualworkbench.command.UtilityCommandExecutor;
import com.ezinnovations.ezvirtualworkbench.command.subcommand.ReloadSubcommand;
import com.ezinnovations.ezvirtualworkbench.config.ConfigManager;
import com.ezinnovations.ezvirtualworkbench.service.CooldownService;
import com.ezinnovations.ezvirtualworkbench.service.MenuOpenService;
import com.ezinnovations.ezvirtualworkbench.service.WorldRestrictionService;
import com.ezinnovations.ezvirtualworkbench.service.impl.CompatibilityMenuOpenService;
import com.ezinnovations.ezvirtualworkbench.service.impl.ConfigWorldRestrictionService;
import com.ezinnovations.ezvirtualworkbench.service.impl.SimpleCooldownService;
import com.ezinnovations.ezvirtualworkbench.util.MessageUtil;
import com.ezinnovations.ezvirtualworkbench.util.SoundHelper;
import org.bukkit.plugin.java.JavaPlugin;

public class EzVirtualWorkbenchPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        ConfigManager configManager = new ConfigManager(this);
        configManager.load();

        MessageUtil messageUtil = new MessageUtil(configManager);
        MenuOpenService menuOpenService = new CompatibilityMenuOpenService(this);
        CooldownService cooldownService = new SimpleCooldownService();
        WorldRestrictionService worldRestrictionService = new ConfigWorldRestrictionService(configManager);
        SoundHelper soundHelper = new SoundHelper(configManager);

        UtilityCommandExecutor utilityCommandExecutor = new UtilityCommandExecutor(
            configManager,
            messageUtil,
            menuOpenService,
            cooldownService,
            worldRestrictionService,
            soundHelper
        );

        ReloadSubcommand reloadSubcommand = new ReloadSubcommand(configManager, messageUtil);
        EzVirtualWorkbenchAdminCommand adminCommand = new EzVirtualWorkbenchAdminCommand(reloadSubcommand, messageUtil);

        CommandRegistrar commandRegistrar = new CommandRegistrar(this);
        commandRegistrar.register("craft", utilityCommandExecutor);
        commandRegistrar.register("ec", utilityCommandExecutor);
        commandRegistrar.register("anvil", utilityCommandExecutor);
        commandRegistrar.register("smithing", utilityCommandExecutor);
        commandRegistrar.register("grindstone", utilityCommandExecutor);
        commandRegistrar.register("loom", utilityCommandExecutor);
        commandRegistrar.register("stonecutter", utilityCommandExecutor);
        commandRegistrar.register("cartography", utilityCommandExecutor);
        commandRegistrar.register("ezvirtualworkbench", adminCommand);

        getLogger().info("EzVirtualWorkbench enabled.");
    }
}
