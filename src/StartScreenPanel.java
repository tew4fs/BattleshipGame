import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;

import javax.swing.*;

public class StartScreenPanel extends JPanel implements MouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int WIDTH = 1280;
	private final int HEIGHT = 720;
	private final int GRIDWIDTH = WIDTH * 4 / 9;
	private final int CELLSIZE = GRIDWIDTH / 11;
	private final int SHOTSTARTX = WIDTH / 3 - CELLSIZE;
	private final int SHOTSTARTY = (HEIGHT / 2 - CELLSIZE) + (CELLSIZE * 3) / 2;
	private final int CRUISERX = 2 * WIDTH / 3 + CELLSIZE / 2, CRUISERY = HEIGHT / 2 - CELLSIZE;
	private final StartScreenPanel SS = this;
	
	private boolean moveToPlayScreen, animation, inSettings, initWater, fadeOutComplete;
	private JPanel fade;
	private JLabel play, settingsLabel, quit;
	private JLabel[] fire;
	private BufferedImage[] shipSprites, waterSprites;
	private ShipPanel carrier, cruiser;
	private ArrayList<WaterPanel> waterArray;
	private SettingsPanel settings;
	private MusicPlayer musicPlayer;

	public StartScreenPanel(boolean animation, MusicPlayer mp, float volume) {
		super();
		this.setLayout(null);
		this.setBounds(0, 0, WIDTH, HEIGHT);
		
		moveToPlayScreen = false;
		this.animation = animation;
		inSettings = false;
		initWater = true;
		fadeOutComplete = false;
		
		fade = new JPanel();
		fade.setBounds(0, 0, WIDTH, HEIGHT);
		fade.setVisible(false);
		this.add(fade);
		
		play = new JLabel("Play", JLabel.CENTER);
		settingsLabel = new JLabel("Settings", JLabel.CENTER);
		quit = new JLabel("Quit", JLabel.CENTER);
		
		fire = new JLabel[5];
		shipSprites = new BufferedImage[20];
		waterSprites = new BufferedImage[11];
		
		waterArray = new ArrayList<>();
		settings = new SettingsPanel(volume);
		musicPlayer = mp;
	}

	// -- Start of Getters and Setters --
	public void setShipSprites(BufferedImage[] shipSprites) {
		this.shipSprites = shipSprites;
	}

	public void setWaterSprites(BufferedImage[] waterSprites) {
		this.waterSprites = waterSprites;
	}
	// -- End of Getters and Setters -- 
	
	// -- Start of Mouse Listener --
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(!inSettings) {
			if(e.getComponent().getName().equals("play")) {
				changeToSelection();
			}else if(e.getComponent().getName().equals("settings")){
				settings.volumeSettings();
				settings.setVisible(true);
				inSettings = true;
				leaveSettings();
			}
			else if(e.getComponent().getName().equals("quit")) {
				System.exit(0);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(!inSettings) {
			if(e.getComponent().getName().equals("play") || e.getComponent().getName().equals("settings") || e.getComponent().getName().equals("quit")) {
				e.getComponent().setFont(new Font("Impact", Font.PLAIN, 45));
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(!inSettings) {
			if(e.getComponent().getName().equals("play") || e.getComponent().getName().equals("settings") || e.getComponent().getName().equals("quit")) {
				e.getComponent().setFont(new Font("Impact", Font.PLAIN, 38));
			}
		}

	}
	// -- End of Mouse Listener --
	
	// -- Start of Settings Configuration --
	public float getVolume() {
		return settings.getVolume();
	}
	
	public void leaveSettings() {
		Timer timer = new Timer(5, null);
		timer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(settings.leave()) {
					settings.setVisible(false);
					inSettings = false;
					timer.stop();
				}
			}
		});
		timer.start();
	}	
	// -- End of Settings Configuration --
	
	// -- Start of Foreground Configuration --
	public void addListeners() {
		play.addMouseListener(SS);
		settingsLabel.addMouseListener(SS);
		quit.addMouseListener(SS);
	}
	
	public void setShips() {
		carrier = new ShipPanel('K', shipSprites);
		cruiser = new ShipPanel('C', shipSprites);

		carrier.setBounds(WIDTH / 3 - 3 * CELLSIZE / 2, HEIGHT / 2 - 2 * CELLSIZE, CELLSIZE, CELLSIZE * 5);
		carrier.setBackground(new Color(0, 0, 0, 0));
		this.add(carrier);

		cruiser.setBounds(2 * WIDTH / 3 + CELLSIZE / 2, HEIGHT / 2 - CELLSIZE, CELLSIZE, CELLSIZE * 3);
		cruiser.setBackground(new Color(0, 0, 0, 0));
		this.add(cruiser);

	}

	public void openingAnimation() {
		JPanel shot = new JPanel();
		shot.setBounds(SHOTSTARTX, SHOTSTARTY, 5, 5);
		shot.setBackground(new Color(255, 255, 0));
		shot.setVisible(false);
		this.add(shot);
		
		JPanel shadow = new JPanel();
		shadow.setBounds(SHOTSTARTX, SHOTSTARTY, 5, 5);
		shadow.setBackground(new Color(0, 0, 0, 100));
		shadow.setVisible(false);
		this.add(shadow);
		
		JLabel title = new JLabel("NAVAL FIGHT", JLabel.CENTER);
		title.setFont(new Font("Impact", Font.BOLD, 100));
		title.setBounds(0, -250, WIDTH, 100);
		title.setForeground(Color.white);
		this.add(title);
		
		play.setFont(new Font("Impact", Font.PLAIN, 38));
		play.setBounds(WIDTH/3, HEIGHT / 2 - 100, WIDTH/3, 50);
		play.setForeground(new Color(255, 255, 255, 0));
		play.setName("play");
		play.setVisible(false);
		this.add(play);
		
		settingsLabel.setFont(new Font("Impact", Font.PLAIN, 38));
		settingsLabel.setBounds(WIDTH/3, HEIGHT / 2, WIDTH/3, 50);
		settingsLabel.setForeground(new Color(255, 255, 255, 0));
		settingsLabel.setName("settings");
		settingsLabel.setVisible(false);
		this.add(settingsLabel);
		
		quit.setFont(new Font("Impact", Font.PLAIN, 38));
		quit.setBounds(WIDTH/3, HEIGHT / 2 + 100, WIDTH/3, 50);
		quit.setForeground(new Color(255, 255, 255, 0));
		quit.setName("quit");
		quit.setVisible(false);
		this.add(quit);
		
		Icon imgIcon = new ImageIcon(this.getClass().getResource("Sprites/ExplosionNoRepeat.gif"));
		JLabel explosion = new JLabel(imgIcon);
		explosion.setVisible(false);
		this.add(explosion);
		
		Icon fireIcon = new ImageIcon(this.getClass().getResource("Sprites/Fire.gif"));
		for(int i = 0; i < fire.length; i++) {
			fire[i] = new JLabel(fireIcon);
			fire[i].setBounds(CRUISERX, CRUISERY + i*3*CELLSIZE/fire.length, CELLSIZE, CELLSIZE);
			fire[i].setVisible(false);
			this.add(fire[i]);
		}
		
		
		JLabel[] explosions = new JLabel[10];
		for(int i = 0; i < explosions.length; i++) {
			explosions[i] = new JLabel(imgIcon);
			explosions[i].setVisible(false);
			this.add(explosions[i]);
		}
		explosions[0].setBounds(CRUISERX, CRUISERY + CELLSIZE, 25, 25);
		explosions[1].setBounds(CRUISERX+21, CRUISERY + CELLSIZE - 20, 25, 25);
		explosions[2].setBounds(CRUISERX+10, CRUISERY + CELLSIZE + 12, 25, 25);
		explosions[3].setBounds(CRUISERX+12, CRUISERY + CELLSIZE - 3, 25, 25);
		explosions[4].setBounds(CRUISERX+3, CRUISERY + 2 * CELLSIZE + 12, 25, 25);
		explosions[5].setBounds(CRUISERX+9, CRUISERY + 2 * CELLSIZE + 10, 25, 25);
		explosions[6].setBounds(CRUISERX+19, CRUISERY + CELLSIZE + 4, 25, 25);
		explosions[7].setBounds(CRUISERX+12, CRUISERY + 2 * CELLSIZE - 23, 25, 25);
		explosions[8].setBounds(CRUISERX+9, CRUISERY + CELLSIZE + 20, 25, 25);
		explosions[9].setBounds(CRUISERX+24, CRUISERY + CELLSIZE - 3, 25, 25);
		
		Timer timer = new Timer(5, null);
		timer.addActionListener(new ActionListener() {
			int gravity = 1;
			int Vy_0 = 5;
			int time = 0;
			int explosionCount = 0;
			int x = shot.getX();
			int opacity = 0;
			boolean shotInProgress = true;
			boolean shotFired = false;
			boolean showMenu = false;
			boolean playingMusic = false;
			public void actionPerformed(ActionEvent e) {
				if(shotInProgress) {
					shot.setVisible(true);
					shadow.setVisible(true);
					if(!shotFired) {
						try{ 
							URL url = this.getClass().getResource("CannonFire.wav");
							AudioPlayer audioPlayer = new AudioPlayer(url); 
							audioPlayer.play(); 
						} 
						catch (Exception ex) {
							ex.printStackTrace(); 
						}
						shotFired = true;
					}
					x = x + 5;
					int y = (-(Vy_0 * time) + (time * time * gravity)/21) + SHOTSTARTY;
					shot.setLocation(x, y);
					shadow.setLocation(x, SHOTSTARTY);
					time++;
					if(y > SHOTSTARTY) {
						shot.setVisible(false);
						shadow.setVisible(false);
						if(explosionCount == 0) {
							explosion.setBounds(x-10, y-10, 25, 25);
							explosion.setVisible(true);
							try{ 
								URL url = this.getClass().getResource("Explosion.wav");
								AudioPlayer audioPlayer = new AudioPlayer(url); 								
								audioPlayer.play(); 
							} 
							catch (Exception ex) { }
						}
						explosions[explosionCount].setVisible(true);
						if(explosionCount == 9) {
							for(int i = 0; i < fire.length; i++) {
								fire[i].setVisible(true);
							}
							shotInProgress = false;
						}
						explosionCount++;
					}
				}else {
					if(!playingMusic) {
						try {
							musicPlayer.restart();
						} catch (IOException e1) {
						} catch (LineUnavailableException e1) {
						} catch (UnsupportedAudioFileException e1) {
						}
						playingMusic = true;
					}
					if(title.getY() < HEIGHT / 4 - 75) {
						title.setLocation(title.getX(), title.getY() + 5);
					}else {
						showMenu = true;
						play.setVisible(true);
						settingsLabel.setVisible(true);
						quit.setVisible(true);
					}
				}
				if(showMenu) {
					if(opacity < 255) {
						opacity += 5;
						play.setForeground(new Color(255, 255, 255, opacity));
						settingsLabel.setForeground(new Color(255, 255, 255, opacity));
						quit.setForeground(new Color(255, 255, 255, opacity));
					}else {
						addListeners();
						timer.stop();
					}
				}
			}
		});
		timer.setInitialDelay(2000);
		timer.start();
	}
	
	public void createMenu() {
		JLabel title = new JLabel("NAVAL FIGHT", JLabel.CENTER);
		title.setFont(new Font("Impact", Font.BOLD, 100));
		title.setBounds(0, HEIGHT/4 - 75, WIDTH, 100);
		title.setForeground(Color.white);
		this.add(title);
		
		play.setFont(new Font("Impact", Font.PLAIN, 38));
		play.setBounds(WIDTH/3, HEIGHT / 2 - 100, WIDTH/3, 50);
		play.setForeground(new Color(255, 255, 255));
		play.setName("play");
		this.add(play);
		
		settingsLabel.setFont(new Font("Impact", Font.PLAIN, 38));
		settingsLabel.setBounds(WIDTH/3, HEIGHT / 2, WIDTH/3, 50);
		settingsLabel.setForeground(new Color(255, 255, 255));
		settingsLabel.setName("settings");
		this.add(settingsLabel);
		
		quit.setFont(new Font("Impact", Font.PLAIN, 38));
		quit.setBounds(WIDTH/3, HEIGHT / 2 + 100, WIDTH/3, 50);
		quit.setForeground(new Color(255, 255, 255));
		quit.setName("quit");
		this.add(quit);
		
		Icon fireIcon = new ImageIcon(this.getClass().getResource("Sprites/Fire.gif"));
		for(int i = 0; i < fire.length; i++) {
			fire[i] = new JLabel(fireIcon);
			fire[i].setBounds(CRUISERX, CRUISERY + i*3*CELLSIZE/fire.length, CELLSIZE, CELLSIZE);
			this.add(fire[i]);
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
				}else if(moveToPlayScreen) {
					timer.stop();
				}
			}
		});
		timer.start();
	}
	// -- End of Background Configuration --

	// -- Start of Change Screen Configuration --
	public void changeToSelection() {
		moveToPlayScreen = true;
		initWater = false;
	}

	public boolean goToPlay() {
		this.updateUI();
		return moveToPlayScreen;
	}
	
	public boolean fadeOutComplete() {
		return fadeOutComplete;
	}

	public void fadeOut() {
		for(int i = 0; i < fire.length; i++) {
			fire[i].setVisible(false);
		}
		fade.setBackground(new Color(0, 0, 0, 0));
		fade.setVisible(true);
		play.removeMouseListener(SS);
		settingsLabel.removeMouseListener(SS);
		quit.removeMouseListener(SS);
		SS.removeMouseListener(SS);
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
	
	public void fadeIn() {
		fade.setBackground(new Color(0, 0, 0, 255));
		fade.setVisible(true);
		initFadeInThread();
	}

	private void initFadeInThread() {
		Timer timer = new Timer(12, null);
			timer.addActionListener(new ActionListener() {
			int alpha = 255;
			public void actionPerformed(ActionEvent e) {
				if(alpha >= 0) {
					fade.setBackground(new Color(0, 0, 0, alpha));
					fade.repaint();
					alpha-=5;
				}else {
					fade.setVisible(false);
					if(!animation) {
						addListeners();
					}
					timer.stop();
				}
			}
		});
		timer.start();
	}
	// -- End of Change Screen Configuration --
	
	public void createAndShowGUI() {
		settings.setVisible(false);
		this.add(settings);
		if(animation) {
			this.openingAnimation();
		}else {
			this.createMenu();
		}
		this.setShips();
		this.createWater();
	}
}
