import java.util.List;
public class HunterAbility implements Ability {
    private int atk;
    private int range;
    public HunterAbility(int atk, int range) {
        this.atk = atk;
        this.range = range;
    }
    public void cast(Position position, List<Enemy> enemies){
        double minDistance = range + 1;
        Enemy toAttack = null;
        for(Enemy e :  enemies){
            if(e.getPosition().distance(position) < minDistance){
                minDistance = e.getPosition().distance(position);
                toAttack = e;
            }
        }
        if(toAttack != null && minDistance <= range){
            toAttack.dealDamage(atk);
        }
        else{
            throw new RuntimeException("no enemy in range of Shoot");
        }
    }
}
