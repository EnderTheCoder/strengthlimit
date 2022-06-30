package dev.ender.strengthlimit.task;

import dev.ender.strengthlimit.config.Config;
import dev.ender.strengthlimit.event.Warp;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.TimerTask;

public class TeleportBackTask extends TimerTask {
    public long startTime = 0;
    public Player player;


    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void run() {

        if (player.isOnline()) {
            player.sendMessage(Config.getMessage("timeout_message"));
            player.performCommand("spawn");
        }
        Warp.teleportTaskMap.put(this.player, null);
    }
}
