/*
 * Mitchell Golding 3552573
 * 17 February 2017
 * CS 1083 Assignment 3
 *
 * Purpose: To play minesweeper
 * Input: Clicks
 * Output: Numbers and mines
 */
package cs1083_assign3;
import javax.swing.*;
public class CS1083_Assign3 {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Sweeper of the Mines");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        MinesweeperPanel classic = new MinesweeperPanel();
        frame.getContentPane().add(classic);
        
        frame. pack();
        frame.setVisible(true);
    }
    
}
