package mcxyhj.cn.knkiss;

import mcxyhj.cn.knkiss.config.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

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
        if(!(sender instanceof Player) || (sender.isOp())){
            if(args.length>=1){
                if(args[0].equalsIgnoreCase("admin")){
                    if(args.length>=2){
                        if(args[1].equalsIgnoreCase("reset")){
                            ProfessionManager.reset();
                            sender.sendMessage("所有人的选择权已重置");
                            return true;
                        }
                        if(!PluginData.debug){
                            sender.sendMessage("将config中的debug更改为TRUE以使用此功能");
                            return true;
                        }
                        if(args[1].equalsIgnoreCase("debug")){
                            debugInfo(sender);
                            sender.sendMessage("以上为xyhjMMO插件所有信息");
                            return true;
                        }
                        if(args[1].equalsIgnoreCase("clear")){
                            ProfessionManager.playerProfession.clear();
                            ProfessionData.professionMap.forEach((s, profession) -> profession.playerList.clear());
                            ConfigManager.clearAllData();
                            sender.sendMessage("已清空插件的所有数据");
                            return true;
                        }
                    }
                }
            }
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
                                    player.sendMessage("你已重新选择职业："+args[1].toLowerCase());
                                    return true;
                                }
                            }
                        }else{
                            PlayerData pd = new PlayerData(player.getName(),0,0,true,args[1].toLowerCase());
                            ProfessionManager.addPlayer(pd);
                            player.sendMessage("你已选择职业："+args[1].toLowerCase());
                            return true;
                        }
                    }
                }
            }else if(args[0].equalsIgnoreCase("gui")){
                if(ProfessionManager.hasPlayer(player.getName())) ProfessionManager.openGUI(player);
                else{
                    player.sendMessage("请先选择职业，可选择的职业有:");
                    ProfessionData.professionMap.forEach((s, profession) -> player.sendMessage(s));
                }
                return true;
            }else if(args[0].equalsIgnoreCase("info")){
                if(!ProfessionManager.hasPlayer(player.getName())) player.sendMessage("你还没有选择职业，没有你的信息");
                else{
                    PlayerData pd = ProfessionManager.getPlayer(player.getName());
                    player.sendMessage("XyhjMMO玩家信息:"+player.getName());
                    player.sendMessage("职业:"+pd.profession);
                    player.sendMessage("等级:"+pd.level);
                    player.sendMessage("经验值:"+pd.exp);
                    player.sendMessage("可更换:"+(pd.change?"是":"否"));
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
            if(sender.isOp()){
                sender.sendMessage("-----XyhjMMO ADMIN HELP-----");
                sender.sendMessage("/mmo admin reset 重置所有玩家的选择机会");
                sender.sendMessage("/mmo admin debug 显示所有玩家信息");
                sender.sendMessage("/mmo admin clear 清理所有玩家数据");
            }
        }else{
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
