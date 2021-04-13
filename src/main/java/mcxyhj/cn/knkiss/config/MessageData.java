package mcxyhj.cn.knkiss.config;

import org.bukkit.entity.Player;

import java.util.*;

public class MessageData {

    public static HashMap<String,List<String>> messageMap = new HashMap<>();

    public static void loadMessageData(){
        ConfigManager.configMap.get("message").getKeys(false).forEach(key -> {
            Object message = ConfigManager.configMap.get("message").get(key);
            if(message instanceof String){
                messageMap.put(key, Collections.singletonList(ConfigManager.configMap.get("message").getString(key)));
            }else if(message instanceof List<?>){
                messageMap.put(key,ConfigManager.configMap.get("message").getStringList(key));
            }
        });
    }

    public static void sendMessage(Player player, String messageName){
        if(!messageMap.containsKey(messageName))return;
        messageMap.get(messageName).forEach(s -> player.sendMessage(s.replace("&","§")));
    }
}
