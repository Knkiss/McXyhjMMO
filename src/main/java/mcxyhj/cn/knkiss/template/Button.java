package mcxyhj.cn.knkiss.template;

import mcxyhj.cn.knkiss.Utils;
import mcxyhj.cn.knkiss.profession.ProfessionManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Button {
    private final ItemStack icon;
    private final List<ItemStack> needList;
    private final List<ItemStack> giveList;
    private final int proLevelNeed;

    private int needLevel = 0;
    private int removeLevel = 0;
    private int giveExp = 0;

    private List<String> commandList = new ArrayList<>();
    private List<String> commandOpList = new ArrayList<>();

    //以下四函数用于创建button
    public Button(ItemStack icon, int proLevelNeed, List<ItemStack> needList, List<ItemStack> giveList){
        this.icon = icon;
        this.needList = needList;
        this.giveList = giveList;
        this.proLevelNeed = proLevelNeed;
        Utils.addLore(icon,"");
        Utils.addLore(icon,"§8需要职业等级:"+proLevelNeed);
        Utils.addLore(icon,"§8需要物品:");
        needList.forEach(itemStack -> {
            if(Objects.requireNonNull(itemStack.getItemMeta()).getDisplayName().isEmpty()){
                Utils.addLore(icon,"§8  "+itemStack.getType()+" §8* "+itemStack.getAmount());
            }else{
                Utils.addLore(icon,"§8  "+Objects.requireNonNull(itemStack.getItemMeta()).getDisplayName()+" §8* "+itemStack.getAmount());
            }
        });
        Utils.addLore(icon,"§8奖励物品:");
        giveList.forEach(itemStack -> {
            if(Objects.requireNonNull(itemStack.getItemMeta()).getDisplayName().isEmpty()){
                Utils.addLore(icon,"§8  "+itemStack.getType()+" * "+itemStack.getAmount());
            }else{
                Utils.addLore(icon,"§8  "+Objects.requireNonNull(itemStack.getItemMeta()).getDisplayName()+" §8* "+itemStack.getAmount());
            }
        });
    }

    public void setLevelInfo(int needLevel,int removeLevel){
        this.needLevel = needLevel;
        this.removeLevel = removeLevel;
        Utils.addLore(icon,"§8需要等级:"+needLevel+" 消耗等级:"+removeLevel);
    }

    public void setGiveExp(int giveExp){
        this.giveExp = giveExp;
        Utils.addLore(icon,"§8奖励经验:"+giveExp);
    }

    public void setCommand(List<String> commandList,List<String> commandOpList){
        this.commandList = commandList;
        this.commandOpList = commandOpList;
    }

    //点击按钮触发
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
