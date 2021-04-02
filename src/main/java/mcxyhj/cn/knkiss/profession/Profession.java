package mcxyhj.cn.knkiss.profession;

import mcxyhj.cn.knkiss.config.PlayerData;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public abstract class Profession{
    String proName;
    HashMap<UUID, PlayerData> playerList;

    public Profession(String proName){
        this.proName = proName;
        playerList = new HashMap<>();
    }

    public void addPlayer(PlayerData playerData){
        playerList.put(playerData.getPlayerUUID(),playerData);
    }

    public void removePlayer(PlayerData playerData){
        playerList.remove(playerData.getPlayerUUID());
    }

    public abstract void onCommand(Player player, String[] args);
}
