package mcxyhj.cn.knkiss.config;

import mcxyhj.cn.knkiss.Utils;
import org.bukkit.command.CommandSender;

import java.util.*;

//数据已保存完毕 格式HashMap<String key,List<Sting> messageList>
//TODO 1添加占位符，2替换信息显示

public class MessageData {

    public static HashMap<String,List<String>> messageMap = new HashMap<>();

    //加载函数
    public static void loadMessageData(){
        ConfigManager.configMap.get("message").getKeys(false).forEach(key -> {
            List<String> messageList = new ArrayList<>();
            for (String s : Utils.getStringList(ConfigManager.configMap.get("message").get(key))){
                messageList.add(s.replace("&","§"));
            }
            messageMap.put(key,messageList);
        });
    }

    //DEBUG函数
    public static void debug(CommandSender sender){
        messageMap.forEach((key, strings) -> strings.forEach(s -> sender.sendMessage(key+": "+s)));
    }
}
