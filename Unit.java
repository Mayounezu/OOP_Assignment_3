public abstract class Unit{
    private final String name;
    private int healthPool;
    private int healthAmount;
    private int atkPts;
    private int defPts;

    protected Unit(String name, int healthPool, int atkPts, int defPts){
        this.name = name;
        this.healthAmount = healthPool;
        if (healthPool <= 0) {
            this.healthPool = 1;
        } else {
            this.healthPool = healthPool;
        }
        this.atkPts = atkPts;
        this.defPts = defPts;
    }

    public int getAtkPts() {
        return atkPts;
    }

    public int getHealthAmount() {
        return healthAmount;
    }

    public int getHealthPool() {
        return healthPool;
    }

    public String getName() {
        return name;
    }

    public int getDefPts() {
        return defPts;
    }

    public void setAtkPts(int atkPts) {
        this.atkPts = atkPts;
    }

    public void setDefPts(int defPts) {
        this.defPts = defPts;
    }

    public void setHealthAmount(int healthAmount) {
        if (healthAmount < 0) {
            this.healthAmount = 0;
        } else if (healthAmount > healthPool) {
            this.healthAmount = healthPool;
        } else {
            this.healthAmount = healthAmount;
        }
    }

    public void setHealthPool(int healthPool) {
        if (healthPool <= 0) {
            this.healthPool = 1;
        } else {
            this.healthPool = healthPool;
        }
    }

    public void visit(Floor floor){
        if (floor.getOccupant() == null) {
            floor.setOccupant(this);
        } else {
            throw new IllegalStateException("Cannot place a unit on a non-empty floor");
        }
    }
    public void visit(Wall wall){
        throw new IllegalStateException("Cannot place a unit on a wall");
    }
    
}