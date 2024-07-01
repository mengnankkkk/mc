package com.mc.newprompter.prompter;
import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
//可以参考一下https://www.jianshu.com/p/911edcaf5e79

public class CoordinatesDisplay implements Listener {
    // 创建计分板
    private Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    // 创建计分目标
    private Objective objective = scoreboard.registerNewObjective("status", "dummy","§b状态信息");


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // 获取玩家
        Player player = event.getPlayer();
        // 设置计分目标的显示位置为左上角
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        // 设置计分目标的名称
        //objective.setDisplayName("坐标信息");

        // 给玩家设置计分板
        player.setScoreboard(scoreboard);

        // 在记分板上设置玩家的血量
        Score healthScore = objective.getScore("§d血量:");
        healthScore.setScore((int) player.getHealth());
        // 在记分板上设置在线人数
        Score onlinePlayersScore = objective.getScore("§5在线人数: ");
        onlinePlayersScore.setScore(Bukkit.getOnlinePlayers().size());
    }//玩家在加入游戏时触发该事件

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        // 获取玩家
        Player player = event.getPlayer();
        // 获取玩家所在的世界
        World world = player.getWorld();

        // 更新玩家的血量
        Score healthScore = objective.getScore("§d血量:");
        healthScore.setScore((int) player.getHealth());

        // 获取玩家的位置
        Location location = player.getLocation();
        // 在记分板上设置坐标
        Score scoreX = objective.getScore("§1X轴:");
        scoreX.setScore((int) location.getX());
        Score scoreY = objective.getScore("§2Y轴:");
        scoreY.setScore((int) location.getY());
        Score scoreZ = objective.getScore("§3Z轴:");
        scoreZ.setScore((int) location.getZ());
    }//玩家在移动时触发这个


    /*@EventHandler
    public void onPlayerJump(PlayerJumpEvent event) {
        // 获取玩家
        Player player = event.getPlayer();
        // 获取玩家所在的世界
        World world = player.getWorld();
        // 获取玩家的位置
        Location location = player.getLocation();
        // 在记分板上设置坐标
        Score scoreX = objective.getScore("§1X:");
        scoreX.setScore((int) location.getX());
        Score scoreY = objective.getScore("§2Y:");
        scoreY.setScore((int) location.getY());
        Score scoreZ = objective.getScore("§3Z:");
        scoreZ.setScore((int) location.getZ());
    }玩家在跳跃时触发这个，不过先注释掉吧*/
}