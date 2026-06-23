public abstract class Enemy extends Unit {

private final int experienceValue; // Experience gained by the player when defeating this unit


protected Enemy(String name, int healthPool, int atkPts, int defPts, int experienceValue, Position position) {

super(name, healthPool, atkPts, defPts, position);

this.experienceValue = experienceValue;

}


public int getExperienceValue() {

return experienceValue;

}


/* * ARCHITECTURAL TIP 1: The Visitor Pattern Hook

* Since your project strictly forbids 'instanceof' during movement,

* this is the exact class level where double-dispatch resolves the unit as an "Enemy".

*/

// @Override

// public void accept(OccupantVisitor visitor) {

// visitor.visit(this);

// }



/*

* ARCHITECTURAL TIP 2: The Game Tick Contract

* On an enemy turn, Monsters move, but Traps sit still and update visibility.

* Defining an abstract turn method here guarantees the GameLogic can just loop

* over a List<Enemy> and call .processTurn() blindly without caring what it is.

*/

public abstract Position processTurn(Player player);

}