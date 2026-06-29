package dnd.tests;

import dnd.board.Position;
import dnd.unit.CombatResult;
import dnd.unit.enemy.Monster;

public class UnitCombatTest {
    public static void run() {
        testRollAttackWithZeroAtkNeverThrows();
        testRollDefenseWithZeroDefNeverThrows();
        testDealDamageClampsAtZero();
        testHealthAmountClampsToPool();
        testHealthPoolMinimumOne();
        testStartBattleInvariants();
    }

    private static void testRollAttackWithZeroAtkNeverThrows() {
        Monster m = new Monster("Test", 'x', 100, 0, 5, new Position(0, 0), 10, 3);
        Assert.assertDoesNotThrow("rollAttack with 0 atk should not throw", () -> {
            for (int i = 0; i < 50; i++) {
                if (m.rollAttack() != 0) {
                    throw new RuntimeException("expected roll of 0 when atk is 0");
                }
            }
        });
    }

    private static void testRollDefenseWithZeroDefNeverThrows() {
        Monster m = new Monster("Test", 'x', 100, 5, 0, new Position(0, 0), 10, 3);
        Assert.assertDoesNotThrow("rollDefense with 0 def should not throw", () -> {
            for (int i = 0; i < 50; i++) {
                if (m.rollDefense() != 0) {
                    throw new RuntimeException("expected roll of 0 when def is 0");
                }
            }
        });
    }

    private static void testDealDamageClampsAtZero() {
        Monster m = new Monster("Test", 'x', 50, 5, 5, new Position(0, 0), 10, 3);
        m.dealDamage(1000);
        Assert.assertEquals("health clamps at 0, never negative", 0, m.getHealthAmount());
    }

    private static void testHealthAmountClampsToPool() {
        Monster m = new Monster("Test", 'x', 50, 5, 5, new Position(0, 0), 10, 3);
        m.setHealthAmount(9999);
        Assert.assertEquals("health amount clamps to pool", 50, m.getHealthAmount());
    }

    private static void testHealthPoolMinimumOne() {
        Monster m = new Monster("Test", 'x', 50, 5, 5, new Position(0, 0), 10, 3);
        m.setHealthPool(0);
        Assert.assertEquals("health pool floors at 1 when set to 0 or below", 1, m.getHealthPool());
    }

    private static void testStartBattleInvariants() {
        Monster attacker = new Monster("Attacker", 'x', 100, 50, 5, new Position(0, 0), 10, 3);
        Monster defender = new Monster("Defender", 'y', 100, 5, 5, new Position(1, 0), 10, 3);
        for (int i = 0; i < 200; i++) {
            defender.setHealthAmount(100);
            CombatResult result = attacker.startBattle(defender);
            Assert.assertTrue("damage is never negative", result.getDamage() >= 0);
            Assert.assertTrue("attack roll within [0, atk)", result.getAttackRoll() >= 0 && result.getAttackRoll() < 50);
            Assert.assertTrue("defense roll within [0, def)", result.getDefenseRoll() >= 0 && result.getDefenseRoll() < 5);
            Assert.assertTrue("health never negative", defender.getHealthAmount() >= 0);
        }
    }
}
