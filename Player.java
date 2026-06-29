import java.util.List;
import java.util.Random;

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

    public void accept(OccupantVisitor visitor){
        visitor.visit(this);
    }

    public void visit(Player player){
        throw new UnsupportedOperationException("Players cannot battle other players");
    }

    public void visit(Enemy enemy){
        startBattle(enemy);
    }

    public void startBattle(Enemy enemy){
        Random rand = new Random();
        int atkRoll = rand.nextInt(atkPts);
        int defRoll = rand.nextInt(enemy.getDefPts());
        if (atkRoll > defRoll) {
            enemy.dealDamage(atkRoll - defRoll);
        }
    }
}