package mcxyhj.cn.knkiss;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class McXyhjMMO extends JavaPlugin {
    private Manager m;

    @Override
    public void onEnable() {
        getLogger().info("星夜幻境MMO开始使用");
        m = new Manager(this);
        Objects.requireNonNull(getCommand("mmo")).setExecutor(m);

        if(!getServer().getOnlinePlayers().isEmpty()){
            getLogger().info("星夜幻境MMO正在重载信息");
            m.checkOnReload();//重载且存在玩家时检查
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("星夜幻境MMO正在关闭");
        m.saveOnDisable();
    }

    public static void main(String[] args){}
}
