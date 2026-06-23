import java.util.Random;

public abstract class Unit{
    protected final String name;
    protected int healthPool;
    protected int healthAmount;
    protected int atkPts;
    protected int defPts;
    protected Position position;
    protected Character tile;


    
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

    public Character GetTile(){
        return tile;
    }
    public abstract void accept(Unit unit);

    public void visit(Floor floor){
        if (floor.getOccupant() == null) {
            floor.setOccupant(this);
        } else {
            floor.getOccupant().accept(this);
        }
    }

    public void visit(Wall wall){
        throw new IllegalArgumentException("Cannot move into a wall");
    }

    public void visit(Player player){
        startBattle(player);
    }

    public void visit(Enemy enemy){
        startBattle(enemy);
    }

    public void startBattle(Unit enemy){
        Random rand = new Random();
        int atkRoll = rand.nextInt(atkPts);
        int defRoll = rand.nextInt(enemy.getDefPts());
        if (atkRoll > defRoll) {
            enemy.dealDamage(atkRoll - defRoll);
        }
    }

    public void dealDamage(int damage){
        setHealthAmount(healthAmount - damage);
    }

    public abstract void updateGameTick();
}