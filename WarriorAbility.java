public class WarriorAbility implements Ability {
    private int cooldown = 0;
    private int maxCooldown;
    public WarriorAbility(int maxCooldown) {
        this.maxCooldown = maxCooldown;
    }
    public void cast(){
        if(cooldown > 0){
            throw new RuntimeException("Ability is on cooldown");
        }
        cooldown = maxCooldown;
    }
    public int getCooldown() {
        return cooldown;
    }
    public void setCooldown(int cooldown) {
        this.cooldown = Math.min(cooldown, maxCooldown);
    }

}
