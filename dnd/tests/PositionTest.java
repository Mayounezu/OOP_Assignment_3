package dnd.tests;

import dnd.board.Position;

public class PositionTest {
    public static void run() {
        testBasicDistance();
        testZeroDistance();
        testNegativeCoordinateRejected();
    }

    private static void testBasicDistance() {
        Position a = new Position(0, 0);
        Position b = new Position(3, 4);
        Assert.assertEquals("3-4-5 triangle distance", 5.0, a.distance(b));
    }

    private static void testZeroDistance() {
        Position a = new Position(7, 2);
        Assert.assertEquals("distance to self is zero", 0.0, a.distance(a));
    }

    private static void testNegativeCoordinateRejected() {
        Position p = new Position(0, 0);
        Assert.assertThrows("setX rejects negative", () -> p.setX(-1));
        Assert.assertThrows("setY rejects negative", () -> p.setY(-1));
    }
}
