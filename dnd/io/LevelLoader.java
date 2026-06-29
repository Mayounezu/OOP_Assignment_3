package dnd.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import dnd.board.Cell;
import dnd.board.Floor;
import dnd.board.GameBoard;
import dnd.board.Position;
import dnd.board.Wall;
import dnd.unit.enemy.Boss;
import dnd.unit.enemy.Enemy;
import dnd.unit.enemy.Monster;
import dnd.unit.enemy.Trap;

public class LevelLoader {

    public static class Level {
        private final GameBoard board;
        private final List<Enemy> enemies;
        private final Position playerStart;

        public Level(GameBoard board, List<Enemy> enemies, Position playerStart) {
            this.board = board;
            this.enemies = enemies;
            this.playerStart = playerStart;
        }

        public GameBoard getBoard() {
            return board;
        }

        public List<Enemy> getEnemies() {
            return enemies;
        }

        public Position getPlayerStart() {
            return playerStart;
        }
    }

    public static List<Level> loadLevels(File directory) throws IOException {
        List<Level> levels = new ArrayList<>();
        int i = 1;
        File file;
        while ((file = new File(directory, "level" + i + ".txt")).exists()) {
            levels.add(loadLevel(file));
            i++;
        }
        return levels;
    }

    public static Level loadLevel(File file) throws IOException {
        List<String> lines = Files.readAllLines(file.toPath());
        int height = lines.size();
        int width = 0;
        for (String line : lines) {
            width = Math.max(width, line.length());
        }

        GameBoard board = new GameBoard(width, height);
        List<Enemy> enemies = new ArrayList<>();
        Position playerStart = null;

        for (int y = 0; y < height; y++) {
            String line = lines.get(y);
            for (int x = 0; x < width; x++) {
                char tile = x < line.length() ? line.charAt(x) : '#';
                Position pos = new Position(x, y);

                if (tile == '#') {
                    board.setCell(x, y, new Cell(new Wall()));
                    continue;
                }

                board.setCell(x, y, new Cell(new Floor()));

                if (tile == '.') {
                    continue;
                }
                if (tile == '@') {
                    playerStart = pos;
                    continue;
                }

                Enemy enemy = createEnemy(tile, pos);
                if (enemy != null) {
                    enemies.add(enemy);
                    board.setOccupant(pos, enemy);
                }
            }
        }

        return new Level(board, enemies, playerStart);
    }

    private static Enemy createEnemy(char tile, Position pos) {
        switch (tile) {
            case 's': return new Monster("Gold Cloak", 's', 80, 8, 3, pos, 25, 3);
            case 'k': return new Monster("Knight", 'k', 200, 14, 8, pos, 50, 4);
            case 'q': return new Monster("Queen's Guard", 'q', 400, 20, 15, pos, 100, 5);
            case 'z': return new Monster("Wright", 'z', 600, 30, 15, pos, 100, 3);
            case 'b': return new Monster("Bear", 'b', 1000, 75, 30, pos, 250, 4);
            case 'g': return new Monster("Giant", 'g', 1500, 100, 40, pos, 500, 5);
            case 'w': return new Monster("White Walker", 'w', 2000, 150, 50, pos, 1000, 6);
            case 'M': return new Boss("The Mountain", 'M', 1000, 60, 25, pos, 500, 6, 5);
            case 'C': return new Boss("Queen Cersei", 'C', 100, 10, 10, pos, 1000, 1, 8);
            case 'K': return new Boss("Night's King", 'K', 5000, 300, 150, pos, 5000, 8, 3);
            case 'B': return new Trap("Bonus Trap", 'B', 1, 1, 1, 250, 1, 5, pos);
            case 'Q': return new Trap("Queen's Trap", 'Q', 250, 50, 10, 100, 3, 7, pos);
            case 'D': return new Trap("Death Trap", 'D', 500, 100, 20, 250, 1, 10, pos);
            default: return null;
        }
    }
}
