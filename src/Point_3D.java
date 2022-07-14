public class Point_3D extends Point {
    private int z;
    public Point_3D(int x, int y, int z) {
        super(x, y);
        this.z = z;
    }

    public int getZ() {
        return z;
    }
    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "Point [x=" + getX() + ", y=" + getY() + ", z=" + getZ() + "]";
    }

}
