package dev.ender.strengthlimit.command;

import dev.ender.strengthlimit.Strength;
import dev.ender.strengthlimit.config.Config;
import dev.ender.strengthlimit.util.DateTransfer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.Date;

public class ResetStrength implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Color.RED + "[错误]该命令仅允许在客户端执行！");
            return false;
        }
        Player player = (Player) commandSender;
        Strength strength = new Strength(player);
        if (DateTransfer.getDay(new Date(strength.getLatestUpdateTime())) == DateTransfer.getDay(new Date())) {
            player.sendMessage(Config.getMessage("reset_fail_message"));
        } else {
            player.sendMessage(Config.getMessage("reset_success_message"));
            strength.reset();
            strength.save(true);
        }
        return true;
    }
}
