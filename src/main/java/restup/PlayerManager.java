package restup;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PlayerManager {
    ArrayList<Player> restingPlayers;

    public PlayerManager() { this.restingPlayers = new ArrayList<Player>(); }

    public void addRester(Player player) {
        if (this.restingPlayers.contains(player)) { return; }
        this.restingPlayers.add(player);
    }

    public void removeRester(Player player) { this.restingPlayers.remove(player); }

    public ArrayList<Player> getRestingPlayers() { return this.restingPlayers; }

    public void clearResters() { this.restingPlayers.clear(); }
}
