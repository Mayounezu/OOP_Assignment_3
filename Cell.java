public class Cell{
    private final Terrain terrain;

    public Cell(Terrain terrain) {
        this.terrain = terrain;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public void accept(Unit unit) {
        terrain.accept(unit);
    }
}