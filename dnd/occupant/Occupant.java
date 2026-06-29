package dnd.occupant;

import dnd.board.Floor;
import dnd.board.Wall;
import dnd.visitor.CellVisitor;
import dnd.visitor.OccupantVisitor;

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
}
