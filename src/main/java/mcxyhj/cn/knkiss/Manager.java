package mcxyhj.cn.knkiss;

import mcxyhj.cn.knkiss.config.ConfigManager;
import mcxyhj.cn.knkiss.profession.ProfessionManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

public class Manager implements CommandExecutor, Listener {
    public static Plugin plugin;
    public static Logger logger;

    public Manager(Plugin plugin){
        Manager.plugin = plugin;
        Manager.logger = plugin.getLogger();
        ProfessionManager.loadOnEnable();
        ConfigManager.loadOnEnable();
    }

    public void checkOnReload(){
        ConfigManager.checkOnReload();
    }

    public void saveOnDisable(){
        ConfigManager.saveOnDisable();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        /*
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length>1){
                if(!args[0].equalsIgnoreCase("admin"))professionManager.excuteCommand(player,args);
                else;
            }else{
                sendHelp(sender);
            }
        }else{
            sendHelp(sender);
        }*/
        return true;
    }

    /*
    private void sendHelp(CommandSender sender){
        //TODO 帮助界面
        if(sender instanceof Player){
            sender.sendMessage("HELP INFO");
        }else{
            logger.info("HELP INFO");
        }
    }*/
}
