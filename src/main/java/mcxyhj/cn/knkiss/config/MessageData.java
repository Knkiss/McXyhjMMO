package mcxyhj.cn.knkiss.config;

import org.bukkit.entity.Player;

import java.util.*;

public class MessageData {

    public static HashMap<String,List<String>> messageMap = new HashMap<>();

    public static void loadMessageData(){
        ConfigManager.messageConfig.getKeys(false).forEach(key -> {
            Object message = ConfigManager.messageConfig.get(key);
            if(message instanceof String){
                messageMap.put(key, Collections.singletonList(ConfigManager.messageConfig.getString(key)));
            }else if(message instanceof List<?>){
                messageMap.put(key,ConfigManager.messageConfig.getStringList(key));
            }
        });
    }

    public static void sendMessage(Player player, String messageName){
        if(!messageMap.containsKey(messageName))return;
        messageMap.get(messageName).forEach(s -> {
            player.sendMessage(s.replace("&","§"));
        });
    }
}
