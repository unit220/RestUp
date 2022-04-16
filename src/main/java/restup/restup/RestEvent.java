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
import org.spigotmc.event.entity.EntityDismountEvent;
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

            if(block.getType() == Material.CAMPFIRE) {
                // quits out if already sitting
                if(player.getVehicle() instanceof ArmorStand) { return; }
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
                //player.sendMessage("You are in a " + player.getVehicle());
                // Add player to list of players resting while they are sitting
                playerManager.addRester(player);

                // Calc % of people resting
                double percentResting = ((double)playerManager.getRestingPlayers().size()/(double)event.getClickedBlock()
                        .getWorld().getPlayers().size())*100;
                // Calc total # of people needed to rest in a given world
                int totalNeedToRest = (int) RestUp.getRestPercent() * (int) event.getClickedBlock().getWorld().getPlayers().size();
                // Calc remaining # of people needed to rest in a given world
                int remainingNeedToRest = totalNeedToRest - playerManager.getRestingPlayers().size();
                // tell everyone
                for (Player p : event.getClickedBlock().getWorld().getPlayers()) {
                    p.sendMessage(ChatColor.YELLOW + "" + player.getDisplayName() + " is resting at a campfire (" + playerManager.getRestingPlayers().size()
                            + "/" + totalNeedToRest + ", " +remainingNeedToRest+ " more needed to pass the day).");
                }
                // See if % people resting > config's rest %
                if (percentResting >= RestUp.getRestPercent()) {
                    // set the time to night
                    world.setTime(13000); //Warning, I think this breaks days past stats, anyone care? No? Cool.
                    playerManager.clearResters(); // Clean up the array tracking % resting
                    armorStand.remove(); // clean up stand
                }
            }
        }
    }

    //TODO this is probably better to use but is broken fix later
//    @EventHandler
//    public void onDismount(EntityDismountEvent event) {
//        if(event.getEntity() instanceof Player)
//        Player player = event.getEntity();
//        if (!(event.getDismounted() instanceof ArmorStand)) return;
//        armorStand.remove(); // clean up stand
//        if(playerManager.getRestingPlayers().contains(player)) playerManager.removeRester(player);
//        Bukkit.getLogger().info("[RestUp] removed player from restingPlayers");
//    }

    @EventHandler
    public void PlayerToggleSneakEvent(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        // if player isn't sitting on stand, ignore the event
        // this will never work right because the player gets up and thus their vehicle is null
        // TODO add replacement so we aren't running all this every sneakevent!!!!
//        if(!(player.getVehicle().toString().equals("CraftArmorStand"))) {
//            return;
//        }
        //player.sendMessage("You stand up");
        armorStand.remove(); // clean up stand
        // remove player from list of resting players TODO move error checking to PlayerManager
        if(playerManager.getRestingPlayers().contains(player)) playerManager.removeRester(player);
        Bukkit.getLogger().info("[RestUp] removed player from restingPlayers");
    }
}
