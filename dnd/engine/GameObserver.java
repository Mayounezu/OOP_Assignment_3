package dnd.engine;

import dnd.board.GameBoard;
import dnd.unit.CombatResult;
import dnd.unit.player.Player;

public interface GameObserver {
    void onRoundComplete(GameBoard board, Player player);
    void onCombat(CombatResult result);
    void onLevelUp(Player player);
    void onMessage(String message);   // ability-cast failures, death notices, etc.
    void onGameOver(boolean playerWon);
}
