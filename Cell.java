public class Cell{
    private final boolean isFloor;
    private Unit occupant;

    public Cell(boolean isFloor) {
        this.isFloor = isFloor;
        this.occupant = null;
    }

    public Cell(boolean isFloor, Unit occupant) {
        this.isFloor = isFloor;
        setOccupant(occupant);
    }

    public boolean getIsFloor() {
        return isFloor;
    }

    public Unit getOccupant() {
        return occupant;
    }

    public void setOccupant(Unit occupant) {
        if (isFloor && this.occupant == null) {
            this.occupant = occupant;
        } else {
            throw new IllegalStateException("Cannot place a unit on a non-empty floor, or the cell is a wall");
        }
    }
}