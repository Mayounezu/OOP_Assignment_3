package dnd.unit.player;

import java.util.List;

import dnd.ability.HunterAbility;
import dnd.board.Position;
import dnd.unit.HeroicUnit;
import dnd.unit.enemy.Enemy;

public class Hunter extends Player implements HeroicUnit {
    private int range;
    private int arrows;
    private int ticksCount = 0;
    public Hunter(String name, int healthPool, int atkPoints, int defPoints, Position position, List<Enemy> enemies, int range){
        super(name, healthPool, atkPoints, defPoints, position, null, enemies);
        this.range = range;
        arrows = level * 10;
        this.ability = new HunterAbility(this, range);
    }
    public void levelUp(){
        super.levelUp();
        arrows += level * 10;
        setAtkPts(getAtkPts() + level * 2);
        setDefPts(getDefPts() + level);
    }
    public void updateGameTick(){
        if(ticksCount == 10){
            arrows += level;
            ticksCount = 0;
        }
        else{
            ticksCount++;
        }
    }
    public void castAbility() {
        if (arrows <= 0) {
            throw new RuntimeException("Out of arrows can't Shoot");
        }
        super.castAbility();
        arrows--;
    }

    public String description(){
        return baseStatus() + " | Level: " + getLevel() + " | XP: " + getExperience() + "/" + (50 * getLevel())
                + " | Arrows: " + arrows;
    }

    public int getArrows() {
        return arrows;
    }
}
