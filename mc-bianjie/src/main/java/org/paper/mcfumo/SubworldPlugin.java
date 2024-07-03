package org.paper.mcfumo;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SubworldPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("SubworldPlugin has been enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("SubworldPlugin has been disabled.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("createsubworld")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Only players can create subworlds.");
                return true;
            }

            Player player = (Player) sender;

            // Create a new subworld
            String subworldName = "subworld_" + player.getName(); // Example: subworld_<playername>
            WorldCreator worldCreator = new WorldCreator(subworldName);
            worldCreator.environment(World.Environment.NORMAL); // 设置环境，可以是 NORMAL, NETHER, 或者 THE_END
            worldCreator.generateStructures(true); // 是否生成结构物，比如村庄和地牢

            World subworld = worldCreator.createWorld(); // 创建世界实例

            if (subworld != null) {
                player.sendMessage("Subworld '" + subworld.getName() + "' has been created!");
            } else {
                player.sendMessage("Failed to create subworld.");
            }

            return true;
        }

        return false;
    }
}
