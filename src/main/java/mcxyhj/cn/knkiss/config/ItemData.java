package mcxyhj.cn.knkiss.config;

import mcxyhj.cn.knkiss.Manager;
import mcxyhj.cn.knkiss.Utils;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class ItemData {
    private static final HashMap<String,ItemStack> itemStackHashMap = new HashMap<>();

    public static final ItemStack nextPage = new ItemStack(Material.PAPER,1);
    public static final ItemStack lastPage = new ItemStack(Material.PAPER,1);

    public static void loadItemData(){
        Utils.setNameAndLore(nextPage,"§7下一页","§8点击翻到下一页");
        Utils.setNameAndLore(lastPage,"§7上一页","§8点击翻到上一页");

        ConfigManager.configMap.get("item").getKeys(false).forEach(key -> {
            try {
                ItemStack itemStack;
                Material type = Material.getMaterial(Objects.requireNonNull(ConfigManager.configMap.get("item").getString(key + ".type")));
                assert type != null;
                itemStack = new ItemStack(type,1);
                ItemMeta itemMeta = itemStack.getItemMeta();
                String name = Objects.requireNonNull(ConfigManager.configMap.get("item").getString(key + ".name")).replace("&","§");
                List<String> loreList = new ArrayList<>();
                Utils.getStringList(ConfigManager.configMap.get("item").get(key+".lore")).forEach(lore -> {
                    loreList.add(lore.replace("&","§"));
                });
                assert itemMeta != null;
                itemMeta.setDisplayName(name);
                itemMeta.setLore(loreList);
                itemStack.setItemMeta(itemMeta);

                //附魔
                if(ConfigManager.configMap.get("item").contains(key+".enchant")){
                    try{
                        Enchantment enchantmentType = Enchantment.getByKey(NamespacedKey.minecraft(Objects.requireNonNull(ConfigManager.configMap.get("item").getString(key + ".enchant")).toLowerCase()));
                        assert enchantmentType != null;
                        itemStack.addUnsafeEnchantment(enchantmentType,ConfigManager.configMap.get("item").getInt(key+".enchant_level"));
                    }catch (Exception e){
                        Utils.warning("item.yml中"+key+"的附魔无法读取");
                    }
                }
                //药水效果
                if(ConfigManager.configMap.get("item").contains(key+".potion")){
                    try{
                        PotionEffectType potionEffectType = PotionEffectType.getByName(Objects.requireNonNull(ConfigManager.configMap.get("item").getString(key + ".potion")));
                        int potionEffectTime = ConfigManager.configMap.get("item").getInt(key + ".potion_time");
                        int potionEffectLevel = ConfigManager.configMap.get("item").getInt(key + ".potion_level");
                        assert potionEffectType != null;
                        PotionEffect potionEffect = new PotionEffect(potionEffectType,potionEffectTime,potionEffectLevel-1);

                        PotionMeta meta = (PotionMeta) itemStack.getItemMeta();
                        assert meta != null;
                        meta.addCustomEffect(potionEffect,false);
                        itemStack.setItemMeta(meta);

                    }catch (Exception e){
                        Utils.warning("item.yml中"+key+"的药水无法读取");
                    }
                }

                //添加物品
                itemStackHashMap.put(key,itemStack);
            }catch (Exception e){
                Utils.warning("item.yml中"+key+"物品无法读取\n");
            }
        });
    }

    public static ItemStack getItemStack(String name){
        return itemStackHashMap.getOrDefault(name, null);
    }

    public static HashMap<String,ItemStack> getItemStackHashMap(){
        return itemStackHashMap;
    }

    public static void debug(CommandSender sender){
        if(sender instanceof Player){
            List<ItemStack> list = new ArrayList<>();
            ItemData.itemStackHashMap.forEach((s, itemStack) -> {
                list.add(itemStack);
            });
            Utils.addItem((Player) sender,list);
        }

        itemStackHashMap.forEach((s, itemStack) -> {
            sender.sendMessage("[ItemData]"+s+".Type:"+itemStack.getType() + "    Name:"+ Objects.requireNonNull(itemStack.getItemMeta()).getDisplayName());
            sender.sendMessage("[ItemData]"+s+".Amount:"+itemStack.getAmount());
            Objects.requireNonNull(itemStack.getItemMeta().getLore()).forEach(lore ->{
                sender.sendMessage("[ItemData]"+s+".Lore:"+lore);
            });
            if(!itemStack.getEnchantments().isEmpty()){
                sender.sendMessage("[ItemData]"+s+".Enchantments:"+itemStack.getEnchantments());
            }
            sender.sendMessage("");
        });
    }
}
