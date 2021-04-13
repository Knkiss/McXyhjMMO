package mcxyhj.cn.knkiss.config;

import mcxyhj.cn.knkiss.Manager;
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
        setNameAndLore(nextPage,"§1下一页","§6点击翻到下一页");
        setNameAndLore(lastPage,"§1上一页","§6点击翻到上一页");

        ConfigManager.configMap.get("item").getKeys(false).forEach(key -> {
            try {
                //常规内容
                ItemStack itemStack;
                Material type = Material.getMaterial(Objects.requireNonNull(ConfigManager.configMap.get("item").getString(key + ".type")));
                assert type != null;
                itemStack = new ItemStack(type,1);

                ItemMeta itemMeta = itemStack.getItemMeta();
                String name = ConfigManager.configMap.get("item").getString(key + ".name");
                assert name != null;
                name = name.replace("&","§");

                List<String> loreList = new ArrayList<>();

                Object lore = ConfigManager.configMap.get("item").get(key+".lore");
                if(lore instanceof String){
                    loreList.add(ConfigManager.configMap.get("item").getString(key+".lore"));
                }else{
                    loreList = ConfigManager.configMap.get("item").getStringList(key+".lore");
                }

                assert itemMeta != null;
                itemMeta.setDisplayName(name);
                itemMeta.setLore(loreList);
                itemStack.setItemMeta(itemMeta);

                //附魔
                if(ConfigManager.configMap.get("item").contains(key+".enchant")){
                    Enchantment enchantment = Enchantment.getByName(ConfigManager.configMap.get("item").getString(key+".enchant"));
                    assert enchantment != null;
                    itemStack.addUnsafeEnchantment(enchantment,ConfigManager.configMap.get("item").getInt(key+".enchant_level"));
                }

                //添加物品
                itemStackHashMap.put(key,itemStack);

            }catch (Exception e){
                Manager.logger.warning("item.yml中"+key+"物品无法读取\n"+ e);
            }
        });

        itemStackHashMap.put("xiaolv6",new ItemStack(Material.BOOK,1));
        itemStackHashMap.get("xiaolv6").addUnsafeEnchantment(Enchantment.DIG_SPEED,6);

        itemStackHashMap.put("fengli6",new ItemStack(Material.BOOK,1));
        itemStackHashMap.get("fengli6").addUnsafeEnchantment(Enchantment.DAMAGE_ALL,6);
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
