package dnd.unit.enemy;

import dnd.board.GameBoard;
import dnd.board.Position;
import dnd.unit.CombatResult;
import dnd.unit.player.Player;

public class Trap extends Enemy{
    private int visibility_time;
    private int invisibility_time;
    private int ticks_count;
    private boolean visible;

    public Trap(String name,Character tile, int healthPool, int atkPts, int defPts,int experience_value ,int visibility_time,int invisibility_time, Position position) {
        super(name, tile, healthPool,  atkPts,  defPts, experience_value, position);
        this.visibility_time = visibility_time;
        this.invisibility_time = invisibility_time;
        this.ticks_count = 0;
        visible = true;
    }

    public void updateGameTick() {
        visible = ticks_count < visibility_time;
        if (ticks_count == visibility_time + invisibility_time) {
            ticks_count = 0;
        } else {
            ticks_count++;
        }
    }

    @Override
    public String description() {
        return baseStatus() + " | Visible: " + visible + " | Exp Value: " + getExperienceValue();
    }

    @Override
    public String toString() {
        if (visible)
            return "" + tile;
        else
            return ".";
    }

    @Override
    public CombatResult processTurn(GameBoard board, Player player) {
        updateGameTick();
        if (getPosition().distance(player.getPosition()) < 2) {
            return startBattle(player);
        }
        return null;
    }

}
