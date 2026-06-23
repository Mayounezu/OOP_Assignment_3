public class Warrior extends Player {
    public Warrior(String name, int healthPool, int atkPoints, int defPoints, int maxCooldown) {
        super(name, healthPool, atkPoints, defPoints);
        ability = new  WarriorAbility(maxCooldown);
    }
    public void cast(){
        super.cast();
        setHealthAmount(Math.min(getHealthAmount() + 10 * getDefPts(), getHealthPool()));
    }
    public void levelUp(){
        super.levelUp();
        ((WarriorAbility) ability).setCooldown(0);
        setHealthPool(getHealthPool() + (level * 5));
        setAtkPts(getAtkPts() + (level * 2));
        setDefPts(getDefPts() + level);
    }
    public void updateGameTick(){
        ((WarriorAbility) ability).setCooldown(((WarriorAbility) ability).getCooldown() - 1);
    }
}
