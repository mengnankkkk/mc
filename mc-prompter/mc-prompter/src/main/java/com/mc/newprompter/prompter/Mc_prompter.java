package com.mc.newprompter.prompter;

import org.bukkit.plugin.java.JavaPlugin;

public final class Mc_prompter extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new CoordinatesDisplay(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
