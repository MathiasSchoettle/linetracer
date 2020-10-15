public class Vector2D {

    private double xValue = 0.0;

    private double yValue = 0.0;

    public Vector2D(double x, double y) {
        this.xValue = x;
        this.yValue = y;
    }

    public Vector2D add(Vector2D vec) {
        return new Vector2D(this.xValue + vec.getX(), this.yValue + vec.getY());
    }

    public Vector2D add(double x, double y) {
        return new Vector2D(this.xValue + x, this.yValue + y);
    }

    public Vector2D sub(Vector2D vec) {
        return new Vector2D(this.xValue - vec.getX(), this.yValue - vec.getY());
    }

    public Vector2D sub(double x, double y) {
        return new Vector2D(this.xValue - x, this.yValue - y);
    }

    public Vector2D scale(double factor) {
        return new Vector2D(this.xValue * factor, this.yValue * factor);
    }

    public double dot(Vector2D vec) {
        return this.xValue * vec.getX() + this.yValue * vec.getY();
    }

    public double angle(Vector2D vec) {
        return Math.acos(this.dot(vec) / (this.length() * vec.length()));
    }

    public Vector2D rotate(double angle) {
        double x = Math.cos(angle) * this.xValue - Math.sin(angle) * this.yValue;
        double y = Math.sin(angle) * this.xValue + Math.cos(angle) * this.yValue;

        return new Vector2D(x, y);
    }

    public double length() {
        return Math.sqrt(this.xValue * this.xValue + this.yValue * this.yValue);
    }

    public Vector2D normalize() {
        return this.scale(1 / this.length());
    }

    @Override
    public String toString() {
        return "(x: " + this.xValue + ", y: " + this.yValue + ")";
    }

    public double getX() {
        return this.xValue;
    }

    public double getY() {
        return this.yValue;
    }
}
