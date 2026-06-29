package dnd.tests;

public class TestRunner {
    public static void main(String[] args) {
        PositionTest.run();
        GameBoardRenderTest.run();
        UnitCombatTest.run();
        PlayerLevelingTest.run();
        AbilityResourceTest.run();
        AbilityRangeTest.run();
        VisitorMovementTest.run();
        TrapVisibilityTest.run();
        MonsterMovementTest.run();
        BossTest.run();

        int passed = Assert.getPassCount();
        int failed = Assert.getFailCount();
        System.out.println();
        System.out.println(passed + " passed, " + failed + " failed.");
        if (failed > 0) {
            System.exit(1);
        }
    }
}
