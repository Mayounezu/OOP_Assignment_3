public class Warrior extends Player {
    private int cooldown = 0;
    private int maxCooldown;
    public Warrior(String name, int healthPool, int atkPoints, int defPoints, int maxCooldown) {
        super(name, healthPool, atkPoints, defPoints);
        ability = new  WarriorAbility();
        this.maxCooldown = maxCooldown;
    }
    public void cast(){
        super.cast();
        setHealthAmount(Math.min(getHealthAmount() + 10 * getDefPts(), getHealthPool()));
    }
    public void levelUp(){
        super.levelUp();
        cooldown = 0;
        setHealthPool(getHealthPool() + (level * 5));
        setAtkPts(getAtkPts() + (level * 2));
        setDefPts(getDefPts() + level);
    }
    public void updateGameTick(){
        if(cooldown > 0){
            cooldown--;
        }
    }
}
