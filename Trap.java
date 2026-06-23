public class Trap extends Enemy{
    private int visibility_time;
    private int invisibility_time;
    private int ticks_count;
    private boolean visible;

    Trap(String name,String tile, int healthPool, int atkPts, int defPts,int experience_value ,int visibility_time,int invisibility_time) {
        super(name,  healthPool,  atkPts,  defPts, experience_value);
        this.visibility_time = visibility_time;
        this.invisibility_time = invisibility_time;
        this.ticks_count = 0;
        visible = true;
    }

    //void attack(Player p){

    public void updateGameTick(){
        ticks_count++;
        if(visible){
            if(ticks_count == visibility_time)
                    visible = false;
        else{
            if(ticks_count == invisibility_time)
                visible = true;
        }
    }



    /*
    void checkRange(Player p){
        if p.GetPosition().distance(this.position);
            attack()
    }
     */


}