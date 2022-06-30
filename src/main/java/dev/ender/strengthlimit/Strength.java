package dev.ender.strengthlimit;

import dev.ender.strengthlimit.config.Config;
import dev.ender.strengthlimit.db.SQLiteSimpleWrap;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

//used for describe strength
public class Strength {

    protected int strength;
    protected Player player;
    protected long latestUpdateTime;
    public Strength(Player player) {
        this.player = player;
        if (isPlayerDataExists(player))
            this.strength = getStrengthByPlayer(player);
        else createPlayerDefaultData(player);
    }

    private int getStrengthByPlayer(Player player) {
        SQLiteSimpleWrap sqlite = new SQLiteSimpleWrap();
        sqlite.prepare("SELECT * FROM strength WHERE player_name = ?");
        sqlite.bindString(1, this.player.getName());
        sqlite.execute();
        ResultSet resultSet = sqlite.result();

        try {
            resultSet.next();
            this.strength = resultSet.getInt("strength");
            this.latestUpdateTime = resultSet.getLong("latest_update_time");
            sqlite.close();
            return this.strength;
        } catch (SQLException e) {
            sqlite.close();
            this.strength = -1;
            return this.strength;
        }
    }

    public int getStrength() {
        return this.strength;
    }

    public Player getPlayer() {
        return this.player;
    }

    public long getLatestUpdateTime() {
        return this.latestUpdateTime;
    }

    public boolean isEnough() {
        return Config.getCosts() <= this.strength;
    }

    public void increase(int amount) {
        int maxStrength = getMaxStrength(this.player);
        if (amount + this.strength > maxStrength) this.strength = maxStrength;
        else this.strength += amount;
    }

    public void decline(int amount) {
        this.strength -= amount;
    }

    public void set(int strength) {
        this.strength = strength;
    }

    public void reset() {
        this.strength = getMaxStrength(this.player);
    }

    public void save(boolean isReset) {
        SQLiteSimpleWrap sqlite = new SQLiteSimpleWrap();
        if (isReset) {
            sqlite.prepare("UPDATE strength SET strength = ?, latest_update_time = ? WHERE player_name = ?");
            sqlite.bindInt(1, this.strength);
            sqlite.bindLong(2, System.currentTimeMillis());
            sqlite.bindString(3, this.player.getName());
        } else {
            sqlite.prepare("UPDATE strength SET strength = ? WHERE player_name = ?");
            sqlite.bindInt(1, this.strength);
            sqlite.bindString(2, this.player.getName());
        }
        sqlite.execute();
        sqlite.close();
    }

    public boolean isPlayerDataExists(Player player) {
        return getStrengthByPlayer(player) != -1;
    }

    public void createPlayerDefaultData(Player player) {
        SQLiteSimpleWrap sqlite = new SQLiteSimpleWrap();
        sqlite.prepare("INSERT INTO strength (player_name, strength, latest_update_time) VALUES (?, ?, ?)");
        sqlite.bindString(1, this.player.getName());
        sqlite.bindInt(2, getMaxStrength(player));
        sqlite.bindLong(3, System.currentTimeMillis());
        sqlite.execute();
        sqlite.close();
    }

    public int getMaxStrength(Player player) {
        return Config.getMaxStrength(player.hasPermission("strengthlimit.vip"));
    }
}
