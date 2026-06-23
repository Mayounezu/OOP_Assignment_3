public abstract class Unit{
    private final String name;
    private int healthPool;
    private int healthAmount;
    private int atkPts;
    private int defPts;
    private Character tile;

    protected Unit(String name,Character tile, int healthPool, int atkPts, int defPts){
        this.name = name;
        this.healthAmount = healthPool;
        if (healthPool <= 0) {
            this.healthPool = 1;
        } else {
            this.healthPool = healthPool;
        }
        this.atkPts = atkPts;
        this.defPts = defPts;
        this.tile = tile;
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

    public Character GetTile(){
        return tile;
    }
}