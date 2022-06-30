package dev.ender.strengthlimit;

import dev.ender.strengthlimit.command.AddStrength;
import dev.ender.strengthlimit.command.ResetStrength;
import dev.ender.strengthlimit.command.SetStrength;
import dev.ender.strengthlimit.db.SQLiteSimpleWrap;
import dev.ender.strengthlimit.event.PlayerJoinInit;
import dev.ender.strengthlimit.event.Warp;
import dev.ender.strengthlimit.placeholder.StrengthPlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Timer;
import java.util.TimerTask;

//main class, entry point of a plugin
public class StrengthLimit extends JavaPlugin {

    public static JavaPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getPluginManager().registerEvents(new PlayerJoinInit(),this);
        Bukkit.getPluginManager().registerEvents(new Warp(),this);
        saveDefaultConfig();
        SQLiteSimpleWrap sqlite = new SQLiteSimpleWrap();
        if (!sqlite.isTableExists()) sqlite.initTable();
        Bukkit.getPluginCommand("resetstrength").setExecutor(new ResetStrength());
        Bukkit.getPluginCommand("addstrength").setExecutor(new AddStrength());
        getLogger().info(Color.BLUE + "StrengthLimit plugin enabled, made by EnderTheCoder and Null");

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new StrengthPlaceholderExpansion().register();
        } else {
            Bukkit.getLogger().warning("Could not find PlaceholderAPI! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() { }
}
