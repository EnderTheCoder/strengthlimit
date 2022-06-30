package dev.ender.strengthlimit.event;

import dev.ender.strengthlimit.Strength;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.lang.annotation.Annotation;

import static org.bukkit.Bukkit.getLogger;

public class PlayerJoinInit implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        getLogger().info("player joined");
        new Strength(event.getPlayer());
    }
}
