import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
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

public class GridPanel extends JPanel implements MouseListener, KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int WIDTH = 1280;
	private final int HEIGHT = 720;
	private final int GRIDWIDTH = WIDTH * 4 / 9;
	private final int GRIDX = WIDTH/2 - GRIDWIDTH/2;
	private final int CELLSIZE = GRIDWIDTH/11;
	private final int GRIDY = HEIGHT/2 - GRIDWIDTH/2 - CELLSIZE;
	private final GridPanel GP = this;
	private final Font messageFont = new Font("impact", Font.PLAIN, 24);
	
	private int shipWidth, shipHeight;
	private boolean initMove, initWater, fadeOutComplete, changeToPlayBool, changeToMenuBool, inSettings; 
	private char[][] charGrid;
	private int[] shipDirections;
	private ArrayList<java.awt.Point> occupied;
	private JPanel gridPane, fade, controls;
	private JLabel doneButton;
	private JLabel[][] grid;
	private BufferedImage[] shipSprites, waterSprites;
	private ShipPanel[] ships;
	private ShipPanel selectedShip;
	private ArrayList<WaterPanel> waterArray;
	private SettingsPanel settings;

	public GridPanel(float volume) {
		super();
		this.setLayout(null);
		this.setBounds(0, 0, WIDTH, HEIGHT);
		this.setBackground(new Color(6, 9, 199));
		
		shipWidth = 0;
		shipHeight = 0;
		initMove = true;
		initWater = true;
		changeToPlayBool = false;
		changeToMenuBool = false;
		inSettings = false;
		fadeOutComplete = false;
		
		charGrid = new char[10][10];
		shipDirections = new int[5];
		occupied = new ArrayList<>();
		
		gridPane = new JPanel();
		fade = new JPanel();
		fade.setBounds(0, 0, WIDTH, HEIGHT);
		fade.setBackground(new Color(0, 0, 0, 255));
		this.add(fade);
		
		doneButton = new JLabel("Done");
		doneButton.setFont(new Font("impact", Font.PLAIN, 40));
		doneButton.setBounds(WIDTH * 5 / 6, HEIGHT * 2 / 3, 100, 100);
		doneButton.setForeground(Color.white);
		doneButton.setName("Done");
		doneButton.setVisible(false);
		doneButton.addMouseListener(this);
		this.add(doneButton);
		
		grid = new JLabel[10][10];
		waterSprites = new BufferedImage[11];
		shipSprites = new BufferedImage[20];
		
		ships = new ShipPanel[5];
		waterArray = new ArrayList<>();
		settings = new SettingsPanel(volume);
		
		this.initiateControlsPanel();
	}
	
	// -- Start of Getters and Setters --
	public float getVolume() {
		return settings.getVolume();
	}
	
	public void setShipSprites(BufferedImage[] shipSprites) {
		this.shipSprites = shipSprites;
	}
	
	public void setWaterSprites(BufferedImage[] waterSprites) {
		this.waterSprites = waterSprites;
	}
	
	public void getOccupiedLocations() {
		occupied.clear();
		for (int i = 0; i < ships.length; i++) {
			java.awt.Point p = ships[i].getLocation();
			int size = Math.max(ships[i].getHeight(), ships[i].getWidth()) / CELLSIZE;
			int x = (int) p.getX();
			int y = (int) p.getY();
			int offsetH = 0, offsetW = 0;
			for (int j = 0; j < size; j++) {
				if (ships[i].getHeight() > ships[i].getWidth()) {
					occupied.add(new java.awt.Point(x, y + offsetH));
					offsetH += CELLSIZE;
				} else {
					occupied.add(new java.awt.Point(x + offsetW, y));
					offsetW += CELLSIZE;
				}
			}
		}
	}
	
	public char[][] getGrid() {
		translateGrid();
		return charGrid;
	}

	public int[] getDirections() {
		translateGrid();
		return shipDirections;
	}
	
	public void translateGrid() {
		ArrayList<java.awt.Point> carrierLocations = new ArrayList<>();
		ArrayList<java.awt.Point> battleshipLocations = new ArrayList<>();
		ArrayList<java.awt.Point> cruiserLocations = new ArrayList<>();
		ArrayList<java.awt.Point> submarineLocations = new ArrayList<>();
		ArrayList<java.awt.Point> destroyerLocations = new ArrayList<>();
		if (ships[0].getDirection() == 0 || ships[0].getDirection() == 2) {
			for (int i = 0; i < 5; i++) {
				carrierLocations.add(new java.awt.Point(ships[0].getX(), ships[0].getY() + CELLSIZE * i));
			}
		} else {
			for (int i = 0; i < 5; i++) {
				carrierLocations.add(new java.awt.Point(ships[0].getX() + CELLSIZE * i, ships[0].getY()));
			}
		}
		if (ships[1].getDirection() == 0 || ships[1].getDirection() == 2) {
			for (int i = 0; i < 4; i++) {
				battleshipLocations.add(new java.awt.Point(ships[1].getX(), ships[1].getY() + CELLSIZE * i));
			}
		} else {
			for (int i = 0; i < 4; i++) {
				battleshipLocations.add(new java.awt.Point(ships[1].getX() + CELLSIZE * i, ships[1].getY()));
			}
		}
		if (ships[2].getDirection() == 0 || ships[2].getDirection() == 2) {
			for (int i = 0; i < 3; i++) {
				cruiserLocations.add(new java.awt.Point(ships[2].getX(), ships[2].getY() + CELLSIZE * i));
			}
		} else {
			for (int i = 0; i < 3; i++) {
				cruiserLocations.add(new java.awt.Point(ships[2].getX() + CELLSIZE * i, ships[2].getY()));
			}
		}
		if (ships[3].getDirection() == 0 || ships[3].getDirection() == 2) {
			for (int i = 0; i < 3; i++) {
				submarineLocations.add(new java.awt.Point(ships[3].getX(), ships[3].getY() + CELLSIZE * i));
			}
		} else {
			for (int i = 0; i < 3; i++) {
				submarineLocations.add(new java.awt.Point(ships[3].getX() + CELLSIZE * i, ships[3].getY()));
			}
		}
		if (ships[4].getDirection() == 0 || ships[4].getDirection() == 2) {
			for (int i = 0; i < 2; i++) {
				destroyerLocations.add(new java.awt.Point(ships[4].getX(), ships[4].getY() + CELLSIZE * i));
			}
		} else {
			for (int i = 0; i < 2; i++) {
				destroyerLocations.add(new java.awt.Point(ships[4].getX() + CELLSIZE * i, ships[4].getY()));
			}
		}
		this.getOccupiedLocations();
		for (int row = 0; row < charGrid.length; row++) {
			for (int col = 0; col < charGrid[row].length; col++) {
				java.awt.Point point = new java.awt.Point(grid[row][col].getX() + GRIDX, grid[row][col].getY() + GRIDY);
				if (occupied.contains(point)) {
					if (carrierLocations.contains(point)) {
						charGrid[row][col] = 'K';
					} else if (battleshipLocations.contains(point)) {
						charGrid[row][col] = 'B';
					} else if (cruiserLocations.contains(point)) {
						charGrid[row][col] = 'C';
					} else if (submarineLocations.contains(point)) {
						charGrid[row][col] = 'S';
					} else {
						charGrid[row][col] = 'D';
					}
				} else {
					charGrid[row][col] = '~';
				}
			}
		}
		for (int i = 0; i < 5; i++) {
			shipDirections[i] = ships[i].getDirection();
		}
	}
	
	// -- End of Getters and Setters --

	// -- Start of Mouse Listener --
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getComponent().getName() != null) {
			if (!inSettings && e.getComponent().getName().equals(doneButton.getName())) {
				changeToPlay();
			} else if (!inSettings && (e.getComponent().getName().equals("Carrier")
					|| e.getComponent().getName().equals("Battleship") || e.getComponent().getName().equals("Cruiser")
					|| e.getComponent().getName().equals("Submarine")
					|| e.getComponent().getName().equals("Destroyer"))) {
				initMove = true;
				selectedShip = (ShipPanel) e.getComponent(); // Keeps track of the last clicked shipped (for rotation)
				ShipPanel panel = (ShipPanel) e.getComponent(); // Gets that JPanel that was clicked
				shipHeight = selectedShip.getHeight();
				shipWidth = selectedShip.getWidth();
				initThread(panel);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getComponent().getName() != null) {
			if (!inSettings && (e.getComponent().getName().equals("Carrier")
					|| e.getComponent().getName().equals("Battleship") || e.getComponent().getName().equals("Cruiser")
					|| e.getComponent().getName().equals("Submarine")
					|| e.getComponent().getName().equals("Destroyer"))) {
				initMove = false;
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
	// -- End of Mouse Listener --

	// -- Start of Keyboard Listener --
	public void keyTyped(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == 'r' && isRunning) { // If r is the selected key, then the most previous clicked
													// ship rotates
			int height = selectedShip.getSize().height;
			int width = selectedShip.getSize().width;
			selectedShip.setSize(height, width); // Swaps the height and width to simulate rotating
			if (selectedShip.getDirection() == 3) {
				selectedShip.setDirection(0);
			} else {
				selectedShip.setDirection(selectedShip.getDirection() + 1);
			}
			selectedShip.changeImage();
		}
		if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
			if (!inSettings) {
				inSettings = true;
				settings.setVisible(true);
				initWater = false;
				leaveSettings();
			} else {
				inSettings = false;
				settings.setVisible(false);
				settings.reset();
				initWater = true;
			}
		}
	}

	public void keyReleased(KeyEvent e) {

	}
	// -- End of Keyboard Listener --
	
	// Start of Settings Configuration --
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
					inSettings = false;
					initWater = true;
					GP.grabFocus();
					timer.stop();
				}
			}
		});
		timer.start();
	}
	
	// -- End of Settings Configurations -- 
	
	// -- Start of Foreground Configuration --
	public void initiateControlsPanel() {
		controls = new JPanel();
		controls.setLayout(new GridLayout(2, 1));
		controls.setBounds(WIDTH/2 + CELLSIZE * 6,  GRIDY + CELLSIZE, 300, HEIGHT/4);
		controls.setBackground(new Color(0, 0, 0, 0));
		this.add(controls);
		
		JPanel firstControl = new JPanel();
		firstControl.setLayout(null);
		firstControl.setBackground(new Color(0, 0, 0, 0));
		JPanel secondControl = new JPanel();
		secondControl.setLayout(null);
		secondControl.setBackground(new Color(0, 0, 0, 0));
		
		JLabel dragAndDrop = new JLabel("Drag and Drop: ", JLabel.CENTER);
		dragAndDrop.setFont(messageFont);
		dragAndDrop.setBounds(10, 10, 150, 50);
		dragAndDrop.setForeground(Color.white);
		firstControl.add(dragAndDrop);
		
		JLabel mouse1Control = new JLabel("M1", JLabel.CENTER);
		mouse1Control.setBorder(new LineBorder(Color.white, 2));
		mouse1Control.setForeground(Color.white);
		mouse1Control.setFont(messageFont);
		mouse1Control.setBounds(170, 10, 45, 45);
		firstControl.add(mouse1Control);
		
		JLabel plusDrag = new JLabel("+ DRAG", JLabel.CENTER);
		plusDrag.setForeground(Color.white);
		plusDrag.setFont(messageFont);
		plusDrag.setBounds(215, 10, 75, 50);
		firstControl.add(plusDrag);
		
		JLabel rotate = new JLabel("Rotate: ", JLabel.CENTER);
		rotate.setFont(messageFont);
		rotate.setBounds(10, 10, 100, 50);
		rotate.setForeground(Color.white);
		secondControl.add(rotate);
		
		JLabel mouse1ControlC = new JLabel("M1", JLabel.CENTER);
		mouse1ControlC.setBorder(new LineBorder(Color.white, 2));
		mouse1ControlC.setForeground(Color.white);
		mouse1ControlC.setFont(messageFont);
		mouse1ControlC.setBounds(120, 10, 45, 45);
		secondControl.add(mouse1ControlC);
		
		JLabel plus = new JLabel(" + ", JLabel.CENTER);
		plus.setForeground(Color.white);
		plus.setFont(messageFont);
		plus.setBounds(170, 10, 50, 50);
		secondControl.add(plus);
		
		JLabel rControl = new JLabel("R", JLabel.CENTER);
		rControl.setBorder(new LineBorder(Color.white, 2));
		rControl.setForeground(Color.white);
		rControl.setFont(messageFont);
		rControl.setBounds(225, 10, 45, 45);
		secondControl.add(rControl);
		
		
		controls.add(firstControl);
		controls.add(secondControl);
	}
	public void buildShips() {
		ShipPanel ship1 = createShip(10, 10, 5, 'K');
		ShipPanel ship2 = createShip(10 + CELLSIZE, 10, 4, 'B');
		ShipPanel ship3 = createShip(10, CELLSIZE * 6 + 10, 3, 'C');
		ShipPanel ship4 = createShip(10 + CELLSIZE, CELLSIZE*6 + 10, 3, 'S');
		ShipPanel ship5 = createShip(10, CELLSIZE*10 + 10, 2, 'D');

		ships[0] = ship1;
		ships[0].setName("Carrier");
		ships[1] = ship2;
		ships[1].setName("Battleship");
		ships[2] = ship3;
		ships[2].setName("Cruiser");
		ships[3] = ship4;
		ships[3].setName("Submarine");
		ships[4] = ship5;
		ships[4].setName("Destroyer");

		for (int i = 0; i < ships.length; i++) {
			ships[i].addMouseListener(this);
		}
	}
	
	public ShipPanel createShip(int x, int y, int size, char shipType) {
		ShipPanel ship = new ShipPanel(shipType, shipSprites);
		ship.setBounds(x, y, CELLSIZE, CELLSIZE * size);
		ship.setBackground(new Color(0, 0, 0, 0));
		this.add(ship);
		return ship;
	}
	
	public JPanel createGrid() {
		gridPane.setLayout(new GridLayout(11, 11));
		gridPane.setBounds(GRIDX, GRIDY, GRIDWIDTH, GRIDWIDTH);
		gridPane.setBackground(new Color(0, 0, 0, 0));
		JLabel filler = new JLabel();
		gridPane.add(filler);
		for (int num = 1; num <= 10; num++) {
			Integer number = num;
			JLabel numberLabel = new JLabel(number.toString(), JLabel.CENTER);
			numberLabel.setFont(new Font("Times", Font.BOLD, 19));
			numberLabel.setForeground(Color.WHITE);
			gridPane.add(numberLabel);
		}
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				if (col == 0) {
					int ascii = row + 65; // makes int corresponding to the ascii code of A-J
					char r = (char) ascii;
					JLabel letter = new JLabel(Character.toString(r), JLabel.CENTER);
					letter.setFont(new Font("Times", Font.BOLD, 19));
					letter.setForeground(Color.white);
					gridPane.add(letter);
				}
				grid[row][col] = new JLabel();
				grid[row][col].setBackground(new Color(0, 0, 0, 0));
				grid[row][col].setBorder(new LineBorder(Color.WHITE, 1));
				gridPane.add(grid[row][col]);
			}
		}
		return gridPane;
	}
	
	volatile private boolean isRunning = false;

	private synchronized boolean checkAndMark() {
		if (isRunning)
			return false;
		isRunning = true;
		return true;
	}

	private void initThread(ShipPanel pan) {
		int originalDirection = pan.getDirection();
		this.getOccupiedLocations();
		PointerInfo mouse = MouseInfo.getPointerInfo();
		java.awt.Point loc = mouse.getLocation();
		int initX = (int) loc.getX(); // Gets the initial location of where the mouse is
		int initY = (int) loc.getY();
		int shipX = (int) pan.getLocation().getX(); // Gets the initial location of the ship that was clicked on
		int shipY = (int) pan.getLocation().getY();
		if (checkAndMark()) {
			new Thread() {
				@SuppressWarnings("deprecation")
				public void run() {
					do {
						PointerInfo mouse = MouseInfo.getPointerInfo();
						java.awt.Point loc = mouse.getLocation();
						int x = (int) loc.getX(); // Finds the new location of where the mouse moved to.
						int y = (int) loc.getY();
						int diffX = x - initX; // Finds the difference between the initial location of the mouse
						int diffY = y - initY; // and the new location of the mouse
						pan.setLocation(shipX + diffX, shipY + diffY); // Adds the difference of the locations to the
																		// initial location of the ship
					} while (initMove);
					isRunning = false; // Terminates thread to stop ship from moving.
					// Moves the ship into the correct grid spot. No longer inconsistent with
					// snapping mechanic.
					boolean foundSpot = false;
					int ySize = selectedShip.getHeight() / CELLSIZE, xSize = selectedShip.getWidth() / CELLSIZE; // Calculates the
																										// offset in
																										// grid
																										// boxes where
																										// the
																										// ship
																										// shouldn't be
																										// able to go

					for (int row = 0; row < grid.length - ySize + 1; row++) { // Loops to each valid grid location
																				// the ship could snap to depending
																				// on height and width of the ship
						for (int col = 0; col < grid[row].length - xSize + 1; col++) {
							if ((int) (Math.abs(grid[row][col].getX() + gridPane.getX() - selectedShip.getX())) <= CELLSIZE/2
									&& (int) (Math.abs(
											grid[row][col].getY() + gridPane.getY() - selectedShip.getY())) <= CELLSIZE/2) {
								// Decides if the ship is within half a cell units from each grid box origin (top left
								// corner)

								// Determines if a ship already occupies the desired location
								boolean unoccupied = true;
								int xOffset = 0, yOffset = 0;
								// This for loop removes the selected ship's original location from the occupied
								// locations list
								for (int i = 0; i < Math.max(shipWidth / CELLSIZE, shipHeight / CELLSIZE); i++) {
									if (shipWidth > shipHeight) {
										occupied.remove(new java.awt.Point(shipX + xOffset, shipY));
									} else {
										occupied.remove(new java.awt.Point(shipX, shipY + yOffset));
									}
									xOffset += CELLSIZE;
									yOffset += CELLSIZE;
								}
								xOffset = 0;
								yOffset = 0;
								// This for loop determines if any of the desired spaces are in the occupied
								// locations list
								for (int i = 0; i < Math.max(xSize, ySize); i++) {
									if (xSize > ySize) {
										if (occupied.contains(
												new java.awt.Point(grid[row][col].getX() + gridPane.getX() + xOffset,
														grid[row][col].getY() + gridPane.getY()))) {
											unoccupied = false;
											break;
										}
									} else {
										if (occupied
												.contains(new java.awt.Point(grid[row][col].getX() + gridPane.getX(),
														grid[row][col].getY() + gridPane.getY() + yOffset))) {
											unoccupied = false;
											break;
										}
									}
									xOffset += CELLSIZE;
									yOffset += CELLSIZE;
								}
								// If all the spaces are unoccupied, the ship is placed in the desired spot.
								if (unoccupied) {
									selectedShip.move(grid[row][col].getX() + GRIDX,
											grid[row][col].getY() + GRIDY);
									// Moves the ship to the correct location
									foundSpot = true;
									break;
								}
							}
						}
					}

					if (!foundSpot) {
						selectedShip.move(shipX, shipY); // Returns to the location of where it was clicked if it
															// doesn't find a valid spot.
						selectedShip.setSize(shipWidth, shipHeight); // Returns the ship to the size it was when it was
																		// clicked.
						selectedShip.setDirection(originalDirection);
					}
					selectedShip.changeImage();
					translateGrid();
					int count = 0;
					for(int row = 0; row < charGrid.length; row++) {
						for(int col = 0; col < charGrid[row].length; col++) {
							if(charGrid[row][col] != '~') {
								count++;
							}
						}
					}
					if(count == 17) {
						doneButton.setVisible(true);
					}
				}
			}.start();
		}
	}
	// -- End of Foreground Configuration --
	
	// -- Start of Background Configuration --
	public void createWater() {
		for (int i = -50; i < HEIGHT; i += 25) {
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
				}else if(changeToMenuBool || changeToPlayBool) {
					timer.stop();
				}
			}
		});
		timer.start();
	}
	// -- End of Background Configuration --
	
	// -- Start of Change Screen Configuration --
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
					GP.addKeyListener(GP);
					GP.addMouseListener(GP);
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

	public void changeToMenu() {
		initWater = false;
		changeToMenuBool = true;
	}

	public boolean changeMenu() {
		this.updateUI();
		return changeToMenuBool;
	}

	public void changeToPlay() {
		initWater = false;
		changeToPlayBool = true;
	}

	public boolean changePlay() {
		this.updateUI();
		return changeToPlayBool;
	}
	
	// -- End of Change Screen Configuration -- 

	public void createAndShowGUI() {
		settings.setVisible(false);
		this.add(settings);
		this.buildShips();
		this.add(this.createGrid());
		this.createWater();
	}

}