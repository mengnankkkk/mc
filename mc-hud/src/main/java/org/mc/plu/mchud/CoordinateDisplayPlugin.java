package com.example;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class CoordinateDisplayPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("CoordinateDisplayPlugin enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("CoordinateDisplayPlugin disabled!");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        sendCoordinateInfo(player);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        sendCoordinateInfo(player);
    }

    private void sendCoordinateInfo(Player player) {
        Location location = player.getLocation();
        World world = location.getWorld();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        String coordinates = String.format("X: %.2f, Y: %.2f, Z: %.2f", x, y, z);

        Location netherLocation = new Location(Bukkit.getWorld("world_nether"), x / 8, y, z / 8);
        String netherCoordinates = String.format("Nether X: %.2f, Y: %.2f, Z: %.2f", netherLocation.getX(), netherLocation.getY(), netherLocation.getZ());

        String fps = "FPS: " + getFPS();

        StringBuilder inventoryContents = new StringBuilder();
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() != Material.AIR) {
                inventoryContents.append(item.getType().toString()).append(", ");
            }
        }

        player.sendMessage(ChatColor.GREEN + "Coordinates: " + ChatColor.WHITE + coordinates);
        player.sendMessage(ChatColor.GREEN + "Nether Coordinates: " + ChatColor.WHITE + netherCoordinates);
        player.sendMessage(ChatColor.GREEN + "FPS: " + ChatColor.WHITE + fps);
        player.sendMessage(ChatColor.GREEN + "Inventory: " + ChatColor.WHITE + inventoryContents.toString());
    }

    private int getFPS() {
        // Simplified example, not a real FPS calculation.
        return (int) (Math.random() * 60 + 30);
    }
}
