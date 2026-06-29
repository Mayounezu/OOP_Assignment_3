package dnd.unit.enemy;

import java.util.Random;

import dnd.board.GameBoard;
import dnd.board.Position;
import dnd.unit.CombatResult;
import dnd.unit.player.Player;

public class Monster extends Enemy {

    private int visionRange; // The radius in which the monster spots the player

    public Monster(String name, Character tile, int healthPool, int atkPts, int defPts, Position position, int experienceValue, int visionRange) {
        super(name, tile, healthPool, atkPts, defPts, experienceValue, position);
        this.visionRange = visionRange;
    }

    public int getVisionRange() {
        return visionRange;
    }

    public void setVisionRange(int visionRange) {
        this.visionRange = visionRange;
    }

    /**
     * Resolves the Monster's movement choice for this game tick.
     * @param player The active player instance, passed in so the monster can inspect its coordinates.
     * @return The Position the monster WANTS to step into.
     */
    public Position decideMove(Player player) {
        Position myPos = this.getPosition();
        Position playerPos = player.getPosition();

        // Check if the player is within vision range
        if (myPos.distance(playerPos) < this.visionRange) {
            return chasePlayer(myPos, playerPos);
        } else {
            return wanderRandomly(myPos);
        }
    }

    @Override
    public CombatResult processTurn(GameBoard board, Player player) {
        Position target = decideMove(player);
        if (target.getX() == getPosition().getX() && target.getY() == getPosition().getY()) {
            return null;
        }
        return attemptStep(board, target);
    }

    private Position chasePlayer(Position myPos, Position playerPos) {
        int dx = myPos.getX() - playerPos.getX();
        int dy = myPos.getY() - playerPos.getY();

        if (Math.abs(dx) > Math.abs(dy)) {
            if (dx > 0) {
                return new Position(myPos.getX() - 1, myPos.getY()); // Move Left
            } else {
                return new Position(myPos.getX() + 1, myPos.getY()); // Move Right
            }
        } else {
            if (dy > 0) {
                return new Position(myPos.getX(), myPos.getY() - 1); // Move Up
            } else {
                return new Position(myPos.getX(), myPos.getY() + 1); // Move Down
            }
        }
    }

    private Position wanderRandomly(Position myPos) {
        Random rand = new Random();
        int choice = rand.nextInt(5); // Generates 0, 1, 2, 3, or 4

        switch (choice) {
            case 0: return new Position(myPos.getX() - 1, myPos.getY()); // Move Left
            case 1: return new Position(myPos.getX() + 1, myPos.getY()); // Move Right
            case 2: return new Position(myPos.getX(), myPos.getY() - 1); // Move Up
            case 3: return new Position(myPos.getX(), myPos.getY() + 1); // Move Down
            default: return myPos; // Stay in place
        }
    }


    @Override
    public void updateGameTick() {

    }

    @Override
    public String description() {
        return "";
    }
}
