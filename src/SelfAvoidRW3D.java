import java.util.HashMap;
import java.util.Random;

public class SelfAvoidRW3D extends SelfAvoidRW2D {
    private static final double pi2 = 2.0 * Math.PI;
    private int z;
    private HashMap<Integer, Point_3D> traversed;

    public SelfAvoidRW3D(int x, int y, int z, int steps) {
        super(x, y, steps);
        this.z = z;
        this.traversed = new HashMap<Integer, Point_3D>();
    }

    public SelfAvoidRW3D(int steps) {
        super(steps);
        this.z = 0;
        this.traversed = new HashMap<Integer, Point_3D>();
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    /**
     * Runs the randomwalker algorithm once, and calculates the values for r and r-squared
     */
    public void runAndAverage(Random rand) {
        String[] directions = {"z+1", "z-1", "y+1", "y-1", "x+1", "x-1"};


        boolean[] moves = new boolean[6];
        super.makeAllTrue(moves);
        boolean movable = true;

        while (movable) {
            int choice = rand.nextInt(5);
            //move randomwalker in 3D by 1 step
            //if the RW is removed, then it is null
            double r2;
            String dir = directions[choice];

            //Check if RW can move to that direction, if not, try next direction
            //If stuck, then remove RW from simulation by replacing with null
            if (dir == "z+1") {
                if (checkIntersection(this.getX(), this.getY(), this.z + 1)) {
                    moves[4] = false;
                    if (super.checkAllFalse(moves)) {
                        movable = false;
                        stuck();
                    }
                    continue;
                }
                this.setZ(this.getZ() + 1);
            } else if (dir == "z-1") {
                if (checkIntersection(this.getX(), this.getY(), this.z - 1)) {
                    moves[5] = false;
                    if (super.checkAllFalse(moves)) {
                        movable = false;
                        stuck();
                    }
                    continue;
                }
                this.setZ(this.getZ() - 1);
            } else if (dir == "y+1") {
                if (checkIntersection(this.getX(), this.getY() + 1, this.z)) {
                    moves[2] = false;
                    if (super.checkAllFalse(moves)) {
                        movable = false;
                        stuck();
                    }
                    continue;
                }
                this.setY(this.getY() + 1);
            } else if (dir == "y-1") {
                if (checkIntersection(this.getX(), this.getY() - 1, this.z)) {
                    moves[3] = false;
                    if (super.checkAllFalse(moves)) {
                        movable = false;
                        stuck();
                    }
                    continue;
                }
                this.setY(this.getY() - 1);;
            } else if (dir == "x+1") {
                if (checkIntersection(this.getX() + 1, this.getY(), this.z)) {
                    moves[0] = false;
                    if (super.checkAllFalse(moves)) {
                        movable = false;
                        stuck();
                    }
                    continue;
                }
                this.setX(this.getX() + 1);
            } else if (dir == "x-1") {
                if (checkIntersection(this.getX() - 1, this.getY(), this.z)) {
                    moves[1] = false;
                    if (super.checkAllFalse(moves)) {
                        movable = false;
                        stuck();
                    }
                    continue;
                }
                this.setX(this.getX() - 1);
            }

            this.setR2(this.getHashKey(),getX()* getX() + getY() * getY() + this.z * this.z);
            traversed.put(getHashKey(), new Point_3D(getX(), getY(), z));
            this.incrementKey();

            movable = false;
        }


    }

    /**
     * Checks if the target x, y, and z is already traversed by RW
     */
    private boolean checkIntersection(int x, int y, int z) {
        for (int i = 0; i < traversed.size(); i++) {
            if (traversed.get(i) != null) {
                Point_3D point = traversed.get(i);
                if (point.getX() == x && point.getY() == y && point.getZ() == z) {
                    return true;
                }
            }
        }
        return false;
    }

}
