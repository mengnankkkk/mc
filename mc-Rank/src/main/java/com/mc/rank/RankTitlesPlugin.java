package com.mc.rank;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class RankTitlesPlugin extends JavaPlugin implements Listener {

    private Map<String, String> rankTitles;

    @Override
    public void onEnable() {
        this.getLogger().info("RankTitles插件已启用");
        getServer().getPluginManager().registerEvents(this, this);
        loadRankTitles();
    }

    @Override
    public void onDisable() {
        this.getLogger().info("RankTitles插件已禁用");
    }

    private void loadRankTitles() {
        rankTitles = new HashMap<>();
        // 添加不同权限组的称号
        rankTitles.put("admin", "§4[管理员]");
        rankTitles.put("moderator", "§2[版主]");
        rankTitles.put("vip", "§6[VIP]");
        rankTitles.put("default", "§7[玩家]");
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String rank = getRank(player);
        String title = rankTitles.getOrDefault(rank, "§7[玩家]");
        String format = title + " %s: %s";
        event.setFormat(String.format(format, player.getDisplayName(), event.getMessage()));
    }

    private String getRank(Player player) {
        // 在这里检查玩家的权限并返回对应的权限组名称
        if (player.hasPermission("rank.admin")) {
            return "admin";
        } else if (player.hasPermission("rank.moderator")) {
            return "moderator";
        } else if (player.hasPermission("rank.vip")) {
            return "vip";
        } else {
            return "default";
        }
    }
}
