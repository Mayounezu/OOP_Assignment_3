package dnd.ability;

import java.util.List;

import dnd.board.Position;
import dnd.unit.Unit;
import dnd.unit.enemy.Enemy;

public class HunterAbility implements Ability {
    private final Unit owner;
    private final int range;

    public HunterAbility(Unit owner, int range) {
        this.owner = owner;
        this.range = range;
    }

    public void cast(Position position, List<Enemy> enemies){
        double minDistance = range + 1;
        Enemy toAttack = null;
        for(Enemy e :  enemies){
            double d = e.getPosition().distance(position);
            if(d < minDistance){
                minDistance = d;
                toAttack = e;
            }
        }
        if(toAttack != null && minDistance <= range){
            int defRoll = toAttack.rollDefense();
            int damage = Math.max(0, owner.getAtkPts() - defRoll);
            toAttack.dealDamage(damage);
        }
        else{
            throw new RuntimeException("no enemy in range of Shoot");
        }
    }
}
