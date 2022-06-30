package dev.ender.strengthlimit.event;

import dev.ender.strengthlimit.Strength;
import dev.ender.strengthlimit.config.Config;
import org.bukkit.Color;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import static org.bukkit.Bukkit.getLogger;

public class Warp implements Listener {
    @EventHandler
    public void onPlayerWrap(PlayerCommandPreprocessEvent event) {
        String targetWarp = event.getMessage().replace("/warp ", "");
        for (String warp : Config.getCostingWarpList()) {
            if (warp.equals(targetWarp)) {
                Strength strength = new Strength(event.getPlayer());
                if (!strength.isEnough()) {
                    event.getPlayer().sendMessage(Config.getMessage("warp_fail_message").replace("%体力%", String.valueOf(strength.getStrength())));
                    event.setCancelled(true);
                }
                else {
                    strength.decline(Config.getCosts());
                    strength.save(false);
                    event.getPlayer().sendMessage(Config.getMessage("warp_success_message").replace("%体力%", String.valueOf(strength.getStrength())));
                }
                break;
            }
        }
    }
}
