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

    //添加职业经验值 number=经验值数量
    public void addExp(int number){
        exp = exp + number;
        while(level<PluginData.maxLevel && exp>=getExpMaxThisLevel()){
            level++;
            exp = exp - getExpMaxThisLevel();
        }
    }

    //获取这个等级的经验值限制
    //TODO 应该从配置文件获取经验值函数
    public int getExpMaxThisLevel(){
        return 100;
    }
}
