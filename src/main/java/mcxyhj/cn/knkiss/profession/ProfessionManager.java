package mcxyhj.cn.knkiss.profession;

import mcxyhj.cn.knkiss.config.PlayerData;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProfessionManager {

    private static HashMap<String,String> playerProfession = new HashMap<>();
    private static HashMap<String,Profession> professionHashMap = new HashMap<>();

    public static void loadOnEnable(){
        professionHashMap.put("Miner",new Miner());
        professionHashMap.put("Enchanter",new Enchanter());
    }

    /*
    public void excuteCommand(Player player, String[] args){
        if(!playerProfession.containsKey(player.getName()) && args.length>=2 && args[0].equalsIgnoreCase("select")){
            if(checkList(args[1].toLowerCase()))
                Objects.requireNonNull(getProfession(args[1].toLowerCase())).addPlayer(createNewData(player,args[1].toLowerCase()));
            else player.sendMessage("Please select a right profession");
            return;
        }
        if(!playerProfession.containsKey(player.getName()) && args.length>=2 && args[0].equalsIgnoreCase("change")){
            if(checkList(args[1].toLowerCase()));
                //TODO 切换职业
            else player.sendMessage("Please select a right profession");
            return;
        }

        Objects.requireNonNull(getProfession(playerProfession.get(player.getName()))).onCommand(player,args);
    }*/

    //添加玩家服务器重载时
    public static void addPlayer(PlayerData playerData){
        professionHashMap.get(playerData.profession).addPlayer(playerData);
    }

    //获得所有playerData
    public static HashMap<String,PlayerData> getPlayerDataList(){
        HashMap<String,PlayerData> playerDataList = new HashMap<>();
        professionHashMap.forEach(((string, profession) -> {
            playerDataList.putAll(profession.getPlayerList());
        }));
        return playerDataList;
    }
}
