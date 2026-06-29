package dnd.board;

import dnd.occupant.Occupant;
import dnd.visitor.CellVisitor;

public interface Terrain {
    public void accept(CellVisitor visitor);
    public String toString();
    public void setOccupant(Occupant occupant);
    public Occupant getOccupant();
}
