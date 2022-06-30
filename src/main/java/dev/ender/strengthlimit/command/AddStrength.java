package dev.ender.strengthlimit.command;

import dev.ender.strengthlimit.Strength;
import dev.ender.strengthlimit.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AddStrength implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (args.length != 2) {
            commandSender.sendMessage(ChatColor.RED + "[体力]错误的参数数量！应为2");
            return false;
        }
        Player player = Bukkit.getPlayer(args[0]);
        if (player == null) {
            commandSender.sendMessage(ChatColor.YELLOW + "[体力]该玩家不存在或者处于离线状态");
            return true;
        }
        Strength strength = new Strength(player);
        strength.increase(Integer.parseInt(args[1]));
        strength.save(false);
        commandSender.sendMessage(Config.getMessage("add_success_message").replace("%玩家%", player.getDisplayName()).replace("%体力%", args[1]));
        return true;
    }
}
