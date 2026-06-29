package dnd.ability;

import java.util.ArrayList;
import java.util.List;

import dnd.board.Position;
import dnd.unit.enemy.Enemy;
import dnd.unit.player.Mage;

public class MageAbility implements Ability {
    private final Mage owner;
    private final int hitCount;
    private final int range;

    public MageAbility(Mage owner, int hitCount, int range) {
        this.owner = owner;
        this.hitCount = hitCount;
        this.range = range;
    }

    public void cast(Position position, List<Enemy> enemies) {
        int hits = 0;
        while (hits < hitCount) {
            List<Enemy> candidates = new ArrayList<>();
            for (Enemy e : enemies) {
                if (e.getHealthAmount() > 0 && e.getPosition().distance(position) < range) {
                    candidates.add(e);
                }
            }
            if (candidates.isEmpty()) {
                break;
            }
            int enemyIndex = (int) (Math.random() * candidates.size());
            Enemy target = candidates.get(enemyIndex);
            int defRoll = target.rollDefense();
            int damage = Math.max(0, owner.getSpellPower() - defRoll);
            target.dealDamage(damage);
            hits++;
        }
    }
}
