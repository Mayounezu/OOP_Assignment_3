public class Wall implements Terrain {
    private final char tile = '#';
    public Wall() {}
    @Override
    public void accept(Occupant occupant) {
        occupant.visit(this);
    }
    @Override
    public String toString() {
        return "" + tile;
    }
}
