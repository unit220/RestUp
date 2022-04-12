package restup.restup;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import restup.restup.tasks.RemoveRester;

import java.util.ArrayList;

public class RestEvent implements Listener {
//    RestUp plugin = new RestUp();
    PlayerManager playerManager = new PlayerManager();
    ArmorStand armorStand;
    
    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent event) {
        if((event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            Block block = event.getClickedBlock();
            World world = event.getClickedBlock().getWorld();
            Player player = event.getPlayer();

            // See what user is holding
            // Bukkit.getLogger().info(player.getInventory().getItemInMainHand().getType().toString());

            if(block.getType() == Material.CAMPFIRE) {
                // quits out if holding something cookable
                if(RestUp.getInvalidItemsList().contains(player.getInventory().getItemInMainHand().getType().toString())) {
                    return;
                }
                // quits if it is already night
                if(world.getTime() >= 13000) {
                    player.sendMessage("You cannot pass the day while it is night!");
                    return;
                }

                player.sendMessage("You sit down to take a rest");
                // spawn an armor stand to act as an entity for the player to sit on
                armorStand = world.spawn(player.getLocation().add(0,-1.65,0), ArmorStand.class);
                armorStand.setGravity(false); // turn off the grav so it doesn't fall
                armorStand.setVisible(false);
                armorStand.addPassenger(player);
                // Add player to list of players resting while they are sitting
                playerManager.addRester(player);

                // Calc % of people resting
                double percentResting = ((double)playerManager.getRestingPlayers().size()/(double)event.getClickedBlock()
                        .getWorld().getPlayers().size())*100;
                // tell everyone
                for (Player p : event.getClickedBlock().getWorld().getPlayers()) {
                    p.sendMessage(ChatColor.GOLD + "" + percentResting + "% of players are resting at campfires and want to pass the day.");
                }
                // See if % people resting > config's rest %
                if (percentResting >= RestUp.getRestPercent()) {
                    // set the time to night
                    world.setTime(13000); //Warning, I think this breaks days past stats, anyone care? No? Cool.
                    playerManager.clearResters(); // Clean up the array tracking % resting
                }
            }
        }
    }

    @EventHandler
    public void PlayerToggleSneakEvent(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        // if the player isn't resting, ignore the event
        if(!playerManager.getRestingPlayers().contains(player)) return;
        playerManager.removeRester(player);
        armorStand.remove(); // clean up stand
        Bukkit.getLogger().info("[RestUp] removed player from restingPlayers");
    }
}
