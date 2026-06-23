public class Floor implements Terrain {
    Unit occupant;
    public Floor() {
        this.occupant = null;
    }
    public Floor(Unit occupant) {
        this.occupant = occupant;
    }

    public Unit getOccupant() {
        return occupant;
    }

    public void setOccupant(Unit occupant) {
        if (this.occupant != null) {
            throw new IllegalStateException("Cannot place a unit on a non-empty floor");
        }
        this.occupant = occupant;
    }

    @Override
    public void accept(Unit unit) {
        unit.visit(this);
    }

}
