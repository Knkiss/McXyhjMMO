package mcxyhj.cn.knkiss.config;

import mcxyhj.cn.knkiss.Utils;
import mcxyhj.cn.knkiss.profession.ProfessionManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*  此文件用于生成一个按钮的类
*   其中包含按钮的图标、被点击后的验证、减少物品、给予奖励、执行指令
* */

public class GuiDataClass {
    public ItemStack icon;
    public List<ItemStack> needList,giveList;
    public int needLevel = 0,removeLevel = 0,giveExp = 0,proLevelNeed;

    public List<String> commandList = new ArrayList<>();
    public List<String> commandOpList = new ArrayList<>();

    public GuiDataClass(int proLevelNeed, List<ItemStack> needList, List<ItemStack> giveList, Material material, String displayName, String...loreArrays){
        setIcon(material,displayName,Arrays.asList(loreArrays));
        this.needList = needList;
        this.giveList = giveList;
        this.proLevelNeed = proLevelNeed;
    }

    public GuiDataClass(int proLevelNeed, List<ItemStack> needList, int needLevel , int removeLevel , List<ItemStack> giveList, Material material, String displayName, String...loreArrays){
        this(proLevelNeed,needList,giveList,material,displayName,loreArrays);
        this.needLevel = needLevel;
        if(removeLevel > needLevel) this.removeLevel = 0;
        else this.removeLevel = removeLevel;
    }

    public GuiDataClass(int proLevelNeed, List<ItemStack> needList, int giveExp , List<ItemStack> giveList, Material material, String displayName, String...loreArrays){
        this(proLevelNeed,needList,giveList,material,displayName,loreArrays);
        this.giveExp = giveExp;
    }

    private void setIcon(Material material,String displayName,List<String> loreList){
        icon = new ItemStack(material,1);
        ItemMeta im = icon.getItemMeta();
        assert im != null;
        im.setDisplayName(displayName);
        im.setLore(loreList);
        icon.setItemMeta(im);
    }

    public void setCommand(String...commandArrays){
        commandList = Arrays.asList(commandArrays);
    }

    public void setCommandAsOp(String...commandOpArrays){
        commandOpList = Arrays.asList(commandOpArrays);
    }

    public void onClick(Player player){
        //职业等级检测
        if(ProfessionManager.getPlayer(player.getName()).level< proLevelNeed){
            player.sendMessage("你的职业等级不足，需要"+ proLevelNeed +"级");
            return;
        }
        //经验检测
        if(player.getLevel() >= needLevel){
            //物品检测
            if(Utils.removeItem(player,needList)){
                player.setLevel(player.getLevel() - removeLevel);
                player.giveExp(giveExp);
                Utils.addItem(player,giveList);
                commandList.forEach(player::performCommand);
                if(player.isOp())commandOpList.forEach(player::performCommand);
                else{
                    player.setOp(true);
                    try{
                        commandOpList.forEach(player::performCommand);
                    }catch (Exception e){
                        player.setOp(false);
                    }
                    player.setOp(false);
                }
            }else{
                player.sendMessage("需要物品不足，需要"+needList.toString());
            }
        }else{
            player.sendMessage("你的等级不足，需要"+needLevel+"级");
        }
    }
}
