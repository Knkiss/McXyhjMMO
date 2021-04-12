package mcxyhj.cn.knkiss.profession;

import mcxyhj.cn.knkiss.gui.GuiManager;
import org.bukkit.event.Listener;

public class Enchanter extends Profession implements Listener {
    public Enchanter() {
        super("附魔师",4);
    }

    @Override
    public void initGUI() {
        setItem(GuiManager.guiDataMap.get("xiaolv6"),1,1,1);
        setItem(GuiManager.guiDataMap.get("naijiu4"),1,1,2);
        setItem(GuiManager.guiDataMap.get("fengli6"),1,1,4);
    }
}
