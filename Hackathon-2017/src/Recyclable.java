import java.awt.Graphics;
import java.awt.Image;

public class Recyclable {
	Image rec;
    int x = 150;
    int y = 125;

    public Recyclable(Image image) {
        rec = image;
        
        int random = (int) (Math.random() * 4);
        if (random == 0) {
        	x = 0;
        	y = 50;
        } else if (random == 1) {
        	x = 325;
        	y = 50;
        } else if (random == 2) {
        	x = 325;
        	y = 509;
        } else {
        	x = 0;
        	y = 509;
        }
    }

    public void drawRec(Graphics g) {
        g.drawImage(rec, x, y, 50, 50, null);
    }
}
