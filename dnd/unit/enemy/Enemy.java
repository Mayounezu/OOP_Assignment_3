package dnd.unit.enemy;

import dnd.board.GameBoard;
import dnd.board.Position;
import dnd.unit.CombatResult;
import dnd.unit.Unit;
import dnd.unit.player.Player;
import dnd.visitor.OccupantVisitor;

public abstract class Enemy extends Unit {

    protected final int experienceValue;

    protected Enemy(String name, Character tile, int healthPool, int atkPts, int defPts, int experienceValue, Position position) {
        super(name, tile, healthPool, atkPts, defPts, position);
        this.experienceValue = experienceValue;
    }

    public int getExperienceValue() {
        return experienceValue;
    }

    @Override
    public void accept(OccupantVisitor visitor) {
        visitor.visit(this);
    }

    public void visit(Player player){
        lastCombatResult = startBattle(player);
    }

    public void visit(Enemy enemy){
        throw new UnsupportedOperationException("Enemies cannot battle other enemies");
    }

    public abstract CombatResult processTurn(GameBoard board, Player player);
}
