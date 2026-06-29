package dnd.tests;

import java.util.ArrayList;
import java.util.List;

import dnd.board.Position;
import dnd.unit.enemy.Enemy;
import dnd.unit.player.Warrior;

public class PlayerLevelingTest {
    public static void run() {
        testLevelUpFullyHealsPlayer();
        testLevelsUpAtExactThreshold();
        testNoLevelUpBelowThreshold();
        testMultipleLevelUpsFromOneBigGain();
        testLevelUpStatGrowth();
    }

    private static Warrior freshWarrior() {
        List<Enemy> enemies = new ArrayList<>();
        return new Warrior("Test Warrior", 100, 10, 2, 3, new Position(0, 0), enemies);
    }

    private static void testLevelUpFullyHealsPlayer() {
        Warrior w = freshWarrior();
        w.dealDamage(80); // health now 20/100
        w.gainExperience(50); // reaching 50*1 triggers a level up
        Assert.assertEquals("level increments", 2, w.getLevel());
        // Player.levelUp() heals to full as soon as the base pool is updated (100 + 2*10 = 120);
        // Warrior's own subclass bonus (+2*5 = +10 pool) is layered on after and does not re-heal,
        // per the spec listing the heal only under the base Player bullet list.
        Assert.assertEquals("current health is restored to the base-leveled pool (before the subclass bonus)",
                120, w.getHealthAmount());
        Assert.assertEquals("final health pool also includes the Warrior-specific bonus", 130, w.getHealthPool());
    }

    private static void testLevelsUpAtExactThreshold() {
        Warrior w = freshWarrior();
        w.gainExperience(50); // "after gaining 50*level" -> reaching the threshold levels up
        Assert.assertEquals("reaching exactly the threshold levels up", 2, w.getLevel());
        Assert.assertEquals("leftover experience after the deduction is zero", 0, w.getExperience());
    }

    private static void testNoLevelUpBelowThreshold() {
        Warrior w = freshWarrior();
        w.gainExperience(49); // one short of the level-1 threshold of 50
        Assert.assertEquals("below threshold should not level up", 1, w.getLevel());
        Assert.assertEquals("experience accumulates", 49, w.getExperience());
    }

    private static void testMultipleLevelUpsFromOneBigGain() {
        Warrior w = freshWarrior();
        // Thresholds: L1->2 needs 50, L2->3 needs 100, L3->4 needs 150. 50+100+150 = 300.
        w.gainExperience(300);
        Assert.assertEquals("a single large XP gain levels up multiple times", 4, w.getLevel());
        Assert.assertEquals("all leftover XP is consumed across the level-ups", 0, w.getExperience());
    }

    private static void testLevelUpStatGrowth() {
        Warrior w = freshWarrior();
        int atkBefore = w.getAtkPts();
        int defBefore = w.getDefPts();
        w.gainExperience(50);
        // base Player at new level 2: atk += 4*2 = +8, def += 2
        // Warrior bonus at new level 2:   atk += 2*2 = +4, def += 2
        Assert.assertEquals("attack grows by base + subclass bonus", atkBefore + 12, w.getAtkPts());
        Assert.assertEquals("defense grows by base + subclass bonus", defBefore + 4, w.getDefPts());
    }
}
