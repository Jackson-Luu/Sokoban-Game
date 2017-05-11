import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class Sokaban extends JFrame implements ActionListener{
	JButton btnBack,btnFirst,btnNext,btnPrev,btnLast,btnSelect,btnMusic,btnReset;
	JComboBox cbMusic;
	JMenuBar menuBar;
	JMenu mnuOption,mnuSet,mnuHelp;
	JMenuItem miReset,miPrev,miNext,miSelect,miExit,miBack;
	JMenuItem miMusic1,miMusic2,miMusic3,miMusic4,miMusic5;
	JMenuItem miHelp;
	//music file
	String sMusic[] = {
		"eyes on me.mid",
		"guang.mid",
		"nor.mid",
		"popo.mid",
		"qin.mid"
	};
	
	public Sokaban(){
		super("Game 2017");
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("pic/box1.png");
		//set icon
		this.setIconImage(image);
		Container c = this.getContentPane();
		c.setLayout(null);
		c.setBackground(Color.orange);
		
		JLabel lblTitle = new JLabel("BOX GAME",JLabel.CENTER);
		lblTitle.setFont(new Font("",Font.ITALIC,20));
		lblTitle.setBounds(100,20,500,30);
		c.add(lblTitle,BorderLayout.NORTH);
		//put buttons
		setButton(c);
		setMenus();
		
		setSize(720,720);
		//setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	//set button
	public void setButton(Container c){
		btnReset = new JButton("Reset");
		btnBack = new JButton("Back");
		btnFirst = new JButton("First");
		btnNext = new JButton("Next");
		btnPrev = new JButton("Prev");
		btnLast = new JButton("Last");
		btnSelect = new JButton("Select");
		btnMusic = new JButton("Music");
		JLabel lblMusic = new JLabel("Select Music");
		c.add(lblMusic);
		
		btnReset.addActionListener(this);
		btnBack.addActionListener(this);
		btnFirst.addActionListener(this);
		btnNext.addActionListener(this);
		btnPrev.addActionListener(this);
		btnLast.addActionListener(this);
		btnSelect.addActionListener(this);
		btnMusic.addActionListener(this);
		//cbMusic.addActionListener(this);
		
		cbMusic = new JComboBox();
		cbMusic.addItem("default");
		cbMusic.addItem("good");
		cbMusic.addItem("music");
		cbMusic.addItem("choose");
		cbMusic.addItem("column");
		
		btnReset.setBounds(600,100,80,30);
		btnBack.setBounds(600,150,80,30);
		btnFirst.setBounds(600,200,80,30);
		btnNext.setBounds(600,250,80,30);
		btnPrev.setBounds(600,300,80,30);
		btnLast.setBounds(600,350,80,30);
		btnSelect.setBounds(600,400,80,30);
		btnMusic.setBounds(600,450,80,30);
		lblMusic.setBounds(600, 500, 80, 30);
		cbMusic.setBounds(600, 530, 80, 30);
		c.add(btnReset);
		c.add(btnBack);
		c.add(btnFirst);
		c.add(btnNext);
		c.add(btnPrev);
		c.add(btnLast);
		c.add(btnSelect);
		c.add(btnMusic);
		c.add(cbMusic);
	}
	
	
	//set menu
	public void setMenus(){
		mnuOption = new JMenu("Option");
		 miReset = new JMenuItem("Reset");
		 miPrev = new JMenuItem("Previous");
		 miNext = new JMenuItem("Next");
		 miSelect = new JMenuItem("Select");
		 miExit = new JMenuItem("Exit");
		 miBack = new JMenuItem("Back");
		
		 mnuOption.add(miBack);
		 mnuOption.add(miReset);
		 mnuOption.add(miPrev);
		 mnuOption.add(miNext);
		 mnuOption.add(miSelect);
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
		 miPrev.addActionListener(this);
		 miNext.addActionListener(this);
		 miSelect.addActionListener(this);
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
		// TODO Auto-generated method stub
		if(e.getSource() == miHelp){
			String str = "COMP2911\n";
			str +="Assignment3\n";
			JOptionPane.showMessageDialog(this, str, "Help", JOptionPane.INFORMATION_MESSAGE);
		} else if(e.getSource() == miExit){
			System.exit(0);
		}
	}
	
	//game panel
	public class MyPanel extends JPanel{
		
	}
}