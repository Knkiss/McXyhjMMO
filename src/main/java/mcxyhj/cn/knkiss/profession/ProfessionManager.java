package mcxyhj.cn.knkiss.profession;

import mcxyhj.cn.knkiss.config.PlayerData;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ProfessionManager {

    public static final HashMap<String,String> playerProfession = new HashMap<>();
    public static final HashMap<String,Profession> professionHashMap = new HashMap<>();

    public static void loadOnEnable(){
        professionHashMap.put("miner",new Miner());
        professionHashMap.put("enchanter",new Enchanter());
    }


    public static void excuteCommand(Player player, String[] args){
        /*
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
         */
    }

    //是否为正确职业
    public static boolean isRightProfession(String professionName){
        return professionHashMap.containsKey(professionName.toLowerCase());
    }

    //玩家是否有数据
    public static boolean hasPlayer(String name){
        return ProfessionManager.playerProfession.containsKey(name);
    }

    //获取玩家数据
    public static PlayerData getPlayer(String name){
        return professionHashMap.get(playerProfession.get(name)).getPlayerData(name);
    }

    //添加玩家
    public static void addPlayer(PlayerData playerData){
        professionHashMap.get(playerData.profession).addPlayer(playerData);
        playerProfession.put(playerData.name,playerData.profession);
    }

    //移除玩家数据
    public static PlayerData removePlayer(String name){
        String profession = playerProfession.get(name);
        playerProfession.remove(name);
        return professionHashMap.get(profession).removePlayerData(name);
    }

    //ConfigManager获得所有playerData
    public static HashMap<String,PlayerData> getPlayerDataList(){
        HashMap<String,PlayerData> playerDataList = new HashMap<>();
        professionHashMap.forEach(((string, profession) -> {
            playerDataList.putAll(profession.getPlayerList());
        }));
        return playerDataList;
    }
}
