package dnd.tests;

import java.util.ArrayList;

import dnd.board.Position;
import dnd.unit.enemy.Monster;
import dnd.unit.player.Warrior;

public class MonsterMovementTest {
    public static void run() {
        testChasesPlayerWithinVisionRange();
        testWandersOutsideVisionRange();
    }

    private static void testChasesPlayerWithinVisionRange() {
        Monster m = new Monster("Chaser", 'x', 50, 5, 5, new Position(5, 5), 10, 10);
        Warrior player = new Warrior("Player", 100, 10, 2, 3, new Position(5, 0), new ArrayList<>());
        // player is directly above (dy dominates), so the monster should move up (y - 1)
        Position move = m.decideMove(player);
        Assert.assertEquals("chases toward the player on the dominant axis (up)", 4, move.getY());
        Assert.assertEquals("x is unchanged when dy dominates", 5, move.getX());
    }

    private static void testWandersOutsideVisionRange() {
        Monster m = new Monster("Wanderer", 'x', 50, 5, 5, new Position(5, 5), 10, 1);
        Warrior player = new Warrior("Player", 100, 10, 2, 3, new Position(50, 50), new ArrayList<>());
        Position move = m.decideMove(player);
        boolean isAdjacentOrSame = Math.abs(move.getX() - 5) + Math.abs(move.getY() - 5) <= 1;
        Assert.assertTrue("random wander only moves to an adjacent cell or stays put", isAdjacentOrSame);
    }
}
