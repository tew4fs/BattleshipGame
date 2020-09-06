import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class PlayPanel extends JPanel implements MouseListener, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final PlayPanel PP = this;
	private final int WIDTH = 1280;
	private final int HEIGHT = 720;
	private final int GRIDWIDTH = WIDTH * 4 / 9;
	private final int GRIDX = WIDTH/4 - GRIDWIDTH/2;
	private final int COMPUTERGRIDX = 3*WIDTH/4 - GRIDWIDTH/2;
	private final int CELLSIZE = GRIDWIDTH/11;
	private final int GRIDY = HEIGHT/2 - GRIDWIDTH/2;
	private final int COMPUTERGRIDY = HEIGHT/2 - GRIDWIDTH/2;
	
	private boolean changeToPlayAgain, changeToMenuBool, showLabels, initWater, fadeOutComplete, paused, newGuess, playersTurn;
	private char[][] computerBoardArray;
	private JPanel playerGridPane, computerGridPane, postGameOptions, fade, messageBackground, gameOverBackground;
	private JLabel playerWonLabel, computerWonLabel, enemyBattleShipSunkLabel, friendlyBattleShipSunkLabel, returnToMenuLabel, playAgainLabel;
	private JLabel[][] playerGrid, computerGrid;
	private ImagePanel[][] hitMarkers, missMarkers; 
	private ShipPanel[] playerShips, computerShips;
	private ArrayList<WaterPanel> waterArray;
	private AI ai;
	private AIBoard computerBoard;
	private PlayerBoard playerBoard;
	private BufferedImage[] waterSprites;
	private SettingsPanel settings;

	public PlayPanel(float volume) {
		super();
		this.setLayout(null);
		this.setBounds(0, 0, WIDTH, HEIGHT);
		this.setBackground(new Color(6, 9, 199));
		
		changeToPlayAgain = false;
		changeToMenuBool = false;
		showLabels = false;
		initWater = true;
		fadeOutComplete = false;
		paused = false;
		newGuess = true;
		playersTurn = false;
		
		computerBoardArray = new char[10][10];
		
		playerGridPane = new JPanel();
		computerGridPane = new JPanel(); 
		postGameOptions = new JPanel();
		fade = new JPanel();
		fade.setBackground(new Color(0, 0, 0, 255));
		fade.setBounds(0, 0, WIDTH, HEIGHT);
		this.add(fade);
		messageBackground = new JPanel();
		gameOverBackground = new JPanel();
		
		playerWonLabel = new JLabel("You Win!", JLabel.CENTER);
		computerWonLabel = new JLabel("Computer Wins!", JLabel.CENTER);
		enemyBattleShipSunkLabel = new JLabel("You Sunk a Battleship!", JLabel.CENTER);
		friendlyBattleShipSunkLabel = new JLabel("One of Your Battleships Has Been Sunk!", JLabel.CENTER);
		returnToMenuLabel = new JLabel("Return To Menu", JLabel.CENTER);
		playAgainLabel = new JLabel("Play Again", JLabel.CENTER);
		playerGrid = new JLabel[10][10];
		computerGrid = new JLabel[10][10];
		
		hitMarkers = new ImagePanel[10][10];
		missMarkers = new ImagePanel[10][10];
		playerShips = new ShipPanel[5];
		computerShips = new ShipPanel[5];
		
		waterArray = new ArrayList<>();
		waterSprites = new BufferedImage[11];
		computerBoard = new AIBoard();
		settings = new SettingsPanel(volume);
	}
	
	public void setShipDirections(int[] dirs) {
		for (int i = 0; i < 5; i++) {
			playerShips[i].setDirection(dirs[i]);
		}
	}

	public boolean contains(char[] list, char cell) {
		for (int i = 0; i < list.length; i++) {
			if (list[i] == cell)
				return true;
		}
		return false;
	}

	public void add(char[] list, char cell) {
		for (int i = 0; i < list.length; i++) {
			if (list[i] == 'X') {
				list[i] = cell;
				break;
			}
		}
	}
	// -- Start of Getters and Setters -- 
	public float getVolume() {
		return settings.getVolume();
	}

	public void setShipSprites(BufferedImage[] shipSprites) {
		playerShips[0] = new ShipPanel('K', shipSprites);
		playerShips[1] = new ShipPanel('B', shipSprites);
		playerShips[2] = new ShipPanel('C', shipSprites);
		playerShips[3] = new ShipPanel('S', shipSprites);
		playerShips[4] = new ShipPanel('D', shipSprites);
		
		computerShips[0] = new ShipPanel('K', shipSprites);
		computerShips[1] = new ShipPanel('B', shipSprites);
		computerShips[2] = new ShipPanel('C', shipSprites);
		computerShips[3] = new ShipPanel('S', shipSprites);
		computerShips[4] = new ShipPanel('D', shipSprites);
	}
	
	public void setWaterSprites(BufferedImage[] waterSprites) {
		this.waterSprites = waterSprites;
	}
	
	public void initiateWinningLabels() {
		computerWonLabel.setBounds(WIDTH / 2 - 250, HEIGHT / 2 - 250, 500, 500);
		computerWonLabel.setFont(new Font("Times", Font.BOLD, 40));
		computerWonLabel.setForeground(Color.GREEN);
		computerWonLabel.setVisible(false);
		this.add(computerWonLabel);
		playerWonLabel.setBounds(WIDTH / 2 - 250, HEIGHT / 2 - 250, 500, 500);
		playerWonLabel.setFont(new Font("Times", Font.BOLD, 40));
		playerWonLabel.setForeground(Color.GREEN);
		playerWonLabel.setVisible(false);
		this.add(playerWonLabel);
		returnToMenuLabel.setFont(new Font("Times", Font.BOLD, 40));
		returnToMenuLabel.setForeground(Color.white);
		returnToMenuLabel.setName("return to menu");
		returnToMenuLabel.addMouseListener(this);
		playAgainLabel.setFont(new Font("Times", Font.BOLD, 40));
		playAgainLabel.setForeground(Color.white);
		playAgainLabel.setName("play again");
		playAgainLabel.addMouseListener(this);
		postGameOptions.setLayout(new GridLayout(1, 2));
		postGameOptions.setBounds(WIDTH/2 - 400, HEIGHT/2 - 150, 800, 500);
		postGameOptions.setBackground(new Color(0, 0, 0, 0));
		postGameOptions.add(returnToMenuLabel);
		postGameOptions.add(playAgainLabel);
		postGameOptions.setVisible(false);
		this.add(postGameOptions);
		gameOverBackground.setBackground(new Color(0, 0, 0, 100));
		gameOverBackground.setBounds(0, 0, WIDTH, HEIGHT);
		gameOverBackground.setVisible(false);
		this.add(gameOverBackground);
	}
	
	public void initiateBattleshipSunkLabels() {
		enemyBattleShipSunkLabel.setBounds(WIDTH / 2 - 500, HEIGHT / 4, 1000, 300);
		enemyBattleShipSunkLabel.setFont(new Font("Times", Font.BOLD, 40));
		enemyBattleShipSunkLabel.setForeground(Color.green);
		enemyBattleShipSunkLabel.setVisible(false);
		this.add(enemyBattleShipSunkLabel);
		friendlyBattleShipSunkLabel.setBounds(WIDTH / 2 - 500, HEIGHT / 4, 1000, 300);
		friendlyBattleShipSunkLabel.setFont(new Font("Times", Font.BOLD, 40));
		friendlyBattleShipSunkLabel.setForeground(Color.red);
		friendlyBattleShipSunkLabel.setVisible(false);
		this.add(friendlyBattleShipSunkLabel);
		messageBackground.setBackground(new Color(0, 0, 0, 100));
		messageBackground.setBounds(0, 0, WIDTH, HEIGHT);
		messageBackground.setVisible(false);
		this.add(messageBackground);
	}
	
	public void setHitAndMissMarkers() {
		for(int row = 0; row < 10; row++) {
			for(int col = 0; col < 10; col++) {
				hitMarkers[row][col] = new ImagePanel('H');
				hitMarkers[row][col].setBounds(col * CELLSIZE + GRIDX + CELLSIZE + 3, row * CELLSIZE + GRIDY + CELLSIZE + 3, CELLSIZE, CELLSIZE);
				hitMarkers[row][col].setBackground(new Color(0, 0, 0, 0));
				hitMarkers[row][col].setVisible(false);
				this.add(hitMarkers[row][col]);
				
				missMarkers[row][col] = new ImagePanel('M');
				missMarkers[row][col].setBounds(col * CELLSIZE + GRIDX + CELLSIZE + 3, row * CELLSIZE + GRIDY + CELLSIZE + 3, CELLSIZE, CELLSIZE);
				missMarkers[row][col].setBackground(new Color(0, 0, 0, 0));
				missMarkers[row][col].setVisible(false);
				this.add(missMarkers[row][col]);
			}
		}
	}
	// -- End of Getters and Setters --
	
	// -- Start of Mouse Listener --
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getComponent().getName().equals("cell")) {
			if (playersTurn && !paused) {
				int col = (e.getComponent().getX()) / CELLSIZE - 1;
				int row = (e.getComponent().getY()) / CELLSIZE - 1;
				if (e.getComponent().getBackground().equals(new Color(0, 0, 0, 0))) {
					if (computerBoard.hitOrMiss(row, col)) {
						e.getComponent().setBackground(Color.red);
					} else {
						e.getComponent().setBackground(Color.white);
					}
					playersTurn = false;
					newGuess = true;
				}
			}
		}
		else if(e.getComponent().getName().equals(returnToMenuLabel.getName())) {
			this.changeToMenu();
		}else if(e.getComponent().getName().equals(playAgainLabel.getName())) {
			this.changeToPlayAgain();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getComponent().getName().equals("cell")) {
			((JComponent) e.getComponent()).setBorder(new LineBorder(Color.green, 2));
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getComponent().getName().equals("cell")) {
			((JComponent) e.getComponent()).setBorder(new LineBorder(Color.white, 1));
		}
	}
	// -- End of Mouse Listener --
	
	// -- Start of Keyboard Listener --
	public void keyTyped(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
			if (initWater) {
				settings.setVisible(true);
				initWater = false;
				paused = true;
				leaveSettings();
			} else {
				settings.setVisible(false);
				initWater = true;
				paused = false;
				initWaterThread(waterArray);
			}
		}
	}

	public void keyReleased(KeyEvent e) {

	}
	// -- End of Keyboard Listener -- 
	
	// -- Start of Settings Configuration --
	public void leaveSettings() {
		Timer timer = new Timer(5, null);
		timer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(settings.leave()) {
					if(settings.returnToMenu()) {
						changeToMenu();
					}
					settings.setVisible(false);
					initWater = true;
					paused = false;
					PP.grabFocus();
					timer.stop();
				}
			}
		});
		timer.start();
	}
	// -- End of Settings Configuration -- 
	
	// -- Start of Foreground Configuration -- 
	public void setPlayerGrid(char[][] grid) {
		playerBoard = new PlayerBoard(grid);
		ai = new AI(playerBoard);
		char[] alreadyFound = new char[5];
		for (int i = 0; i < 5; i++) {
			alreadyFound[i] = 'X';
		}
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				if (grid[row][col] != '~' && !contains(alreadyFound, grid[row][col])) {
					add(alreadyFound, grid[row][col]);
					if (grid[row][col] == 'K') {
						playerShips[0].setLocation((GRIDX + CELLSIZE) + col * CELLSIZE + 3, (GRIDY + CELLSIZE) + CELLSIZE * row + 3);
						if (playerShips[0].getDirection() == 0 || playerShips[0].getDirection() == 2) {
							playerShips[0].setSize(CELLSIZE, 5*CELLSIZE);
						} else {
							playerShips[0].setSize(5*CELLSIZE, CELLSIZE);
						}
					} else if (grid[row][col] == 'B') {
						playerShips[1].setLocation((GRIDX + CELLSIZE) + col * CELLSIZE + 3, (GRIDY + CELLSIZE) + CELLSIZE * row + 3);
						if (playerShips[1].getDirection() == 0 || playerShips[1].getDirection() == 2) {
							playerShips[1].setSize(CELLSIZE, 4*CELLSIZE);
						} else {
							playerShips[1].setSize(4*CELLSIZE, CELLSIZE);
						}
					} else if (grid[row][col] == 'C') {
						playerShips[2].setLocation((GRIDX + CELLSIZE) + col * CELLSIZE + 3, (GRIDY + CELLSIZE) + CELLSIZE * row + 3);
						if (playerShips[2].getDirection() == 0 || playerShips[2].getDirection() == 2) {
							playerShips[2].setSize(CELLSIZE, 3*CELLSIZE);
						} else {
							playerShips[2].setSize(3*CELLSIZE, CELLSIZE);
						}
					} else if (grid[row][col] == 'S') {
						playerShips[3].setLocation((GRIDX + CELLSIZE) + col * CELLSIZE + 3, (GRIDY + CELLSIZE) + CELLSIZE * row + 3);
						if (playerShips[3].getDirection() == 0 || playerShips[3].getDirection() == 2) {
							playerShips[3].setSize(CELLSIZE, 3*CELLSIZE);
						} else {
							playerShips[3].setSize(3*CELLSIZE, CELLSIZE);
						}
					} else if (grid[row][col] == 'D') {
						playerShips[4].setLocation((GRIDX + CELLSIZE) + col * CELLSIZE + 3, (GRIDY + CELLSIZE) + CELLSIZE * row + 3);
						if (playerShips[4].getDirection() == 0 || playerShips[4].getDirection() == 2) {
							playerShips[4].setSize(CELLSIZE, 2*CELLSIZE);
						} else {
							playerShips[4].setSize(2*CELLSIZE, CELLSIZE);
						}
					}
				}
			}
		}
	}
	
	public void setComputerGrid() {
		computerBoardArray = computerBoard.getBoard();
		char[] alreadyFound = new char[5];
		for (int i = 0; i < 5; i++) {
			alreadyFound[i] = 'X';
		}
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				if (computerBoardArray[row][col] != '~' && !contains(alreadyFound, computerBoardArray[row][col])) {
					add(alreadyFound, computerBoardArray[row][col]);
					if (computerBoardArray[row][col] == 'K') {
						computerShips[0].setLocation((COMPUTERGRIDX + CELLSIZE) + col * CELLSIZE + 3, (COMPUTERGRIDY + CELLSIZE) + CELLSIZE * row + 3);
						if(col < 9 && computerBoardArray[row][col] == computerBoardArray[row][col+1]) {
							computerShips[0].setDirection(1);
							computerShips[0].setSize(5*CELLSIZE, CELLSIZE);
						}else {
							computerShips[0].setDirection(0);
							computerShips[0].setSize(CELLSIZE, 5*CELLSIZE);
						}
					} else if (computerBoardArray[row][col] == 'B') {
						computerShips[1].setLocation((COMPUTERGRIDX + CELLSIZE) + col * CELLSIZE + 3, (COMPUTERGRIDY + CELLSIZE) + CELLSIZE * row + 3);
						if(col < 9 && computerBoardArray[row][col] == computerBoardArray[row][col+1]) {
							computerShips[1].setDirection(1);
							computerShips[1].setSize(5*CELLSIZE, CELLSIZE);
						}else {
							computerShips[1].setDirection(0);
							computerShips[1].setSize(CELLSIZE, 5*CELLSIZE);
						}
					} else if (computerBoardArray[row][col] == 'C') {
						computerShips[2].setLocation((COMPUTERGRIDX + CELLSIZE) + col * CELLSIZE + 3, (COMPUTERGRIDY + CELLSIZE) + CELLSIZE * row + 3);
						if(col < 9 && computerBoardArray[row][col] == computerBoardArray[row][col+1]) {
							computerShips[2].setDirection(1);
							computerShips[2].setSize(5*CELLSIZE, CELLSIZE);
						}else {
							computerShips[2].setDirection(0);
							computerShips[2].setSize(CELLSIZE, 5*CELLSIZE);
						}
					} else if (computerBoardArray[row][col] == 'S') {
						computerShips[3].setLocation((COMPUTERGRIDX + CELLSIZE) + col * CELLSIZE + 3, (COMPUTERGRIDY + CELLSIZE) + CELLSIZE * row + 3);
						if(col < 9 && computerBoardArray[row][col] == computerBoardArray[row][col+1]) {
							computerShips[3].setDirection(1);
							computerShips[3].setSize(5*CELLSIZE, CELLSIZE);
						}else {
							computerShips[3].setDirection(0);
							computerShips[3].setSize(CELLSIZE, 5*CELLSIZE);
						}
					} else if (computerBoardArray[row][col] == 'D') {
						computerShips[4].setLocation((COMPUTERGRIDX + CELLSIZE) + col * CELLSIZE + 3, (COMPUTERGRIDY + CELLSIZE) + CELLSIZE * row + 3);
						if(col < 9 && computerBoardArray[row][col] == computerBoardArray[row][col+1]) {
							computerShips[4].setDirection(1);
							computerShips[4].setSize(5*CELLSIZE, CELLSIZE);
						}else {
							computerShips[4].setDirection(0);
							computerShips[4].setSize(CELLSIZE, 5*CELLSIZE);
						}
					}
				}
			}
		}
	}
	
	public void initiateShips() {
		playerShips[0].setBackground(new Color(0, 0, 0, 0));
		playerShips[0].changeImage();
		this.add(playerShips[0]);

		playerShips[1].setBackground(new Color(0, 0, 0, 0));
		playerShips[1].changeImage();
		this.add(playerShips[1]);

		playerShips[2].setBackground(new Color(0, 0, 0, 0));
		playerShips[2].changeImage();
		this.add(playerShips[2]);

		playerShips[3].setBackground(new Color(0, 0, 0, 0));
		playerShips[3].changeImage();
		this.add(playerShips[3]);

		playerShips[4].setBackground(new Color(0, 0, 0, 0));
		playerShips[4].changeImage();
		this.add(playerShips[4]);
		
		computerShips[0].setBackground(new Color(0, 0, 0, 0));
		computerShips[0].changeImage();
		this.add(computerShips[0]);
		
		computerShips[1].setBackground(new Color(0, 0, 0, 0));
		computerShips[1].changeImage();
		this.add(computerShips[1]);
		
		computerShips[2].setBackground(new Color(0, 0, 0, 0));
		computerShips[2].changeImage();
		this.add(computerShips[2]);
		
		computerShips[3].setBackground(new Color(0, 0, 0, 0));
		computerShips[3].changeImage();
		this.add(computerShips[3]);
		
		computerShips[4].setBackground(new Color(0, 0, 0, 0));
		computerShips[4].changeImage();
		this.add(computerShips[4]);
		
		for(int i = 0; i < computerShips.length; i++) {
			computerShips[i].setVisible(false);
		}
	}
	
	public void createPlayerGrid() {
		// Creates the 10 by 10 grid
		JLabel playerBoardLabel = new JLabel("Your Board", JLabel.CENTER);
		playerBoardLabel.setBounds(WIDTH / 4 - 275, HEIGHT / 2 - 325, 550, 50);
		playerBoardLabel.setFont(new Font("Times", Font.BOLD, 30));
		playerBoardLabel.setForeground(Color.white);
		this.add(playerBoardLabel);
		playerGridPane.setBounds(GRIDX, GRIDY, GRIDWIDTH, GRIDWIDTH);
		playerGridPane.setLayout(new GridLayout(11, 11));
		JLabel filler = new JLabel();
		playerGridPane.add(filler);
		for(int num = 1; num <= 10; num++) {
			Integer number = num;
			JLabel numberLabel = new JLabel(number.toString(), JLabel.CENTER);
			numberLabel.setFont(new Font("Times", Font.BOLD, 19));
			numberLabel.setForeground(Color.WHITE);
			playerGridPane.add(numberLabel);
		}
		
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				if(col == 0) {
					int ascii = row + 65; // makes int corresponding to the ascii code of A-J
					char r = (char) ascii;
					JLabel letter = new JLabel(Character.toString(r), JLabel.CENTER);
					letter.setFont(new Font("Times", Font.BOLD, 19));
					letter.setForeground(Color.white);
					playerGridPane.add(letter);
				}
				playerGrid[row][col] = new JLabel();
				playerGrid[row][col].setBackground(new Color(0, 0, 0, 0));
				playerGrid[row][col].setBorder(new LineBorder(Color.WHITE, 1));
				playerGridPane.add(playerGrid[row][col]);
			}
		}
		playerGridPane.setBackground(new Color(0, 0, 0, 0));
		this.add(playerGridPane);
	}

	public void createComputerGrid() {
		JLabel computerBoardLabel = new JLabel("Enemy  Board", JLabel.CENTER);
		computerBoardLabel.setBounds(3 * WIDTH / 4 - 250, HEIGHT / 2 - 325, 500, 50);
		computerBoardLabel.setFont(new Font("Times", Font.BOLD, 30));
		computerBoardLabel.setForeground(Color.white);
		this.add(computerBoardLabel);
		computerGridPane.setBounds(COMPUTERGRIDX, GRIDY, GRIDWIDTH, GRIDWIDTH);
		computerGridPane.setLayout(new GridLayout(11, 11));
		JLabel filler = new JLabel();
		computerGridPane.add(filler);
		for(int num = 1; num <= 10; num++) {
			Integer number = num;
			JLabel numberLabel = new JLabel(number.toString(), JLabel.CENTER);
			numberLabel.setFont(new Font("Times", Font.BOLD, 19));
			numberLabel.setForeground(Color.WHITE);
			computerGridPane.add(numberLabel);
		}
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				if(col == 0) {
					int ascii = row + 65; // makes int corresponding to the ascii code of A-J
					char r = (char) ascii;
					JLabel letter = new JLabel(Character.toString(r), JLabel.CENTER);
					letter.setFont(new Font("Times", Font.BOLD, 19));
					letter.setForeground(Color.white);
					computerGridPane.add(letter);
				}
				computerGrid[row][col] = new JLabel();
				computerGrid[row][col].setBorder(new LineBorder(Color.WHITE, 1));
				computerGrid[row][col].setOpaque(true);
				computerGrid[row][col].setBackground(new Color(0, 0, 0, 0));
				computerGrid[row][col].setName("cell");
				computerGridPane.add(computerGrid[row][col]);
				computerGrid[row][col].addMouseListener(this);
			}
		}
		computerGridPane.setBackground(new Color(0, 0, 0, 0));
		this.add(computerGridPane);
	}
	
	public void showTitles(boolean player) {
		Timer timer = new Timer(1500, null);
		timer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if(showLabels) {
					messageBackground.setVisible(true);
					if(player) {
						friendlyBattleShipSunkLabel.setVisible(true);
						showLabels = false;
					}else {
						enemyBattleShipSunkLabel.setVisible(true);
						showLabels = false;
					}
				}else {
					paused = false;
					friendlyBattleShipSunkLabel.setVisible(false);
					enemyBattleShipSunkLabel.setVisible(false);
					messageBackground.setVisible(false);
					timer.stop();
				}
			}
		});
		timer.setInitialDelay(0);
		timer.start();
	}
	// -- End of Foreground Configuration --

	// -- Start of Background Configuration -- 
	public void createWater() {
		for (int i = -25; i < HEIGHT; i += 25) {
			for (int k = 0; k < WIDTH; k += 25) {
				WaterPanel water = new WaterPanel(waterSprites);
				water.setBounds(k, i, 25, 25);
				this.add(water);
				waterArray.add(water);
			}
		}
		initWaterThread(waterArray);
	}

	private void initWaterThread(ArrayList<WaterPanel> water) {
		Timer timer = new Timer(5, null);
		timer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (initWater) {
					for (int i = 0; i < water.size(); i++) {
						if (water.get(i).getY() < HEIGHT) {
							water.get(i).setLocation(water.get(i).getX(), water.get(i).getY() + 1);
						} else {
							water.get(i).changeImage();
							water.get(i).setLocation(water.get(i).getX(), -50);
						}
					}
				}else if(changeToMenuBool || changeToPlayAgain) {
					timer.stop();
				}
			}
		});
		timer.start();
	}
	// -- End of Background Configuration --
	
	// -- Start of Change Screen Configuration --
	public void changeToPlayAgain() {
		initWater = false;
		changeToPlayAgain = true;
	}

	public boolean playAgain() {
		this.updateUI();
		return changeToPlayAgain;
	}
	
	public void changeToMenu() {
		initWater = false;
		changeToMenuBool = true;
	}

	public boolean changeMenu() {
		this.updateUI();
		return changeToMenuBool;
	}

	public void fadeIn() {
		fade.setBackground(new Color(0, 0, 0, 255));
		fade.setVisible(true);
		initFadeInThread();
	}

	private void initFadeInThread() {
		final Timer timer = new Timer(12, null);
			timer.addActionListener(new ActionListener() {
			int alpha = 255;
			public void actionPerformed(ActionEvent e) {
				if(alpha >= 0) {
					fade.setBackground(new Color(0, 0, 0, alpha));
					fade.repaint();
					alpha-=5;
				}else {
					fade.setVisible(false);
					PP.addKeyListener(PP);
					play();
					timer.stop();
				}
			}
		});
		timer.start();
	}
	
	public boolean fadeOutComplete() {
		return fadeOutComplete;
	}

	public void fadeOut() {
		fade.setBackground(new Color(0, 0, 0, 0));
		fade.setVisible(true);
		initFadeOutThread();
	}

	private void initFadeOutThread() {
		Timer timer = new Timer(12, null);
			timer.addActionListener(new ActionListener() {
			int alpha = 0;
			public void actionPerformed(ActionEvent e) {
				if(alpha <= 255) {
					fade.setBackground(new Color(0, 0, 0, alpha));
					fade.repaint();
					alpha+=5;
				}else {
					fadeOutComplete = true;
					timer.stop();
				}
			}
		});
		timer.start();
	}
	// -- End of Change Screen Configuration --

	public void createAndShowGUI() {
		this.add(settings);
		settings.setVisible(false);
		this.initiateWinningLabels();
		this.initiateBattleshipSunkLabels();
		this.setHitAndMissMarkers();
		this.setComputerGrid();
		this.initiateShips();
		this.createPlayerGrid();
		this.createComputerGrid();
		this.createWater();
	}

	public void play() {
		Timer timer = new Timer(20, null);
		timer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if(!paused) {
					if(!playerBoard.checkIfLost() && !computerBoard.checkIfLost()) {
						if(!playersTurn) {
							while (!ai.guess()) {
							}
							ArrayList<Point> guessed = ai.getGuesses();
							int row = guessed.get(guessed.size() - 1).getHeight();
							int col = guessed.get(guessed.size() - 1).getWidth();
							if (playerBoard.getBoard()[row][col] == 'O') {
								missMarkers[row][col].setVisible(true);
							} else if (playerBoard.getBoard()[row][col] == 'X') {
								hitMarkers[row][col].setVisible(true);
							}
							playersTurn = true;
							if(playerBoard.aShipSunk()) {
								paused = true;
								showLabels = true;
								showTitles(true);
							}
						}else {
							if(computerBoard.aShipSunk() && newGuess) {
								paused = true;
								showLabels = true;
								showTitles(false);
							}
							newGuess = false;
						}
					}else {
						gameOverBackground.setVisible(true);
						postGameOptions.setVisible(true);
						if (playerBoard.checkIfLost()) {
							computerWonLabel.setVisible(true);
							for(int i = 0; i < computerShips.length; i++) {
								computerShips[i].setVisible(true);
							}
						} else {
							playerWonLabel.setVisible(true);
							for(int i = 0; i < computerShips.length; i++) {
								computerShips[i].setVisible(true);
							}
						}
						playersTurn = false;
						timer.stop();
					}
				}
			}
		});
		timer.start();
	}
	
}
