import java.util.HashMap;
import java.util.Random;

public class SelfAvoidRW2D {
    private static final double pi2 = 2.0 * Math.PI;
    private int x;
    private int y;
    private double[] r2;
    private boolean isStuck;
    private int hashKey;
    private HashMap<Integer, Point> traversed;

    public SelfAvoidRW2D(int x, int y, int steps) {
        this.x = x;
        this.y = y;
        this.isStuck = false;
        this.traversed = new HashMap<Integer, Point>();
        this.hashKey = 0;
        this.r2 = new double[steps];
    }

    public SelfAvoidRW2D(int steps) {
        this.x = 0;
        this.y = 0;
        this.isStuck = false;
        this.traversed = new HashMap<Integer, Point>();
        this.hashKey = 0;
        this.r2 = new double[steps];
    }

    public void stuck() {
        this.isStuck = true;
    }

    public int getHashKey(){
        return this.hashKey;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getR2(int index) {
        return r2[index];
    }

    public void setR2(int index, double value) {
        this.r2[index] = value;
    }

    public void incrementKey() {
        this.hashKey = this.hashKey + 1;
    }

    /**
     * Runs the randomwalker algorithm once, and calculates the values
     */
    private void runAndAverage(Random rand) {
        String[] directions = {"y+1", "y-1", "x+1", "x-1"};

        boolean[] moves = new boolean[4];
        makeAllTrue(moves);
        boolean movable = true;

        while (movable) {
            int choice = rand.nextInt(3);
            //move randomwalker in 2D by 1 step
            double r2;
            String dir = directions[choice];

            if (dir == "y+1") {
                if (checkIntersection(this.x, this.y + 1)) {
                    moves[2] = false;
                    if (checkAllFalse(moves)) {
                        movable = false;
                    }
                    continue;
                }
                this.y += 1;
            } else if (dir == "y-1") {
                if (checkIntersection(this.x, this.y - 1)) {
                    moves[3] = false;
                    if (checkAllFalse(moves)) {
                        movable = false;
                        isStuck = true;
                    }
                    continue;
                }
                this.y -= 1;
            } else if (dir == "x+1") {
                if (checkIntersection(this.x + 1, this.y)) {
                    moves[0] = false;
                    if (checkAllFalse(moves)) {
                        movable = false;
                        isStuck = true;
                    }
                    continue;
                }
                this.x += 1;
            } else if (dir == "x-1") {
                if (checkIntersection(this.x - 1, this.y)) {
                    moves[1] = false;
                    if (checkAllFalse(moves)) {
                        movable = false;
                        isStuck = true;
                    }
                    continue;
                }
                this.x -= 1;
            }

            traversed.put(hashKey, new Point(this.x, this.y));
            this.r2[hashKey] = this.x * this.x + this.y * this.y;

            incrementKey();
            movable = false;
        }

    }

    // Checks if an array of booleans is all false
    protected boolean checkAllFalse(boolean[] moves) {
        for (int i = 0; i < moves.length; i++) {
            if (moves[i]) {
                return false;
            }
        }
        return true;
    }

    // Makes an array of booleans all true
    protected void makeAllTrue(boolean[] moves) {
        for (int i = 0; i < moves.length; i++) {
            moves[i] = true;
        }
    }

    /**
     * Checks if the target x and y is already traversed by RW
     */
    private boolean checkIntersection(int x, int y) {
        for (int i = 0; i < this.traversed.size(); i++) {
            if (traversed.get(i) != null) {
                Point point = traversed.get(i);
                if (point.getX() == x && point.getY() == y) {
                    return true;
                }
            }
        }
        return false;
    }
}
