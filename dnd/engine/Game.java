package dnd.engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dnd.board.GameBoard;
import dnd.board.Position;
import dnd.io.LevelLoader;
import dnd.unit.CombatResult;
import dnd.unit.enemy.Enemy;
import dnd.unit.player.Player;

public class Game {
    private final List<GameObserver> observers = new ArrayList<>();
    private final List<LevelLoader.Level> levels;
    private final Player player;
    private InputProvider inputProvider;

    private GameBoard board;
    private List<Enemy> enemies;
    private int levelIndex = 0;
    private boolean gameOver = false;
    private boolean playerWon = false;

    public Game(Player player, List<LevelLoader.Level> levels) {
        this.player = player;
        this.levels = levels;
    }

    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    public void setInputProvider(InputProvider inputProvider) {
        this.inputProvider = inputProvider;
    }

    public void run() {
        loadLevel(0);
        notifyRoundComplete();
        while (!gameOver) {
            char action = inputProvider.readAction();
            processPlayerAction(action);
            if (!gameOver) {
                processEnemyTurns();
            }
            if (!gameOver) {
                player.updateGameTick();
                checkLevelComplete();
            }
            notifyRoundComplete();
        }
        notifyGameOver(playerWon);
    }

    private void loadLevel(int index) {
        LevelLoader.Level level = levels.get(index);
        this.board = level.getBoard();
        this.enemies = player.getEnemies();
        this.enemies.clear();
        this.enemies.addAll(level.getEnemies());
        player.setPosition(level.getPlayerStart());
        board.setOccupant(player.getPosition(), player);
    }

    private void processPlayerAction(char action) {
        switch (action) {
            case 'w': movePlayer(0, -1); break;
            case 's': movePlayer(0, 1); break;
            case 'a': movePlayer(-1, 0); break;
            case 'd': movePlayer(1, 0); break;
            case 'e': castAbility(); break;
            case 'q':
            default:
                break;
        }
    }

    private void movePlayer(int dx, int dy) {
        Position target = new Position(player.getPosition().getX() + dx, player.getPosition().getY() + dy);
        Enemy targetEnemy = findEnemyAt(target);
        CombatResult result = player.attemptStep(board, target);
        if (result == null) {
            return;
        }
        notifyCombat(result);
        if (result.isDefenderDied() && targetEnemy != null) {
            notifyMessage(player.getName() + " killed " + targetEnemy.getName()
                    + " and gained " + targetEnemy.getExperienceValue() + " experience.");
            grantExperience(targetEnemy.getExperienceValue());
            enemies.remove(targetEnemy);
        }
    }

    private void castAbility() {
        try {
            player.castAbility();
            notifyMessage(player.getName() + " casts an ability.");
            cleanupDeadEnemies();
        } catch (RuntimeException e) {
            notifyMessage(e.getMessage());
        }
    }

    private void cleanupDeadEnemies() {
        Iterator<Enemy> it = enemies.iterator();
        while (it.hasNext()) {
            Enemy e = it.next();
            if (e.getHealthAmount() <= 0) {
                notifyMessage(player.getName() + " killed " + e.getName()
                        + " and gained " + e.getExperienceValue() + " experience.");
                grantExperience(e.getExperienceValue());
                board.setOccupant(e.getPosition(), null);
                it.remove();
            }
        }
    }

    private void processEnemyTurns() {
        for (Enemy enemy : new ArrayList<>(enemies)) {
            if (!enemies.contains(enemy) || gameOver) {
                continue;
            }
            CombatResult result = enemy.processTurn(board, player);
            if (result == null) {
                continue;
            }
            notifyCombat(result);
            if (result.isDefenderDied() && result.getDefender() == player) {
                player.setTile('X');
                gameOver = true;
                playerWon = false;
                notifyMessage(player.getName() + " has died.");
            }
        }
    }

    private void checkLevelComplete() {
        if (gameOver || !enemies.isEmpty()) {
            return;
        }
        levelIndex++;
        if (levelIndex >= levels.size()) {
            gameOver = true;
            playerWon = true;
            notifyMessage("All levels complete! You win!");
        } else {
            notifyMessage("Level complete! Loading next level...");
            loadLevel(levelIndex);
        }
    }

    private void grantExperience(int amount) {
        int before = player.getLevel();
        player.gainExperience(amount);
        if (player.getLevel() != before) {
            notifyLevelUp();
        }
    }

    private Enemy findEnemyAt(Position pos) {
        for (Enemy e : enemies) {
            if (e.getPosition().getX() == pos.getX() && e.getPosition().getY() == pos.getY()) {
                return e;
            }
        }
        return null;
    }

    private void notifyCombat(CombatResult result) {
        for (GameObserver o : observers) {
            o.onCombat(result);
        }
    }

    private void notifyRoundComplete() {
        for (GameObserver o : observers) {
            o.onRoundComplete(board, player);
        }
    }

    private void notifyLevelUp() {
        for (GameObserver o : observers) {
            o.onLevelUp(player);
        }
    }

    private void notifyMessage(String message) {
        for (GameObserver o : observers) {
            o.onMessage(message);
        }
    }

    private void notifyGameOver(boolean won) {
        for (GameObserver o : observers) {
            o.onGameOver(won);
        }
    }
}
