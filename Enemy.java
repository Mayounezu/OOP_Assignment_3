public abstract class Enemy extends Unit {

    protected final int experienceValue;

    protected Enemy(String name, Character tile, int healthPool, int atkPts, int defPts, int experienceValue, Position position) {
        super(name, tile, healthPool, atkPts, defPts, position);
        this.experienceValue = experienceValue;
    }

    public int getExperienceValue() {
        return experienceValue;
    }

    @Override
    public void accept(OccupantVisitor visitor) {
        visitor.visit(this);
    }

    public abstract void processTurn();
}