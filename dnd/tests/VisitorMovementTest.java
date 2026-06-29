package dnd.tests;

import java.util.ArrayList;
import java.util.List;

import dnd.board.Cell;
import dnd.board.Floor;
import dnd.board.GameBoard;
import dnd.board.Position;
import dnd.board.Wall;
import dnd.unit.CombatResult;
import dnd.unit.enemy.Enemy;
import dnd.unit.enemy.Monster;
import dnd.unit.player.Warrior;

public class VisitorMovementTest {
    public static void run() {
        testFreeMoveVacatesOldCellAndOccupiesNew();
        testWallBlocksMovement();
        testSteppingOntoEnemyTriggersCombatInsteadOfImmediateMove();
        testVacatesCellOnDeathPolymorphism();
        testEnemyDoesNotTakeOverDeadPlayersCell();
    }

    private static GameBoard emptyBoard(int width, int height) {
        GameBoard board = new GameBoard(width, height);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                board.setCell(x, y, new Cell(new Floor()));
            }
        }
        return board;
    }

    private static void testFreeMoveVacatesOldCellAndOccupiesNew() {
        GameBoard board = emptyBoard(3, 3);
        List<Enemy> enemies = new ArrayList<>();
        Warrior w = new Warrior("Mover", 100, 10, 2, 3, new Position(0, 0), enemies);
        board.setOccupant(new Position(0, 0), w);

        CombatResult result = w.attemptStep(board, new Position(1, 0));

        Assert.assertEquals("free move produces no combat result", null, result);
        Assert.assertEquals("unit's position is updated", 1, w.getPosition().getX());
        Assert.assertEquals("old cell is vacated", null, board.getOccupant(new Position(0, 0)));
        Assert.assertEquals("new cell holds the mover", w, board.getOccupant(new Position(1, 0)));
    }

    private static void testWallBlocksMovement() {
        GameBoard board = emptyBoard(3, 3);
        board.setCell(1, 0, new Cell(new Wall()));
        List<Enemy> enemies = new ArrayList<>();
        Warrior w = new Warrior("Mover", 100, 10, 2, 3, new Position(0, 0), enemies);
        board.setOccupant(new Position(0, 0), w);

        CombatResult result = w.attemptStep(board, new Position(1, 0));

        Assert.assertEquals("walking into a wall yields no combat result", null, result);
        Assert.assertEquals("position is unchanged when blocked by a wall", 0, w.getPosition().getX());
        Assert.assertEquals("mover remains the occupant of its original cell", w, board.getOccupant(new Position(0, 0)));
    }

    private static void testSteppingOntoEnemyTriggersCombatInsteadOfImmediateMove() {
        GameBoard board = emptyBoard(3, 3);
        List<Enemy> enemies = new ArrayList<>();
        Warrior w = new Warrior("Mover", 100, 10, 2, 3, new Position(0, 0), enemies);
        Monster enemy = new Monster("Blocker", 'x', 1000, 5, 5, new Position(1, 0), 10, 3);
        board.setOccupant(new Position(0, 0), w);
        board.setOccupant(new Position(1, 0), enemy);

        CombatResult result = w.attemptStep(board, new Position(1, 0));

        Assert.assertTrue("stepping onto a living enemy produces a combat result", result != null);
        Assert.assertEquals("mover does not occupy the target cell while the defender survives",
                w, board.getOccupant(new Position(0, 0)));
        Assert.assertEquals("defender remains on its cell since it has overwhelming health",
                enemy, board.getOccupant(new Position(1, 0)));
    }

    private static void testVacatesCellOnDeathPolymorphism() {
        Monster enemy = new Monster("Enemy", 'x', 10, 5, 5, new Position(0, 0), 10, 3);
        Warrior player = new Warrior("Player", 10, 5, 5, 3, new Position(0, 0), new ArrayList<>());
        Assert.assertTrue("an enemy's cell becomes free when it dies (player can take over)",
                enemy.vacatesCellOnDeath());
        Assert.assertFalse("the player's cell stays occupied by its corpse when it dies",
                player.vacatesCellOnDeath());
    }

    private static void testEnemyDoesNotTakeOverDeadPlayersCell() {
        GameBoard board = emptyBoard(3, 3);
        List<Enemy> enemies = new ArrayList<>();
        Warrior player = new Warrior("Victim", 1, 0, 0, 3, new Position(1, 0), enemies);
        Monster attacker = new Monster("Killer", 'x', 100, 50, 0, new Position(0, 0), 10, 3);
        board.setOccupant(new Position(1, 0), player);
        board.setOccupant(new Position(0, 0), attacker);

        CombatResult result = null;
        for (int i = 0; i < 1000 && (result == null || !result.isDefenderDied()); i++) {
            player.setHealthAmount(1);
            result = attacker.attemptStep(board, new Position(1, 0));
        }

        Assert.assertTrue("eventually killed the player", result != null && result.isDefenderDied());
        Assert.assertEquals("the dead player's cell still holds the player, not the killer",
                player, board.getOccupant(new Position(1, 0)));
        Assert.assertEquals("the killer never moved off its own cell",
                attacker, board.getOccupant(new Position(0, 0)));
    }
}
