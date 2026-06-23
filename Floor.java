public class Floor implements Terrain {
    private Occupant occupant;
    private char tile = '.';

    public Floor() {
        this.occupant = null;
    }
    public Floor(Occupant occupant) {
        this.occupant = occupant;
    }

    public Occupant getOccupant() {
        return occupant;
    }

    public void setOccupant(Occupant occupant) {
        if (this.occupant != null) {
            throw new IllegalStateException("Cannot place a unit on a non-empty floor");
        }
        this.occupant = occupant;
    }

    @Override
    public void accept(Occupant occupant) {
        occupant.visit(this);
    }

    @Override
    public String toString() {
        return "" + tile;
    }
}
