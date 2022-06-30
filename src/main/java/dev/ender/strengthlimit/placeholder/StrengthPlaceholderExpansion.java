package dev.ender.strengthlimit.placeholder;

import dev.ender.strengthlimit.config.Config;
import dev.ender.strengthlimit.event.Warp;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import dev.ender.strengthlimit.Strength;

import java.util.HashMap;

public class StrengthPlaceholderExpansion extends PlaceholderExpansion {
    private StrengthPlaceholderExpansion plugin;

    @Override
    public String getAuthor() {
        return "EnderTheCoder";
    }

    @Override
    public String getIdentifier() {
        return "strengthlimit";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String getRequiredPlugin() {
        return "StrengthLimit";
    }

    @Override
    public boolean canRegister() {
        return (plugin = (StrengthPlaceholderExpansion) Bukkit.getPluginManager().getPlugin(getRequiredPlugin())) != null;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {

        Strength strength = new Strength(player.getPlayer());

        if(params.equalsIgnoreCase("strength")){
            return String.valueOf(strength.getStrength());
        }
        if(params.equalsIgnoreCase("timeleft")){
            if (Warp.teleportTaskMap.get(player.getPlayer()) == null) return null;
            return String.valueOf((Warp.teleportTaskMap.get(player.getPlayer()).getStartTime() + Config.getTimeout() * 1000L - System.currentTimeMillis()) / 1000L);
        }

        return null;
    }
}
