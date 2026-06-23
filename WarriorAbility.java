import java.util.ArrayList;
import java.util.List;
public class WarriorAbility implements Ability {
    private int healthPool;
    public WarriorAbility(int healthPool) {
        this.healthPool = healthPool;
    }
    public void cast(Position position, List<Enemy> enemies) {
        List<Enemy> inRange = new ArrayList<>();
        for(Enemy e : enemies){
            if(e.getPosition().distance(position) <= 3){
                inRange.add(e);
            }
        }
        if(!inRange.isEmpty()){
            int atkIndex = (int)(Math.random() * inRange.size());
            inRange.get(atkIndex).dealDamage(healthPool / 10);
        }
    }
}
