package restup.restup;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public final class RestUp extends JavaPlugin {
    static List<String> invalidItemsList; // this is probably bad...

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("RestUp: loading!");

        Bukkit.getLogger().info("RestUp: loading invalid items");
        saveDefaultConfig();
        invalidItemsList = this.getConfig().getStringList("invalidItems");
        Bukkit.getLogger().info("RestUp: invalid items:");
        Bukkit.getLogger().info(Arrays.toString(invalidItemsList.toArray()));

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
}
