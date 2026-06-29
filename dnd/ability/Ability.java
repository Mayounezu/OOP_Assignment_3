package dnd.ability;

import java.util.List;

import dnd.board.Position;
import dnd.unit.enemy.Enemy;

public interface Ability {
    public void cast(Position position, List<Enemy> enemies);
}
