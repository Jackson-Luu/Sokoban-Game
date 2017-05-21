import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;


public class MainMenu extends JFrame implements ActionListener,ItemListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton btnLoad,btnExit,btnNew, btnMusic;
	JComboBox<String> cbMusic;
	JMenuBar menuBar;
	JMenu mnuOption,mnuSet,mnuHelp;
	JMenuItem miReset,miExit,miBack;
	JMenuItem miMusic1,miMusic2,miMusic3,miMusic4,miMusic5;
	JMenuItem miHelp;
	//music file
	String sMusic[] = {
		"casino.mid",
		"forest.mid",
		"luf2casino.mid",
		"party.mid",
		"tom.mid"
	};
	String musicName[]={
			"Casino",
			"Forest",
			"Jumper",
			"party",
			"Go ahead"
			
	};
	Music music;
	Container c;
	
	//game panel
	JPanel mainPanel;
	public MainMenu(){
		super("Game 2017");
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("pic/0.png");
		//set icon
		this.setIconImage(image);
		c = this.getContentPane();
		c.setLayout(null);
		c.setBackground(Color.orange);
		
		JLabel lblTitle = new JLabel("SOKOBAN GAME",JLabel.CENTER);
		lblTitle.setFont(new Font("",Font.BOLD,20));
		lblTitle.setBounds(100,20,500,30);
		c.add(lblTitle,BorderLayout.NORTH);
		setMenus();
		
		music = new Music();
		
		createPanel(c);
	}
	
	public void createPanel(Container con) {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(4, 1));
		mainPanel.setBounds(60,120,450,450);
		
		btnNew = new JButton("New Game");
		btnLoad = new JButton("Load Game");
		btnExit = new JButton("Exit Game");
		btnMusic = new JButton("Music Off");
		cbMusic = new JComboBox<String>(musicName);
		JLabel lblMusic = new JLabel("Select Music");
		c.add(lblMusic);
		
		btnNew.addActionListener(this);
		btnLoad.addActionListener(this);
		btnExit.addActionListener(this);
		btnMusic.addActionListener(this);
		cbMusic.addItemListener(this);
		
		//Stops game freezing after button press.
		btnNew.setFocusable(false);
		btnLoad.setFocusable(false);
		btnExit.setFocusable(false);
		btnMusic.setFocusable(false);
		cbMusic.setFocusable(false);
		
		lblMusic.setBounds(550, 310, 80, 30);
		
		mainPanel.add(btnNew);
		mainPanel.add(btnLoad);
		mainPanel.add(btnMusic);
		mainPanel.add(btnExit);

		con.add(cbMusic);
		cbMusic.setBounds(550, 340, 80, 30);
		con.add(mainPanel);
		setSize(720,720);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}	
	
	//set menu
	public void setMenus(){
		mnuOption = new JMenu("Option");
		 miReset = new JMenuItem("Reset");
		 miExit = new JMenuItem("Exit");
		 miBack = new JMenuItem("Back");
		
		 mnuOption.add(miBack);
		 mnuOption.add(miReset);
		 mnuOption.addSeparator();
		 mnuOption.add(miExit);
		
		 mnuSet = new JMenu("Setting");
		 miMusic1 = new JMenuItem("default");
		 miMusic2 = new JMenuItem("good");
		 miMusic3 = new JMenuItem("music");
		 miMusic4 = new JMenuItem("choose");
		 miMusic5 = new JMenuItem("column");
		 mnuSet.add(miMusic1);
		 mnuSet.add(miMusic2);
		 mnuSet.add(miMusic3);
		 mnuSet.add(miMusic4);
		 mnuSet.add(miMusic5);
		 
		 mnuHelp = new JMenu("Help");
		 mnuHelp.setMnemonic('H');
		 miHelp = new JMenuItem("About us...");
		 mnuHelp.add(miHelp);
		 
		 miBack.addActionListener(this);
		 miReset.addActionListener(this);
		 miExit.addActionListener(this);
		 miMusic1.addActionListener(this);
		 miMusic2.addActionListener(this);
		 miMusic3.addActionListener(this);
		 miMusic4.addActionListener(this);
		 miMusic5.addActionListener(this);
		 miHelp.addActionListener(this);
		 
		 menuBar = new JMenuBar();
		 menuBar.add(mnuOption);
		 menuBar.add(mnuSet);
		 menuBar.add(mnuHelp);
		 this.setJMenuBar(menuBar);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(miHelp)){
			String str = "COMP2911\n";
			str +="Assignment3\n";
			JOptionPane.showMessageDialog(this, str, "Help", JOptionPane.INFORMATION_MESSAGE);
		} else if(e.getSource().equals(miExit) || e.getSource().equals(btnExit)){
			System.exit(0);
		} else if(e.getSource().equals(btnNew)){
			music.stopMusic();
			new Sokoban(new GameEngine(15, 15));
		} else if(e.getSource().equals(btnLoad)){
		} else if(e.getSource().equals(btnMusic)){
			String state = btnMusic.getText();
			if(state.equals("Music Off")){
				if(music.isPlay()){
					music.stopMusic();
				}
				btnMusic.setText("Music On");	
			} else {
				btnMusic.setText("Music Off");
				music.loadSound();
			}
			
			
	    } else if(e.getSource().equals(miMusic1)){
			music.setMusic(sMusic[0]);
			if(music.isPlay()){
				music.stopMusic();
			}
			music.loadSound();
		} else if(e.getSource().equals(miMusic2)){
			music.setMusic(sMusic[1]);
			if(music.isPlay()){
				music.stopMusic();
			}
			music.loadSound();
		} else if(e.getSource().equals(miMusic3)){
			music.setMusic(sMusic[2]);
			if(music.isPlay()){
				music.stopMusic();
			}
			music.loadSound();
		} else if(e.getSource().equals(miMusic4)){
			music.setMusic(sMusic[3]);
			if(music.isPlay()){
				music.stopMusic();
			}
			music.loadSound();
		} else if(e.getSource().equals(miMusic5)){
			music.setMusic(sMusic[4]);
			if(music.isPlay()){
				music.stopMusic();
			}
			music.loadSound();
		}
		
		if(e.getSource().equals(btnMusic)) {
		} else {
			c.remove(mainPanel);
			createPanel(c);
			c.revalidate();
			c.repaint();
		}
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		int index = cbMusic.getSelectedIndex();
		String fileName = sMusic[index];
		music.setMusic(fileName);
		if(music.isPlay()){
			music.stopMusic();
		}
		music.loadSound();
	}
}