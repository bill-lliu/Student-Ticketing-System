/* February 11, 2019
 * Bill Liu
 * TicketSystem.java
 * main method for the Ticketing System Program
 */

//Graphics &GUI imports
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Component;
//Keyboard and mouse imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
//Utility
import java.util.ArrayList;


public class TicketingSystem extends JFrame{
	
	//main class variables
	static JFrame window;
	JPanel gamePanel;
	ArrayList<Student> AllStudents;

	//main method
	public static void main(String[] args) {
	     window = new TicketingSystem();
	}

	//Constructor to initialize the system
   	TicketingSystem() { 
    super("Ticketing System");  
    
    //creates variables to write and draw
    String 
	
}