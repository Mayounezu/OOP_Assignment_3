package dnd.visitor;

import dnd.unit.player.Player;
import dnd.unit.enemy.Enemy;

public interface OccupantVisitor {
    public void visit(Player player);
    public void visit(Enemy enemy);
}
