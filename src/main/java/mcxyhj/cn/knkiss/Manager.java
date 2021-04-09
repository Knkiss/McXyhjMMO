package mcxyhj.cn.knkiss;

import mcxyhj.cn.knkiss.config.ConfigManager;
import mcxyhj.cn.knkiss.config.PlayerData;
import mcxyhj.cn.knkiss.config.PluginData;
import mcxyhj.cn.knkiss.profession.ProfessionManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.logging.Logger;

public class Manager implements CommandExecutor, Listener {
    public static Plugin plugin;
    public static Logger logger;

    public Manager(Plugin plugin){
        Manager.plugin = plugin;
        Manager.logger = plugin.getLogger();
        ProfessionManager.loadOnEnable();
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
        if(!(sender instanceof Player) || (((Player)sender).isOp())){
            if(args.length>=1){
                if(args[0].equalsIgnoreCase("admin")){
                    if(args.length>=2){
                        if(args[1].equalsIgnoreCase("debug")){
                            debugInfo(sender);
                            return true;
                        }
                        if(args[1].equalsIgnoreCase("clear")){
                            ProfessionManager.playerProfession.clear();
                            ProfessionManager.professionHashMap.forEach((s, profession) -> {
                                profession.playerList.clear();
                            });

                            ConfigManager.clearAllData();
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
            }
            //ProfessionManager.excuteCommand(player,args);
        }
        sendHelp(sender);
        return true;
    }

    private void sendHelp(CommandSender sender){
        //TODO 帮助界面
        if(sender instanceof Player){
            sender.sendMessage("HELP INFO");
        }else{
            logger.info("HELP INFO");
        }
    }

    private void debugInfo(CommandSender sender){
        sender.sendMessage("-------------PluginData Check-------------");
        sender.sendMessage("MaxLevel = "+PluginData.maxLevel);

        sender.sendMessage("-------------ProfessionManager Check-------");
        sender.sendMessage(ProfessionManager.playerProfession.toString());

        sender.sendMessage("-------------ProfessionList---------------");
        ProfessionManager.professionHashMap.forEach((professionName, profession) -> {
            sender.sendMessage("--------"+professionName+":");
            profession.getPlayerList().forEach((playerName, playerData) -> {
                sender.sendMessage(playerName+":"
                        + " Level."+playerData.level
                        + " exp."+playerData.exp
                        + " canChange."+playerData.change
                );
            });
        });
    }
}
