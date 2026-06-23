import java.util.List;
public abstract class Player extends Unit
{
    protected int experience = 0;
    protected int level = 1;
    protected Ability ability;
    protected List<Enemy> enemies;

    protected Player(String name, int healthPool, int atkPoints, int defPoints, Position position, Ability ability, List<Enemy> enemies)
    {
        super(name,'@', healthPool, atkPoints, defPoints, position);
        this.enemies = enemies;
        this.ability = ability;
    }
    public void levelUp(){
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
    public void cast(){
        ability.cast(position, enemies);
    }

    public void accept(Occupant occupant){
        occupant.visit(this);
    }
}