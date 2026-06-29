package dnd.visitor;

import dnd.board.Floor;
import dnd.board.Wall;

public interface CellVisitor {
    public void visit(Floor floor);
    public void visit(Wall wall);
}
