package mcxyhj.cn.knkiss.profession;

import mcxyhj.cn.knkiss.Manager;
import mcxyhj.cn.knkiss.config.buttonClass;
import mcxyhj.cn.knkiss.config.ItemData;
import mcxyhj.cn.knkiss.config.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public abstract class Profession implements Listener {
    public final String proName;
    public final HashMap<String, PlayerData> playerList;
    public List<Inventory> guiList = new LinkedList<>();
    public HashMap<Integer, buttonClass> guiDataHashMap = new HashMap<>();
    int guiMax;

    public Profession(String proName,int guiNumber){
        this.proName = proName;
        this.guiMax = guiNumber-1;
        playerList = new HashMap<>();
        createGUI(guiNumber);
        initGUI();
        Bukkit.getPluginManager().registerEvents(this, Manager.plugin);
    }

    //根据不同职业创建不同数量的gui
    private void createGUI(int guiNumber){
        for(int i=0;i<guiNumber;i++){
            Inventory gui = Bukkit.createInventory(null,54,proName+" 第"+(i+1)+"页");
            if(i!=0)gui.setItem(45, ItemData.lastPage);
            if(i<guiNumber-1)gui.setItem(53, ItemData.nextPage);
            guiList.add(gui);
        }
    }

    //将按钮添加到对应gui中，若重复会被替换
    public void setItem(buttonClass buttonClass, int selectGUI, int hang, int lie){
        if(selectGUI-1>guiMax||selectGUI-1<0)return;
        if(hang>5||hang<0)return;
        if(lie>9||lie<0)return;
        int slot = (hang-1)*9 + (lie-1);
        guiList.get(selectGUI-1).setItem(slot, buttonClass.icon);
        int id = selectGUI*100+(hang-1)*9+(lie-1);
        if(guiDataHashMap.containsKey(id))guiDataHashMap.replace(id, buttonClass);
        else guiDataHashMap.put(id, buttonClass);
    }


    @EventHandler
    public void onInteractGui(InventoryClickEvent e){
        if(checkGui(e))return;
        Player player = (Player)e.getWhoClicked();
        int slot = e.getSlot();

        for(int i = 0;i<=this.guiMax;i++){
            if(e.getInventory().equals(guiList.get(i))){
                guiDataHashMap.get((i+1)*100+slot).onClick(player);
            }
        }
    }

    //上边的函数调用此函数进行检测
    public boolean checkGui(InventoryClickEvent e){
        if(guiList.contains(e.getInventory()))e.setCancelled(true);
        if(e.getCurrentItem() == null) return true;
        Player player = (Player)e.getWhoClicked();
        int slot = e.getRawSlot();

        if(slot == 45){
            openGUI(player, Math.max(guiList.indexOf(e.getInventory()) - 1, 0));
            return true;
        }else if(slot == 53){
            openGUI(player,guiList.indexOf(e.getInventory())+1);
            return true;
        }else return slot > 53;
    }

    //添加经验值
    public void addExp(String name,int number){
        playerList.get(name).addExp(number);
    }

    //是否存在玩家
    public boolean hasPlayer(String name){
        return playerList.containsKey(name);
    }

    //获取玩家列表
    public HashMap<String, PlayerData> getPlayerList(){
        return playerList;
    }

    //添加玩家
    public void addPlayer(PlayerData playerData){
        playerList.put(playerData.name,playerData);
    }

    //获取玩家数据
    public PlayerData getPlayerData(String name){
        return playerList.get(name);
    }

    //移除玩家数据并反回
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

    //添加物品到gui中
    public abstract void initGUI();
}
