package mcxyhj.cn.knkiss.profession;

import mcxyhj.cn.knkiss.config.PlayerData;
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
        if(e.getBlock().getType().equals(Material.DIAMOND_ORE));
    }
}
