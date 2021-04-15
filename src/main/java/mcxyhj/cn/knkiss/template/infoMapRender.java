package mcxyhj.cn.knkiss.template;

import mcxyhj.cn.knkiss.ProfessionManager;
import mcxyhj.cn.knkiss.config.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.map.*;

public class infoMapRender extends MapRenderer {
    @Override
    public void render(MapView map, MapCanvas canvas, Player player) {
        try{
            for (int x = 0; x < 128; x++) {
                for (int y = 0; y < 128; y++) {
                    canvas.setPixel(x, y, MapPalette.WHITE);
                }
            }
            PlayerData pd = ProfessionManager.getPlayer(player.getName());
            canvas.drawText(7, 15, MinecraftFont.Font, player.getName()+"'s Info");
            canvas.drawText(7, 30, MinecraftFont.Font, "Profession: "+ pd.profession);
            canvas.drawText(7, 45, MinecraftFont.Font, "level: "+ pd.level);
            canvas.drawText(7, 60, MinecraftFont.Font, "exp: "+ pd.exp);
            canvas.drawText(7, 75, MinecraftFont.Font, "canReSelect: "+ (pd.change?"Yes":"No"));

            map.setLocked(true);
            map.setTrackingPosition(false);
        }catch (Exception e){

        }

    }
}
