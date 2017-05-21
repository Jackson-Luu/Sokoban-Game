import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;


public class Sokoban extends JFrame implements ActionListener,ItemListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton btnBack,btnMusic,btnReset;
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
	GameEngine game;
	Container c;
	
	//game panel
	MyPanel mainPanel;
	public Sokoban(GameEngine currGame){
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
		//put buttons
		setButton(c);
		setMenus();
		
		music = new Music();
		
		game = currGame;
		createPanel(c);
		
		//keyboard input code
		addKeyListener(new TAdapter());
		setFocusable(true);
        Timer timer = new Timer(100, this);
        timer.start();		
	}
	
	public void createPanel(Container con) {
		mainPanel = new MyPanel(game.getState().getCurrMap().getLocations());
		mainPanel.setBounds(60,120,450,450);
		con.add(mainPanel);
		setSize(720,720);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	//set button
	public void setButton(Container c){
		btnReset = new JButton("Reset");
		btnBack = new JButton("Back");
		btnMusic = new JButton("Music Off");
		cbMusic = new JComboBox<String>(musicName);
		JLabel lblMusic = new JLabel("Select Music");
		c.add(lblMusic);
		
		btnReset.addActionListener(this);
		btnBack.addActionListener(this);
		btnMusic.addActionListener(this);
		cbMusic.addItemListener(this);
		
		//Stops game freezing after button press.
		btnReset.setFocusable(false);
		btnBack.setFocusable(false);
		btnMusic.setFocusable(false);
		
		btnReset.setBounds(550,250,80,30);
		btnBack.setBounds(550,300,80,30);
		btnMusic.setBounds(550,350,80,30);
		lblMusic.setBounds(550, 400, 80, 30);
		cbMusic.setBounds(550, 430, 80, 30);
		c.add(btnReset);
		c.add(btnBack);
		c.add(btnMusic);
		c.add(cbMusic);
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
		} else if(e.getSource().equals(miExit)){
			System.exit(0);
		} else if(e.getSource().equals(btnReset)){
			game.resetState();
		} else if(e.getSource().equals(btnBack)){
			game.prevState();
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
		
		c.remove(mainPanel);
		createPanel(c);
		c.revalidate();
		c.repaint();
	}
	
	private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
        	int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT) {
            	game.makeMove(Map.MOVE_LEFT);
            }

            if (key == KeyEvent.VK_RIGHT) {
            	game.makeMove(Map.MOVE_RIGHT);
            }

            if (key == KeyEvent.VK_UP) {
            	game.makeMove(Map.MOVE_UP);
            }

            if (key == KeyEvent.VK_DOWN) {
            	game.makeMove(Map.MOVE_DOWN);
            }
        }
    }
	
	//game panel
	class MyPanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int[][] oriMap;
		private int[][] tempMap;
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image mapimg[] = {
			kit.getImage("pic/2.png"),
			kit.getImage("pic/8.GIF"),
			kit.getImage("pic/7.GIF"),
			kit.getImage("pic/5.GIF"),
			kit.getImage("pic/6.GIF"),
			kit.getImage("pic/3.png"),
			kit.getImage("pic/0.png"),
			kit.getImage("pic/4.gif"),
			kit.getImage("pic/9.png"),
			kit.getImage("pic/1.gif")
		};
		public int[][] getOriMap() {
			return oriMap;
		}
		public void setOriMap(int[][] oriMap) {
			this.oriMap = oriMap;
		}
		public MyPanel(int[][] map){
			readMap(map);
			setSize(600,600);
			this.requestFocus();
			repaint();
		}
		public void readMap(int[][] map){
			this.oriMap = map;
			this.tempMap = map;
		}
		

		@Override
		public void paint(Graphics g){
			for(int i = 0; i < 15; i++){
				for(int j = 0; j< 15;j++){
					g.drawImage(mapimg[this.tempMap[j][i]], i*30, j*30, 30,30,this);
				}
			}
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
			music.loadSound();
		}
	}
}
