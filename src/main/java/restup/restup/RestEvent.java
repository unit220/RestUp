package restup.restup;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class RestEvent implements Listener {
    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent event) {
        if((event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            Block block = event.getClickedBlock();
            World world = event.getClickedBlock().getWorld();
            Player player = event.getPlayer();

            // See what user is holding
            Bukkit.getLogger().info(player.getInventory().getItemInMainHand().getType().toString());

            if(block.getType() == Material.CAMPFIRE) {
                // quits out if holding something
                if(RestUp.getInvalidItemsList().contains(player.getInventory().getItemInMainHand().getType().toString())) {
                    return;
                }
                // quits if it is already night
                if(world.getTime() >= 13000) {
                    player.sendMessage("You cannot pass the day while it is night!");
                    return;
                }
                // TODO check against a list of invalid items instead
//                if(player.getInventory().getItemInMainHand().getType() != Material.PORKCHOP) {
//                    player.sendMessage("You cannot rest with stuff in hand, relax!");
//                    return;
//                }

                // spawn an arrow to act as an entity for the player to sit on
                Entity arrow = world.spawnArrow(player.getLocation().add(0,-0.5D,0),new Vector(0,90,0),0,0);
                arrow.addPassenger(player);
                // TODO add player to list of players resting while they are sitting

                // set the time to night
                world.setTime(13000); //Warning, I think this breaks days past stats, anyone care? No? Cool.
            }
        }

    }
}
