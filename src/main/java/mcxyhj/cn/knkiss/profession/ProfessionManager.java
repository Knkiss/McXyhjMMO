package mcxyhj.cn.knkiss.profession;

import mcxyhj.cn.knkiss.Manager;
import mcxyhj.cn.knkiss.config.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class ProfessionManager {

    public Miner miner;
    public Enchanter enchanter;
    HashMap<UUID,String> playerProfession = new HashMap<>();

    public ProfessionManager(){
        miner = new Miner();
        enchanter = new Enchanter();
        Bukkit.getPluginManager().registerEvents(miner, Manager.plugin);
        Bukkit.getPluginManager().registerEvents(enchanter, Manager.plugin);
    }

    public void excuteCommand(Player player, String[] args){
        if(!playerProfession.containsKey(player.getUniqueId()) && args.length>=2 && args[0].equalsIgnoreCase("select")){
            if(checkList(args[1].toLowerCase()))
                Objects.requireNonNull(getProfessionClass(args[1].toLowerCase())).addPlayer(createNewData(player,args[1].toLowerCase()));
            else player.sendMessage("Please select a right profession");
            return;
        }
        if(!playerProfession.containsKey(player.getUniqueId()) && args.length>=2 && args[0].equalsIgnoreCase("change")){
            if(checkList(args[1].toLowerCase()));
                //TODO 切换职业
            else player.sendMessage("Please select a right profession");
            return;
        }

        Objects.requireNonNull(getProfessionClass(playerProfession.get(player.getUniqueId()))).onCommand(player,args);
    }

    private PlayerData createNewData(Player player,String profession){
        playerProfession.put(player.getUniqueId(),profession);
        return new PlayerData(player.getName(),0,0,true,profession,player.getUniqueId());
    }

    private Profession getProfessionClass(String profession){
        if(profession.equalsIgnoreCase("miner")){
            return miner;
        }else if(profession.equalsIgnoreCase("enchanter")){
            return enchanter;
        }
        return null;
    }

    private boolean checkList(String profession){
        return profession.equalsIgnoreCase("miner") || profession.equalsIgnoreCase("enchanter");
    }
}
