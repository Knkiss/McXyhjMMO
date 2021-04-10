package mcxyhj.cn.knkiss.profession;

import mcxyhj.cn.knkiss.Utils;
import mcxyhj.cn.knkiss.assests.itemStackData;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Enchanter extends Profession implements Listener {
    public Enchanter() {
        super("附魔师",4);
    }

    @Override
    public void initGUI() {
        for(int i=0;i<25;i++){
            addItem(itemStackData.naijiu6);
            addItem(itemStackData.xiaolv6);
        }
        setItem(itemStackData.xiaolv6,4,3,3);
    }

    @EventHandler
    public void onInteractGui(InventoryClickEvent e){
        if(super.checkGui(e))return;
        Player player = (Player)e.getWhoClicked();
        int slot = e.getSlot();
        List<ItemStack> hasList = new ArrayList<>();
        List<ItemStack> giveList = new ArrayList<>();
        if(e.getInventory().equals(guiList.get(0))){
            if(slot == 0){

                hasList.add(new ItemStack(Material.STONE,5));
                if(Utils.removeItem(player,hasList)){
                    giveList.add(new ItemStack(Material.DIAMOND_BLOCK,64));
                    giveList.add(new ItemStack(Material.GOLD_BLOCK,16));
                    giveList.add(new ItemStack(Material.IRON_BLOCK,4));
                    Utils.addItem(player,giveList);
                }


            }else if(slot == 1){
                player.sendMessage("you click 1");
            }
        }else if(e.getInventory().equals(guiList.get(1))){
            if(slot == 0){
                player.sendMessage("you click 0");
            }else if(slot == 1){
                player.sendMessage("you click 1");
            }
        }
    }
}
