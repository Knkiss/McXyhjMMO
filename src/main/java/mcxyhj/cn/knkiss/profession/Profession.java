package mcxyhj.cn.knkiss.profession;

import mcxyhj.cn.knkiss.config.PlayerData;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public abstract class Profession{
    private String proName;
    private HashMap<String, PlayerData> playerList;

    public Profession(String proName){
        this.proName = proName;
        playerList = new HashMap<>();
    }

    public HashMap<String, PlayerData> getPlayerList(){
        return playerList;
    }

    public void addPlayer(PlayerData playerData){
        playerList.put(playerData.name,playerData);
    }

    public abstract void onCommand(Player player, String[] args);
}
