import java.util.List;

public class Hunter extends Player implements HeroicUnit {
    private int range;
    private int arrows;
    private int ticksCount = 0;
    public Hunter(String name, int healthPool, int atkPoints, int defPoints, Position position, List<Enemy> enemies, int range){
        super(name, healthPool, atkPoints, defPoints, position, new HunterAbility(atkPoints, range), enemies);
        this.range = range;
        arrows = level * 10;
    }
    public void levelUp(){
        super.levelUp();
        arrows += level * 10;
        setAtkPts(getAtkPts() + level * 2);
        setDefPts(getDefPts() + level);
    }
    public void updateGameTick(){
        if(ticksCount == 10){
            arrows += level;
            ticksCount = 0;
        }
        else{
            ticksCount++;
        }
    }
    public void castAbility() {
        if (arrows <= 0) {
            throw new RuntimeException("Out of arrows can't Shoot");
        }
        super.castAbility();
    }

    public String description(){
        return "The Hunter is a player class that uses arrows to shoot the closest enemy within range.  ";
    }
}
