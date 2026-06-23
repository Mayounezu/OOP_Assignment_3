public class MageAbility implements Ability {
    private int hitCount;
    private int spellPower;
    private int range;
    public MageAbility(int hitCount, int spellPower, int range) {
        this.hitCount = hitCount;
        this.spellPower = spellPower;
        this.range = range;
    }
    public void cast(Position position) {
        int hits = 0;
        while (hits < this.hitCount && ) {
            //hit enemy in range
            hits++;
        }
    }
}
