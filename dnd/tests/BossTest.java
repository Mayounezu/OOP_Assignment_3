package dnd.tests;

import java.util.ArrayList;

import dnd.ability.BossAbility;
import dnd.board.Cell;
import dnd.board.Floor;
import dnd.board.GameBoard;
import dnd.board.Position;
import dnd.unit.CombatResult;
import dnd.unit.enemy.Boss;
import dnd.unit.player.Warrior;

public class BossTest {
    public static void run() {
        testShoebodybopUsesAttackMinusDefense();
        testBossCastsOnAbilityFrequencyTickWithoutMoving();
        testBossDoesNotCastOutOfVisionRange();
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

    private static void testShoebodybopUsesAttackMinusDefense() {
        Boss boss = new Boss("Test Boss", 'M', 1000, 60, 5, new Position(0, 0), 500, 5, 3);
        Warrior player = new Warrior("Victim", 1000, 10, 0, 3, new Position(1, 0), new ArrayList<>());
        BossAbility ability = new BossAbility();
        CombatResult result = ability.cast(boss, player);
        Assert.assertEquals("attacker is the boss", boss, result.getAttacker());
        Assert.assertEquals("defender is the player", player, result.getDefender());
        Assert.assertEquals("attack roll equals the boss's attack points (a flat shot)", 60, result.getAttackRoll());
        // With player defense 0, defense roll is always 0, so full 60 damage lands.
        Assert.assertEquals("full damage lands when the player has no defense", 60, result.getDamage());
    }

    private static void testBossCastsOnAbilityFrequencyTickWithoutMoving() {
        GameBoard board = emptyBoard(5, 1);
        Boss boss = new Boss("Caster", 'M', 1000, 60, 5, new Position(0, 0), 500, 5, 0);
        Warrior player = new Warrior("Target", 1000, 10, 0, 3, new Position(2, 0), new ArrayList<>());
        board.setOccupant(new Position(0, 0), boss);
        board.setOccupant(new Position(2, 0), player);

        // abilityFrequency 0 -> combatTicks(0) == frequency(0) on the very first turn => cast.
        CombatResult result = boss.processTurn(board, player);

        Assert.assertTrue("boss produces a combat result when it casts", result != null);
        Assert.assertEquals("the cast targets the player", player, result.getDefender());
        Assert.assertEquals("the boss does not move on a cast turn", 0, boss.getPosition().getX());
        Assert.assertEquals("boss remains the occupant of its own cell after casting",
                boss, board.getOccupant(new Position(0, 0)));
    }

    private static void testBossDoesNotCastOutOfVisionRange() {
        GameBoard board = emptyBoard(20, 1);
        // ctor args: ..., experienceValue=500, visionRange=3, abilityFrequency=0
        Boss boss = new Boss("Caster", 'M', 1000, 60, 5, new Position(0, 0), 500, 3, 0);
        Warrior player = new Warrior("Target", 1000, 10, 0, 3, new Position(15, 0), new ArrayList<>());
        board.setOccupant(new Position(0, 0), boss);
        board.setOccupant(new Position(15, 0), player);

        // Player is far outside vision range 3, so the boss must wander, never shoot the player.
        CombatResult result = boss.processTurn(board, player);
        boolean shotThePlayer = result != null && result.getDefender() == player && result.getAttackRoll() == 60;
        Assert.assertFalse("boss must not cast Shoebodybop when the player is out of vision range", shotThePlayer);
    }
}
