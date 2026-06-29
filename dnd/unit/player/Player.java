package dnd.unit.player;

import java.util.List;
import java.util.Random;

import dnd.ability.Ability;
import dnd.board.Position;
import dnd.unit.Unit;
import dnd.unit.enemy.Enemy;
import dnd.visitor.OccupantVisitor;

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
    public void castAbility(){
        ability.cast(position, enemies);
    }

    public void accept(OccupantVisitor visitor){
        visitor.visit(this);
    }

    public void visit(Player player){
        throw new UnsupportedOperationException("Players cannot battle other players");
    }

    public void visit(Enemy enemy){
        lastCombatResult = startBattle(enemy);
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
}
