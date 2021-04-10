package mcxyhj.cn.knkiss.assests;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class itemStackData {
    public static ItemStack naijiu6 = new ItemStack(Material.BOOK,1);
    public static ItemStack naijiu7 = new ItemStack(Material.BOOK,1);
    public static ItemStack naijiu8 = new ItemStack(Material.BOOK,1);
    public static ItemStack naijiu9 = new ItemStack(Material.BOOK,1);
    public static ItemStack naijiu10 = new ItemStack(Material.BOOK,1);
    public static ItemStack xiaolv6 = new ItemStack(Material.BOOK,1);
    public static ItemStack xiaolv7 = new ItemStack(Material.BOOK,1);
    public static ItemStack xiaolv8 = new ItemStack(Material.BOOK,1);
    public static ItemStack xiaolv9 = new ItemStack(Material.BOOK,1);
    public static ItemStack xiaolv10 = new ItemStack(Material.BOOK,1);

    public static ItemStack nextPage = new ItemStack(Material.PAPER,1);
    public static ItemStack lastPage = new ItemStack(Material.PAPER,1);

    public static void initItemStack(){
        setNameAndLore(nextPage,"下一页","点击翻到下一页");
        setNameAndLore(lastPage,"上一页","点击翻到上一页");

        naijiu6.addUnsafeEnchantment(Enchantment.DURABILITY,6);
        naijiu7.addUnsafeEnchantment(Enchantment.DURABILITY,7);
        naijiu8.addUnsafeEnchantment(Enchantment.DURABILITY,8);
        naijiu9.addUnsafeEnchantment(Enchantment.DURABILITY,9);
        naijiu10.addUnsafeEnchantment(Enchantment.DURABILITY,10);
        xiaolv6.addUnsafeEnchantment(Enchantment.DIG_SPEED,6);
        xiaolv7.addUnsafeEnchantment(Enchantment.DIG_SPEED,7);
        xiaolv8.addUnsafeEnchantment(Enchantment.DIG_SPEED,8);
        xiaolv9.addUnsafeEnchantment(Enchantment.DIG_SPEED,9);
        xiaolv10.addUnsafeEnchantment(Enchantment.DIG_SPEED,10);

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
