import java.awt.Graphics;
import java.awt.Image;

public class Trash {
	Image trash;
    int x = 150;
    int y = 125;

    public Trash(Image image) {
        trash = image;
        
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

    public void drawTrash(Graphics g) {
        g.drawImage(trash, x, y, 50, 50, null);
    }
}
