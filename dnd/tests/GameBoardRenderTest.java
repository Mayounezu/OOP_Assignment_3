package dnd.tests;

import dnd.board.Cell;
import dnd.board.Floor;
import dnd.board.GameBoard;
import dnd.board.Position;
import dnd.board.Wall;
import dnd.unit.enemy.Monster;

public class GameBoardRenderTest {
    public static void run() {
        testRenderShowsWallsAndFloors();
        testRenderShowsOccupants();
    }

    private static GameBoard threeByOneBoard() {
        GameBoard board = new GameBoard(3, 1);
        board.setCell(0, 0, new Cell(new Wall()));
        board.setCell(1, 0, new Cell(new Floor()));
        board.setCell(2, 0, new Cell(new Floor()));
        return board;
    }

    private static void testRenderShowsWallsAndFloors() {
        GameBoard board = threeByOneBoard();
        String rendered = board.toString().trim();
        Assert.assertEquals("unoccupied board renders walls and floors", "#..", rendered);
    }

    private static void testRenderShowsOccupants() {
        GameBoard board = threeByOneBoard();
        Monster m = new Monster("Tester", 'x', 50, 5, 5, new Position(1, 0), 10, 3);
        board.setOccupant(new Position(1, 0), m);
        String rendered = board.toString().trim();
        Assert.assertEquals("occupied cell renders the occupant's tile, not the bare terrain", "#x.", rendered);
    }
}
