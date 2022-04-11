package restup.restup;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class RestUp extends JavaPlugin {
    static List<String> invalidItemsList; // this is probably bad...
    static double restPercent; // but I'ma doit again

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("RestUp: loading!");

        // config stuff
        Bukkit.getLogger().info("RestUp: loading invalid items");
        saveDefaultConfig();
        invalidItemsList = this.getConfig().getStringList("invalidItems");
        restPercent = this.getConfig().getDouble("restPercent");
        Bukkit.getLogger().info("RestUp: invalid items:");
        Bukkit.getLogger().info(Arrays.toString(invalidItemsList.toArray()));
        Bukkit.getLogger().info("RestUp: % of players needed to rest:");
        Bukkit.getLogger().info(String.valueOf(restPercent));

        // event stuff
        Bukkit.getPluginManager().registerEvents(new RestEvent(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("RestUp: shutting down!");
    }

    public static List<String> getInvalidItemsList() {
        return invalidItemsList;
    }

    public static double getRestPercent() { return restPercent; }
}
