package vg.civcraft.mc.BlockMeta.storage;

import com.mysql.jdbc.Driver;
import org.bukkit.Location;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by isaac on 2/17/2015.
 */
public class Database {

    private static final String META_TABLE = "META_TO_BLOCK";

    private String url;
    private String db;
    private String tablePrefix;
    private String user;
    private String password;

    private Connection connection;

    private PreparedStatement addMeta;
    private PreparedStatement getMeta;
    private PreparedStatement deleteMeta;

    public Database(String url, String db, String tablePrefix, String user, String password){
        this.url = url;
        this.db = db;
        this.tablePrefix = tablePrefix;

        this.user = user;
        this.password = password;
        connect();
        genTable();
        setPreparedStatements();
    }

    private void genTable() {
        try {
            connection.nativeSQL("CREATE TABLE " + META_TABLE + " IF NOT EXISTS (" +
                            "meta varchar(256)," +
                            "world_id char(36)," +
                            "x int," +
                            "y int," +
                            "z int," +
                            "chunk_x int not null," +
                            "chunk_z int not null," +
                            "primary key (world_id, x, y, z)" +
                            "key (chunk_x, chunk_z))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean connect() {
        try {
            Driver.class.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }

        try {
            connection = DriverManager.getConnection(String.format("jdbc:mysql://%s?user=%s&password=%s",url, db, user, password));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void setPreparedStatements() {
        try {
            addMeta = connection.prepareCall("INSERT INTO " + META_TABLE
                                            +" VALUES (?, ?, ?, ?, ?, ?, ?)");

            getMeta = connection.prepareCall("SELECT FROM" + META_TABLE + " " +
                                             "WHERE world_id=? AND " +
                                             "x=? AND " +
                                             "y=? AND " +
                                             "z=?");

            deleteMeta = connection.prepareCall("DELETE FROM" + META_TABLE + " " +
                                                "WHERE world_id=? AND " +
                                                "x=? AND " +
                                                "y=? AND " +
                                                "z=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void insertBlock(Location location, String meta) {
        connect();
        setPreparedStatements();
        try {
            addMeta.setString(1, meta);
            addMeta.setString(2, location.getWorld().getUID().toString());
            addMeta.setInt(3, location.getBlockX());
            addMeta.setInt(4, location.getBlockY());
            addMeta.setInt(5, location.getBlockZ());
            addMeta.setInt(6, location.getChunk().getX());
            addMeta.setInt(7, location.getChunk().getZ());
            addMeta.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getMetaForLocation(Location location){
        connect();
        setPreparedStatements();
        try {
            deleteMeta.setString(1, location.getWorld().getUID().toString());
            deleteMeta.setInt(2, location.getBlockX());
            deleteMeta.setInt(3, location.getBlockY());
            deleteMeta.setInt(4, location.getBlockZ());
            deleteMeta.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
