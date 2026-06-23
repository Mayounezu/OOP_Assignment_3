public abstract class Player extends Unit
{
    protected int experience = 0;
    protected int level = 1;

    protected Player(String name, int healthPool, int atkPoints, int defPoints)
    {
        super(name, healthPool, atkPoints, defPoints);
    }
    private void levelUp(){
        experience -= (50 * level);
        level++;
        setHealthPool(getHealthPool() + (level * 10));
        setHealthAmount(getHealthAmount() + (level * 10));
        setAtkPts(getAtkPts() + (level * 4));
        setDefPts(getDefPts() + level);
    }
    public void gainExperience(int experience)
    {
        this.experience += experience;
        if(this.experience > level * 50){
            levelUp();
        }
    }
    public abstract void cast();
}