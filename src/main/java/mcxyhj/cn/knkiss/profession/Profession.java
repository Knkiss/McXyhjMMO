package mcxyhj.cn.knkiss.profession;

import mcxyhj.cn.knkiss.Manager;
import mcxyhj.cn.knkiss.assests.itemStackData;
import mcxyhj.cn.knkiss.config.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public abstract class Profession implements Listener {
    public final String proName;
    public final HashMap<String, PlayerData> playerList;
    public List<Inventory> guiList = new LinkedList<>();
    private int slot = 0,gui = 0,guiMax;

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
            if(i!=0)gui.setItem(45,itemStackData.lastPage);
            if(i<guiNumber-1)gui.setItem(53,itemStackData.nextPage);
            guiList.add(gui);
        }
    }

    //不按顺序直接添加物品 和setItem冲突
    public void addItem(ItemStack itemStack){
        if(gui>guiMax) return;
        guiList.get(gui).setItem(slot,itemStack);
        slot++;
        if(slot==45){
            slot = 0;
            gui ++;
        }
    }

    //指定位置物品，和addItem冲突 selectgui>0
    public void setItem(ItemStack itemStack,int selectGUI,int hang,int lie){
        if(selectGUI-1>guiMax||selectGUI-1<0)return;
        if(hang>5||hang<0)return;
        if(lie>9||lie<0)return;
        int slot = (hang-1)*9 + (lie-1);
        guiList.get(selectGUI-1).setItem(slot,itemStack);
    }

    //子类调用此函数进行统一检测
    public boolean checkGui(InventoryClickEvent e){
        if(guiList.contains(e.getInventory()))e.setCancelled(true);
        if(e.getCurrentItem() == null) return true;
        Player player = (Player)e.getWhoClicked();
        int slot = e.getSlot();

        if(slot == 45){
            openGUI(player,guiList.indexOf(e.getInventory())-1);
            return true;
        }else if(slot == 53){
            openGUI(player,guiList.indexOf(e.getInventory())+1);
            return true;
        }
        return false;
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
        playerList.forEach((s, playerData) -> {
            playerData.change = true;
        });
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
