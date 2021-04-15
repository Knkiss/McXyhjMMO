package mcxyhj.cn.knkiss;

import mcxyhj.cn.knkiss.config.PlayerData;
import mcxyhj.cn.knkiss.config.ProfessionData;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ProfessionManager {

    public static final HashMap<String,String> playerProfession = new HashMap<>();

    public static void giveProExp(String name,int proExp){
        ProfessionData.professionMap.get(playerProfession.get(name)).addExp(name,proExp);
    }

    //打开对应职业类的GUI
    public static void openGUI(Player player){
        ProfessionData.professionMap.get(playerProfession.get(player.getName())).openGUI(player);
    }

    //重置所有playerData的change机会
    public static void reset(){
        ProfessionData.professionMap.forEach((s, profession) -> profession.reset());
    }

    //是否为正确职业
    public static boolean isRightProfession(String professionName){
        return ProfessionData.professionMap.containsKey(professionName.toLowerCase());
    }

    //玩家是否有数据
    public static boolean hasPlayer(String name){
        return ProfessionManager.playerProfession.containsKey(name);
    }

    //获取玩家数据
    public static PlayerData getPlayer(String name){
        return ProfessionData.professionMap.get(playerProfession.get(name)).getPlayerData(name);
    }

    //添加玩家
    public static void addPlayer(PlayerData playerData){
        ProfessionData.professionMap.get(playerData.profession).addPlayer(playerData);
        playerProfession.put(playerData.name,playerData.profession);
    }

    //移除玩家数据
    public static PlayerData removePlayer(String name){
        String profession = playerProfession.get(name);
        playerProfession.remove(name);
        return ProfessionData.professionMap.get(profession).removePlayerData(name);
    }

    //ConfigManager获得所有playerData
    public static HashMap<String,PlayerData> getPlayerDataList(){
        HashMap<String,PlayerData> playerDataList = new HashMap<>();
        ProfessionData.professionMap.forEach(((string, profession) -> playerDataList.putAll(profession.getPlayerList())));
        return playerDataList;
    }
}
