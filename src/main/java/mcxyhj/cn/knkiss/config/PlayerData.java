package mcxyhj.cn.knkiss.config;

import java.util.UUID;

public class PlayerData {
    String name,profession;
    int level,exp;
    boolean change;
    UUID playerUUID;

    int maxLevel = 1000;

    public PlayerData(String name,int level,int exp,boolean change,String profession,UUID playerUUID) {
        this.name = name;
        this.profession = profession;
        this.level = level;
        this.exp = exp;
        this.change = change;
        this.playerUUID = playerUUID;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public String getName() {
        return name;
    }

    public void addExp(int number){
        if (level>=maxLevel)return;
        exp += number;
        if(exp >= 200+(level/5)*200){
            exp-= 200+(level/5)*200;
            level++;
        }
    }

    public boolean isBigThan(int level){
        return this.level >= level;
    }
}
