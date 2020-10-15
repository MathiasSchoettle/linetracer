import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class Canvas extends JPanel {

    private ArrayList<RayHit> rayHits = new ArrayList<>();

    public Canvas() {
    }

    public Dimension getPreferredSize() {
        return new Dimension(this.getParent().getSize());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.GRAY);
        g.fillRect(0, 0, this.getWidth(), this.getHeight() / 2);

        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, this.getHeight() / 2, this.getWidth(), this.getHeight());

        g.setColor(Color.BLACK);

        for(int i = 0; i < this.getWidth(); i++) {

            double distance = this.rayHits.get(i).getHitPoint().length();
            int height =(int) ((double) this.getHeight() / (((double) (this.getHeight() - 1)) * (distance / Camera.VIEW_DISTANCE)));

            g.setColor(this.rayHits.get(i).getColorOfHit());
            g.drawLine(i, this.getHeight() /2 - height / 2, i ,this.getHeight() / 2 + height / 2);
        }
    }

    public void updateHits(ArrayList<RayHit> hits) {
        this.rayHits = hits;
    }
}