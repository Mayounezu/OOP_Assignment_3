package dnd.unit.player;

import java.util.List;

import dnd.ability.WarriorAbility;
import dnd.board.Position;
import dnd.unit.HeroicUnit;
import dnd.unit.enemy.Enemy;

public class Warrior extends Player implements HeroicUnit {
    private int cooldown = 0;
    private int maxCooldown;
    public Warrior(String name, int healthPool, int atkPoints, int defPoints, int maxCooldown, Position position, List<Enemy> enemies) {
        super(name, healthPool, atkPoints, defPoints, position, new WarriorAbility(healthPool), enemies);
        this.maxCooldown = maxCooldown;
    }
    public void castAbility(){
        if(cooldown > 0){
            throw new RuntimeException("Avenger's Shield is on cooldown");
        }
        super.castAbility();
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
