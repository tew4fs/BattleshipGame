import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SettingsPanel extends JPanel implements MouseListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	private final int WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	
	private boolean leave, returnToMenu;
    private float volume;
	private JPanel options, volumeOptions;
	private JLabel resume, settings, exit, exitGameLabel, yesButton, noButton, backButton, backButtonStartScreen, setVolumeLabel;
 	private JSpinner spinner;
    private JSlider slider;
    
	public SettingsPanel(float musicVolume) {
		this.setBackground(new Color(0, 0, 0, 100));
		this.setBounds(0, 0, WIDTH, HEIGHT);
		this.setLayout(null);
		volume = musicVolume;
		spinner = new JSpinner();
		slider = new JSlider();
		
		options = new JPanel();
		leave = false;
		returnToMenu = false;
		resume = new JLabel("Resume");
		settings = new JLabel("Settings");
		exit = new JLabel("Exit");
		exitGameLabel = new JLabel("Are You Sure You Want To Exit?");
		yesButton = new JLabel("Yes");
		noButton = new JLabel("No");
		backButton = new JLabel("Back");
		backButtonStartScreen = new JLabel("Back");
		setVolumeLabel = new JLabel("Change Volume");
		
		BoxLayout layout = new BoxLayout(options, BoxLayout.PAGE_AXIS);
		options.setBounds(WIDTH/2-100, HEIGHT/2 - 200, 200, 300);
		options.setBackground(Color.white);
		options.setBorder(new LineBorder(Color.yellow, 3));
		options.setLayout(layout);
		
		resume.setFont(new Font("impact", Font.PLAIN, 30));
		resume.setName("resume");
		
		settings.setFont(new Font("impact", Font.PLAIN, 30));
		settings.setName("settings");
		
		exit.setFont(new Font("impact", Font.PLAIN, 30));
		exit.setName("exit");
		
		exitGameLabel.setFont(new Font("impact", Font.PLAIN, 30));
		setVolumeLabel.setFont(new Font("impact", Font.PLAIN, 30));
		
		yesButton.setFont(new Font("impact", Font.PLAIN, 30));
		yesButton.setName("yes");
		
		noButton.setFont(new Font("impact", Font.PLAIN, 30));
		noButton.setName("no");
		
		backButton.setFont(new Font("impact", Font.PLAIN, 30));
		backButton.setName("back");
		
		backButtonStartScreen.setFont(new Font("impact", Font.PLAIN, 30));
		backButtonStartScreen.setName("back-start");
		
		resume.setAlignmentX(CENTER_ALIGNMENT);
		settings.setAlignmentX(CENTER_ALIGNMENT);
		exit.setAlignmentX(CENTER_ALIGNMENT);
		exitGameLabel.setAlignmentX(CENTER_ALIGNMENT);
		yesButton.setAlignmentX(CENTER_ALIGNMENT);
		noButton.setAlignmentX(CENTER_ALIGNMENT);
		backButton.setAlignmentX(CENTER_ALIGNMENT);
		backButtonStartScreen.setAlignmentX(CENTER_ALIGNMENT);
		setVolumeLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		options.add(Box.createRigidArea(new Dimension(0, 30)));
		options.add(resume);
		options.add(Box.createRigidArea(new Dimension(0, 50)));
		options.add(settings);
		options.add(Box.createRigidArea(new Dimension(0, 50)));
		options.add(exit);
		options.add(Box.createRigidArea(new Dimension(0, 30)));
		
		resume.addMouseListener(this);
		settings.addMouseListener(this);
		exit.addMouseListener(this);
		yesButton.addMouseListener(this);
		noButton.addMouseListener(this);
		backButton.addMouseListener(this);
		backButtonStartScreen.addMouseListener(this);
		
		volumeOptions = new JPanel();
		volumeOptions.setAlignmentX(CENTER_ALIGNMENT);
		volumeOptions.setBackground(new Color(0, 0, 0, 0));
	 	spinner = new JSpinner();
        slider = new JSlider();
        slider.setValue((int)(volume*100));
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider s = (JSlider) e.getSource();
                spinner.setValue(s.getValue());
            }
        });
        volumeOptions.add(slider);
        spinner.setModel(new SpinnerNumberModel((int)(volume*100), 0, 100, 1));
        spinner.setEditor(new JSpinner.NumberEditor(spinner, "0'%'"));
        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSpinner s = (JSpinner) e.getSource();
                slider.setValue((Integer) s.getValue());
                volume = (float)((int)s.getValue()/100.0);
            }
        });
        volumeOptions.add(spinner);
		
		
		createAndShowGUI();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getComponent().getName().equals(resume.getName()) || e.getComponent().getName().equals(backButtonStartScreen.getName())) {
			leave = true;
		}else if(e.getComponent().getName().equals(exit.getName())) {
			options.removeAll();
			options.setBounds(WIDTH/2-250, HEIGHT/2 - 200, 500, 300);
			options.add(Box.createRigidArea(new Dimension(0, 30)));
			options.add(exitGameLabel);
			options.add(Box.createRigidArea(new Dimension(0, 50)));
			options.add(yesButton);
			options.add(Box.createRigidArea(new Dimension(0, 50)));
			options.add(noButton);
			options.add(Box.createRigidArea(new Dimension(0, 30)));
		}else if(e.getComponent().getName().equals(yesButton.getName())) {
			returnToMenu = true;
			leave = true;
		}else if(e.getComponent().getName().equals(noButton.getName()) || e.getComponent().getName().equals(backButton.getName())) {
			options.removeAll();
			options.setBounds(WIDTH/2-100, HEIGHT/2 - 200, 200, 300);
			options.add(Box.createRigidArea(new Dimension(0, 30)));
			options.add(resume);
			options.add(Box.createRigidArea(new Dimension(0, 50)));
			options.add(settings);
			options.add(Box.createRigidArea(new Dimension(0, 50)));
			options.add(exit);
			options.add(Box.createRigidArea(new Dimension(0, 30)));
		}else if(e.getComponent().getName().equals(settings.getName())) {
			options.removeAll();
			options.setBounds(WIDTH/2-250, HEIGHT/2 - 200, 500, 300);
			options.add(Box.createRigidArea(new Dimension(0, 30)));
			options.add(setVolumeLabel);
			options.add(Box.createRigidArea(new Dimension(0, 50)));
			options.add(volumeOptions);
			options.add(Box.createRigidArea(new Dimension(0, 50)));
			options.add(backButton);
			options.add(Box.createRigidArea(new Dimension(0, 30)));
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getComponent().getName().equals(resume.getName()) || e.getComponent().getName().equals(backButtonStartScreen.getName())) {
			leave = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
	public float getVolume() {
		return volume;
	}
	
	public void volumeSettings() {
		options.removeAll();
		options.setBounds(WIDTH/2-250, HEIGHT/2 - 200, 500, 300);
		options.add(Box.createRigidArea(new Dimension(0, 30)));
		options.add(setVolumeLabel);
		options.add(Box.createRigidArea(new Dimension(0, 50)));
		options.add(volumeOptions);
		options.add(Box.createRigidArea(new Dimension(0, 50)));
		options.add(backButtonStartScreen);
		options.add(Box.createRigidArea(new Dimension(0, 30)));
	}
	
	public void reset() {
		options.removeAll();
		options.setBounds(WIDTH/2-100, HEIGHT/2 - 200, 200, 300);
		options.add(Box.createRigidArea(new Dimension(0, 30)));
		options.add(resume);
		options.add(Box.createRigidArea(new Dimension(0, 50)));
		options.add(settings);
		options.add(Box.createRigidArea(new Dimension(0, 50)));
		options.add(exit);
		options.add(Box.createRigidArea(new Dimension(0, 30)));
	}
	
	public boolean leave() {
		return leave;
	}
	
	public boolean returnToMenu() {
		return returnToMenu;
	}
	
	public void createAndShowGUI() {
		this.add(options);
	}
}
