import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Camera {

    private final Vector2D POSITION = new Vector2D(0f, 0f);

    private final Vector2D DIRECTION = new Vector2D(0f, -1f);

    public final static double VIEW_DISTANCE = 1000f;

    private Vector2D worldPos;

    private Vector2D worldDir;

    private double fov;

    private int xRes;

    private int yRes;

    public ArrayList<Vector2D> rays = new ArrayList<>();

    private ArrayList<Wall> transformedWalls = new ArrayList<>();

    public Camera(Vector2D pos, Vector2D dir, double fov, int xRes, int yRes) {

        if ((float) xRes / (float) yRes < 0.5 || (float) xRes / (float) yRes > 2.0)
            throw new IllegalArgumentException("The aspect Ratio has to be between '0.5' and '2' (calculated " + ((float) xRes / (float) yRes) + ")");

        this.worldPos = pos;
        this.worldDir = dir;
        this.fov = fov;
        this.xRes = xRes;
        this.yRes = yRes;

        this.generateRays();
    }

    private void generateRays() {
        double cam_width = 2 * Math.tan(Math.toRadians(this.fov) / 2);
        double offset = cam_width / (this.xRes - 1);

        Vector2D startOfRays = this.DIRECTION.add(-cam_width / 2, 0f);

        for (int i = 0; i < this.xRes; i++) {
            this.rays.add(startOfRays.add((offset * (double) i), 0f).normalize());
        }
    }

    public ArrayList<RayHit> getIntersections(ArrayList<Wall> walls) {
        Vector2D offset = this.POSITION.sub(this.worldPos);
        double angle = this.DIRECTION.angle(this.worldDir);
        ArrayList<RayHit> hits = new ArrayList<>();

        for (Wall w : walls) {
            this.transformedWalls.add(new Wall(w.getStart().add(offset).rotate(angle), w.getEnd().add(offset).rotate(angle), w.getColor()));
        }

        // TODO after transformation C1 is always 0 ? can be simplified
        for (Vector2D ray : this.rays) {

            Vector2D scaledRay = ray.scale(Camera.VIEW_DISTANCE);
            RayHit hit = new RayHit(new Vector2D(10000, 10000), Color.BLACK); // TODO generic backgroundcolor?

            for (Wall w : this.transformedWalls) {

                // math to get intersection
                double A1 = scaledRay.getY() - this.POSITION.getY();
                double B1 = this.POSITION.getX() - scaledRay.getX();
                double C1 = A1 * this.POSITION.getX() + B1 * this.POSITION.getY();

                assert C1 == 0;

                double A2 = w.getEnd().getY() - w.getStart().getY();
                double B2 = w.getStart().getX() - w.getEnd().getX();
                double C2 = A2 * w.getStart().getX() + B2 * w.getEnd().getY();

                double det = A1 * B2 - A2 * B1;

                // if ray and wall are parallel treat as if ray doesn't hit
                if (det == 0)
                    break;

                // point where lines intersect
                Vector2D intersect = new Vector2D((B2 * C1 - B1 * C2) / det, (A1 * C2 - A2 * C1) / det);

                // check if segments intersect and distance is smaller than previous
                if (intersect.getX() <= Math.max(this.POSITION.getX(), scaledRay.getX())
                        && intersect.getX() >= Math.min(this.POSITION.getX(), scaledRay.getX())
                        && intersect.getY() <= Math.max(this.POSITION.getY(), scaledRay.getY())
                        && intersect.getY() >= Math.min(this.POSITION.getY(), scaledRay.getY())
                        && intersect.length() < hit.getHitPoint().length()) {

                        intersect = intersect.rotate(-angle).sub(offset);
                        hit = new RayHit(intersect, w.getColor());
                }
            }

            hits.add(hit);
        }

        return hits;
    }

    public void takeImage(ArrayList<Wall> walls) {

        ArrayList<RayHit> hits = this.getIntersections(walls);

        BufferedImage img = new BufferedImage(this.xRes, this.yRes, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();

        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, 0, this.xRes, this.yRes / 2);

        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(0, this.yRes / 2, this.xRes, this.yRes);

        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(Color.BLACK);

        for(int i = 0; i < this.xRes; i++) {

            double distance = hits.get(i).getHitPoint().length();
            int height =(int) ((double) this.yRes / (((double) (this.yRes - 1)) * (distance / Camera.VIEW_DISTANCE) + 1));

            g2d.setColor(hits.get(i).getColorOfHit());
            g2d.drawLine(i, this.yRes /2 - height / 2, i ,this.yRes / 2 + height / 2);
        }

        File outfile = new File("image.jpg");
        try {
            ImageIO.write(img, "jpg", outfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void rotate(double rad) {
        this.worldDir = this.worldDir.rotate(rad);
    }

    public void move(double x, double y) {
        this.worldPos = this.worldPos.add(x, y);
    }

    public Vector2D getWorldDir() {
        return this.worldDir;
    }

    public void setWorldDir(Vector2D worldDir) {
        this.worldDir = worldDir;
    }

    public Vector2D getWorldPos() {
        return this.worldPos;
    }

    public void setWorldPos(Vector2D worldPos) {
        this.worldPos = worldPos;
    }

    public int getXRes() {
        return this.xRes;
    }

    public int getYRes() {
        return this.yRes;
    }
}
