/**
 * 
 * @author Jackson Luu, Keller Huang, Dan Yoo, Evan Han and Lilac Liu
 *
 */

import javax.swing.*;

public class GameInterface {

    private static void createGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Interactive Puzzle Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        
        /*
        Working on adding buttons next:
        JButton b1 = new JButton("New Game");
        b1.setVerticalTextPosition(AbstractButton.CENTER);
        b1.setHorizontalTextPosition(AbstractButton.LEADING);
        */
 
        //Display the window.
        //frame.pack();
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
  
    public static void main(String[] args) {

    	
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createGUI();
            }
        });
    }
}
