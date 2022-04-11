package restup.restup;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PlayerManager {
    ArrayList<Player> restingPlayers = new ArrayList<Player>();

    public void addRester(Player player) {
        restingPlayers.add(player);
    }

    public void removeRester(Player player) {
        restingPlayers.remove(player);
    }

    public ArrayList<Player> getRestingPlayersArray() { return restingPlayers; }

    public void clearResters() { restingPlayers.clear(); }
}
