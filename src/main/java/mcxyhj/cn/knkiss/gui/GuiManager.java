package mcxyhj.cn.knkiss.gui;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GuiManager {
    public static HashMap<String, GuiData> guiDataMap = new HashMap<>();

    public static void loadOnEnable(){
        ItemData.initItemStack();
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
                giveList.add(ItemData.fengli6);
                guiDataMap.put("fengli6",new GuiData(0,needList,3,giveList,
                        Material.BOOK,"锋利6","需要1个铁块","给予3经验值"));
            }else if(i == 1){
                needList.add(new ItemStack(Material.DIAMOND_BLOCK,1));
                giveList.add(ItemData.xiaolv6);
                guiDataMap.put("xiaolv6",new GuiData(0,needList,10,3,giveList,
                        Material.BOOK,"效率6","需要1个钻石块","要求10级等级，消耗3级"));
            }else if(i == 0){
                needList.add(new ItemStack(Material.GOLD_BLOCK,1));
                giveList.add(ItemData.naijiu4);
                guiDataMap.put("naijiu4",new GuiData(5,needList,giveList,
                        Material.BOOK,"耐久4","需要职业等级5和1个金块"));
            }

            needList = new ArrayList<>();
            giveList = new ArrayList<>();
        }
    }
}
