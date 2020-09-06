import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
import javax.swing.*;
public class Window extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int WIDTH = 1280;
	private final int HEIGHT = 720;
	private final boolean ANIMATION = true;

	private boolean onStartScreen, onShipSelectionScreen, onPlayScreen, initHomeScreen, initSelectionScreen, initPlayScreen;
	private float musicVolume;
	private StartScreenPanel startScreen;
	private GridPanel selectionScreen;
	private PlayPanel playScreen;
	private BufferedImage carrier0, carrier1, carrier2, carrier3, battleship0, battleship1, battleship2, battleship3, cruiser0, cruiser1, cruiser2, cruiser3, submarine0, submarine1, submarine2, submarine3, destroyer0, destroyer1, destroyer2, destroyer3;
	private BufferedImage water0, water1, water2, water3, water4, water5, water6, water7, water8, water9, island0;
	private BufferedImage[] shipSprites, waterSprites;
	private MusicPlayer musicPlayer;
	
	public Window() {
		super();
		this.setLayout(null);
		this.setBounds(0, 0, WIDTH, HEIGHT);
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setBackground(new Color(6, 9, 199));
		this.setVisible(true);
		
		onStartScreen = false;
		onShipSelectionScreen = false;
		onPlayScreen = false;
		initHomeScreen = false;
		initSelectionScreen = false;
		initPlayScreen = false;
		onStartScreen = true;
		shipSprites = new BufferedImage[20];
		waterSprites = new BufferedImage[11];
		musicVolume = (float) 0.7;
		
		try{ 
			URL url = this.getClass().getResource("ActionMusic.wav");
			musicPlayer = new MusicPlayer(url); 
			musicPlayer.stop();
		} 
		catch (Exception ex) {  }
		initSprites(); 
		initializeStartScreen(ANIMATION);
		this.getVolume();
	}
	
	private void getVolume() {
		Timer timer = new Timer(5, null);
		timer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(onStartScreen) {
					musicVolume = startScreen.getVolume();
				}else if(onShipSelectionScreen) {
					musicVolume = selectionScreen.getVolume();
				}else if(onPlayScreen){
					musicVolume = playScreen.getVolume();
				}
				musicPlayer.changeVolume(musicVolume);
			}
			
		});
		timer.start();
	}
	
	public void initSprites() {
		try {
			carrier0 = ImageIO.read(getClass().getResource("Sprites/ShipSprites/Carrier0.png"));
			carrier1 = ImageIO.read(getClass().getResource("Sprites/ShipSprites/Carrier1.png"));
			carrier2 = ImageIO.read(getClass().getResource("Sprites/ShipSprites/Carrier2.png"));
			carrier3 = ImageIO.read(getClass().getResource("Sprites/ShipSprites/Carrier3.png"));
			
			battleship0 = ImageIO.read(getClass().getResource("Sprites/ShipSprites/Battleship0.png"));
			battleship1 = ImageIO.read(getClass().getResource("Sprites/ShipSprites/Battleship1.png"));
			battleship2 = ImageIO.read(getClass().getResource("Sprites/ShipSprites/Battleship2.png"));
			battleship3 = ImageIO.read(getClass().getResource("Sprites/ShipSprites/Battleship3.png"));
			
			cruiser0 = ImageIO.read(getClass().getResource("Sprites/ShipSprites/Cruiser0.png"));
			cruiser1 = ImageIO.read(getClass().getResource("Sprites/ShipSprites/Cruiser1.png"));
			cruiser2 = ImageIO.read(getClass().getResource("Sprites/ShipSprites/Cruiser2.png"));
			cruiser3 = ImageIO.read(getClass().getResource("Sprites/ShipSprites/Cruiser3.png"));
			
			submarine0 = ImageIO.read(getClass().getResource("Sprites/ShipSprites/Submarine0.png"));
			submarine1 = ImageIO.read(getClass().getResource("Sprites/ShipSprites/Submarine1.png"));
			submarine2 = ImageIO.read(getClass().getResource("Sprites/ShipSprites/Submarine2.png"));
			submarine3 = ImageIO.read(getClass().getResource("Sprites/ShipSprites/Submarine3.png"));
			
			destroyer0 = ImageIO.read(getClass().getResource("Sprites/ShipSprites/Destroyer0.png"));
			destroyer1 = ImageIO.read(getClass().getResource("Sprites/ShipSprites/Destroyer1.png"));
			destroyer2 = ImageIO.read(getClass().getResource("Sprites/ShipSprites/Destroyer2.png"));
			destroyer3 = ImageIO.read(getClass().getResource("Sprites/ShipSprites/Destroyer3.png"));
			
			shipSprites[0] = carrier0;
			shipSprites[1] = carrier1;
			shipSprites[2] = carrier2;
			shipSprites[3] = carrier3;
			shipSprites[4] = battleship0;
			shipSprites[5] = battleship1;
			shipSprites[6] = battleship2;
			shipSprites[7] = battleship3;
			shipSprites[8] = cruiser0;
			shipSprites[9] = cruiser1;
			shipSprites[10] = cruiser2;
			shipSprites[11] = cruiser3;
			shipSprites[12] = submarine0;
			shipSprites[13] = submarine1;
			shipSprites[14] = submarine2;
			shipSprites[15] = submarine3;
			shipSprites[16] = destroyer0;
			shipSprites[17] = destroyer1;
			shipSprites[18] = destroyer2;
			shipSprites[19] = destroyer3;
			
			water0 = ImageIO.read(getClass().getResource("Sprites/WaterSprites/Water0.png"));
			water1 = ImageIO.read(getClass().getResource("Sprites/WaterSprites/Water1.png"));
			water2 = ImageIO.read(getClass().getResource("Sprites/WaterSprites/Water2.png"));
			water3 = ImageIO.read(getClass().getResource("Sprites/WaterSprites/Water3.png"));
			water4 = ImageIO.read(getClass().getResource("Sprites/WaterSprites/Water4.png"));
			water5 = ImageIO.read(getClass().getResource("Sprites/WaterSprites/Water5.png"));
			water6 = ImageIO.read(getClass().getResource("Sprites/WaterSprites/Water6.png"));
			water7 = ImageIO.read(getClass().getResource("Sprites/WaterSprites/Water7.png"));
			water8 = ImageIO.read(getClass().getResource("Sprites/WaterSprites/Water8.png"));
			water9 = ImageIO.read(getClass().getResource("Sprites/WaterSprites/Water9.png"));
			island0 = ImageIO.read(getClass().getResource("Sprites/WaterSprites/Island0.png"));
			
			waterSprites[0] = water0;
			waterSprites[1] = water1;
			waterSprites[2] = water2;
			waterSprites[3] = water3;
			waterSprites[4] = water4;
			waterSprites[5] = water5;
			waterSprites[6] = water6;
			waterSprites[7] = water7;
			waterSprites[8] = water8;
			waterSprites[9] = water9;
			waterSprites[10] = island0;
		} catch (IOException e) {}
	}
	
	public void goToStartScreen() {
		if(onShipSelectionScreen) {
			selectionScreen.fadeOut();
			while(!selectionScreen.fadeOutComplete()) {
				System.out.print("");
			}
		}else if(onPlayScreen) {
			playScreen.fadeOut();
			while(!playScreen.fadeOutComplete()) {
				System.out.print("");
			}
		}
		this.removeAll();
		this.initializeStartScreen(false);
		onStartScreen = true;
		onShipSelectionScreen = false;
		onPlayScreen = false;
	}

	public void goToSelectionScreen() {
		if(onStartScreen) {
			startScreen.fadeOut();
			while(!startScreen.fadeOutComplete()) {
				System.out.print("");
			}
		}else if(onPlayScreen) {
			playScreen.fadeOut();
			while(!playScreen.fadeOutComplete()) {
				System.out.print("");
			}
		}
		this.removeAll();
		this.initializeSelectionScreen();
		onStartScreen = false;
		onPlayScreen = false;
		onShipSelectionScreen = true;
	}

	public void goToPlayScreen() {
		selectionScreen.fadeOut();
		while(!selectionScreen.fadeOutComplete()) {
			System.out.print("");
		}
		
		this.removeAll();
		char[][] grid = selectionScreen.getGrid();
		int [] shipDirections = selectionScreen.getDirections();
		this.initializePlayScreen(grid, shipDirections);
		onShipSelectionScreen = false;
		onPlayScreen = true;
	}

	private void initHomeScreenThread() {
		new Thread() {
			public void run() {
				do {
					if(startScreen.goToPlay()) {
						goToSelectionScreen();
						initHomeScreen = false;
					}
				} while (initHomeScreen);
			}
		}.start();
	}
	
	private void initSelectionScreenThread() {
		new Thread() {
			public void run() {
				do {
					if(selectionScreen.changePlay()) {
						goToPlayScreen();
						initSelectionScreen = false;
					}else if(selectionScreen.changeMenu()) {
						goToStartScreen();
						initSelectionScreen = false;
					}
				} while (initSelectionScreen);
			}
		}.start();
	}
	
	private void initPlayScreenThread() {
		new Thread() {
			public void run() {
				do {
					if(playScreen.playAgain()) {
						goToSelectionScreen();
						initPlayScreen = false;
					}else if(playScreen.changeMenu()) {
						goToStartScreen();
						initPlayScreen = false;
					}
				} while (initPlayScreen);
			}
		}.start();
	}
	
	public void initializeStartScreen(boolean animation) {
		startScreen = new StartScreenPanel(animation, musicPlayer, musicVolume);
		this.add(startScreen);
		startScreen.setShipSprites(shipSprites);
		startScreen.setWaterSprites(waterSprites);
		startScreen.createAndShowGUI();
		startScreen.grabFocus();
		startScreen.fadeIn();
		initHomeScreen = true;
		initHomeScreenThread();
	}
	
	public void initializeSelectionScreen() {
		selectionScreen = new GridPanel(musicVolume);
		this.add(selectionScreen);
		selectionScreen.setShipSprites(shipSprites);
		selectionScreen.setWaterSprites(waterSprites);
		selectionScreen.createAndShowGUI();
		selectionScreen.fadeIn();
		selectionScreen.grabFocus();
		initSelectionScreen = true;
		initSelectionScreenThread();
	}
	
	public void initializePlayScreen(char[][] grid, int[] dirs) {
		playScreen = new PlayPanel(musicVolume);
		this.add(playScreen);
		playScreen.setShipSprites(shipSprites);
		playScreen.setShipDirections(dirs);
		playScreen.setPlayerGrid(grid);
		playScreen.setWaterSprites(waterSprites);
		playScreen.createAndShowGUI();
		playScreen.fadeIn();
		playScreen.grabFocus();
		initPlayScreen = true;
		initPlayScreenThread();
	}

	public static void main(String[] args) {
		int WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		JFrame frame = new JFrame("Battleship");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0, 0, WIDTH, HEIGHT);
		Window window = new Window();
		frame.add(window);
		frame.pack();
		frame.setVisible(true);

	}
}
