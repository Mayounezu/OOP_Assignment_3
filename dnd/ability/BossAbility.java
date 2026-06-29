package dnd.ability;

import dnd.unit.CombatResult;
import dnd.unit.Unit;
import dnd.unit.player.Player;

/**
 * Encapsulates the Boss "Shoebodybop" ability: the boss shoots the player for an amount
 * equal to the boss's attack points, and the player tries to defend itself. Returns a
 * CombatResult so the engine can report the shot through the same channel as melee combat.
 */
public class BossAbility {

    public CombatResult cast(Unit boss, Player player) {
        int attack = boss.getAtkPts();
        int defRoll = player.rollDefense();
        int damage = Math.max(0, attack - defRoll);
        if (damage > 0) {
            player.dealDamage(damage);
        }
        boolean defenderDied = player.getHealthAmount() <= 0;
        return new CombatResult(boss, player, attack, defRoll, damage, defenderDied);
    }
}
