package com.mc.newprompter.prompter;

import org.bukkit.plugin.java.JavaPlugin;

public final class Prompter extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        //注册监听器
        getServer().getPluginManager().registerEvents(new DeathCoordinates(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}