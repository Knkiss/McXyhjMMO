package mcxyhj.cn.knkiss.config;

import mcxyhj.cn.knkiss.Manager;
import mcxyhj.cn.knkiss.ProfessionManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ConfigManager {
    public static final HashMap<String,File> fileMap = new HashMap<>();
    public static HashMap<String,FileConfiguration> configMap = new HashMap<>();

    //清理所有数据 DEBUG
    public static void clearAllData(){
        Manager.plugin.saveResource("data.yml", true);
        fileMap.replace("data",new File(Manager.plugin.getDataFolder(), "data.yml"));
        configMap.replace("data",YamlConfiguration.loadConfiguration(fileMap.get("data")));
    }

    //加载函数
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
        MessageData.loadMessageData();
        PluginData.loadPluginData();
        ItemData.loadItemData();
        ProfessionData.loadProfessionData();
    }

    //重载函数
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

    //保存函数
    public static void saveOnDisable(){
        ProfessionData.professionMap.forEach((id, profession) -> profession.playerList.forEach((s, playerData) -> {
            String path = playerData.name;
            configMap.get("data").set(path+".level",playerData.level);
            configMap.get("data").set(path+".exp",playerData.exp);
            configMap.get("data").set(path+".profession",playerData.profession);
            configMap.get("data").set(path+".change",playerData.change);
        }));
        saveCustomConfig();
    }

    //玩家加入 path=玩家名
    public static void playerJoin(String path){
        if(!configMap.get("data").contains(path+".level"))return;
        int level = configMap.get("data").getInt(path+".level");
        int exp = configMap.get("data").getInt(path+".exp");
        String profession = configMap.get("data").getString(path+".profession");
        boolean change = configMap.get("data").getBoolean(path+".change");
        PlayerData playerData = new PlayerData(path,level,exp,change,profession);
        ProfessionManager.addPlayer(playerData);
    }

    //玩家离开 path=玩家名
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

    //保存默认配置
    private static void saveDefaultConfig() {
        fileMap.forEach((name, file1) -> {
            if(file1 == null){
                fileMap.replace(name,new File(Manager.plugin.getDataFolder(), name+".yml"));
                file1=fileMap.get(name);
            }
            if(!file1.exists())Manager.plugin.saveResource(name+".yml", false);
        });
    }

    //获取所有配置
    private static void getAllConfig(){
        fileMap.forEach((name,file1) -> configMap.put(name,YamlConfiguration.loadConfiguration(file1)));
    }

    //保存自定义配置文件
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
