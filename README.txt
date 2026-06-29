Dungeons and Dragons - HW3 (Principles of Object-Oriented Programming)
========================================================================

Build & run
-----------
    javac -d out $(find dnd -name "*.java")
    java -cp out dnd.Main <path-to-levels-directory>

Run the test suite (no external dependency, see "Testing" below):
    java -cp out dnd.tests.TestRunner


Bonus tasks implemented
------------------------
1. Hunter player class (Shoot ability, arrow resource), including the
   pre-defined character Ygritte.
2. HeroicUnit interface + Boss enemy class (Shoebodybop ability). The
   Mountain, Queen Cersei and Night's King are implemented as Boss
   instances (per the spec's required vision range / ability frequency
   values) rather than plain Monsters.


Significant design choices
---------------------------
* Two-level visitor pattern, exactly as specified: CellVisitor dispatches
  on terrain (Wall/Floor), OccupantVisitor dispatches on occupant
  (Player/Enemy). Neither visitor uses instanceof or casting anywhere.
  Unit implements both visitor interfaces (it is the only thing that
  moves) while also extending Occupant (it is also something that can be
  visited, i.e. stood on).

* CombatResult is an immutable value object returned by Unit.startBattle()
  / Unit.attemptStep(). It is how combat details (attacker, defender,
  rolls, damage, death) travel from deep inside the visitor dispatch chain
  back up to the engine, without the business layer ever touching the UI
  directly.

* Observer/callback split for UI communication, per the spec's
  instructions:
    - GameObserver (output): Game notifies registered observers of round
      completion, combat, level-ups, messages and game-over. CLI and
      GameLogger both implement it independently of one another.
    - InputProvider (input): Game calls back into CLI to read the next
      keypress. The business layer (dnd.board, dnd.unit, dnd.ability,
      dnd.engine) never calls System.out.println directly; only
      dnd.ui.CLI and dnd.engine.GameLogger do.

* Unit.vacatesCellOnDeath() is a small polymorphic hook (default true,
  overridden to false on Player) that decides whether a unit's cell
  becomes free for the killer to move into. This is what makes "the
  player takes a slain enemy's position" and "a player killed by an
  enemy stays put, marked 'X'" both fall out of the same attemptStep()
  logic without any instanceof/casting.

* Package layout: dnd.board (terrain/grid), dnd.occupant, dnd.visitor,
  dnd.unit (+ .player, .enemy), dnd.ability, dnd.engine (Game loop,
  GameObserver, InputProvider, GameLogger), dnd.io (LevelLoader),
  dnd.ui (CLI).


Testing
-------
No build tool (Maven/Gradle) is wired into this project, so rather than
pull in JUnit as an unmanaged dependency, the test suite is a small
self-contained framework under dnd.tests (Assert + plain test classes +
TestRunner) that compiles and runs with nothing but the JDK. It covers
both basic and edge cases across combat math (including zero-attack /
zero-defense edge cases), leveling formulas (including levelling up multiple
times from one large XP gain and the exact-threshold case), ability resource
consumption, movement/visitor dispatch (free moves, wall blocking,
combat-instead-of-move, death/cell-takeover asymmetry between players and
enemies), trap visibility cycling, monster chase/wander movement, the boss
Shoebodybop ability (flat ranged shot, cast-or-move cadence, vision gating),
and board rendering.


Notable deviations / clarifications from a literal first reading of the
spec
-------------------------------------------------------------------------
* Trap visibility cycling follows the assignment's given pseudocode
  exactly ("if ticks_count == visibility_time + invisibility_time, reset
  to 0, else increment"). Traced precisely, this produces visibility_time
  visible ticks followed by (invisibility_time + 1) invisible ticks per
  cycle, one tick longer than the prose description ("becomes invisible
  after visibility_time ticks, visible again after invisibility_time
  ticks") would suggest. The implementation follows the literal formula
  given, since it is the more precise of the two descriptions.

* Ability damage: the spec says Mage/Rogue/Hunter targets "will try to
  defend" themselves, so those three abilities have the target roll a
  defense check (random 0..defense) before taking damage. Warrior's
  Avenger's Shield has no such wording in the spec and deals its damage
  unmitigated.
