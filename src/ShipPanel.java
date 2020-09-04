import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class ShipPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private final int GRIDWIDTH = WIDTH * 4 / 9;
	private final int CELLSIZE = GRIDWIDTH/11;
	
	private int direction;
	private char type;
	private BufferedImage i, carrier0, carrier1, carrier2, carrier3, battleship0, battleship1, battleship2, battleship3, cruiser0, cruiser1, cruiser2, cruiser3, submarine0, submarine1, submarine2, submarine3, destroyer0, destroyer1, destroyer2, destroyer3;

	public ShipPanel(char type, BufferedImage[] shipSprites) {
		direction = 0;
		
		carrier0 = shipSprites[0];
		carrier1 = shipSprites[1];
		carrier2 = shipSprites[2];
		carrier3 = shipSprites[3];
		battleship0 = shipSprites[4];
		battleship1 = shipSprites[5];
		battleship2 = shipSprites[6];
		battleship3 = shipSprites[7];
		cruiser0 = shipSprites[8];
		cruiser1 = shipSprites[9];
	 	cruiser2 = shipSprites[10];
		cruiser3 = shipSprites[11];
		submarine0 = shipSprites[12];
		submarine1 = shipSprites[13];
		submarine2 = shipSprites[14];
		submarine3 = shipSprites[15];
		destroyer0 = shipSprites[16];
		destroyer1 = shipSprites[17];
		destroyer2 = shipSprites[18];
		destroyer3 = shipSprites[19];
		this.type = type;
		if (type == 'K') {
			if (direction == 0) {
				i = carrier0;
			} else if (direction == 1) {
				i = carrier1;
			} else if (direction == 2) {
				i = carrier2;
			} else {
				i = carrier3;
			}
		} else if (type == 'B') {
			if (direction == 0) {
				i = battleship0;
			} else if (direction == 1) {
				i = battleship1;
			} else if (direction == 2) {
				i = battleship2;
			} else {
				i = battleship3;
			}
		} else if (type == 'C') {
			if (direction == 0) {
				i = cruiser0;
			} else if (direction == 1) {
				i = cruiser1;
			} else if (direction == 2) {
				i = cruiser2;
			} else {
				i = cruiser3;
			}
		} else if (type == 'S') {
			if (direction == 0) {
				i = submarine0;
			} else if (direction == 1) {
				i = submarine1;
			} else if (direction == 2) {
				i = submarine2;
			} else {
				i = submarine3;
			}
		} else if (type == 'D') {
			if (direction == 0) {
				i = destroyer0;
			} else if (direction == 1) {
				i = destroyer1;
			} else if (direction == 2) {
				i = destroyer2;
			} else {
				i = destroyer3;
			}
		} else {
		}
	}

	public void setDirection(int dir) {
		direction = dir;
	}

	public int getDirection() {
		return direction;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		double multiplier = (double)(CELLSIZE/50);
		if (multiplier == 0) {
			multiplier = 1;
		}
		BufferedImage bi;
		bi = scale(i, i.getType(), (int)(i.getWidth()*multiplier), (int)(i.getHeight()*multiplier), multiplier, multiplier);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bi, 0, 0, null);
	}
	
	public static BufferedImage scale(BufferedImage sbi, int imageType, int dWidth, int dHeight, double fWidth, double fHeight) {
	    BufferedImage dbi = null;
	    if(sbi != null) {
	        dbi = new BufferedImage(dWidth, dHeight, imageType);
	        Graphics2D g = dbi.createGraphics();
	        AffineTransform at = AffineTransform.getScaleInstance(fWidth, fHeight);
	        g.drawRenderedImage(sbi, at);
	    }
	    return dbi;
	}

	public void changeImage() {
		try {
			if (type == 'K') {
				if (direction == 0) {
					i = carrier0;
				} else if (direction == 1) {
					i = carrier1;
				} else if (direction == 2) {
					i = carrier2;
				} else {
					i = carrier3;
				}
			} else if (type == 'B') {
				if (direction == 0) {
					i = battleship0;
				} else if (direction == 1) {
					i = battleship1;
				} else if (direction == 2) {
					i = battleship2;
				} else {
					i = battleship3;
				}
			} else if (type == 'C') {
				if (direction == 0) {
					i = cruiser0;
				} else if (direction == 1) {
					i = cruiser1;
				} else if (direction == 2) {
					i = cruiser2;
				} else {
					i = cruiser3;
				}
			} else if (type == 'S') {
				if (direction == 0) {
					i = submarine0;
				} else if (direction == 1) {
					i = submarine1;
				} else if (direction == 2) {
					i = submarine2;
				} else {
					i = submarine3;
				}
			} else if (type == 'D') {
				if (direction == 0) {
					i = destroyer0;
				} else if (direction == 1) {
					i = destroyer1;
				} else if (direction == 2) {
					i = destroyer2;
				} else {
					i = destroyer3;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
