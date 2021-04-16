package mcxyhj.cn.knkiss;

import mcxyhj.cn.knkiss.config.ConfigManager;
import mcxyhj.cn.knkiss.config.ProfessionData;
import mcxyhj.cn.knkiss.template.infoMapRender;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.map.MapView;

import javax.swing.*;
import java.awt.*;
import java.util.*;
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
   
    
    @EventHandler
    public void onPrepareAnvilEvent(PrepareAnvilEvent e){
        if(e.getInventory().getItem(0)==null || e.getInventory().getItem(1)==null || e.getResult()==null) return;
        if(!Objects.requireNonNull(e.getInventory().getItem(1)).getType().equals(Material.ENCHANTED_BOOK))return;
        if(Objects.requireNonNull(e.getInventory().getItem(0)).getType().equals(Material.ENCHANTED_BOOK))return;
        ItemStack enchantBook = e.getInventory().getItem(1);
        ItemStack result = e.getResult();
        assert enchantBook != null;
        assert result != null;
        try{
            Map<Enchantment,Integer> map = result.getEnchantments();
            Map<Enchantment,Integer> enchantMap = ((EnchantmentStorageMeta) Objects.requireNonNull(enchantBook.getItemMeta())).getStoredEnchants();
            
            map.forEach((enchantment, integer) -> {
                //map是可以出现的附魔
               
                if(enchantMap.containsKey(enchantment)){
                    result.removeEnchantment(enchantment);
                    
                    
                    if(enchantMap.get(enchantment) > integer){
                        result.addUnsafeEnchantment(enchantment,enchantMap.get(enchantment));
                    }else{
                        result.addUnsafeEnchantment(enchantment,integer);
                    }
                }
            });
        }catch (Exception ignored){}
    }
}
