package vg.civcraft.mc.BlockMeta;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import vg.civcraft.mc.BlockMeta.storage.Database;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by isaac on 2/17/2015.
 */
public class BlockMetaPlugin extends JavaPlugin {
    protected static Database db;
    protected static BlockMetaManager manager;
    protected static Logger logger;

    private FileConfiguration config;

    public void onLoad() {

        config = getConfig();
        config.options().copyDefaults(true);

        try {
            config.load(new File(getDataFolder()+File.pathSeparator + "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }

        logger = getLogger();
        db = new Database(config.getString("mysql.url","localhost:3306"),
                config.getString("mysql.db","bukkit"),
                config.getString("mysql.prefix",""),
                config.getString("mysql.user", ""),
                config.getString("mysql.password", ""));
        manager = new BlockMetaManager();
        Bukkit.getPluginManager().registerEvents(manager, this);
    }

    public static BlockMetaManager getManager() {
        return manager;
    }
}
