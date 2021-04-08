package mcxyhj.cn.knkiss;

import mcxyhj.cn.knkiss.config.ConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ManageListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        ConfigManager.playerJoin(e.getPlayer().getName());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        ConfigManager.playerQuit(e.getPlayer().getName());
    }
}
