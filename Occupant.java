import java.util.Random;

public abstract class Occupant implements CellVisitor {
    protected char tile;
    public Occupant(char tile) {
        this.tile = tile;
    }

    public String toString() {
        return "" + tile;
    }
    public abstract void accept(OccupantVisitor visitor);
    public abstract void visit(Floor floor);
    public abstract void visit(Wall wall);
    public abstract void visit(Player player);
    public abstract void visit(Enemy enemy);
}
