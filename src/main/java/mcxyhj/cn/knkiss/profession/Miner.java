package mcxyhj.cn.knkiss.profession;

import mcxyhj.cn.knkiss.Manager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class Miner extends Profession implements Listener {
    public Miner() {
        super("矿工");
    }

    @Override
    public void onCommand(Player player, String[] args) {

    }

    @EventHandler
    public void onPlayerBreakBlock(BlockBreakEvent e){
        if(!this.hasPlayer(e.getPlayer().getName()))return;
        if(e.getBlock().getType().equals(Material.STONE)){
            addExp(e.getPlayer().getName(),7);
        }
    }
}
