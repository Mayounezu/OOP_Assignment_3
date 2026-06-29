package dnd.tests;

import dnd.board.Position;
import dnd.unit.enemy.Trap;

public class TrapVisibilityTest {
    public static void run() {
        testVisibilityCyclesCorrectly();
    }

    private static void testVisibilityCyclesCorrectly() {
        // visibility_time=2, invisibility_time=3 -> cycle length 5
        // Per the spec's literal pseudocode, the reset only fires once ticks_count reaches
        // (visibility_time + invisibility_time), so the cycle actually spans that many + 1
        // ticks (values 0..5 here) -- 2 visible ticks followed by 4 invisible ticks.
        Trap trap = new Trap("Test Trap", 'B', 10, 5, 5, 25, 2, 3, new Position(0, 0));
        String[] expected = {"B", "B", ".", ".", ".", ".", "B", "B", ".", "."};
        for (int i = 0; i < expected.length; i++) {
            trap.updateGameTick();
            Assert.assertEquals("rendered tile at tick " + (i + 1), expected[i], trap.toString());
        }
    }
}
