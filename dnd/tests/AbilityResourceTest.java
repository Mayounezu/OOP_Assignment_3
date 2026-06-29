package dnd.tests;

import java.util.ArrayList;
import java.util.List;

import dnd.board.Position;
import dnd.unit.enemy.Enemy;
import dnd.unit.enemy.Monster;
import dnd.unit.player.Hunter;
import dnd.unit.player.Mage;
import dnd.unit.player.Rogue;
import dnd.unit.player.Warrior;

public class AbilityResourceTest {
    public static void run() {
        testWarriorCooldownEngagesAfterCast();
        testMageDeductsManaOnCast();
        testRogueDeductsEnergyOnCast();
        testHunterDeductsArrowOnSuccessfulCast();
        testHunterDoesNotConsumeArrowOnFailedCast();
    }

    private static void testWarriorCooldownEngagesAfterCast() {
        List<Enemy> enemies = new ArrayList<>();
        Warrior w = new Warrior("Test Warrior", 100, 10, 2, 3, new Position(0, 0), enemies);
        Assert.assertDoesNotThrow("first cast should succeed", w::castAbility);
        Assert.assertThrows("second cast immediately after should be blocked by cooldown", w::castAbility);
    }

    private static void testMageDeductsManaOnCast() {
        List<Enemy> enemies = new ArrayList<>();
        Mage m = new Mage("Test Mage", 100, 5, 1, 6, 100, 20, 15, 5, new Position(0, 0), enemies);
        int manaBefore = m.getCurrentMana();
        m.castAbility();
        Assert.assertEquals("mana decreases by manaCost on successful cast", manaBefore - 20, m.getCurrentMana());
    }

    private static void testRogueDeductsEnergyOnCast() {
        List<Enemy> enemies = new ArrayList<>();
        Rogue r = new Rogue("Test Rogue", 100, 10, 2, 20, new Position(0, 0), enemies);
        int energyBefore = r.getEnergy();
        r.castAbility();
        Assert.assertEquals("energy decreases by cost on successful cast", energyBefore - 20, r.getEnergy());
    }

    private static void testHunterDeductsArrowOnSuccessfulCast() {
        List<Enemy> enemies = new ArrayList<>();
        Monster target = new Monster("Target", 'x', 50, 5, 0, new Position(1, 0), 10, 3);
        enemies.add(target);
        Hunter h = new Hunter("Test Hunter", 100, 30, 2, new Position(0, 0), enemies, 6);
        int arrowsBefore = h.getArrows();
        h.castAbility();
        Assert.assertEquals("arrow count decreases by 1 on successful cast", arrowsBefore - 1, h.getArrows());
    }

    private static void testHunterDoesNotConsumeArrowOnFailedCast() {
        List<Enemy> enemies = new ArrayList<>(); // no enemies in range
        Hunter h = new Hunter("Test Hunter", 100, 30, 2, new Position(0, 0), enemies, 6);
        int arrowsBefore = h.getArrows();
        Assert.assertThrows("cast with no enemy in range should throw", h::castAbility);
        Assert.assertEquals("arrow count unchanged after a failed cast", arrowsBefore, h.getArrows());
    }
}
