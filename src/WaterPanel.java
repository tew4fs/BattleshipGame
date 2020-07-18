import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class WaterPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage i;
	private BufferedImage water0, water1, water2, water3, water4, water5, water6, water7, water8, water9, island0;


	public WaterPanel(BufferedImage[] waterSprites) {
		
		water0 = waterSprites[0];
		water1 = waterSprites[1];
		water2 = waterSprites[2];
		water3 = waterSprites[3];
		water4 = waterSprites[4];
		water5 = waterSprites[5];
		water6 = waterSprites[6];
		water7 = waterSprites[7];
		water8 = waterSprites[8];
		water9 = waterSprites[9];
		island0 = waterSprites[10];
		
		int waterChance = (int) (Math.random() * 100);
		if (waterChance < 10) {
			i = water0;
		} else if (waterChance < 20) {
			i = water1;
		} else if (waterChance < 30) {
			i = water2;
		} else if (waterChance < 40) {
			i = water3;
		} else if (waterChance < 50) {
			i = water4;
		} else if (waterChance < 60) {
			i = water5;
		} else if (waterChance < 70) {
			i = water6;
		} else if (waterChance < 80) {
			i = water7;
		} else if (waterChance < 90) {
			i = water8;
		} else {
			i = water9;
		}

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(i, 0, 0, null);
	}

	public void changeImage() {
		int islandChance = (int) (Math.random() * 1000);
		if (islandChance < 5) {
			i = island0;
		} else {
			int waterChance = (int) (Math.random() * 100);
			if (waterChance < 10) {
				i = water0;
			} else if (waterChance < 20) {
				i = water1;
			} else if (waterChance < 30) {
				i = water2;
			} else if (waterChance < 40) {
				i = water3;
			} else if (waterChance < 50) {
				i = water4;
			} else if (waterChance < 60) {
				i = water5;
			} else if (waterChance < 70) {
				i = water6;
			} else if (waterChance < 80) {
				i = water7;
			} else if (waterChance < 90) {
				i = water8;
			} else {
				i = water9;
			}
		}
	}
}
