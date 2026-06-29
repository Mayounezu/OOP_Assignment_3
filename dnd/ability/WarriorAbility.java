package dnd.ability;

import java.util.ArrayList;
import java.util.List;

import dnd.board.Position;
import dnd.unit.Unit;
import dnd.unit.enemy.Enemy;

public class WarriorAbility implements Ability {
    private final Unit owner;

    public WarriorAbility(Unit owner) {
        this.owner = owner;
    }

    public void cast(Position position, List<Enemy> enemies) {
        List<Enemy> inRange = new ArrayList<>();
        for(Enemy e : enemies){
            if(e.getPosition().distance(position) < 3){
                inRange.add(e);
            }
        }
        if(!inRange.isEmpty()){
            int atkIndex = (int)(Math.random() * inRange.size());
            inRange.get(atkIndex).dealDamage(owner.getHealthPool() / 10);
        }
    }
}
