package restup.tasks;

import org.bukkit.scheduler.BukkitRunnable;
import restup.RestUp;

public class RemoveRester extends BukkitRunnable {

     RestUp plugin;

    public RemoveRester(RestUp plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        System.out.println("TASK HAS BEEN RUN!");
    }
}
