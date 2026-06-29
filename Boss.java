public class Boss extends Monster implements HeroicUnit{

    private int combatTicks;
    Boss(String name, Character tile, int healthPool, int atkPts,
         int defPts, Position position, int experienceValue, int visionRange, int abilityFrequency) {
        super(name, tile, healthPool, atkPts, defPts, position, experienceValue,visionRange);
        combatTicks = 0;
    }

    public void castAbility() {

    }

    @Override
    public


    @Override
    public String description() {
        return "A Boss is an enemy class that behaves like a monster but can cast a special ability.  ";
    }
}
