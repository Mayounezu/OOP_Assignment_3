public class Mage extends Player{
    private int range;
    private int manaPool;
    private int currentMana;
    private int manaCost;
    private int spellPower;
    private int hitCount;
    public Mage(String name, int healthPool, int atkPoints, int defPoints, int range, int manaPool, int manaCost, int spellPower, int hitCount, Position position){
        super(name, healthPool, atkPoints, defPoints, position, new MageAbility(hitCount, spellPower, range));
        this.range = range;
        this.manaPool = manaPool;
        this.manaCost = manaCost;
        this.spellPower = spellPower;
        this.hitCount = hitCount;
        currentMana = manaPool / 4;
    }
    public void levelUp(){
        super.levelUp();
        manaPool += level * 25;
        currentMana = Math.min(currentMana + manaPool / 4, manaPool);
        spellPower += level * 10;
    }
    public void updateGameTick(){
        currentMana = Math.min(currentMana + level, manaPool);
    }
    public void cast(){
        if(currentMana < manaCost){
            throw new RuntimeException("Not enough mana for Blizzard");
        }
        super.cast();
    }
}
