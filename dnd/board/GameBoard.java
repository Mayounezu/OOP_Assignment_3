package dnd.board;

import dnd.occupant.Occupant;

public class GameBoard {
    private Cell[][] board;

    public GameBoard(int width, int height) {
        board = new Cell[height][width];
    }

    public Cell getCell(Position p) {
        return getCell(p.getX(), p.getY());
    }

    public Cell getCell(int x, int y) {
        if (x < 0 || x >= board[0].length || y < 0 || y >= board.length) {
            throw new IndexOutOfBoundsException("Coordinates out of bounds");
        }
        return board[y][x];
    }

    public void setCell(int x, int y, Cell cell) {
        if (x < 0 || x >= board[0].length || y < 0 || y >= board.length) {
            throw new IndexOutOfBoundsException("Coordinates out of bounds");
        }
        board[y][x] = cell;
    }

    public Occupant getOccupant(Position position) {
        return getCell(position.getX(), position.getY()).getTerrain().getOccupant();
    }

    public void setOccupant(Position position, Occupant occupant) {
        getCell(position.getX(), position.getY()).getTerrain().setOccupant(occupant);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                sb.append(board[y][x].toString());
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
