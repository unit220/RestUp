package restup.restup;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class RestUp extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("RestUp loading!");

        Bukkit.getPluginManager().registerEvents(new RestEvent(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("RestUp shutting down!");
    }
}
