package dnd.ability;

import java.util.List;

import dnd.board.Position;
import dnd.unit.Unit;
import dnd.unit.enemy.Enemy;

public class RogueAbility implements Ability {
    private final Unit owner;

    public RogueAbility(Unit owner) {
        this.owner = owner;
    }

    public void cast(Position position, List<Enemy> enemies) {
        for(Enemy e :  enemies) {
            if(e.getPosition().distance(position) < 2) {
                int defRoll = e.rollDefense();
                int damage = Math.max(0, owner.getAtkPts() - defRoll);
                e.dealDamage(damage);
            }
        }
    }
}
