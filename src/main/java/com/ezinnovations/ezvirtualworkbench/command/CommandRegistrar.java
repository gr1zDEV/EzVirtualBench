package com.ezinnovations.ezvirtualworkbench.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class CommandRegistrar {
    private final JavaPlugin plugin;

    public CommandRegistrar(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void register(String command, CommandExecutor executor) {
        Objects.requireNonNull(plugin.getCommand(command), "Command not declared in plugin.yml: " + command)
            .setExecutor(executor);
    }
}
