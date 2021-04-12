package mcxyhj.cn.knkiss.gui;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemData {
    public static ItemStack naijiu4 = new ItemStack(Material.BOOK,1);
    public static ItemStack xiaolv6 = new ItemStack(Material.BOOK,1);
    public static ItemStack fengli6 = new ItemStack(Material.BOOK,1);


    public static ItemStack nextPage = new ItemStack(Material.PAPER,1);
    public static ItemStack lastPage = new ItemStack(Material.PAPER,1);

    public static void initItemStack(){
        setNameAndLore(nextPage,"下一页","点击翻到下一页");
        setNameAndLore(lastPage,"上一页","点击翻到上一页");

        naijiu4.addUnsafeEnchantment(Enchantment.DURABILITY,4);
        xiaolv6.addUnsafeEnchantment(Enchantment.DIG_SPEED,6);
        fengli6.addUnsafeEnchantment(Enchantment.DAMAGE_ALL,6);
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
}
