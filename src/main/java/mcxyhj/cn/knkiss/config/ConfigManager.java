package mcxyhj.cn.knkiss.config;

import mcxyhj.cn.knkiss.Manager;
import mcxyhj.cn.knkiss.profession.ProfessionManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ConfigManager {

    private static final HashMap<String,File> fileMap = new HashMap<>();
    public static HashMap<String,FileConfiguration> configMap = new HashMap<>();

    //debug
    public static void clearAllData(){
        Manager.plugin.saveResource("data.yml", true);
        fileMap.replace("data",new File(Manager.plugin.getDataFolder(), "data.yml"));
        configMap.replace("data",YamlConfiguration.loadConfiguration(fileMap.get("data")));
    }

    public static void loadOnEnable(){
        //注册文件
        fileMap.put("config",null);
        fileMap.put("data",null);
        fileMap.put("item",null);
        fileMap.put("message",null);
        fileMap.put("profession",null);

        //获取file和config
        saveDefaultConfig();
        getAllConfig();

        //调用对应类加载对应数据
        PluginData.loadPluginData();
        MessageData.loadMessageData();
        ItemData.loadItemData();
    }

    public static void checkOnReload(){
        Bukkit.getServer().getOnlinePlayers().forEach(player -> {
            String path = player.getName();
            if(!configMap.get("data").contains(path+".level"))return;

            int level = configMap.get("data").getInt(path+".level");
            int exp = configMap.get("data").getInt(path+".exp");
            String profession = configMap.get("data").getString(path+".profession");
            boolean change = configMap.get("data").getBoolean(path+".change");
            PlayerData playerData = new PlayerData(path,level,exp,change,profession);
            ProfessionManager.addPlayer(playerData);
        });
    }

    public static void saveOnDisable(){
        ProfessionManager.getPlayerDataList().forEach((s, playerData) -> {
            String path = playerData.name;
            configMap.get("data").set(path+".level",playerData.level);
            configMap.get("data").set(path+".exp",playerData.exp);
            configMap.get("data").set(path+".profession",playerData.profession);
            configMap.get("data").set(path+".change",playerData.change);
        });
        saveCustomConfig();
    }

    public static void playerJoin(String path){
        if(!configMap.get("data").contains(path+".level"))return;

        int level = configMap.get("data").getInt(path+".level");
        int exp = configMap.get("data").getInt(path+".exp");
        String profession = configMap.get("data").getString(path+".profession");
        boolean change = configMap.get("data").getBoolean(path+".change");
        PlayerData playerData = new PlayerData(path,level,exp,change,profession);
        ProfessionManager.addPlayer(playerData);
    }

    public static void playerQuit(String path){
        if(ProfessionManager.hasPlayer(path)){
            PlayerData playerData = ProfessionManager.removePlayer(path);
            configMap.get("data").set(path+".level",playerData.level);
            configMap.get("data").set(path+".exp",playerData.exp);
            configMap.get("data").set(path+".profession",playerData.profession);
            configMap.get("data").set(path+".change",playerData.change);
            saveCustomConfig();
        }
    }

    //配置文件默认方法 不必更改
    private static void saveDefaultConfig() {
        fileMap.forEach((name, file1) -> {
            if(file1 == null){
                fileMap.replace(name,new File(Manager.plugin.getDataFolder(), name+".yml"));
                file1=fileMap.get(name);
            }
            if(!file1.exists())Manager.plugin.saveResource(name+".yml", false);
        });
    }

    private static void getAllConfig(){
        fileMap.forEach((name,file1) -> configMap.put(name,YamlConfiguration.loadConfiguration(file1)));
    }

    private static void saveCustomConfig() {
        if (configMap.get("data") == null || fileMap.get("data") == null) {
            return;
        }
        try {
            configMap.get("data").save(fileMap.get("data"));
        } catch (IOException ex) {
            Manager.plugin.getLogger().warning("Could not save config to " + fileMap.get("data"));
        }
    }
}
