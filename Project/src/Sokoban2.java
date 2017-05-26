import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;


public class Sokoban extends JFrame implements ActionListener{

    private static final long serialVersionUID = 1L;
    JMenuBar menuBar;
    JMenu mnuOption,mnuSet,mnuHelp;
    JMenuItem miReset,miExit,miBack;
    JMenuItem miMusic1,miMusic2,miMusic3,miMusic4,miMusic5;
    JMenuItem miHelp;
    Boolean Completed, menu;
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
            "Party",
            "Go ahead"

    };
    Music music;
    GameEngine game;
    Container c;

    //game panel
    gamePanel mainPanel;
    menuPanel menuPanel;
    JLabel lblTitle;
    JPanel cardLayout;
    public Sokoban(GameEngine currGame){
        super("Game 2017");

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("pic/0.png");
        //set icon
        this.setIconImage(image);
        c = this.getContentPane();
        c.setLayout(new BorderLayout());
        c.setBackground(Color.orange);

        lblTitle = new JLabel("SOKOBAN GAME",JLabel.CENTER);
        lblTitle.setFont(new Font("",Font.BOLD,20));
        lblTitle.setPreferredSize(new Dimension(720, 100));
        c.add(lblTitle, BorderLayout.PAGE_START);
        //put buttons
        setMenus();
        Completed = false;

        music = new Music();

        game = currGame;
        cardLayout = new JPanel();
        cardLayout.setLayout(new CardLayout());
        menuPanel = new menuPanel(cardLayout);
        mainPanel = new gamePanel(cardLayout);
        cardLayout.add(menuPanel, "Main Menu");
        
        c.add(cardLayout);
        this.setContentPane(c);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationByPlatform(true);

        //keyboard input code
        addKeyListener(new TAdapter());
        setFocusable(true);
        Timer timer = new Timer(10, this);
        timer.start();
        
        this.setVisible(true);
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
        miMusic1 = new JMenuItem("Casino");
        miMusic2 = new JMenuItem("Forest");
        miMusic3 = new JMenuItem("Jumper");
        miMusic4 = new JMenuItem("Party");
        miMusic5 = new JMenuItem("Go Ahead");
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
        if(game.isFinished() && !Completed){
            Completed = true;
            String finished = "You Win!";
            JOptionPane.showMessageDialog(this, finished, "Help", JOptionPane.INFORMATION_MESSAGE);
        }
        if(e.getSource().equals(miHelp)){
            String str = "COMP2911\n";
            str +="Assignment3\n";
            JOptionPane.showMessageDialog(this, str, "Help", JOptionPane.INFORMATION_MESSAGE);
        } else if(e.getSource().equals(miExit)){
            System.exit(0);
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
            
            if (menu == false) {
            	mainPanel = new gamePanel(cardLayout);
        		cardLayout.add(mainPanel);
        		((CardLayout)cardLayout.getLayout()).next(cardLayout);
        		c.revalidate();
        		c.repaint();
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
                kit.getImage("pic/8.GIF"),
                kit.getImage("pic/7.GIF"),
                kit.getImage("pic/5.GIF"),
                kit.getImage("pic/6.GIF")
        };
        public int[][] getOriMap() {
            return oriMap;
        }
        public void setOriMap(int[][] oriMap) {
            this.oriMap = oriMap;
        }
        public MyPanel(int[][] map){
            readMap(map);
            this.setOpaque(true);
            this.requestFocus();
            repaint();
        }
        public void readMap(int[][] map){
            this.oriMap = map;
            this.tempMap = map;
        }


        @Override
        public void paintComponent(Graphics g){
        	g.setColor(Color.ORANGE);
        	g.fillRect(0, 0, getWidth(), getHeight());
        	g.setColor(getForeground());
            for(int i = 0; i < 15; i++){
                for(int j = 0; j< 15;j++){
                    g.drawImage(mapimg[this.tempMap[j][i]], i*30, j*30, 30,30,this);
                }
            }
        }
        
        @Override
    	public Dimension getPreferredSize() {
    		return (new Dimension(500, 500));
    	}
    }
    
    class menuPanel extends JPanel {
    	
		private static final long serialVersionUID = 6508884619039209211L;
		private JButton btnNew, btnLoad, btnExit;	
    	
    	public menuPanel(JPanel contentPane) {
    		this.setLayout(new GridLayout(4, 1, 0, 50));
    		this.setBackground(Color.orange);
    		
    		btnNew = new JButton("New Game");
    		btnLoad = new JButton("Load Game");
    		btnExit = new JButton("Exit Game");
    		
    		btnNew.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				game.newGame();
    				Completed = false;
    				menu = false;
    				mainPanel = new gamePanel(cardLayout);
    	        	cardLayout.add(mainPanel);
    	        	((CardLayout)cardLayout.getLayout()).next(cardLayout);
    	        	c.revalidate();
    	        	c.repaint();
    			}
    		});
    		btnLoad.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				Completed = false;
    				menu = false;
    			}
    		});
    		btnExit.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				System.exit(0);
    			}
    		});
    		
    		//Stops game freezing after button press.
    		btnNew.setFocusable(false);
    		btnLoad.setFocusable(false);
    		btnExit.setFocusable(false);
    		
    		add(btnNew);
    		add(btnLoad);
    		add(btnExit);
    	}
    	
    	@Override
    	public Dimension getPreferredSize() {
    		return (new Dimension(600, 600));
    	}
    }
    
    class gamePanel extends JPanel implements ItemListener{
    	
		private static final long serialVersionUID = -3734882620271204708L;
		private JButton btnReset, btnBack, btnMusic, btnMenu;
    	private JComboBox<String> cbMusic;
    	private JPanel buttons;
    	
    	public gamePanel(JPanel contentPane) {
    		this.setLayout(new GridBagLayout());
    		this.setBackground(Color.orange);
    		
    		GridBagConstraints gbc = new GridBagConstraints();
    		gbc.gridx = 0;
    		gbc.gridy = 0;
    		gbc.fill = GridBagConstraints.BOTH;
    		gbc.anchor = GridBagConstraints.NORTH;
            add(new MyPanel(game.getState().getCurrMap().getLocations()));
            buttons = new JPanel();
            buttons.setLayout(new GridLayout(6, 1, 0, 20));
            buttons.setBackground(Color.orange);
    		
            btnReset = new JButton("Reset");
            btnBack = new JButton("Back");
            btnMusic = new JButton("Music Off");
            JLabel lblMusic = new JLabel("Select Music:");
            cbMusic = new JComboBox<String>(musicName);
            cbMusic.addItemListener(this);
            btnMenu = new JButton("Main Menu");            
    		
    		btnReset.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				game.resetState();
    				mainPanel = new gamePanel(cardLayout);
    	        	cardLayout.add(mainPanel);
    	        	((CardLayout)cardLayout.getLayout()).next(cardLayout);
    	        	c.revalidate();
    	            c.repaint();    				
    			}
    		});
    		btnBack.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				game.prevState();
    				mainPanel = new gamePanel(cardLayout);
    	        	cardLayout.add(mainPanel);
    	        	((CardLayout)cardLayout.getLayout()).next(cardLayout);
    	        	c.revalidate();
    	            c.repaint();
    			}
    		});
    		btnMusic.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
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
    			}
    		});
    		btnMenu.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				menu = true;
    				cardLayout.add(menuPanel);
    				((CardLayout)cardLayout.getLayout()).next(cardLayout);
    	        	c.revalidate();
    	            c.repaint();
    			}
    		});
    		
    		//Stops game freezing after button press.
    		btnReset.setFocusable(false);
            btnBack.setFocusable(false);
            btnMusic.setFocusable(false);
            cbMusic.setFocusable(false);
            btnMenu.setFocusable(false);
            
            buttons.add(btnReset);
            buttons.add(btnBack);
            buttons.add(btnMusic);
            buttons.add(lblMusic);
            buttons.add(cbMusic);
            buttons.add(btnMenu);
            
            gbc.gridx++;
            gbc.fill = GridBagConstraints.BOTH;
            add(buttons);
    	}
    	
    	@Override
    	public Dimension getPreferredSize() {
    		return (new Dimension(600, 600));
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

}
