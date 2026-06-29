package dnd.engine;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import dnd.board.GameBoard;
import dnd.unit.CombatResult;
import dnd.unit.Unit;
import dnd.unit.player.Player;

public class GameLogger implements GameObserver {
    private final PrintWriter writer;

    public GameLogger(String filePath) throws IOException {
        this.writer = new PrintWriter(new FileWriter(filePath, true), true);
    }

    @Override
    public void onRoundComplete(GameBoard board, Player player) {
        writer.println("Round complete - " + player.getName() + " at level " + player.getLevel()
                + ", health " + player.getHealthAmount() + "/" + player.getHealthPool());
    }

    @Override
    public void onCombat(CombatResult result) {
        Unit attacker = result.getAttacker();
        Unit defender = result.getDefender();
        writer.println(attacker.getName() + " attacks " + defender.getName()
                + " (attack roll " + result.getAttackRoll() + " vs defense roll " + result.getDefenseRoll()
                + ") for " + result.getDamage() + " damage."
                + (result.isDefenderDied() ? " " + defender.getName() + " died." : ""));
    }

    @Override
    public void onLevelUp(Player player) {
        writer.println(player.getName() + " leveled up to level " + player.getLevel() + ".");
    }

    @Override
    public void onMessage(String message) {
        writer.println(message);
    }

    @Override
    public void onGameOver(boolean playerWon) {
        writer.println(playerWon ? "Game over: player won." : "Game over: player died.");
        writer.close();
    }
}
