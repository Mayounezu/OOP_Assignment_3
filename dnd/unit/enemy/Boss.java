package dnd.unit.enemy;

import java.util.Random;

import dnd.board.GameBoard;
import dnd.board.Position;
import dnd.unit.CombatResult;
import dnd.unit.HeroicUnit;
import dnd.unit.player.Player;

public class Boss extends Monster implements HeroicUnit{

    private int combatTicks;
    private int abilityFrequency;

    public Boss(String name, Character tile, int healthPool, int atkPts,
         int defPts, Position position, int experienceValue, int visionRange, int abilityFrequency) {
        super(name, tile, healthPool, atkPts, defPts, position, experienceValue,visionRange);
        this.abilityFrequency = abilityFrequency;
        combatTicks = 0;
    }

    public void castAbility() {

    }

    private CombatResult shoebodybop(Player player) {
        Random rand = new Random();
        int defRoll = rand.nextInt(player.getDefPts());
        int damage = Math.max(0, atkPts - defRoll);
        if (damage > 0) {
            player.dealDamage(damage);
        }
        boolean defenderDied = player.getHealthAmount() <= 0;
        return new CombatResult(this, player, atkPts, defRoll, damage, defenderDied);
    }

    @Override
    public CombatResult processTurn(GameBoard board, Player player) {
        CombatResult abilityResult = null;
        if (getPosition().distance(player.getPosition()) < getVisionRange()) {
            if (combatTicks == abilityFrequency) {
                combatTicks = 0;
                abilityResult = shoebodybop(player);
            } else {
                combatTicks++;
            }
        } else {
            combatTicks = 0;
        }
        CombatResult moveResult = super.processTurn(board, player);
        return moveResult != null ? moveResult : abilityResult;
    }

    @Override
    public String description() {
        return "A Boss is an enemy class that behaves like a monster but can cast a special ability.  ";
    }
}
