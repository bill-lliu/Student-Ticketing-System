/* February 11, 2019
 * Bill Liu
 * TicketSystem.java
 * main method for the Ticketing System Program
 */

//Graphics & GUI imports
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Toolkit;
//Keyboard and mouse imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
//file input output
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//Utility
import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings("serial")
public class TicketingSystem extends JFrame{
	
	//main class variables
	static JFrame window;
	JPanel gamePanel;
	ArrayList<Student> studentList = new ArrayList<Student>();
		
	//main method
	public static void main(String[] args) {
	     window = new TicketingSystem();
	}

	//----------------------Initial System Constructor-------------------
   	TicketingSystem() { 
   		super("Ticketing System");  
   		
   		//creates variables to write and draw
   		String eventName;
   		
   		
   		
   		//the program will try to find the event data which is saved as a text file
   		//if the file does not exist, it will generate a new file with this name
   		
   		//asks the user for the name of the file
   		eventName = JOptionPane.showInputDialog(null, "What is the name of your event?", "RHHS Event Organizer");

   		//initiates the file
   		File eventTextFile = new File(eventName + ".txt");


   		if (eventTextFile.exists() && !eventTextFile.isDirectory()) {
   			//reading from the file to fill variables
   			System.out.println("file successfully found");
   			try {
   				//creates the writer for the file
   				FileReader MyReader = new FileReader(eventName + ".txt");
   				BufferedReader MyBuffer = new BufferedReader(MyReader);
   				
   				//reading the file and adding the strings to the student list
   				String nextLine;
   				while ((nextLine = MyBuffer.readLine()) != null) {
   					String[] tmpStrings = nextLine.split(";");
   					
   					//trims off spaces of each variable that may have been entered
   					for (int i=0; i<tmpStrings.length; i++) {
   						tmpStrings[i] = tmpStrings[i].trim();
   					}
   					//changes the diet restriction from a string to an array list
   					String[] tmpDietResString = tmpStrings[2].split(",");
   					ArrayList<String> tmpDietResList = new ArrayList<String>();
   					for (int i=0; i<tmpDietResString.length; i++) {//trimming spaces
   						tmpDietResList.add(tmpDietResString[i].trim());//adding to temporary array list
   					}
   					//changes the friends's student numbers from a string to an array list
   					String[] tmpStudentNumString = tmpStrings[3].split(",");
   					ArrayList<String> tmpStudentNumList = new ArrayList<String>();
   					for (int i=0; i<tmpStudentNumString.length; i++) {//trimming spaces
   						tmpStudentNumList.add(tmpStudentNumString[i].trim());//adding to temporary array list
   					}
   					
   					//adds the student to the master student list
   					Student student = new Student(tmpStrings[0], tmpStrings[1], tmpDietResList, tmpStudentNumList);
   					studentList.add(student);
   					
   					/*System.out.println(studentList.get(0).getName() + ", " 
   							+ studentList.get(0).getStudentNumber() + ", " 
   							+ studentList.get(0).getDietaryRestrictions() + ", " 
   							+ studentList.get(0).getFriendStudentNumbers()); 
   							sample output */
   					
   				}
   				
   				MyBuffer.close();//closes buffer so file does not corrupt
   			} catch (IOException e) {
   				System.out.println("error while reading file");
   			}
   		   		
   		} else {
   			//creating the event with the input event name
   			System.out.println("no file of that name found... generating new file");
   			try {
   				//creates the writer for the file
   				FileWriter MyWriter = new FileWriter(eventName + ".txt");
   				PrintWriter MyPrinter = new PrintWriter(MyWriter);
   				
   				MyPrinter.close();//closes printer so file does not corrupt
   			} catch (IOException e) {
   				System.out.println("error while writing file");
   			}
   			
   		}
   		
   		
   		
   		
   		
   		
   		//panel showing home page
   		
   		//panel showing student list
   		
   		//panel showing adding a student
   		
   		
   		// Set the frame to full screen 
   	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   	    this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
   	    //this.setUndecorated(true);  //Set to true to remove title bar
   	    //frame.setResizable(false);


   	    
   	    //Set up the game panel (where we put our graphics)
   	    gamePanel = new GameAreaPanel();
   	    this.add(new GameAreaPanel());
   	    
   	    MyMouseListener mouseListener = new MyMouseListener();
   	    this.addMouseListener(mouseListener);
   	    this.requestFocusInWindow(); //make sure the frame has focus
   	    
   	    this.setVisible(true);
   	}//end of constructor
   	
   	//---------------------------Game Panel Class---------------------------
   	private class GameAreaPanel extends JPanel {
   	    public void paintComponent(Graphics g) {   
   	       super.paintComponent(g); //required
   	       setDoubleBuffered(true); 
   	       
   	       //insert here stuff that would happen every frame
   	       
   	       }
   	}
   	
   	
   	//--------------------------Mouse Listener Class---------------------
   	private class MyMouseListener implements MouseListener {
        
   		//write if statement for if mouse is clicked then do action where clicked
   		
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
		}
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
		}
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
		}
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
		}
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
		}
		
      }//end of mouse listener
   	
   	
}