package dnd.tests;

import java.util.ArrayList;
import java.util.List;

import dnd.board.Position;
import dnd.unit.enemy.Enemy;
import dnd.unit.enemy.Monster;
import dnd.unit.player.Rogue;

public class AbilityRangeTest {
    public static void run() {
        testRogueAbilityDoesNotHitEnemyExactlyAtBoundary();
        testRogueAbilityHitsEnemyStrictlyWithinRange();
    }

    private static void testRogueAbilityDoesNotHitEnemyExactlyAtBoundary() {
        List<Enemy> enemies = new ArrayList<>();
        Monster boundaryEnemy = new Monster("Boundary", 'x', 100, 5, 1000, new Position(2, 0), 10, 3);
        enemies.add(boundaryEnemy);
        Rogue r = new Rogue("Test Rogue", 100, 10, 2, 20, new Position(0, 0), enemies);
        int healthBefore = boundaryEnemy.getHealthAmount();
        r.castAbility();
        Assert.assertEquals("enemy exactly at range 2 (spec requires strictly < 2) takes no damage",
                healthBefore, boundaryEnemy.getHealthAmount());
    }

    private static void testRogueAbilityHitsEnemyStrictlyWithinRange() {
        List<Enemy> enemies = new ArrayList<>();
        Monster nearEnemy = new Monster("Near", 'x', 100, 5, 0, new Position(1, 0), 10, 3);
        enemies.add(nearEnemy);
        Rogue r = new Rogue("Test Rogue", 100, 40, 2, 20, new Position(0, 0), enemies);
        int healthBefore = nearEnemy.getHealthAmount();
        r.castAbility();
        Assert.assertTrue("enemy strictly within range 2 takes damage",
                nearEnemy.getHealthAmount() < healthBefore);
    }
}
