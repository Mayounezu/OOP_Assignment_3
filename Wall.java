public class Wall implements Terrain {
    public Wall() {}
    @Override
    public void accept(Unit unit) {
        unit.visit(this);
    }
}
