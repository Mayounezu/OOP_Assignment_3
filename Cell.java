private class Cell{
    private final String state;
    private Unit occupant;

    public Cell(String state) {
        this.state = state;
        this.occupant = null;
    }

    public String getState() {
        return state;
    }

    public void setOccupant(Unit occupant) {
        if (state.equals("floor")) {
            this.occupant = occupant;
        } else {
            throw new IllegalStateException("Cannot place a unit on a non-empty cell.");
        }
    }
}