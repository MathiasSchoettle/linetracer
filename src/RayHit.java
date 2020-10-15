import java.awt.*;

public class RayHit {

    Vector2D hitPoint;

    Color colorOfHit;

    public RayHit(Vector2D hitPoint, Color colorOfHit) {
        this.hitPoint = hitPoint;
        this.colorOfHit = colorOfHit;
    }

    public Vector2D getHitPoint() {
        return this.hitPoint;
    }

    public Color getColorOfHit() {
        return this.colorOfHit;
    }
}

