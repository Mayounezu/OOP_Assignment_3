public interface Terrain {
    public void accept(Occupant Occupant);
    public String toString();
    public void setOccupant(Occupant occupant);
    public Occupant getOccupant();
}
