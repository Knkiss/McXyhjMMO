package mcxyhj.cn.knkiss.config;

public class PluginData {
    public static int maxLevel;
    public static boolean debug;

    public static void loadPluginData(){
        maxLevel = ConfigManager.config.getInt("maxLevel");
        debug = ConfigManager.config.getBoolean("debug");
    }
}
