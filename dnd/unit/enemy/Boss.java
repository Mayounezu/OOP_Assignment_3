package dnd.unit.enemy;

import dnd.ability.BossAbility;
import dnd.board.GameBoard;
import dnd.board.Position;
import dnd.unit.CombatResult;
import dnd.unit.HeroicUnit;
import dnd.unit.player.Player;

public class Boss extends Monster implements HeroicUnit{

    private final BossAbility ability = new BossAbility();
    private int combatTicks;
    private final int abilityFrequency;

    // The player the boss is currently engaging; set each turn so the no-arg
    // HeroicUnit.castAbility() has a target to shoot at.
    private Player currentTarget;
    private CombatResult lastAbilityResult;

    public Boss(String name, Character tile, int healthPool, int atkPts,
         int defPts, Position position, int experienceValue, int visionRange, int abilityFrequency) {
        super(name, tile, healthPool, atkPts, defPts, position, experienceValue,visionRange);
        this.abilityFrequency = abilityFrequency;
        combatTicks = 0;
    }

    @Override
    public void castAbility() {
        lastAbilityResult = ability.cast(this, currentTarget);
    }

    @Override
    public CombatResult processTurn(GameBoard board, Player player) {
        boolean inVisionRange = getPosition().distance(player.getPosition()) < getVisionRange();
        if (inVisionRange && combatTicks == abilityFrequency) {
            // Per the spec the boss casts instead of moving on this turn.
            combatTicks = 0;
            currentTarget = player;
            castAbility();
            return lastAbilityResult;
        }
        if (inVisionRange) {
            combatTicks++;
        } else {
            combatTicks = 0;
        }
        // Not casting this turn: move like a monster (chase if in range, else wander).
        return super.processTurn(board, player);
    }

    @Override
    public String description() {
        return baseStatus() + " | Vision Range: " + getVisionRange() + " | Ability Frequency: " + abilityFrequency
                + " | Exp Value: " + getExperienceValue();
    }
}
