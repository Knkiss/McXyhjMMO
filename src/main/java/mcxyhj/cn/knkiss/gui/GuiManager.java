package mcxyhj.cn.knkiss.gui;

import mcxyhj.cn.knkiss.config.GuiDataClass;
import mcxyhj.cn.knkiss.config.ItemData;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*  添加按钮，按理说应该由不同的职业主动添加按钮，不过也可以写到配置文件中读取！
*   配置文件应该是最佳方法
* */

public class GuiManager {
    public static HashMap<String, GuiDataClass> guiDataMap = new HashMap<>();

    public static void loadOnEnable(){
        initGuiData();
    }

    public static void initGuiData(){
        List<ItemStack> needList = new ArrayList<>();
        List<ItemStack> giveList = new ArrayList<>();

        //添加示例 guiDataMap.put(name,new guiData(levelNeed,needList,                      giveList,Material.BOOK,displayName,lore数组));
        //添加示例 guiDataMap.put(name,new guiData(levelNeed,needList,giveExp,              giveList,Material.BOOK,displayName,lore数组));
        //添加示例 guiDataMap.put(name,new guiData(levelNeed,needList,needLevel,removeLevel,giveList,Material.BOOK,displayName,lore数组));
        for(int i=2;i>=0;i--){
            if(i == 2){
                needList.add(new ItemStack(Material.IRON_BLOCK,1));
                giveList.add(ItemData.itemStackHashMap.get("fengli6"));
                guiDataMap.put("fengli6",new GuiDataClass(0,needList,3,giveList,
                        Material.BOOK,"锋利6","需要1个铁块","给予3经验值"));
            }else if(i == 1){
                needList.add(new ItemStack(Material.DIAMOND_BLOCK,1));
                giveList.add(ItemData.itemStackHashMap.get("xiaolv6"));
                guiDataMap.put("xiaolv6",new GuiDataClass(0,needList,10,3,giveList,
                        Material.BOOK,"效率6","需要1个钻石块","要求10级等级，消耗3级"));
            }else if(i == 0){
                needList.add(new ItemStack(Material.GOLD_BLOCK,1));
                giveList.add(ItemData.itemStackHashMap.get("naijiu4"));
                guiDataMap.put("naijiu4",new GuiDataClass(0,needList,giveList,
                        Material.BOOK,"耐久4","需要1个金块"));
            }

            needList = new ArrayList<>();
            giveList = new ArrayList<>();
        }
    }
}
