package mcxyhj.cn.knkiss.config;

import mcxyhj.cn.knkiss.Utils;
import mcxyhj.cn.knkiss.profession.Profession;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class ProfessionData {
    public static HashMap<String, Profession> professionMap = new HashMap<>();

    public static void loadProfessionData(){
        ConfigManager.configMap.get("profession").getKeys(false).forEach(key -> {
            //职业信息
            String name = ConfigManager.configMap.get("profession").getString(key+".settings.name");
            List<String> infoList = Utils.getStringList(ConfigManager.configMap.get("profession").get(key+".settings.info"));


            //gui界面
            List<Inventory> guiList = new LinkedList<>();


            ConfigurationSection ui = ConfigManager.configMap.get("profession").getConfigurationSection(key+".gui");
            assert ui != null;
            ui.getKeys(false).forEach(id -> {
                String iconNmae = ui.getString(id+".displayName");
                List<String> iconLore = Utils.getStringList(ui.get(id+".lore"));
                int proLevelNeed = ui.getInt(id+".proLevelNeed");
                //TODO
                if(ui.contains(id+".needLevel")){
                    int needLevel = ui.getInt(id+".needLevel");
                    int removeLevel = ui.getInt(id+".removeLevel");
                }else if(ui.contains(id+".giveExp")){
                    int giveExp = ui.getInt(id+".giveExp");
                }

            });
        });
    }
}
