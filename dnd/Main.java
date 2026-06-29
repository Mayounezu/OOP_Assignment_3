package dnd;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dnd.board.Position;
import dnd.engine.Game;
import dnd.io.LevelLoader;
import dnd.ui.CLI;
import dnd.unit.enemy.Enemy;
import dnd.unit.player.Hunter;
import dnd.unit.player.Mage;
import dnd.unit.player.Player;
import dnd.unit.player.Rogue;
import dnd.unit.player.Warrior;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Usage: java dnd.Main <levels-directory>");
            return;
        }

        List<Enemy> sharedEnemies = new ArrayList<>();
        Position placeholder = new Position(0, 0);

        List<Player> options = new ArrayList<>();
        options.add(new Warrior("Jon Snow", 300, 30, 4, 3, placeholder, sharedEnemies));
        options.add(new Warrior("The Hound", 400, 20, 6, 5, placeholder, sharedEnemies));
        options.add(new Mage("Melisandre", 100, 5, 1, 6, 300, 30, 15, 5, placeholder, sharedEnemies));
        options.add(new Mage("Thoros of Myr", 250, 25, 4, 4, 150, 20, 20, 3, placeholder, sharedEnemies));
        options.add(new Rogue("Arya Stark", 150, 40, 2, 20, placeholder, sharedEnemies));
        options.add(new Rogue("Bronn", 250, 35, 3, 50, placeholder, sharedEnemies));
        options.add(new Hunter("Ygritte", 220, 30, 2, placeholder, sharedEnemies, 6));

        CLI cli = new CLI();
        Player player = cli.selectPlayer(options);

        List<LevelLoader.Level> levels = LevelLoader.loadLevels(new File(args[0]));
        if (levels.isEmpty()) {
            System.out.println("No level files found in " + args[0]);
            return;
        }

        Game game = new Game(player, levels);
        game.addObserver(cli);
        game.setInputProvider(cli);
        game.run();
    }
}
