package mcxyhj.cn.knkiss.profession;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class Miner extends Profession implements Listener {
    public Miner() {
        super("矿工",1);
    }

    @Override
    public void initGUI() {
        
    }

    @EventHandler
    public void onPlayerBreakBlock(BlockBreakEvent e){
        if(!this.hasPlayer(e.getPlayer().getName()))return;
        if(e.getBlock().getType().equals(Material.STONE)){
            addExp(e.getPlayer().getName(),7);
        }
    }
}
