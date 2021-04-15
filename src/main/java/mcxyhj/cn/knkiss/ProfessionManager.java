package mcxyhj.cn.knkiss;

import mcxyhj.cn.knkiss.config.PlayerData;
import mcxyhj.cn.knkiss.config.ProfessionData;
import mcxyhj.cn.knkiss.template.infoMapRender;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapView;

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

    //展示玩家个人信息
    public static void showInfoToPlayer(Player player){
        PlayerData pd = getPlayer(player.getName());
        player.sendMessage(pd.name+"的个人信息");
        player.sendMessage("职业:"+ProfessionData.professionMap.get(pd.profession).proName);
        player.sendMessage("等级:"+pd.level);
        player.sendMessage("经验:"+pd.exp);
        player.sendMessage("可重选:"+(pd.change?"是":"否"));
        //TODO 可视化给玩家看
    }

    //选择职业的Gui
    public static void selectProfessionGui(Player player){
        player.openInventory(ProfessionData.professionGui);
    }
}
