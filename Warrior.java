import java.util.List;
public class Warrior extends Player {
    private int cooldown = 0;
    private int maxCooldown;
    public Warrior(String name, int healthPool, int atkPoints, int defPoints, int maxCooldown, Position position, List<Enemy> enemies) {
        super(name, healthPool, atkPoints, defPoints, position, new WarriorAbility(healthPool), enemies);
        this.maxCooldown = maxCooldown;
    }
    public void cast(){
        if(cooldown > 0){
            throw new RuntimeException("Avenger's Shield is on cooldown");
        }
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

    @Override
    public String description() {
        return "";
    }
}
