package dnd.unit;

public class CombatResult {
    private final Unit attacker;
    private final Unit defender;
    private final int attackRoll;
    private final int defenseRoll;
    private final int damage;
    private final boolean defenderDied;

    public CombatResult(Unit attacker, Unit defender, int attackRoll, int defenseRoll, int damage, boolean defenderDied) {
        this.attacker = attacker;
        this.defender = defender;
        this.attackRoll = attackRoll;
        this.defenseRoll = defenseRoll;
        this.damage = damage;
        this.defenderDied = defenderDied;
    }

    public Unit getAttacker() {
        return attacker;
    }

    public Unit getDefender() {
        return defender;
    }

    public int getAttackRoll() {
        return attackRoll;
    }

    public int getDefenseRoll() {
        return defenseRoll;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isDefenderDied() {
        return defenderDied;
    }
}
