package dnd.ability;

import java.util.ArrayList;
import java.util.List;

import dnd.board.Position;
import dnd.unit.enemy.Enemy;

public class MageAbility implements Ability {
    private int hitCount;
    private int spellPower;
    private int range;
    public MageAbility(int hitCount, int spellPower, int range) {
        this.hitCount = hitCount;
        this.spellPower = spellPower;
        this.range = range;
    }
    public void cast(Position position, List<Enemy> enemies) {
        int hits = 0;
        List<Enemy> enemiesToCast = new ArrayList<>();
        for(Enemy  e: enemies){
            if(e.getPosition().distance(position) <= range){
                enemiesToCast.add(e);
            }
        }
        while (hits < this.hitCount && enemiesToCast.size() > 0) {
            int enemyIndex = (int)(Math.random() * enemies.size());
            enemiesToCast.get(enemyIndex).dealDamage(spellPower);
            hits++;
        }
    }
}
