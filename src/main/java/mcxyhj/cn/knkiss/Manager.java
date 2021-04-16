package mcxyhj.cn.knkiss;

import mcxyhj.cn.knkiss.config.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.logging.Logger;

public class Manager implements CommandExecutor {
    public static Plugin plugin;
    public static Logger logger;

    public Manager(Plugin plugin){
        Manager.plugin = plugin;
        Manager.logger = plugin.getLogger();
        ConfigManager.loadOnEnable();
        Bukkit.getPluginManager().registerEvents(new ManageListener(), Manager.plugin);
    }

    public void checkOnReload(){
        ConfigManager.checkOnReload();
    }

    public void saveOnDisable(){
        ConfigManager.saveOnDisable();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //控制台
        if(!(sender instanceof Player) || sender.isOp()){
            if(args.length>=1){
                if(args[0].equalsIgnoreCase("admin")){
                    if(args.length>=2){
                        if(args[1].equalsIgnoreCase("reset")){
                            ProfessionManager.reset();
                            MessageData.sendMessage("admin_reset",sender);
                            return true;
                        }
                        if(!PluginData.debug){
                            MessageData.sendMessage("debug_need",sender);
                            return true;
                        }
                        if(args[1].equalsIgnoreCase("debug")){
                            debugInfo(sender);
                            MessageData.sendMessage("admin_debug",sender);
                            return true;
                        }
                        if(args[1].equalsIgnoreCase("clear")){
                            ProfessionManager.playerProfession.clear();
                            ProfessionData.professionMap.forEach((s, profession) -> profession.playerList.clear());
                            ConfigManager.clearAllData();
                            MessageData.sendMessage("admin_clear",sender);
                            return true;
                        }
                    }
                }
            }
            //控制台直接退出
            if(!(sender instanceof Player)){
                sendHelp(sender);
                return true;
            }
        }

        //玩家
        Player player = (Player) sender;
        if(args.length>=1){
            if(args[0].equalsIgnoreCase("select")){
                if(args.length>=2){
                    if(ProfessionManager.isRightProfession(args[1])){
                        if(ProfessionManager.hasPlayer(player.getName())){
                            PlayerData pd = ProfessionManager.getPlayer(player.getName());
                            if(pd.change){
                                if(!pd.profession.equalsIgnoreCase(args[1])){
                                    ProfessionManager.removePlayer(player.getName());
                                    pd.profession = args[1].toLowerCase();
                                    pd.change = false;
                                    pd.level = pd.level /2;
                                    pd.exp = 0;
                                    ProfessionManager.addPlayer(pd);
                                    HashMap<String,String> replaceStringHashMap = new HashMap<>();
                                    replaceStringHashMap.put("%proName%",args[1].toLowerCase());
                                    MessageData.sendMessage("reSelect_ok",sender,replaceStringHashMap);
                                    return true;
                                }
                            }else{
                                MessageData.sendMessage("select_no_chance",sender);
                                return true;
                            }
                        }else{
                            PlayerData pd = new PlayerData(player.getName(),0,0,true,args[1].toLowerCase());
                            ProfessionManager.addPlayer(pd);
                            HashMap<String,String> replaceStringHashMap = new HashMap<>();
                            replaceStringHashMap.put("%proName%",args[1].toLowerCase());
                            MessageData.sendMessage("reSelect_ok",sender,replaceStringHashMap);
                            return true;
                        }
                    }
                }
                player.sendMessage("选择职业列表");
                ProfessionManager.selectProfessionGui(player);
                return true;

            }else if(args[0].equalsIgnoreCase("gui")){
                if(ProfessionManager.hasPlayer(player.getName())) ProfessionManager.openGUI(player);
                else{
                    player.sendMessage("请先选择职业");
                    ProfessionManager.selectProfessionGui(player);
                }
                return true;
            }else if(args[0].equalsIgnoreCase("info")){
                if(!ProfessionManager.hasPlayer(player.getName())){
                    player.sendMessage("请先选择职业");
                    ProfessionManager.selectProfessionGui(player);
                }else{
                    ProfessionManager.showInfoToPlayer(player);
                }
                return true;
            }
        }
        sendHelp(sender);
        return true;
    }

    private void sendHelp(CommandSender sender){
        if(sender instanceof Player){
            sender.sendMessage("-----XyhjMMO PLAYER HELP-----");
            sender.sendMessage("/mmo select <profession> 选择职业");
            sender.sendMessage("/mmo gui 打开对应职业界面");
            sender.sendMessage("/mmo info 查看个人信息");
        }
        if(!(sender instanceof Player) || sender.isOp()){
            sender.sendMessage("-----XyhjMMO CONSOLE HELP-----");
            sender.sendMessage("/mmo admin reset 重置所有玩家的选择机会");
            sender.sendMessage("/mmo admin debug 显示所有玩家信息");
            sender.sendMessage("/mmo admin clear 清理所有玩家数据");
        }
    }

    private void debugInfo(CommandSender sender){
        //MessageData.debug(sender);
        ItemData.debug(sender);
        //ProfessionData.debug(sender);
    }
}
