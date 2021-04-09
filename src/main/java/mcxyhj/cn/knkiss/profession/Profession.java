package mcxyhj.cn.knkiss.profession;

import com.google.common.util.concurrent.ListenableFuture;
import mcxyhj.cn.knkiss.Manager;
import mcxyhj.cn.knkiss.config.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public abstract class Profession implements Listener {
    public final String proName;
    public final HashMap<String, PlayerData> playerList;

    public Profession(String proName){
        this.proName = proName;
        playerList = new HashMap<>();
        Bukkit.getPluginManager().registerEvents(this, Manager.plugin);
    }

    public void addExp(String name,int number){
        playerList.get(name).addExp(number);
    }

    public boolean hasPlayer(String name){
        return playerList.containsKey(name);
    }

    public HashMap<String, PlayerData> getPlayerList(){
        return playerList;
    }

    public void addPlayer(PlayerData playerData){
        playerList.put(playerData.name,playerData);
    }

    public PlayerData getPlayerData(String name){
        return playerList.get(name);
    }

    public PlayerData removePlayerData(String name){
        PlayerData playerData =  playerList.get(name);
        playerList.remove(name);
        return playerData;
    }

    public void reset(){
        playerList.forEach((s, playerData) -> {
            playerData.change = true;
        });
    }

    public abstract void onCommand(Player player, String[] args);
}
