package dnd.ui;

import java.util.List;
import java.util.Scanner;

import dnd.board.GameBoard;
import dnd.engine.GameObserver;
import dnd.engine.InputProvider;
import dnd.unit.CombatResult;
import dnd.unit.Unit;
import dnd.unit.player.Player;

public class CLI implements GameObserver, InputProvider {
    private final Scanner scanner = new Scanner(System.in);

    public Player selectPlayer(List<Player> options) {
        System.out.println("Choose your character:");
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i).getName());
        }
        while (true) {
            System.out.print("Enter choice: ");
            String line = scanner.nextLine().trim();
            try {
                int choice = Integer.parseInt(line);
                if (choice >= 1 && choice <= options.size()) {
                    return options.get(choice - 1);
                }
            } catch (NumberFormatException ignored) {
                // fall through to the invalid-choice message below
            }
            System.out.println("Invalid choice.");
        }
    }

    @Override
    public char readAction() {
        while (true) {
            System.out.print("Action (w/a/s/d move, e cast ability, q do nothing): ");
            String line = scanner.nextLine().trim();
            if (line.length() == 1 && "wasdeq".indexOf(line.charAt(0)) >= 0) {
                return line.charAt(0);
            }
            System.out.println("Invalid action. Please enter one of: w a s d e q");
        }
    }

    @Override
    public void onRoundComplete(GameBoard board, Player player) {
        System.out.println(board);
        System.out.println(player.getName() + " - " + player.description());
    }

    @Override
    public void onCombat(CombatResult result) {
        Unit attacker = result.getAttacker();
        Unit defender = result.getDefender();
        System.out.println(attacker.getName() + " attacks " + defender.getName()
                + " (attack roll " + result.getAttackRoll() + " vs defense roll " + result.getDefenseRoll()
                + ") for " + result.getDamage() + " damage.");
        if (result.isDefenderDied()) {
            System.out.println(defender.getName() + " has died.");
        }
    }

    @Override
    public void onLevelUp(Player player) {
        System.out.println(player.getName() + " leveled up! Now level " + player.getLevel() + ".");
    }

    @Override
    public void onMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void onGameOver(boolean playerWon) {
        System.out.println(playerWon ? "You win!" : "Game over - you died.");
    }
}
