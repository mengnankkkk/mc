package com.mc.newprompter.mcjoinleaveplungin;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Mc_JoinleavePlungin extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        this.getLogger().info("JoinLeave插件已启用");
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        this.getLogger().info("JoinLeave插件已禁用");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String joinMessage = String.format("§a欢迎 §b%s §a来到服务器！", event.getPlayer().getName());
        event.setJoinMessage(joinMessage);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        String quitMessage = String.format("§c%s §e离开了服务器，再见！", event.getPlayer().getName());
        event.setQuitMessage(quitMessage);
    }
}
