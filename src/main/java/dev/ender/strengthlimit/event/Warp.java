package dev.ender.strengthlimit.event;

import dev.ender.strengthlimit.Strength;
import dev.ender.strengthlimit.StrengthLimit;
import dev.ender.strengthlimit.config.Config;
import dev.ender.strengthlimit.task.TeleportBackTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.HashMap;
import java.util.Objects;
import java.util.Timer;

public class Warp implements Listener {

    public static HashMap<Player, TeleportBackTask> teleportTaskMap = new HashMap<>();

    @EventHandler
    public void onPlayerWrap(PlayerCommandPreprocessEvent event) {
        String command = event.getMessage().toLowerCase();
        if (!command.contains("/warp")) return;
        else if (!Objects.equals(event.getMessage(), command)) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("[strengthlimit]Invalid command for warp. You shouldn't use uppercase in your warp command.");
            return;
        }
        String targetWarp = command.replace("/warp ", "");
        for (String warp : Config.getCostingWarpList()) {
            if (warp.equals(targetWarp)) {
                Strength strength = new Strength(event.getPlayer());

                TeleportBackTask task = new TeleportBackTask();
                task.setPlayer(event.getPlayer());
                task.setStartTime(System.currentTimeMillis());
                teleportTaskMap.put(event.getPlayer(), task);
                Timer timer = new Timer();
                timer.schedule(task, Config.getTimeout() * 1000L);

                if (!strength.isEnough()) {
                    event.getPlayer().sendMessage(Config.getMessage("warp_fail_message").replace("%体力%", String.valueOf(strength.getStrength())));
                    event.setCancelled(true);
                } else {
                    strength.decline(Config.getCosts());
                    strength.save(false);
                    event.getPlayer().sendMessage(Config.getMessage("warp_success_message").replace("%体力%", String.valueOf(strength.getStrength())));
                }
                break;
            }
        }
    }
}
