package mcxyhj.cn.knkiss.template;

import mcxyhj.cn.knkiss.Manager;
import mcxyhj.cn.knkiss.config.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.List;

public class Profession implements Listener {
    public String proName;
    public List<String> info;
    public List<Inventory> guiList;
    public HashMap<Integer, Button> guiButtonMap;

    public HashMap<String, PlayerData> playerList = new HashMap<>();

    //Profession构造函数，传必须参数
    public Profession(String proName,List<String> info,List<Inventory> guiList,HashMap<Integer, Button> guiButtonMap){
        this.proName = proName;
        this.info = info;
        this.guiList = guiList;
        this.guiButtonMap = guiButtonMap;
        Bukkit.getPluginManager().registerEvents(this, Manager.plugin);
    }

    //点击本类中的表的有效按钮时触发
    @EventHandler
    public void onInteractGui(InventoryClickEvent e){
        if(!guiList.contains(e.getInventory()))return;
        if(e.getCurrentItem() == null) return;
        e.setCancelled(true);

        Player player = (Player)e.getWhoClicked();
        int slot = e.getRawSlot();

        if(slot == 45){
            openGUI(player, Math.max(guiList.indexOf(e.getInventory()) - 1, 0));
            return;
        }else if(slot == 53) {
            openGUI(player, guiList.indexOf(e.getInventory()) + 1);
            return;
        }else if(slot > 53){
            return;
        }
        guiButtonMap.get((guiList.indexOf(e.getInventory())+1)*100+slot).onClick(player);
    }

    //添加经验值 name=玩家名 number=经验值
    public void addExp(String name,int number){
        playerList.get(name).addExp(number);
    }

    //获取玩家列表
    public HashMap<String, PlayerData> getPlayerList(){
        return playerList;
    }

    //添加玩家
    public void addPlayer(PlayerData playerData){
        playerList.put(playerData.name,playerData);
    }

    //获取玩家数据 name=玩家名
    public PlayerData getPlayerData(String name){
        return playerList.get(name);
    }

    //移除玩家数据并反回 name=玩家名
    public PlayerData removePlayerData(String name){
        PlayerData playerData =  playerList.get(name);
        playerList.remove(name);
        return playerData;
    }

    //重置change机会
    public void reset(){
        playerList.forEach((s, playerData) -> playerData.change = true);
    }

    //打开gui
    public void openGUI(Player player){
        this.openGUI(player,0);
    }
    public void openGUI(Player player,int guiNumber){
        player.openInventory(guiList.get(guiNumber));
    }
}
