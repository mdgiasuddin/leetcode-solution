package heap;

public class Point2D implements Comparable<Point2D> {
    public int distanceFromOrigin;
    public int x;
    public int y;

    public Point2D(int distanceFromOrigin, int x, int y) {
        this.distanceFromOrigin = distanceFromOrigin;
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Point2D o) {
        return this.distanceFromOrigin - o.distanceFromOrigin;
    }
}
