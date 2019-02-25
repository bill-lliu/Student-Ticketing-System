/* February 11, 2019
 * Bill Liu
 * TicketSystem.java
 * main method for the Ticketing System Program
 */

//Graphics & GUI imports
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Toolkit;
//Keyboard and mouse imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//file input output
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//Utility
import java.util.ArrayList;

@SuppressWarnings("serial")
public class TicketingSystem extends JFrame{

	//main class variables
	static JFrame window;
	JPanel mainPanel;
	JPanel listPanel;
	ArrayList<Student> studentList = new ArrayList<Student>();

	//main method
	public static void main(String[] args) {
		window = new TicketingSystem();
	}

	//Gets a student when given student number OR name

	private Student findStudent(String identifier) {
		//If given a student number
		if (isNumber(identifier)) {
			for (Student student : studentList) {
				if ((student.getStudentNumber()).equals(identifier)) return student;
			}
		}
		//If given a student name
		else {
			for (Student student : studentList) {
				if ((student.getName()).equals(identifier)) return student;
			}
		}
		//If student not found
		return null;
	}

	private boolean isNumber(String text) {
		//Is a student number
		try {
			Integer.parseInt(text);
		}
		//Is student name
		catch (NumberFormatException | NullPointerException e) {
			return false;
		}
		return true;
	}

	//----------------------Initial System Constructor-------------------
	TicketingSystem() {
		super("Ticketing System");

		//creates variables to write and draw
		String eventName;


		//the program will try to find the event data which is saved as a text file
		//if the file does not exist, it will generate a new file with this name

		//asks the user for the name of the file
		eventName = JOptionPane.showInputDialog(null, "What is the name of your event?" + "\n" + "(Enter a new name for a new event)", "RHHS Event Organizer", JOptionPane.PLAIN_MESSAGE);

		//in case the user clicks out of the panel without entering anything
		if (eventName == null) {
			System.exit(0);
		}

		//initiates the file
		File eventTextFile = new File(eventName + ".csv");


		//********If a file is found**************
		if (eventTextFile.exists() && !eventTextFile.isDirectory()) {
			//reading from the file to fill variables
			System.out.println("file successfully found... collecting data");
			try {
				//creates the writer for the file
				FileReader MyReader = new FileReader(eventName + ".csv");
				BufferedReader MyBuffer = new BufferedReader(MyReader);

				//reading the file and adding the strings to the student list
				//Need to read first line in order to remove titles from data
				String nextLine = MyBuffer.readLine();
				while ((nextLine = MyBuffer.readLine()) != null) {
					//For csv files, don't split commas inside of quotation marks
					String[] tmpStrings = nextLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);

					//trims off spaces of each variable that may have been entered
					for (int i=0; i<tmpStrings.length; i++) {
						tmpStrings[i] = tmpStrings[i].trim();
					}

					//changes the diet restriction from a string to an array list
					String[] tmpDietResString = tmpStrings[2].split(",");
					ArrayList<String> tmpDietResList = new ArrayList<String>();
					for (int i=0; i<tmpDietResString.length; i++) {//trimming spaces
						tmpDietResString[i] = tmpDietResString[i].replace("\"", ""); //strip quotation marks
						tmpDietResList.add(tmpDietResString[i].trim());//adding to temporary array list
					}
					//changes the friends's student numbers from a string to an array list
					String[] tmpStudentNumString = tmpStrings[3].split(",");
					ArrayList<String> tmpStudentNumList = new ArrayList<String>();
					for (int i=0; i<tmpStudentNumString.length; i++) {//trimming spaces
						tmpStudentNumString[i] = tmpStudentNumString[i].replace("\"", ""); //strip quotation marks
						tmpStudentNumList.add(tmpStudentNumString[i].trim());//adding to temporary array list
					}

					//adds the student to the master student list
					Student student = new Student(tmpStrings[0], tmpStrings[1], tmpDietResList, tmpStudentNumList);
					studentList.add(student);

   					/*System.out.println(studentList.get(0).getName() + ", "
   							+ studentList.get(0).getStudentNumber() + ", "
   							+ studentList.get(0).getDietaryRestrictions() + ", "
   							+ studentList.get(0).getFriendStudentNumbers());*/

				}//end while loop for reading info


				MyBuffer.close();//closes buffer so file does not corrupt

			} catch (IOException e) {
				System.out.println("error while reading file");
			}


			//********If no file of that name is found***************
		} else {
			//creating the event with the input event name
			System.out.println("no file of that name found... generating new file");
			try {
				//creates the writer for the file
				FileWriter MyWriter = new FileWriter(eventName + ".csv");//creates the file
				
				/*StringBuilder MyBuilder = new StringBuilder();
			      MyBuilder.append("Name,");
			      MyBuilder.append("Student Number,");
			      MyBuilder.append("Dietary Restrictions,");
			      MyBuilder.append("Friends");
			      MyBuilder.append('\n');

			      MyWriter.write(MyBuilder.toString());*/ //no longer necessary;
				
				MyWriter.close();
			} catch (IOException e) {
				System.out.println("error while writing file");
			}

		}

