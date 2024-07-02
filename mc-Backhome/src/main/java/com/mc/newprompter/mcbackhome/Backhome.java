package com.mc.newprompter.mcbackhome;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Backhome extends JavaPlugin implements TabExecutor {
    private static final int MAX_HOMES = 10;
    private Map<String, List<Location>> playerHomes = new HashMap<>();
    public static Backhome backhome;

    @Override
    public void onEnable() {
        backhome = this;
        this.getLogger().info("回家插件已启用");
        this.getCommand("sethome").setExecutor(this);
        this.getCommand("home").setExecutor(this);
    }

    @Override
    public void onDisable() {
        this.getLogger().info("回家插件已禁用");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("只有玩家才能使用这个指令。");
            return true;
        }

        Player player = (Player) sender;
        String playerName = player.getName();

        if (command.getName().equalsIgnoreCase("sethome")) {
            List<Location> homes = playerHomes.getOrDefault(playerName, new ArrayList<>());
            if (homes.size() >= MAX_HOMES) {
                player.sendMessage("你不能设置超过 " + MAX_HOMES + " 个家。");
                return true;
            }
            homes.add(player.getLocation());
            playerHomes.put(playerName, homes);
            player.sendMessage("家设置成功！");
            return true;
        } else if (command.getName().equalsIgnoreCase("home")) {
            if (args.length == 0) {
                player.sendMessage("请输入家的编号 (1-" + MAX_HOMES + ")。");
                return true;
            }

            try {
                int homeIndex = Integer.parseInt(args[0]) - 1;
                List<Location> homes = playerHomes.get(playerName);
                if (homes == null || homeIndex < 0 || homeIndex >= homes.size()) {
                    player.sendMessage("无效的家编号。");
                    return true;
                }
                player.teleport(homes.get(homeIndex));
                player.sendMessage("已传送到家 " + (homeIndex + 1) + "。");
                return true;
            } catch (NumberFormatException e) {
                player.sendMessage("家的编号必须是一个有效的数字。");
                return true;
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equalsIgnoreCase("home")) {
            if (args.length == 1 && sender instanceof Player) {
                Player player = (Player) sender;
                List<Location> homes = playerHomes.get(player.getName());
                List<String> completions = new ArrayList<>();
                if (homes != null) {
                    for (int i = 1; i <= homes.size(); i++) {
                        completions.add(String.valueOf(i));
                    }
                }
                return completions;
            }
        }
        return null;
    }
}
