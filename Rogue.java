import java.util.List;
public class Rogue extends Player {
    private int energy = 100;
    private int cost;
    public Rogue(String name, int healthPool, int atkPoints, int defPoints,int cost, Position position, List<Enemy> enemies) {
        super(name, healthPool, atkPoints, defPoints,  position, new RogueAbility(atkPoints), enemies);
        this.cost = cost;
    }
    public void levelUp() {
        super.levelUp();
        energy = 100;
        setAtkPts(getAtkPts() + (level * 3));
    }
    public void cast(){
        if(energy < cost){
            throw new RuntimeException("Not enough energy to cast Fan Of Knives");
        }
        super.cast();
    }
    public void updateGameTick(){
        energy = Math.min(energy + 10, 100);
    }
}
