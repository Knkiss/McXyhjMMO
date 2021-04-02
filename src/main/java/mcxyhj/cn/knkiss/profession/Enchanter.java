package mcxyhj.cn.knkiss.profession;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class Enchanter extends Profession implements Listener {
    public Enchanter() {
        super("附魔师");
    }

    @Override
    public void onCommand(Player player, String[] args) {

    }

    @EventHandler
    public void onPlayerBreakBlock(BlockBreakEvent e){

    }
}
