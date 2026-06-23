public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        if (x < 0) {
            throw new IllegalArgumentException("X coordinate cannot be negative");
        }
        this.x = x;
    }
    public void setY(int y) {
        if (y < 0) {
            throw new IllegalArgumentException("Y coordinate cannot be negative");
        }
        this.y = y;
    }
    public double distance(Position other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }
}
