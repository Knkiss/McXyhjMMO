package mcxyhj.cn.knkiss.config;

public class PlayerData {
    public String name,profession;
    public int level,exp;
    public boolean change;

    public PlayerData(String name,int level,int exp,boolean change,String profession) {
        this.name = name;
        this.profession = profession;
        this.level = level;
        this.exp = exp;
        this.change = change;
    }

    public void addExp(int number){
        exp = exp + number;
        while(level<PluginData.maxLevel && exp>=getExpMaxThisLevel()){
            level++;
            exp = exp - getExpMaxThisLevel();
        }
    }

    public int getExpMaxThisLevel(){
        return 100;
    }
}
