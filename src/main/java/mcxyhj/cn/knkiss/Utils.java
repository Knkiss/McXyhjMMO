package mcxyhj.cn.knkiss;

import mcxyhj.cn.knkiss.template.infoMapRender;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;

import java.util.*;
import java.util.List;

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

    public static void setNameAndLore(ItemStack item,String name, String lore){
        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(name);
        if(lore != null){
            List<String> loreList = new ArrayList<>();
            loreList.add(lore);
            itemMeta.setLore(loreList);
        }
        item.setItemMeta(itemMeta);
    }

    public static List<String> getStringList(Object object){
        List<String> stringList = new ArrayList<>();
        if(object instanceof String){
            stringList.add(((String) object).replace("&","§"));
        }else if(object instanceof List<?>){
            for (Object o : (List<?>) object) {
                stringList.add(((String) o).replace("&","§"));
            }
        }
        return stringList;
    }

    public static ItemStack createItem(Material material, String displayName, List<String> loreList){
        ItemStack itemStack = new ItemStack(material,1);
        ItemMeta im = itemStack.getItemMeta();
        assert im != null;
        im.setDisplayName(displayName);
        im.setLore(loreList);
        itemStack.setItemMeta(im);
        return itemStack;
    }

    public static void addLore(ItemStack itemStack,String lore){
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        List<String> loreList = itemMeta.getLore();
        if(loreList == null){
            loreList = new ArrayList<>();
        }
        loreList.add(lore);

        itemMeta.setLore(loreList);
        itemStack.setItemMeta(itemMeta);
    }

    //DEBUG USE 用于获取Item的信息方便打印
    public static List<String> getItemInfo(String key,ItemStack itemStack){
        if(itemStack==null)return new ArrayList<>();
        List<String> itemInfo = new ArrayList<>();
        itemInfo.add(key+".Type:"+itemStack.getType());
        itemInfo.add(key+".Amount:"+ itemStack.getAmount());
        if(!Objects.requireNonNull(itemStack.getItemMeta()).getDisplayName().equals("")){
            itemInfo.add(key+".Name:"+ Objects.requireNonNull(itemStack.getItemMeta()).getDisplayName());
        }
        if(itemStack.getItemMeta().getLore() != null){
            itemStack.getItemMeta().getLore().forEach(lore -> itemInfo.add(key+".Lore:"+lore));
        }
        if(!itemStack.getEnchantments().isEmpty()){
            itemInfo.add(key+".Enchantments:"+itemStack.getEnchantments());
        }
        return itemInfo;
    }

    //发送地图
    public static void sendMap(Player player){
        ItemStack map = new ItemStack(Material.FILLED_MAP,1);
        MapMeta mapMeta = (MapMeta) map.getItemMeta();
        assert mapMeta != null;

        MapView mapView = Bukkit.createMap(player.getWorld());
        mapView.getRenderers().clear();
        mapView.addRenderer(new infoMapRender());
        mapMeta.setMapView(mapView);
        mapMeta.setDisplayName(player.getName()+"的个人信息");

        map.setItemMeta(mapMeta);

        if(player.getInventory().getItemInMainHand().getType().isAir()){
            player.getInventory().setItemInMainHand(map);
            player.updateInventory();
        }else{
            player.sendMessage("你的主手已有其他物品");
        }
    }
}
