import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Placeholders for position and enemies list
        Position pos = new Position(0, 0);
        List<Enemy> enemies = new ArrayList<>();
// ==========================================
// PLAYERS
// ==========================================

// Warriors
        Warrior jonSnow = new Warrior("Jon Snow", 300, 30, 4, 3, pos, enemies);
        Warrior theHound = new Warrior("The Hound", 400, 20, 6, 5, pos, enemies);

// Rogues
        Rogue aryaStark = new Rogue("Arya Stark", 150, 40, 2, 20, pos, enemies);
        Rogue bronn = new Rogue("Bronn", 250, 35, 3, 50, pos, enemies);

// ==========================================
// ENEMIES
// ==========================================

// Monsters
        Monster goldCloak = new Monster("Gold Cloak", 's', 80, 8, 3, pos, 25, 3);
        Monster knight = new Monster("Knight", 'k', 200, 14, 8, pos, 50, 4);
        Monster queensGuard = new Monster("Queen's Guard", 'q', 400, 20, 15, pos, 100, 5);
        Monster wright = new Monster("Wright", 'z', 600, 30, 15, pos, 100, 3);
        Monster bear = new Monster("Bear", 'b', 1000, 75, 30, pos, 250, 4);
        Monster giant = new Monster("Giant", 'g', 1500, 100, 40, pos, 500, 5);
        Monster whiteWalker = new Monster("White Walker", 'w', 2000, 150, 50, pos, 1000, 6);
        Monster theMountain = new Monster("The Mountain", 'M', 1000, 60, 25, pos, 500, 6);
        Monster queenCersei = new Monster("Queen Cersei", 'C', 100, 10, 10, pos, 1000, 1);
        Monster nightsKing = new Monster("Night's King", 'K', 5000, 300, 150, pos, 5000, 8);

// Traps
        Trap bonusTrap = new Trap("Bonus Trap", 'B', 1, 1, 1, 250, 1, 5, pos);
        Trap queensTrap = new Trap("Queen's Trap", 'Q', 250, 50, 10, 100, 3, 7, pos);
        Trap deathTrap = new Trap("Death Trap", 'D', 500, 100, 20, 250, 1, 10, pos);
    }
}
