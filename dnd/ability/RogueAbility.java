package dnd.ability;

import java.util.List;

import dnd.board.Position;
import dnd.unit.enemy.Enemy;

public class RogueAbility implements Ability {
    private int atkPoints;
    public RogueAbility(int atkPoints) {
        this.atkPoints = atkPoints;
    }
    public void cast(Position position, List<Enemy> enemies) {
        for(Enemy e :  enemies) {
            if(e.getPosition().distance(position) <= 2) {
                e.dealDamage(atkPoints);
            }
        }
    }
}
