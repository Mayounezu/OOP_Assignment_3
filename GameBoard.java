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

    public Occupant getOccupantAt(Position position) {
        return getCell(position.getX(), position.getY()).getTerrain().getOccupant();
    }

}