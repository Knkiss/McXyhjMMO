package mcxyhj.cn.knkiss.config;

import mcxyhj.cn.knkiss.Manager;
import mcxyhj.cn.knkiss.Utils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

/*  储存插件用到的所有特殊物品，此文件应该读取配置文件后自动生成！
*
* */

public class ItemData {
    public static HashMap<String,ItemStack> itemStackHashMap = new HashMap<>();
    public static ItemStack nextPage = new ItemStack(Material.PAPER,1);
    public static ItemStack lastPage = new ItemStack(Material.PAPER,1);

    public static void loadItemData(){
        Utils.setNameAndLore(nextPage,"§1下一页","§6点击翻到下一页");
        Utils.setNameAndLore(lastPage,"§1上一页","§6点击翻到上一页");

        ConfigManager.configMap.get("item").getKeys(false).forEach(key -> {
            try {
                ItemStack itemStack;
                Material type = Material.getMaterial(Objects.requireNonNull(ConfigManager.configMap.get("item").getString(key + ".type")));
                assert type != null;
                itemStack = new ItemStack(type,1);
                ItemMeta itemMeta = itemStack.getItemMeta();
                String name = Objects.requireNonNull(ConfigManager.configMap.get("item").getString(key + ".name")).replace("&","§");
                List<String> loreList = Utils.getStringList(ConfigManager.configMap.get("item").get(key+".lore"));
                assert itemMeta != null;
                itemMeta.setDisplayName(name);
                itemMeta.setLore(loreList);
                itemStack.setItemMeta(itemMeta);

                //附魔
                if(ConfigManager.configMap.get("item").contains(key+".enchant")){
                    Enchantment enchantmentType = Enchantment.getByName(ConfigManager.configMap.get("item").getString(key+".enchant"));
                    assert enchantmentType != null;
                    itemStack.addUnsafeEnchantment(enchantmentType,ConfigManager.configMap.get("item").getInt(key+".enchant_level"));
                }

                //添加物品
                itemStackHashMap.put(key,itemStack);

            }catch (Exception e){
                Manager.logger.warning("item.yml中"+key+"物品无法读取\n"+ e);
            }
        });
    }

    public static ItemStack getItemStack(String name){
        return itemStackHashMap.getOrDefault(name, null);
    }
}
