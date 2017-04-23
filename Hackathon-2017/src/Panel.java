//import java.awt.BorderLayout;
//import java.awt.GridLayout;
//import java.awt.Image;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.image.BufferedImage;
//import java.io.IOException;
//import java.net.URL;
//import java.util.Iterator;
//import java.util.LinkedList;
//import java.util.List;
//import java.awt.Dimension;
//import java.awt.Graphics;

//import javax.imageio.ImageIO;
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.SwingConstants;
//import javax.swing.SwingUtilities;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class Panel extends JPanel {

	JButton startButton = new JButton("Start");
	JLabel lifeLabel = new JLabel("Lives: 3");
	JLabel scoreLabel = new JLabel("Score: 0");
	
	BufferedImage backGround;
	BufferedImage deadBackground;
	BufferedImage trash;
	BufferedImage recyclable;
	BufferedImage bin;
	BufferedImage damage1;
	BufferedImage damage2;
	BufferedImage damage3;
	
	int width = 375;
	int height = 559;
	
	int centerx = 160;
	int centery = 260;
	
	List<Trash> trashArr;
	List<Recyclable> recArr;
	
	int lives = 3;
	int score = 0;
	
	//boolean moving = false;
	
	javax.swing.Timer spawnTimer = new javax.swing.Timer(5000, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			//spawn an enemy from one of the 8 random locations
			int randomInt = (int) (Math.random() * 5);
			if (randomInt == 0) {
				recArr.add(new Recyclable(recyclable));
			} else {
				trashArr.add(new Trash(trash));
			}
		}
	});
	
	javax.swing.Timer moveTimer = new javax.swing.Timer(100, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			//spawn an enemy from one of the 8 random locations
			Iterator<Trash> it = trashArr.iterator();
			Iterator<Recyclable> re = recArr.iterator();
            while (it.hasNext()) {
                Trash t = it.next();
                //in this statement,  check if the thing is touching the recycle bin
                if (t.x < centerx+20 && t.x > centerx-20 && t.y < centery+40 && t.y > centery) {
                    it.remove();
                    takeDamage();
                } else { //if it is not, move it normally along its vector
                    if (t.x > centerx+5) {
                    	t.x -= 10;
                    } else if (t.x <= centerx-5) {
                    	t.x += 10;
                    }
                	
                    if (t.y > centery+5) {
                    	t.y -= 10;
                    } else if (t.x <= centery-5) {
                    	t.y += 10;
                    }
                }
            }
            
            while (re.hasNext()) {
                Recyclable r = re.next();
                //in this statement,  check if the thing is touching the recycle bin
                if (r.x < centerx+20 && r.x > centerx-20 && r.y < centery+40 && r.y > centery) {
                    re.remove();
                    score++;
                    updateScore();
                } else { //if it is not, move it normally along its vector
                    if (r.x > centerx+5) {
                    	r.x -= 10;
                    } else if (r.x <= centerx-5) {
                    	r.x += 10;
                    }
                	
                    if (r.y > centery+5) {
                    	r.y -= 10;
                    } else if (r.x <= centery-5) {
                    	r.y += 10;
                    }
                }
            }
            repaint();
		}
	});
	
	public Panel() {
		
		try {
			backGround = ImageIO.read(new URL("https://c402277.ssl.cf1.rackcdn.com/photos/946/images/story_full_width/forests-why-matter_63516847.jpg?1345534028"));
			deadBackground = ImageIO.read(new URL("https://i.ytimg.com/vi/uafeSbQ3hXE/hqdefault.jpg"));
			trash = ImageIO.read(new URL("http://images.amcnetworks.com/bbcamerica.com/wp-content/uploads/2012/05/trash.jpg"));
			recyclable = ImageIO.read(new URL("http://www.extravectors.com/Vectors/MainImages/Water-bottle-vector-object201456.JPG"));
			bin = ImageIO.read(new URL("http://www.homedepot.com/catalog/productImages/1000/4a/4a4f493c-3324-4e7a-9401-e0e9b07461ef_1000.jpg"));
			damage1 = ImageIO.read(new URL("http://www.freeiconspng.com/uploads/x-png-18.png"));
			damage2 = ImageIO.read(new URL("http://www.freeiconspng.com/uploads/x-png-18.png"));
			damage3 = ImageIO.read(new URL("http://www.freeiconspng.com/uploads/x-png-18.png"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		//super();
		this.setLayout(new BorderLayout());
		final JPanel menuPanel = new JPanel();
		
		menuPanel.setPreferredSize(new Dimension(375, 41));
		
		lifeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		trashArr = new LinkedList<>();
		recArr = new LinkedList<>();
		
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				startGame();
			}
		});
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int mousex = e.getX();
				int mousey = e.getY();
				Iterator<Trash> it = trashArr.iterator();
				Iterator<Recyclable> re = recArr.iterator();
		        while (it.hasNext()) {
		            Trash t = it.next();
		            if (mousex > t.x && mousex < t.x+50 && mousey > t.y && mousey < t.y+50) {
		            	//you clicked on this object (inside the boundaries)
		            	System.out.println("Trash hit!");
		            	score++;
		            	updateScore();
		            	it.remove();
		            }
		        }
		        while (re.hasNext()) {
		        	Recyclable r = re.next();
		        	if (mousex > r.x && mousex < r.x+50 && mousey > r.y && mousey < r.y+50) {
		        		//you clicked on this object (inside the boundaries)
		        		System.out.println("Bottle hit!");
		        		takeDamage();
		        		re.remove();
		        	}
		        }
			}
		});
		menuPanel.setLayout(new GridLayout(1, 3));
		
		menuPanel.add(lifeLabel);
		menuPanel.add(scoreLabel);
		menuPanel.add(startButton);
		
		this.add(menuPanel, BorderLayout.NORTH);
	}
	
	public void startGame () {
		startButton.setText("Restart");
		spawnTimer.start();
		moveTimer.start();
		lives = 3;
		score = 0;
		//moving = true;
		//reset the image of the recycling bin to have all it's arrows
		
		//remove all existing trash/recycling objects
		Iterator<Trash> it = trashArr.iterator();
		Iterator<Recyclable> re = recArr.iterator();
        while (it.hasNext()) {
            it.remove();
        }
        while (re.hasNext()) {
            re.remove();
        }
	}
	
	public void endGame() {
		System.out.println("Game Over");
		spawnTimer.stop();
		moveTimer.stop();
		startButton.setText("Start");
		//moving = false;
		//keep the enemies on screen, but stop their movements
	}
	
	public void takeDamage() {
		lives--;
		updateLives();
		if (lives == 0) {
			endGame();
		}
	}
	
	public void updateLives() {
		lifeLabel.setText("Lives: " + lives);
	}
	
	public void updateScore() {
		scoreLabel.setText("Score: " + score);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (lives > 0) {	
        	g.drawImage(backGround, 0, 41, width, height-41, this);
        } else {
        	g.drawImage(deadBackground, 0, 41, width, height-41, this);
        }
        g.drawImage(bin, 137, 250, 100, 100, this);
        
        for (Trash t : trashArr) {
            t.drawTrash(g);
        }
        for (Recyclable r : recArr) {
            r.drawRec(g);
        }
        
        //g.drawImage(trash, 0, 0, 100, 100, this);
        //g.drawImage(recyclable, 0, 0, 50, 50, this);
        
        if (lives <= 2) {
        	g.drawImage(damage1, 175, 275, 20, 20, this);
        } if (lives <= 1) {
        	g.drawImage(damage2, 185, 290, 20, 20, this);
        } if (lives <= 0) {
        	g.drawImage(damage3, 165, 290, 20, 20, this);
        }
    }
	
	@Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Defend the Planet!");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new Panel());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
