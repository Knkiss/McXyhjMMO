package mcxyhj.cn.knkiss;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class McXyhjMMO extends JavaPlugin {
    public static Manager m;

    @Override
    public void onEnable() {
        m = new Manager(this);
        if(!this.getServer().getOnlinePlayers().isEmpty())m.checkOnReload();//重载且存在玩家时检查
        Objects.requireNonNull(getCommand("mmo")).setExecutor(m);
    }

    @Override
    public void onDisable() {
        m.saveOnDisable();
    }
}
