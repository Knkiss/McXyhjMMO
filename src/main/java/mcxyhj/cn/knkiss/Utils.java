package mcxyhj.cn.knkiss;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;

public class Utils {
    public static boolean removeItem(Player player, List<ItemStack> itemStackList){
        if(!checkItem(player, itemStackList)){
            player.sendMessage("请将物品拆分为准确数量后再次点击");
            return false;
        }
        PlayerInventory inv = player.getInventory();
        for (ItemStack itemStack: itemStackList){
            inv.setItem(inv.first(itemStack),null);
        }
        player.updateInventory();
        return true;
    }

    public static void addItem(Player player,List<ItemStack> itemStackList){
        ItemStack[] itemStacks = new ItemStack[itemStackList.size()];
        for(int i=0;i<itemStackList.size();i++){
            itemStacks[i] = itemStackList.get(i);
        }
        HashMap<Integer, ItemStack> itemList = player.getInventory().addItem(itemStacks);
        if(!itemList.isEmpty()){
            player.sendMessage("背包空间不足，已掉落于地面上");
            itemList.forEach((integer, itemStack) -> player.getWorld().dropItem(player.getLocation(),itemStack));
        }
        player.updateInventory();
    }

    public static boolean checkItem(Player player, List<ItemStack> itemStackList){
        PlayerInventory inv = player.getInventory();
        for (ItemStack itemStack: itemStackList){
            if(!inv.contains(itemStack)){
                player.sendMessage(itemStack.toString());
                return false;
            }
        }
        return true;
    }
}
