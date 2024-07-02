package com.mc.newprompter.mctpa;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public final class TpaPlugin extends JavaPlugin {
    private static final int COOLDOWN_TIME_SECONDS = 60;
    private Map<UUID, UUID> tpaRequests = new HashMap<>();
    private Map<UUID, Long> lastTpaTime = new HashMap<>();

    @Override
    public void onEnable() {
        this.getLogger().info("TPA插件已启用");
        this.getCommand("tpa").setExecutor(this);
        this.getCommand("tpaaccept").setExecutor(this);
        this.getCommand("tpadeny").setExecutor(this);
    }

    @Override
    public void onDisable() {
        this.getLogger().info("TPA插件已禁用");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("只有玩家才能使用这个指令。");
            return true;
        }

        Player player = (Player) sender;
        UUID playerId = player.getUniqueId();

        if (command.getName().equalsIgnoreCase("tpa")) {
            if (args.length == 0) {
                player.sendMessage("请输入目标玩家名称。");
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage("玩家 " + args[0] + " 不在线。");
                return true;
            }

            if (lastTpaTime.containsKey(playerId)) {
                long lastTime = lastTpaTime.get(playerId);
                long currentTime = System.currentTimeMillis();
                long timeElapsed = currentTime - lastTime;

                if (timeElapsed < TimeUnit.SECONDS.toMillis(COOLDOWN_TIME_SECONDS)) {
                    long timeRemaining = COOLDOWN_TIME_SECONDS - TimeUnit.MILLISECONDS.toSeconds(timeElapsed);
                    player.sendMessage("你需要等待 " + timeRemaining + " 秒后才能再次发送传送请求。");
                    return true;
                }
            }

            lastTpaTime.put(playerId, System.currentTimeMillis());
            tpaRequests.put(target.getUniqueId(), playerId);

            Component message = Component.text(player.getName() + " 请求传送到你的位置。")
                    .append(Component.text(" [接受]")
                            .color(NamedTextColor.GREEN)
                            .clickEvent(ClickEvent.runCommand("/tpaaccept " + player.getName()))
                            .hoverEvent(HoverEvent.showText(Component.text("点击接受"))))
                    .append(Component.text(" [拒绝]")
                            .color(NamedTextColor.RED)
                            .clickEvent(ClickEvent.runCommand("/tpadeny " + player.getName()))
                            .hoverEvent(HoverEvent.showText(Component.text("点击拒绝"))));

            target.sendMessage(message);
            player.sendMessage("已发送传送请求给 " + target.getName() + "。");
            return true;
        } else if (command.getName().equalsIgnoreCase("tpaaccept")) {
            if (args.length == 0) {
                player.sendMessage("请指定要接受传送请求的玩家。");
                return true;
            }

            Player requester = Bukkit.getPlayer(args[0]);
            if (requester == null || !tpaRequests.containsKey(player.getUniqueId()) ||
                    !tpaRequests.get(player.getUniqueId()).equals(requester.getUniqueId())) {
                player.sendMessage("无效的传送请求。");
                return true;
            }

            tpaRequests.remove(player.getUniqueId());
            requester.teleport(player.getLocation());
            player.sendMessage("已接受传送请求。");
            requester.sendMessage("你的传送请求已被接受。");
            return true;
        } else if (command.getName().equalsIgnoreCase("tpadeny")) {
            if (args.length == 0) {
                player.sendMessage("请指定要拒绝传送请求的玩家。");
                return true;
            }

            Player requester = Bukkit.getPlayer(args[0]);
            if (requester == null || !tpaRequests.containsKey(player.getUniqueId()) ||
                    !tpaRequests.get(player.getUniqueId()).equals(requester.getUniqueId())) {
                player.sendMessage("无效的传送请求。");
                return true;
            }

            tpaRequests.remove(player.getUniqueId());
            player.sendMessage("已拒绝传送请求。");
            requester.sendMessage("你的传送请求已被拒绝。");
            return true;
        }

        return false;
    }
}
