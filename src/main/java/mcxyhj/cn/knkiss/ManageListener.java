package mcxyhj.cn.knkiss;

import mcxyhj.cn.knkiss.config.ConfigManager;
import mcxyhj.cn.knkiss.config.ProfessionData;
import mcxyhj.cn.knkiss.template.infoMapRender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.map.MapView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ManageListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        ConfigManager.playerJoin(e.getPlayer().getName());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        ConfigManager.playerQuit(e.getPlayer().getName());
    }

    @EventHandler
    public void onClickGui(InventoryClickEvent e){
        if(!e.getInventory().equals(ProfessionData.professionGui))return;
        e.setCancelled(true);
        if(e.getCurrentItem()==null)return;
        if(e.getRawSlot()>53)return;

        ProfessionData.guiSelectMap.get(e.getRawSlot()).onClick((Player) e.getWhoClicked());
        e.getWhoClicked().closeInventory();
    }
}
