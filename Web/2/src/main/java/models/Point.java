package models;


public class Point {
    private final int x;
    private final double y;
    private final double r;
    private final boolean isInArea;

    public Point(int x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.isInArea = isInArea(x,y,r);
    }

    private boolean isInArea(int x, double y, double r) {
        if(x < 0 && y >= 0) {
            return -2*x + y <= r && 2*x >= -r && y <= r;
        } else if (x>= 0 && y >= 0) {
            return 4*(x*x + y*y) <= r;
        } else if (x>=0 && y <= 0) {
            return x <= r && y >= -r;
        }
        return false;
    }

    public int getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        return r;
    }

    public boolean isInArea() {
        return isInArea;
    }
}
