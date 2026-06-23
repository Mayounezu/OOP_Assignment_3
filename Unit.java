public abstract class Unit{
    protected final String name;
    protected int healthPool;
    protected int healthAmount;
    protected int atkPts;
    protected int defPts;
    protected Position position;

    
protected Unit(String name, int healthPool, int atkPts, int defPts, Position position){

this.name = name;

this.healthAmount = healthPool;

if (healthPool <= 0) {

this.healthPool = 1;

} else {

this.healthPool = healthPool;

}

this.atkPts = atkPts;

this.defPts = defPts;

this.position = position;

}

public Position getPosition() {

return position;

}



public void setPosition(Position p) {

this.position = p;

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
    
    public abstract void updateGameTick();
}