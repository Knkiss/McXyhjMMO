package mcxyhj.cn.knkiss.config;

import mcxyhj.cn.knkiss.Utils;
import mcxyhj.cn.knkiss.template.Button;
import mcxyhj.cn.knkiss.template.Profession;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ProfessionData {
    public static HashMap<String, Profession> professionMap = new HashMap<>();
    public static Inventory professionGui;
    public static HashMap<Integer,Button> guiSelectMap = new HashMap<>();

    //加载函数
    public static void loadProfessionData(){
        professionGui = Bukkit.createInventory(null,54,"请选择你的职业");

        ConfigManager.configMap.get("profession").getKeys(false).forEach(key -> {
            //职业信息
            String name = Objects.requireNonNull(ConfigManager.configMap.get("profession").getString(key + ".settings.name")).replace("&","§");
            List<String> infoList = Utils.getStringList(ConfigManager.configMap.get("profession").get(key+".settings.info"));
            Material proIcon = Material.getMaterial(Objects.requireNonNull(ConfigManager.configMap.get("profession").getString(key + ".settings.icon")));

            //gui界面
            List<Inventory> guiList = new LinkedList<>();
            HashMap<Integer, Button> guiButtonMap = new HashMap<>();

            //gui界面添加按钮
            ConfigurationSection ui = ConfigManager.configMap.get("profession").getConfigurationSection(key+".gui");
            assert ui != null;
            ui.getKeys(false).forEach(id -> {
                //ICON信息
                String iconName = Objects.requireNonNull(ui.getString(id + ".displayName")).replace("&","§");
                List<String> iconLore = Utils.getStringList(ui.get(id+".lore"));
                Material iconType = Material.getMaterial(Objects.requireNonNull(ui.getString(id + ".type")));
                //必须内容
                ItemStack icon = Utils.createItem(iconType,iconName,iconLore);
                int proLevelNeed = ui.getInt(id+".proLevelNeed");
                List<ItemStack> needList = new ArrayList<>();
                List<ItemStack> giveList = new ArrayList<>();
                //needList读取
                ui.getStringList(id+".needList").forEach(s -> {
                    if(s.contains("@")){
                        String itemKey = s.split(",")[0].replace("@","");
                        ItemStack item = ItemData.getItemStack(itemKey).clone();
                        item.setAmount(Integer.parseInt(s.split(",")[1]));
                        needList.add(item);
                    }else{
                        ItemStack item = new ItemStack(Objects.requireNonNull(Material.getMaterial(s.split(",")[0])),
                                Integer.parseInt(s.split(",")[1]));
                        needList.add(item);
                    }
                });
                //giveList读取
                ui.getStringList(id+".giveList").forEach(s -> {
                    if(s.contains("@")){
                        String itemKey = s.split(",")[0].replace("@","");
                        ItemStack item = ItemData.getItemStack(itemKey).clone();
                        item.setAmount(Integer.parseInt(s.split(",")[1]));
                        giveList.add(item);
                    }else{
                        ItemStack item = new ItemStack(Objects.requireNonNull(Material.getMaterial(s.split(",")[0])),
                                Integer.parseInt(s.split(",")[1]));
                        giveList.add(item);
                    }
                });

                Button button = new Button(icon,proLevelNeed,needList,giveList);

                //添加可选信息
                if(ui.contains(id+".needLevel")){
                    int needLevel = ui.getInt(id+".needLevel");
                    int removeLevel = ui.getInt(id+".removeLevel");
                    button.setLevelInfo(needLevel,removeLevel);
                }
                if(ui.contains(id+".giveExp")){
                    int giveExp = ui.getInt(id+".giveExp");
                    button.setGiveExp(giveExp);
                }
                if(ui.contains(id+".giveProExp")){
                    int giveProExp = ui.getInt(id+".giveProExp");
                    button.setGiveProExp(giveProExp);
                }
                if(ui.contains(id+".commands")){
                    List<String> commandOpList = new ArrayList<>();
                    List<String> commandList = new ArrayList<>();

                    ui.getStringList(id+".commands").forEach(command -> {
                        String commandLow = command.toLowerCase();
                        if(commandLow.contains("[op]")){
                            commandOpList.add(commandLow.replace("[op]",""));
                        }else{
                            commandList.add(commandLow);
                        }
                    });
                    button.setCommand(commandList,commandOpList);
                }

                //最后读取位置，并添加inventory
                int uiNumber = ui.getInt(id+".uiNumber");
                int slot = ui.getInt(id+".slot");
                if(uiNumber<1)uiNumber=1;
                if(slot<0||slot>44)slot=44;
                for(int i=guiList.size();i<uiNumber;i++){//i=1 uiNumber=2
                    Inventory gui = Bukkit.createInventory(null,54,name+" 第"+(i+1)+"页");
                    if(i!=0)gui.setItem(45, ItemData.lastPage);
                    if(i==guiList.size() && i!=0)guiList.get(i-1).setItem(53,ItemData.nextPage);
                    if(i<uiNumber-1)gui.setItem(53, ItemData.nextPage);

                    if(i==uiNumber-1){
                        gui.setItem(slot,icon);//添加到当前要求界面
                        guiButtonMap.put((i+1)*100+slot,button);
                    }
                    guiList.add(gui);
                }
            });
            //添加到professionMap中，并添加职业选择按钮到gui
            professionMap.put(key.toLowerCase(),new Profession(name,infoList,guiList,guiButtonMap));

            try{
                assert proIcon != null;
                ItemStack icon = new ItemStack(proIcon,1);
                ItemMeta itemMeta = icon.getItemMeta();
                assert itemMeta != null;
                itemMeta.setDisplayName(name);
                itemMeta.setLore(infoList);
                icon.setItemMeta(itemMeta);
                List<String> commandList = new ArrayList<>();
                commandList.add("mmo select "+key);
                Button proButton = new Button(icon,commandList);

                professionGui.addItem(icon);
                guiSelectMap.put(professionGui.firstEmpty()-1,proButton);

            }catch (Exception e){
                Utils.warning("无法添加"+name+"职业选择按钮，查询设置");
            }
        });
    }

    //DEBUG函数
    public static void debug(CommandSender sender){
        professionMap.forEach((proID, profession) -> {
            sender.sendMessage("[Profession]"+proID+".Name:"+profession.proName);
            profession.info.forEach(s -> sender.sendMessage("[Profession]"+proID+".Info:"+s));
            sender.sendMessage("");
        });
    }
}
