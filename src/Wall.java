import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class Wall {

    private Vector2D start = null;

    private Vector2D end = null;

    private Color color = null;

    public Wall(Vector2D start, Vector2D end, Color color) {
        this.start = start;
        this.end = end;

        this.color = color;
    }

    @Override
    public String toString() {
        return "Wall{" +
                "start=" + this.start +
                ", end=" + this.end +
                ", color=" + this.color +
                '}';
    }

    public Vector2D getStart() {
        return this.start;
    }

    public Vector2D getEnd() {
        return this.end;
    }

    public Color getColor() {
        return this.color;
    }
}
