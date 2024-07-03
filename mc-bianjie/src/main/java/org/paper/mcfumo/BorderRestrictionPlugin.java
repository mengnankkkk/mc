package org.paper.mcfumo;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class BorderRestrictionPlugin extends JavaPlugin implements Listener {

    private static final double BORDER_LIMIT = 100000.0; // 10万格限制的边界距离，可根据需求调整

    @Override
    public void onEnable() {
        // 注册事件监听器
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("BorderRestrictionPlugin has been enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("BorderRestrictionPlugin has been disabled.");
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Location to = event.getTo();

        if (to != null && (isOutsideBorder(to) || isAboveNetherCeiling(to))) {
            event.getPlayer().setHealth(0.0); // 玩家超出边界或在地狱的基岩层以上时执行瞬间死亡操作
            event.setCancelled(true); // 取消事件，防止玩家继续移动
            event.getPlayer().sendMessage("You have reached beyond the border limit or above the Nether ceiling and died."); // 提示玩家
        }
    }

    private boolean isOutsideBorder(Location location) {
        double distanceSquared = location.getX() * location.getX() + location.getZ() * location.getZ();
        return distanceSquared > BORDER_LIMIT * BORDER_LIMIT; // 检查玩家是否超出限制范围的平方距离
    }

    private boolean isAboveNetherCeiling(Location location) {
        World world = location.getWorld();
        if (world != null && world.getEnvironment() == World.Environment.NETHER) {
            return location.getY() > 127.0; // Nether ceiling的Y坐标是127
        }
        return false;
    }
}
