package restup.restup;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class RestEvent implements Listener {
    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent event) {
        if((event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            Block block = event.getClickedBlock();
            World world = event.getClickedBlock().getWorld();
            Player player = event.getPlayer();

            if(block.getType() == Material.CAMPFIRE) {
                // quits if it is already night
                if(world.getTime() >= 13000) {
                    player.sendMessage("You cannot pass the day while it is night!");
                    return;
                }
                // quits out if holding something
                // TODO check against a list of invalid items
                if(player.getInventory().getItemInMainHand().getType() != Material.AIR) {
                    player.sendMessage("You cannot rest with stuff in hand, relax!");
                    return;
                }
                world.setTime(13000); //Warning, I think this breaks days past stats, anyone care? No? Cool.
            }
        }

    }
}
