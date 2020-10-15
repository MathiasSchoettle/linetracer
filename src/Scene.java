import Utils.ColorUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Scene {

    Camera mainCamera = new Camera(new Vector2D(0,0), new Vector2D(0, -1), 90, 1200, 600);

    ArrayList<Wall> walls = new ArrayList<>();

    private boolean showDebugValue = false;

    public Scene() {
        this.setupWalls();
    }

    private void setupWalls() {

        Vector2D corner1 = new Vector2D(-10,10);
        Vector2D corner2 = new Vector2D(10,10);
        Vector2D corner3 = new Vector2D(10,-10);
        Vector2D corner4 = new Vector2D(-10,-10);

        this.walls.add(new Wall(corner1, corner2, ColorUtils.getRandomColor()));
        this.walls.add(new Wall(corner2, corner3, ColorUtils.getRandomColor()));
        this.walls.add(new Wall(corner3, corner4, ColorUtils.getRandomColor()));
        this.walls.add(new Wall(corner4, corner1, ColorUtils.getRandomColor()));
    }

    public static void main(String[] args) throws InterruptedException {
        Scene testScene = new Scene();

        JFrame f = new JFrame("Line-Tracer");
        Container pane = f.getContentPane();
        pane.setLayout(new BorderLayout());

        JButton switchViewButton = new JButton("Switch View");
        switchViewButton.addActionListener(e -> {
            testScene.showDebugValue = !testScene.showDebugValue;
        });
        pane.add(switchViewButton, BorderLayout.EAST);

        JTextField timePerFrame = new JTextField("0 ms");
        timePerFrame.setEditable(false);
        pane.add(timePerFrame, BorderLayout.NORTH);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(600, 600);
        f.setResizable(false);

        DebugCanvas dc = new DebugCanvas();
        dc.setCamera(testScene.mainCamera);
        dc.setWalls(testScene.walls);
        pane.add(dc, BorderLayout.CENTER);

        f.setVisible(true);

        dc.setCamera(testScene.mainCamera);
        dc.setWalls(testScene.walls);
        dc.setHits(testScene.mainCamera.getIntersections(testScene.walls));


        while(!testScene.showDebugValue) {
                long time = System.currentTimeMillis();
                testScene.mainCamera.rotate(Math.toRadians(1));
                timePerFrame.setText((System.currentTimeMillis() - time) + " ms");
                dc.repaint();
                dc.setHits(testScene.mainCamera.getIntersections(testScene.walls));
        }
    }
}
