package mcxyhj.cn.knkiss.config;

import mcxyhj.cn.knkiss.Manager;
import mcxyhj.cn.knkiss.profession.ProfessionManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private static File dataFile = null;
    private static FileConfiguration dataConfig = null;
    private static File file = null;
    private static FileConfiguration config = null;

    //debug
    public static void clearAllData(){
        Manager.plugin.saveResource("data.yml", true);
        dataFile = new File(Manager.plugin.getDataFolder(), "data.yml");
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
    }

    //启动加载 重载读取 关闭保存
    public static void loadOnEnable(){
        saveDefaultConfig();
        dataConfig = getCustomConfig();
        config = getDefaultConfig();
        PluginData.maxLevel = config.getInt("maxLevel");
    }

    public static void checkOnReload(){
        Bukkit.getServer().getOnlinePlayers().forEach(player -> {
            String path = player.getName();
            if(!dataConfig.contains(path+".level"))return;

            int level = dataConfig.getInt(path+".level");
            int exp = dataConfig.getInt(path+".exp");
            String profession = dataConfig.getString(path+".profession");
            boolean change = dataConfig.getBoolean(path+".change");
            PlayerData playerData = new PlayerData(path,level,exp,change,profession);
            ProfessionManager.addPlayer(playerData);
        });
    }

    public static void saveOnDisable(){
        ProfessionManager.getPlayerDataList().forEach((s, playerData) -> {
            String path = playerData.name;
            dataConfig.set(path+".level",playerData.level);
            dataConfig.set(path+".exp",playerData.exp);
            dataConfig.set(path+".profession",playerData.profession);
            dataConfig.set(path+".change",playerData.change);
        });
        saveCustomConfig();
    }

    //通用函数
    public static void playerJoin(String path){
        if(!dataConfig.contains(path+".level"))return;

        int level = dataConfig.getInt(path+".level");
        int exp = dataConfig.getInt(path+".exp");
        String profession = dataConfig.getString(path+".profession");
        boolean change = dataConfig.getBoolean(path+".change");
        PlayerData playerData = new PlayerData(path,level,exp,change,profession);
        ProfessionManager.addPlayer(playerData);
    }

    public static void playerQuit(String path){
        if(ProfessionManager.hasPlayer(path)){
            PlayerData playerData = ProfessionManager.removePlayer(path);
            dataConfig.set(path+".level",playerData.level);
            dataConfig.set(path+".exp",playerData.exp);
            dataConfig.set(path+".profession",playerData.profession);
            dataConfig.set(path+".change",playerData.change);
            saveCustomConfig();
        }
    }

    //配置文件默认方法 不必更改
    private static void saveDefaultConfig() {
        if (dataFile == null || file == null) {
            dataFile = new File(Manager.plugin.getDataFolder(), "data.yml");
            file = new File(Manager.plugin.getDataFolder(), "config.yml");
        }
        if (!dataFile.exists()) {
            Manager.plugin.saveResource("data.yml", false);
        }
        if (!file.exists()) {
            Manager.plugin.saveResource("config.yml", false);
        }
    }

    private static void saveCustomConfig() {
        if (dataConfig == null || dataFile == null) {
            return;
        }
        try {
            dataConfig.save(dataFile);
        } catch (IOException ex) {
            Manager.plugin.getLogger().warning("Could not save config to " + dataFile);
        }
    }

    private static FileConfiguration getCustomConfig() {
        if (dataConfig == null) {
            if (dataFile == null) {
                dataFile = new File(Manager.plugin.getDataFolder(), "data.yml");
            }
            dataConfig = YamlConfiguration.loadConfiguration(dataFile);
        }
        return dataConfig;
    }

    private static FileConfiguration getDefaultConfig() {
        if (config == null) {
            if (file == null) {
                file = new File(Manager.plugin.getDataFolder(), "config.yml");
            }
            config = YamlConfiguration.loadConfiguration(file);
        }
        return config;
    }
}
