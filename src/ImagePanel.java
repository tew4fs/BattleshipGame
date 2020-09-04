import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.DataInputStream;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int direction;
	private Image i, hitMarker, missMarker;
	public ImagePanel(char type) {
		direction = 0;
		// ====== Start of Loading Images ======
		try {
			DataInputStream datainputstream = new DataInputStream(
					getClass().getResourceAsStream("Sprites/RedDotSprite.png"));
			byte abyte0[] = new byte[datainputstream.available()];
			datainputstream.readFully(abyte0);
			datainputstream.close();
			hitMarker = Toolkit.getDefaultToolkit().createImage(abyte0);

			datainputstream = new DataInputStream(
					getClass().getResourceAsStream("Sprites/WhiteDotSprite.png"));
			byte abyte1[] = new byte[datainputstream.available()];
			datainputstream.readFully(abyte1);
			datainputstream.close();
			missMarker = Toolkit.getDefaultToolkit().createImage(abyte1);
		} catch (Exception e) {

		}

		// ====== End of Loading Images ======
		if (type == 'H') {
			i = hitMarker;
		} else if (type == 'M') {
			i = missMarker;
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
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(i, 0, 0, null);
	}
	
}
