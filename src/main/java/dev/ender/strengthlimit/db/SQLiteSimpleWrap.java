package dev.ender.strengthlimit.db;

import java.sql.*;

import static net.minecraft.server.v1_7_R4.MinecraftServer.getLogger;

public class SQLiteSimpleWrap {
    private Connection connection = null;
    private PreparedStatement statement = null;

    private Timestamp startTime;

    public void connect() {
        this.startTime = new Timestamp(System.currentTimeMillis());
        try {
            Class.forName("org.sqlite.JDBC");
            //如果没有数据库文件的话会自动创建
            this.connection = DriverManager.getConnection("jdbc:sqlite:./plugins/StrengthLimit/main.db");
        } catch (Exception e) {
            getLogger().error(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public Connection getConnection() {
        try {
            if (this.connection == null || !this.connection.isValid(1000)) connect();
        } catch (SQLException e) {
            getLogger().error("获取数据库连接时发生错误");

            e.printStackTrace();
        }
        return connection;
    }

    public void prepare(String sql) {
        try {
            statement = getConnection().prepareStatement(sql);
        } catch (SQLException e) {
            getLogger().error("预处理SQL语句时发生错误");
            e.printStackTrace();
        }
    }

    public void bindString(int number, String value) {
        try {
            statement.setString(number, value);
        } catch (SQLException e) {
            getLogger().error("绑定参数时时发生错误");
            e.printStackTrace();
        }
    }

    public void bindInt(int number, int value) {
        try {
            statement.setInt(number, value);
        } catch (SQLException e) {
            getLogger().error("绑定参数时时发生错误");
            e.printStackTrace();
        }

    }

    public void bindDouble(int number, double value) {
        try {
            statement.setDouble(number, value);
        } catch (SQLException e) {
            getLogger().error("绑定参数时时发生错误");
            e.printStackTrace();
        }
    }

    public void bindLong(int number, long value) {
        try {
            statement.setLong(number, value);
        } catch (SQLException e) {
            getLogger().error("绑定参数时时发生错误");
            e.printStackTrace();
        }
    }

    public void execute() {
        try {
            statement.execute();
        } catch (SQLException e) {
            getLogger().error("执行SQL语句时发生错误");
            e.printStackTrace();
        }
    }

    public ResultSet result() {
        try {
            return statement.getResultSet();
        } catch (SQLException e) {
            getLogger().error("获取数据库查询结果时发生错误");
            e.printStackTrace();
            return null;
        }
    }

    public void close() {
        //计算数据库查询所用时间并输出调试信息
        Timestamp endTime = new Timestamp(System.currentTimeMillis());

//        if (ConfigReader.isOnDebug())
            getLogger().info(String.format("数据库连接关闭，本次查询共用时%s毫秒", endTime.getTime() - startTime.getTime()));

        try {
            this.connection.close();
        } catch (SQLException e) {
            getLogger().error("关闭数据库连接时发生错误");
            e.printStackTrace();
        }
    }

    public boolean isTableExists() {
        SQLiteSimpleWrap s = new SQLiteSimpleWrap();
        s.prepare("SELECT * FROM sqlite_master WHERE type='table' AND name = ?");
        s.bindString(1, "strength");
        s.execute();
        ResultSet resultSet = s.result();
        try {
            boolean res = resultSet.next();
            s.close();
            return res;
        } catch (SQLException e) {
            s.close();
            return false;
        }
    }

    public void initTable() {
        SQLiteSimpleWrap sqlite = new SQLiteSimpleWrap();
        sqlite.prepare("create table strength\n" +
                "(\n" +
                "    player_name        text not null,\n" +
                "    strength           integer,\n" +
                "    latest_update_time text\n" +
                ");\n");
        sqlite.execute();
        sqlite.close();
    }

}
