package dnd.unit;

import java.util.Random;

import dnd.board.Cell;
import dnd.board.Floor;
import dnd.board.GameBoard;
import dnd.board.Position;
import dnd.board.Wall;
import dnd.occupant.Occupant;
import dnd.unit.enemy.Enemy;
import dnd.unit.player.Player;
import dnd.visitor.OccupantVisitor;

public abstract class Unit extends Occupant implements OccupantVisitor {
    protected final String name;
    protected int healthPool;
    protected int healthAmount;
    protected int atkPts;
    protected int defPts;
    protected Position position;
    protected CombatResult lastCombatResult;



    protected Unit(String name, char tile, int healthPool, int atkPts, int defPts, Position position){
        super(tile);
        this.name = name;
        this.healthAmount = healthPool;
        if (healthPool <= 0) {
        this.healthPool = 1;
        }
        else {
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
    public abstract void accept(OccupantVisitor visitor);

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

    public abstract void visit(Player player);

    public abstract void visit(Enemy enemy);

    public void dealDamage(int damage){
        setHealthAmount(healthAmount - damage);
    }

    public CombatResult startBattle(Unit unit){
        Random rand = new Random();
        int atkRoll = rand.nextInt(atkPts);
        int defRoll = rand.nextInt(unit.getDefPts());
        int damage = 0;
        if (atkRoll > defRoll) {
            damage = atkRoll - defRoll;
            unit.dealDamage(damage);
        }
        boolean defenderDied = unit.getHealthAmount() <= 0;
        return new CombatResult(this, unit, atkRoll, defRoll, damage, defenderDied);
    }

    public CombatResult getLastCombatResult() {
        return lastCombatResult;
    }

    public void clearCombatResult() {
        lastCombatResult = null;
    }

    /**
     * Attempts to step onto the cell at {@code target}. Goes through the same
     * CellVisitor/OccupantVisitor double-dispatch chain used for movement and combat,
     * then resolves the board-level bookkeeping (vacating the old cell, taking over a
     * defeated defender's cell) that the visitor chain itself cannot do, since neither
     * Floor nor the visitors know this unit's previous position.
     */
    public CombatResult attemptStep(GameBoard board, Position target) {
        clearCombatResult();
        Cell cell;
        try {
            cell = board.getCell(target.getX(), target.getY());
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        boolean wasOccupied = cell.getTerrain().getOccupant() != null;
        try {
            cell.accept(this);
        } catch (RuntimeException e) {
            return null;
        }
        CombatResult result = getLastCombatResult();
        if (!wasOccupied) {
            board.setOccupant(this.position, null);
            this.position = target;
        } else if (result != null && result.isDefenderDied()) {
            board.setOccupant(this.position, null);
            board.setOccupant(target, null);
            board.setOccupant(target, this);
            this.position = target;
        }
        return result;
    }

    public abstract void updateGameTick();

    //returns the full status of the unit (override in each subclass).
    //Used during combat and on the player’s turn.
    public abstract String description();

}
