package com.mc.newprompter.prompter;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathCoordinates implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        // 获取玩家死亡的位置
        Location location = event.getEntity().getLocation();
        // 在游戏聊天框内输出坐标
        event.getEntity().sendMessage("你死亡的坐标是: " + location.getX() + ", " + location.getY() + ", " + location.getZ());

    }

}