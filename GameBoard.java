public class GameBoard {
    private Cell[][] board;

    public GameBoard(int width, int height) {
        board = new Cell[height][width];
    }

    public Cell getCell(int x, int y) {
        if (x < 0 || x >= board[0].length || y < 0 || y >= board.length) {
            throw new IndexOutOfBoundsException("Coordinates out of bounds");
        }
        return board[y][x];
    }

    public Occupant getOccupant(Position position) {
        return getCell(position.getX(), position.getY()).getTerrain().getOccupant();
    }

    public void setOccupant(Position position, Occupant occupant) {
        getCell(position.getX(), position.getY()).getTerrain().setOccupant(occupant);
    }

    public void render(){
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                System.out.print(board[y][x].getTerrain().toString());
            }
            System.out.println();
        }
    }
}