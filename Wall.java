public class Wall implements Terrain {
    private final char tile = '#';
    public Wall() {}
    @Override
    public void accept(CellVisitor visitor) {
        visitor.visit(this);
    }
    @Override
    public String toString() {
        return "" + tile;
    }

    @Override
    public void setOccupant(Occupant occupant) {
        throw new IllegalStateException("Cannot place a unit on a wall");
    }

    @Override
    public Occupant getOccupant() {
        return null;
    }

}
