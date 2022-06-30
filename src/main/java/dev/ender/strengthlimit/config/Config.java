package dev.ender.strengthlimit.config;

import dev.ender.strengthlimit.StrengthLimit;
import org.bukkit.configuration.file.FileConfiguration;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.ArrayList;
import java.util.List;

public class Config {
    public static FileConfiguration config = StrengthLimit.instance.getConfig();

    public static int getCosts() {
        return config.getInt("costs");
    }

    public static int getMaxStrength(boolean isVIP) {
        return isVIP ? config.getInt("vip_max_strength") : config.getInt("default_max_strength");
    }

    public static List<String> getCostingWarpList() {
//        List<String> list= new ArrayList<>();
        return (List<String>) config.getList("warps");
    }

    public static String getMessage(String messageKey) {
        return config.getString(messageKey);
    }

    public static String getWarpBack() {
        return config.getString("teleport_back_warp");
    }

    public static int getTimeout() {
        return config.getInt("warp_timeout");
    }
}
