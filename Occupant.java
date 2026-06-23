public abstract class Occupant {
    private char tile;
    public Occupant(char tile) {
        this.tile = tile;
    }

    public String toString() {
        return "" + tile;
    }
}