		//********starts the panel***************
		// Set the frame to full screen
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		//this.setUndecorated(true);  //Set to true to remove title bar
		//frame.setResizable(false);

		//Set up the display panel
		
		mainPanel = new AddStudentPanel();
		listPanel = new StudentListPanel();
		this.setLayout(new BorderLayout());
		this.add(new AddStudentPanel());
		this.add(new StudentListPanel(), BorderLayout.CENTER);
		this.setTitle(eventName);
		this.pack();
		this.setVisible(true);
		this.requestFocusInWindow(); //make sure the frame has focus

		/*//initiates listener
		MyMouseListener mouseListener = new MyMouseListener();
		this.addMouseListener(mouseListener);*/ // no longer needed

	}//end of constructor



	//---------------------------Home Page Display---------------------------
	private class HomePagePanel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g); //required
			setDoubleBuffered(true);

			//insert here stuff that would happen every frame

		}
		
		private class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == editButton) {
					String studentNumber = JOptionPane.showInputDialog(null, "Enter student number:", "Edit Student", JOptionPane.PLAIN_MESSAGE);
				}
			}
		}
	}
	

	/*private JPanel createMainPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JButton myButton = new JButton("My Button");
		panel.add(myButton);

		return panel;
	}*/



	//------------------------Add Student Display-------------------
	private class AddStudentPanel extends JPanel {

		public void paintComponent(Graphics g) {
			super.paintComponent(g); //required
			setDoubleBuffered(true);

		}
	}

	//------------------------View Current List Display-------------------
	private class StudentListPanel extends JPanel {
		ClickListener click = new ClickListener();
		JButton backButton = new JButton("Back");
		JButton editButton = new JButton("Edit Student");
		StudentListPanel() {
			this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
			//Column names
			String[] columnNames = { "Name", "Student Number", "Dietary Restrictions", "Friends" };
			//Convert ArrayList of students to array
			String[][] data = new String[studentList.size()][4];
			for (int row = 0; row < studentList.size(); row++) {
				data[row][0] = (studentList.get(row)).getName();
				data[row][1] = (studentList.get(row)).getStudentNumber();
				//Format dietary restrictions
				String diet = "";
				for (int i = 0; i < ((studentList.get(row)).getDietaryRestrictions()).size(); i++) {
					if (i != 0) diet += ", ";
					diet += ((studentList.get(row)).getDietaryRestrictions()).get(i);
				}
				data[row][2] = diet;
				//Format friends
				String friends = "";
				for (int i = 0; i < ((studentList.get(row)).getFriendStudentNumbers()).size(); i++) {
					if (i != 0) friends += ", ";
					//Find other friends names if available
					String friendNumber = ((studentList.get(row)).getFriendStudentNumbers()).get(i);
					Student friend = findStudent(friendNumber);
					if (friend != null) {
						friends += friend.getName() + " (" + friendNumber + ")";
					} else {
						friends += friendNumber;
					}
				}
				data[row][3] = friends;
			}
			//Create JTable
			JPanel tablePanel = new JPanel(new BorderLayout());
			JTable table = new JTable(data, columnNames);
			table.setEnabled(false);
			//Create JScrollPane
			JScrollPane sp = new JScrollPane(table);
			tablePanel.add(sp);
			//Create buttons
			JPanel buttonPanel = new JPanel(new FlowLayout());

			backButton.setVerticalTextPosition(JButton.CENTER);
			backButton.setHorizontalTextPosition(JButton.CENTER);

			editButton.setVerticalTextPosition(JButton.CENTER);
			editButton.setHorizontalTextPosition(JButton.CENTER);
			editButton.addActionListener(click);

			buttonPanel.add(backButton);
			buttonPanel.add(editButton);
			this.add(tablePanel);
			this.add(buttonPanel);

		}

		private class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == editButton) {
					String studentNumber = JOptionPane.showInputDialog(null, "Enter student number:", "Edit Student", JOptionPane.PLAIN_MESSAGE);
				} else if (e.getSource() == backButton) {
					//StudentListPanel.setEnabled(false);
					//should close jpanel for student then reopen home panel
				}
			}
		}


		public void paintComponent(Graphics g) {
			super.paintComponent(g); //required
			setDoubleBuffered(true);
			
		}
	}



	/*//--------------------------Mouse Listener Class---------------------
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

	}//end of mouse listener*/ // no longer needed


}