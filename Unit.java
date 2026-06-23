public class Unit{
    private String name;
    private int healthPool;
    private int healthAmount;
    private int atkPts;
    private int defPts;

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
        this.healthAmount = healthAmount;
    }

    public void setHealthPool(int healthPool) {
        this.healthPool = healthPool;
    }

    public void setName(String name) {
        this.name = name;
    }
}