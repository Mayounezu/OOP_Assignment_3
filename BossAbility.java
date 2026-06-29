public class BossAbility implements Ability{

    private int atk;
    private int visionRange;

    public BossAbility(int atk, int visionRange) {
        this.atk = atk;
        this.visionRange = visionRange;
    }

    // The boss only targets the player, so we just pass the Player object
    public void cast(Position bossPosition, Player player) {

        // Calculate distance between the boss and the player
        double distance = bossPosition.distance(player.getPosition());

        // Check if the player is within vision range
        if (distance < visionRange) {
            // The player takes damage equal to the boss's attack points.
            // (Assuming dealDamage handles the defense roll inside the Player class)
            player.dealDamage(atk);
        } else {
            // Optional: You can throw an exception or just do nothing if the player is too far
            throw new RuntimeException("Player is not in vision range of Shoebodybop");
        }
    }
}