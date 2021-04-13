package mcxyhj.cn.knkiss.config;

public class PluginData {
    public static int maxLevel;
    public static boolean debug;

    public static void loadPluginData(){
        maxLevel = ConfigManager.configMap.get("config").getInt("maxLevel");
        debug = ConfigManager.configMap.get("config").getBoolean("debug");
    }
}
