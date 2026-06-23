public class GameBoard {
    private Unit[][] board;
    private int rows;
    private int cols;

    public GameBoard(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        board = new Unit[rows][cols];
    }

    public boolean placeUnit(Unit unit, int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return false; // Out of bounds
        }
        if (board[row][col] != null) {
            return false; //  already occupied
        }
        board[row][col] = unit;
        return true;
    }

    public Unit getUnit(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return null; // Out of bounds
        }
        return board[row][col];
    }

    public boolean removeUnit(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return false; // Out of bounds
        }
        if (board[row][col] == null) {
            return false; // No unit to remove
        }
        board[row][col] = null;
        return true;
    }

    public void displayBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] != null) {
                    System.out.print(board[i][j].getName() + "\t");
                } else {
                    System.out.print("Empty\t");
                }
            }
            System.out.println();
        }
    }
}