import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class DebugCanvas extends JPanel {

    private ArrayList<RayHit> rayHits = new ArrayList<>();

    private Camera camera;

    private ArrayList<Wall> walls = new ArrayList<>();

    public DebugCanvas() {
    }

    public Dimension getPreferredSize() {
        return new Dimension(this.getParent().getSize());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        double factor = 20;

        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());

        g2.setColor(Color.BLACK);
        Vector2D middle = new Vector2D(this.getWidth() / 2d, this.getHeight() / 2d);

        for (Wall w : this.walls) {
            Vector2D tempStart = w.getStart().add(middle);
            tempStart = tempStart.add(tempStart.sub(middle).scale(factor));

            Vector2D tempEnd = w.getEnd().add(middle);
            tempEnd = tempEnd.add(tempEnd.sub(middle).scale(factor));

            g2.setStroke(new BasicStroke(3));
            g2.drawLine((int) tempStart.getX(), (int) tempStart.getY(), (int) tempEnd.getX(), (int) tempEnd.getY());
        }

        Vector2D pos = this.camera.getWorldPos().add(middle);
        pos = pos.add(pos.sub(middle).scale(factor));
        g2.setColor(Color.RED);
        int circleSize = (int) (factor / 2);
        g2.drawOval((int) pos.getX() - (circleSize / 2), (int) pos.getY() - (circleSize / 2), circleSize, circleSize);

        Vector2D dir = this.camera.getWorldDir();


        if (true) {

            g2.setColor(Color.BLUE);
            g2.setStroke(new BasicStroke(1.5f));

            Vector2D temp = pos.add(dir.scale(factor * 2));
            g2.drawLine((int) pos.getX(), (int) pos.getY(), (int) temp.getX(), (int) temp.getY());
        } else {

            g2.setColor(Color.BLUE);
            g2.setStroke(new BasicStroke(0.5f));

            int count = 0;

            for (Vector2D r : this.camera.rays) {

                if((count = count % 20) == 0) {

                    Vector2D temp = r.rotate(Math.toRadians(20)).normalize().add(middle);
                    temp = temp.add(temp.sub(middle).scale(factor * 5));
                    g2.drawLine((int) pos.getX(), (int) pos.getY(), (int) temp.getX(), (int) temp.getY());
                }

                count++;
            }
        }
    }

    public void setHits(ArrayList<RayHit> hits) {
        this.rayHits = hits;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void setWalls(ArrayList<Wall> walls) {
        this.walls = walls;
    }
}