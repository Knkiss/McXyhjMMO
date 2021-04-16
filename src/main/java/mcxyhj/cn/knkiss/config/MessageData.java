package mcxyhj.cn.knkiss.config;

import mcxyhj.cn.knkiss.Manager;
import mcxyhj.cn.knkiss.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

//数据已保存完毕 格式HashMap<String key,List<Sting> messageList> replace("&","§")

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
            messageMap.put(key.toLowerCase(),messageList);
        });
    }

    public static void warning(String warn){
        Manager.logger.warning(warn);
    }

    public static void info(String info){
        Manager.logger.info(info);
    }

    //无可用替换参数发送信息
    public static void sendMessage(String key,CommandSender sender){
        messageMap.get(key.toLowerCase()).forEach(s -> {
            sender.sendMessage(messageMap.get("index").get(0)+s);
        });
    }

    //带参发送信息
    public static void sendMessage(String key,CommandSender sender,HashMap<String,String> replaceStringMap){
        messageMap.get(key.toLowerCase()).forEach(s -> {
            final String[] send = {s};
            replaceStringMap.forEach((s1, s2) -> {
                send[0] = send[0].replace(s1,s2);
            });
            sender.sendMessage(messageMap.get("index").get(0)+send[0]);
        });
    }

    //DEBUG函数
    public static void debug(CommandSender sender){
        messageMap.forEach((key, strings) -> strings.forEach(s -> sender.sendMessage(key+": "+s)));
    }
}
