package dnd.unit.player;

import java.util.List;

import dnd.ability.RogueAbility;
import dnd.board.Position;
import dnd.unit.HeroicUnit;
import dnd.unit.enemy.Enemy;

public class Rogue extends Player implements HeroicUnit {
    private int energy = 100;
    private int cost;
    public Rogue(String name, int healthPool, int atkPoints, int defPoints,int cost, Position position, List<Enemy> enemies) {
        super(name, healthPool, atkPoints, defPoints,  position, null, enemies);
        this.ability = new RogueAbility(this);
        this.cost = cost;
    }
    public void levelUp() {
        super.levelUp();
        energy = 100;
        setAtkPts(getAtkPts() + (level * 3));
    }
    public void castAbility(){
        if(energy < cost){
            throw new RuntimeException("Not enough energy to cast Fan Of Knives");
        }
        super.castAbility();
        energy -= cost;
    }
    public void updateGameTick(){
        energy = Math.min(energy + 10, 100);
    }

    public int getEnergy() {
        return energy;
    }

    @Override
    public String description() {
        return baseStatus() + " | Level: " + getLevel() + " | XP: " + getExperience() + "/" + (50 * getLevel())
                + " | Energy: " + energy + "/100";
    }
}
