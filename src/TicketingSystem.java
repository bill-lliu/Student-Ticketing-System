/* February 11, 2019
 * Bill Liu and Victor Lin
 * TicketingSystem.java
 */

//Graphics & GUI imports
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
//Keyboard and mouse imports
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//file input output
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//Utility
import java.util.ArrayList;

/**
 * TicketingSystem
 * This class is the graphical interface for an event ticketing system.
 *
 * @author Bill Liu
 * @author Victor Lin
 */
public class TicketingSystem extends JFrame{

	//main class variables
	private static JFrame window;
	private JPanel mainPanel;
	private JPanel listPanel;
	private JPanel addPanel;
	private static ArrayList<Student> studentList = new ArrayList<Student>();
	private static String eventName;
	private int width = 1200; //used to set size of display panel
	private int height = 700;

	//main method
	public static void main(String[] args) {
		window = new TicketingSystem();
	}

	
	//-------------functions for the class-----------------
	//Gets a student when given student number OR name
	private ArrayList<Student> findStudent(String identifier) {
		ArrayList<Student> possibleStudents = new ArrayList<Student>();
		//If given a student number
		//check for blank student
		if (identifier.equals("")) {
			return null;
		}
		if (isNumber(identifier)) {
			for (Student student : studentList) {
				//Assume only one student with student number
				if ((student.getStudentNumber()).equals(identifier)) {
					possibleStudents.add(student);
					return possibleStudents;
				}
			}
		}
		//If given a student name
		else {
			for (Student student : studentList) {
				if (((student.getName().toLowerCase())).equals(identifier.toLowerCase())) {
					possibleStudents.add(student);
				}
			}
			//return list if there's at least one person
			if (!(possibleStudents.isEmpty())) return possibleStudents;
		}
		//If student not found
		return null;
	}

	//checks if entity is a number
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
	
	
	//function to save csv file
	static void saveFile() {
		//new information written to a temporary file
		File oldFile = new File(eventName+".csv");
		File newFile = new File("tmp.csv");
		try {
			FileWriter fw = new FileWriter("tmp.csv", true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			ArrayList<String> tmpDietList = new ArrayList<String>();
			ArrayList<String> tmpFriendsList = new ArrayList<String>();
			String toPrint = "";
			//headers
			pw.print("Name, Student Number, Dietary Restrictions, Friends"+'\n');
			//pw.flush();
		    //adds each student
			for (int i=0; i<studentList.size(); i++) {
				toPrint += studentList.get(i).getName()+",";
				toPrint += studentList.get(i).getStudentNumber()+",";
				tmpDietList = studentList.get(i).getDietaryRestrictions(); //gets tmp variables
				toPrint += tmpDietList + ",";
				tmpFriendsList = studentList.get(i).getFriendStudentNumbers();
				toPrint += tmpFriendsList;
				toPrint = toPrint.replace("[", "\""); //changes brackets to quotation marks
				toPrint = toPrint.replace("]", "\"");
				pw.print(toPrint + '\n');
			    //pw.flush();
				
				//resets temporary variables
				toPrint = "";
				tmpDietList = new ArrayList<String>();
				tmpFriendsList = new ArrayList<String>();

			}
			pw.flush();
			pw.close();
			oldFile.delete();
			File tmp = new File(eventName+".csv");
			newFile.renameTo(tmp);
			
		} catch (IOException e) {
	      System.out.println("error while saving");
		}
	}
	
	
	//changing font
	public static void setUIFont (javax.swing.plaf.FontUIResource f){
		java.util.Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
	    	Object key = keys.nextElement();
	    	Object value = UIManager.get (key);
	    	if (value instanceof javax.swing.plaf.FontUIResource) {
	    		UIManager.put (key, f);
	    	}
	    }
	}

	//----------------------Initial System Constructor-------------------
	private TicketingSystem() {
		super("Ticketing System");

		//sets the font
		setUIFont (new javax.swing.plaf.FontUIResource("Century Gothic",Font.PLAIN,24));
		
		//the program will try to find the event data which is saved as a text file
		//if the file does not exist, it will generate a new file with this name
		
		//asks the user for the name of the file
		UIManager.put("OptionPane.background", Color.CYAN);
		eventName = JOptionPane.showInputDialog(null, "Welcome to BlueSeater!"+"\n"+"What is the name of your event?"+"\n"+"(Enter a new name for a new event)", "BlueSeater", JOptionPane.PLAIN_MESSAGE);
        
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
				//other temporary variables used
				String[] tmpStrings;
				String[] tmpDietResString;
				String[] tmpStudentNumString;
				ArrayList<String> tmpDietResList;
				ArrayList<String> tmpStudentNumList;
				while ((nextLine = MyBuffer.readLine()) != null) {
					//For csv files, don't split commas inside of quotation marks
					tmpStrings = nextLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);

					//trims off spaces of each variable that may have been entered
					for (int i=0; i<tmpStrings.length; i++) {
						tmpStrings[i] = tmpStrings[i].trim();
					}

					//changes the diet restriction from a string to an array list
					tmpDietResString = tmpStrings[2].split(",");
					tmpDietResList = new ArrayList<String>();
					for (int i=0; i<tmpDietResString.length; i++) {//trimming spaces
						tmpDietResString[i] = tmpDietResString[i].replace("\"", ""); //strip quotation marks
						tmpDietResList.add(tmpDietResString[i].trim());//adding to temporary array list
					}
					//changes the friends's student numbers from a string to an array list
					tmpStudentNumString = tmpStrings[3].split(",");
					tmpStudentNumList = new ArrayList<String>();
					for (int i=0; i<tmpStudentNumString.length; i++) {//trimming spaces
						tmpStudentNumString[i] = tmpStudentNumString[i].replace("\"", ""); //strip quotation marks
						tmpStudentNumList.add(tmpStudentNumString[i].trim());//adding to temporary array list
					}

					//adds the student to the master student list
					Student student = new Student(tmpStrings[0], tmpStrings[1], tmpDietResList, tmpStudentNumList);
					studentList.add(student);
					
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
				//creates the writer for the file; creating the file
				FileWriter MyWriter = new FileWriter(eventName + ".csv");
				MyWriter.close();
			} catch (IOException e) {
				System.out.println("error while writing file");
			}

		}

		//***********starts the panel**************
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainPanel = new HomePagePanel();
		addPanel = new AddStudentPanel();
		this.setLayout(new BorderLayout());
		this.add(mainPanel, BorderLayout.CENTER);
		this.setTitle(eventName);
		this.pack();
		this.setSize(width, height);
		this.getContentPane().setBackground(Color.CYAN);
		this.setVisible(true);
		this.requestFocusInWindow(); //make sure the frame has focus
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();//moves window to the middle of the screen
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);


	}//end of constructor


	//---------------------------Home Page Display---------------------------
	private class HomePagePanel extends JPanel {
		ClickListener click = new ClickListener();
		//creates the parts of homepage
		JLabel creditLabel = new JLabel("Bill Liu/Victor Lin 2019");
		JLabel blankLabel2 = new JLabel(" ");
		JButton exitButton = new JButton("Save & Exit");
		JLabel blankLabel4 = new JLabel(" ");
		JLabel greetingLabel = new JLabel("Welcome to BlueSeater!");
		JLabel blankLabel6 = new JLabel(" ");
		JLabel blankLabel7 = new JLabel(" ");
		JLabel promptLabel = new JLabel("I would like to...");
		JLabel blankLabel9 = new JLabel(" ");
		JButton listButton = new JButton("View Student List");
		JButton addButton = new JButton("Add New Student");
		JButton floorPlanButton = new JButton("View Floor Plan");
		//JButton editButton = new JButton("Edit Student");
		HomePagePanel() {
			//adds parts in the right order
			this.setLayout(new GridLayout(4,3,100,100));
			this.add(creditLabel);
			this.add(blankLabel2);
			this.add(exitButton);
			this.add(blankLabel4);
			this.add(greetingLabel);
			this.add(blankLabel6);
			this.add(blankLabel7);
			this.add(promptLabel);
			this.add(blankLabel9);
			this.add(listButton);
			this.add(addButton);
			this.add(floorPlanButton);
			exitButton.addActionListener(click);
			listButton.addActionListener(click);
			addButton.addActionListener(click);
			floorPlanButton.addActionListener(click);
			this.setBackground(Color.CYAN);
		}

		/*public void paintComponent(Graphics g) {
			super.paintComponent(g); //required
			setDoubleBuffered(true);

		}*/

		//for what happens when something is clicked
		private class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == listButton) {//list all student button
					window.remove(mainPanel);
					listPanel = new StudentListPanel();
					listPanel.setBackground(Color.CYAN);
					window.add(listPanel, BorderLayout.CENTER);
					window.repaint();
					window.pack();
					window.setSize(width, height);
				} else if (e.getSource() == addButton) {//add student button
					window.remove(mainPanel);
					addPanel = new AddStudentPanel();
					addPanel.setBackground(Color.CYAN);
					window.add(addPanel, BorderLayout.CENTER);
					window.repaint();
					window.pack();
					window.setSize(width, height);
				} else if (e.getSource() == floorPlanButton) {//generate floorplan button
					int seats = 0;
					String input = null;
					input = JOptionPane.showInputDialog(null, "Enter number of seats per table:");
					if (input != null) {
						/*try {
							seats = Integer.parseInt(input);
							if (seats > 0) {
								//Use seating alg
								SeatingAlg alg = new SeatingAlg();
								ArrayList<Table> tables = alg.generateTables(studentList, seats);

								//Display floor plan
								FloorPlan plan = new FloorPlan();
								plan.generateFloorPlan(tables);
								plan.displayFloorPlan();
							}
							else {
								JOptionPane.showMessageDialog(null, "Must be a number greater than 0");
							}
						}
						catch (Exception exc) {
							JOptionPane.showMessageDialog(null, "Input must be a number");
						}*/
					}
				} else if (e.getSource() == exitButton) {//exit button
					saveFile();
					window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
				}
			}
		}

	}



	//------------------------Add Student Display-------------------
	private class AddStudentPanel extends JPanel {
		//read class at your own risk
		//will cause decrease in brain cells
		Student editStudent = null;
		boolean editing = false;

		//ClickListener
		ClickListener click = new ClickListener();

		//Labels
		JLabel nameLabel = new JLabel("Name *");
		JLabel stuNumLabel = new JLabel("Student Number *");
		JLabel dietLabel = new JLabel("<html>Dietary Restrictions<br/>(Separated with commas)</html>");
		JLabel friendsLabel = new JLabel("<html>Friends<br/>(Separated with commas)</html>");
		JLabel blankLabel = new JLabel("");
		JLabel requiredLabel = new JLabel("* Required", SwingConstants.CENTER);

		//Fields
		JTextField nameField = new JTextField();
		JTextField stuNumField = new JTextField();
		JTextField dietField = new JTextField();
		JTextField friendsField = new JTextField();

		//Buttons
		JButton cancelButton = new JButton("Cancel");
		JButton addButton = new JButton("Add Student");
		JButton deleteButton = new JButton("Delete Student");

		//Create a new student
		AddStudentPanel() {
			this.setLayout(new GridLayout(6,2,60,60));
			//Add to panel
			this.add(nameLabel);
			this.add(nameField);
			this.add(stuNumLabel);
			this.add(stuNumField);
			this.add(dietLabel);
			this.add(dietField);
			this.add(friendsLabel);
			this.add(friendsField);
			this.add(blankLabel);
			this.add(requiredLabel);
			this.add(cancelButton);
			this.add(addButton);

			//Add ActionListeners
			cancelButton.addActionListener(click);
			addButton.addActionListener(click);
		}

		//Edit an existing student
		AddStudentPanel(Student student) {
			editStudent = student;
			editing = true;
			this.setLayout(new GridLayout(6,2,60,60));
			//Edit addButton
			addButton = new JButton("Save Student");
			//Fill panels
			nameField.setText(student.getName());
			stuNumField.setText(student.getStudentNumber());
			String tempDiet = "";
			for (int i = 0; i < (student.getDietaryRestrictions()).size(); i++) {
				if (i != 0) tempDiet += ", ";
				tempDiet += (student.getDietaryRestrictions()).get(i);
			}
			dietField.setText(tempDiet);
			String tempFriends = "";
			for (int i = 0; i < (student.getFriendStudentNumbers()).size(); i++) {
				if (i != 0) tempFriends += ", ";
				tempFriends += (student.getFriendStudentNumbers()).get(i);
			}
			friendsField.setText(tempFriends);
			//Add to panel
			this.add(nameLabel);
			this.add(nameField);
			this.add(stuNumLabel);
			this.add(stuNumField);
			this.add(dietLabel);
			this.add(dietField);
			this.add(friendsLabel);
			this.add(friendsField);
			this.add(deleteButton);
			this.add(requiredLabel);
			this.add(cancelButton);
			this.add(addButton);

			//Add ActionListeners
			deleteButton.addActionListener(click);
			cancelButton.addActionListener(click);
			addButton.addActionListener(click);
		}

		//for when something is clicked
		private class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == addButton) {
					//Begin by assuming input is valid
					boolean valid = true;
					//Get all fields
					String name = nameField.getText().trim();
					String studentNumber = stuNumField.getText().trim();
					ArrayList<String> diet = new ArrayList<String>();
					String[] tempDiet = (dietField.getText()).split(",");
					for (int i = 0; i < tempDiet.length; i++) {
						diet.add(tempDiet[i].trim());
					}
					ArrayList<String> friends = new ArrayList<String>();
					String[] tempFriends = (friendsField.getText()).split(",");
					for (int i = 0; i < tempFriends.length; i++) {
						if (!tempFriends[i].equals("")) {
							friends.add(tempFriends[i].trim());
						}
					}
					//Check for empty name
					if (name.equals("")) {
						valid = false;
						JOptionPane.showMessageDialog(null, "Name field required");
					}
					//Check for empty student number
					if (studentNumber.equals("")) {
						valid = false;
						JOptionPane.showMessageDialog(null,"Student number required");
					}
					//Check if student number is a number
					else if (!isNumber(studentNumber)) {
						valid = false;
						JOptionPane.showMessageDialog(null,"Student number can only consist of numbers");
					}
					//Check if student number already exists
					else if (findStudent(studentNumber) != null) {
						//If not editing a student
						if (editStudent == null) {
							valid = false;
							JOptionPane.showMessageDialog(null, "Student number already in use by another student");
						}
						//If student being edited has changed student number
						else if (!((editStudent.getStudentNumber()).equals(studentNumber))) {
							valid = false;
							JOptionPane.showMessageDialog(null, "Student number already in use by another student");
						}
					}
					//Check if friends exist in the system
					String invalidStudents = "";
					if (friends.size() > 0) {
						for (int i = 0; i < friends.size(); i++) {
							//Only check for names
							String currentTest = friends.get(i);
							if (!isNumber(currentTest)) {
								if ((findStudent(friends.get(i))) == null) {
									valid = false;
									invalidStudents += (friends.get(i) + "\n");
								}
							}
							if (!invalidStudents.equals("")) {
								JOptionPane.showMessageDialog(null, ("The following students are not in the database:\n") + invalidStudents + "Please replace student names with student numbers and try again.");
							}
						}
					}
					if (valid) {
						//Double check for students with same names and convert names to student numbers
						for (int i = 0; i < friends.size(); i++) {
							ArrayList<Student> results = findStudent(friends.get(i));
							if (results == null) {
								//no students found
							}
							//One student found
							else if (results.size() == 1) {
								friends.set(i, results.get(0).getStudentNumber());
							}
							//Multiple students found
							else if (results.size() > 1) {
								String[] numList = new String[results.size()];
								for (int j = 0; j < results.size(); j++) {
									numList[j] = (results.get(j)).getStudentNumber();
								}
								Object selectedStudent = JOptionPane.showInputDialog(null, ("Warning: Multiple students found with name " + friends.get(i) + ", please select student number"), "Select Student", JOptionPane.DEFAULT_OPTION, null, numList, "0");
								friends.set(i, selectedStudent.toString());
							}
						}
						//Add student to arraylist
						Student newStudent = new Student(name, studentNumber, diet, friends);
						if (editing) {
							int index = studentList.indexOf(editStudent);
							studentList.set(index, newStudent);
						}
						else {
							studentList.add(newStudent);
						}
						//saves the file automatically
						saveFile();
						//Close window
						window.remove(addPanel);
						mainPanel.setBackground(Color.CYAN);
						window.add(mainPanel, BorderLayout.CENTER);
						window.repaint();
						window.pack();
						window.setSize(width, height);
						//Reset fields
					}
				}
				if (e.getSource() == deleteButton) {
					//Confirm deletion
					int reply = JOptionPane.showConfirmDialog(null, "Warning: this action cannot be undone. Are you sure you want to delete this student?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
					if (reply == JOptionPane.YES_OPTION) {
						studentList.remove(editStudent);
						//saves the file automatically
						saveFile();
						//Close window
						window.remove(addPanel);
						mainPanel.setBackground(Color.CYAN);
						window.add(mainPanel, BorderLayout.CENTER);
						window.repaint();
						window.pack();
						window.setSize(width, height);
					}
				}
				if (e.getSource() == cancelButton) {
					//Close window
					window.remove(addPanel);
					mainPanel.setBackground(Color.CYAN);
					window.add(mainPanel, BorderLayout.CENTER);
					window.repaint();
					window.pack();
					window.setSize(width, height);
				}
			}
		}

		/*public void paintComponent(Graphics g) {
			super.paintComponent(g); //required
			setDoubleBuffered(true);

		}*/
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

					try {
						//Since there is only one student number associated with each student, get first in array
						Student friend = (findStudent(friendNumber)).get(0);
						if (friend != null) friends += friend.getName() + " (" + friendNumber + ")";
					} catch(Exception e) {
						//Friend number not currently registered in system, add friend number for now
						friends += friendNumber;
					}
				}
				data[row][3] = friends;
			}
			//Create JTable
			JPanel tablePanel = new JPanel(new BorderLayout());
			JTable table = new JTable(data, columnNames);
			table.setRowHeight(50);
			table.setBackground(Color.CYAN);
			table.setEnabled(false);
			//Create JScrollPane
			JScrollPane sp = new JScrollPane(table);
			tablePanel.add(sp);
			//Create buttons
			JPanel buttonPanel = new JPanel(new FlowLayout());

			backButton.setVerticalTextPosition(JButton.CENTER);
			backButton.setHorizontalTextPosition(JButton.CENTER);
			backButton.addActionListener(click);

			editButton.setVerticalTextPosition(JButton.CENTER);
			editButton.setHorizontalTextPosition(JButton.CENTER);
			editButton.addActionListener(click);

			buttonPanel.add(backButton);
			buttonPanel.add(editButton);
			this.add(tablePanel);
			buttonPanel.setBackground(Color.BLUE);
			this.add(buttonPanel);

		}

		//for when something is clicked
		private class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) throws NullPointerException {
				if (e.getSource() == editButton) {
					//Get student to edit
					String searchStudent = JOptionPane.showInputDialog(null, "Enter student number or name:", "Edit Student", JOptionPane.PLAIN_MESSAGE);
					ArrayList<Student> results = findStudent(searchStudent);
					//If no student found
					if (results == null) {
						JOptionPane.showMessageDialog(null,"No student found with that student number or name");
					}
					else {
						Student match = null;
						//One student found
						if (results.size() == 1) {
								match = results.get(0);
						}
						//Multiple students found
						else if (results.size() > 1) {
							String[] numList = new String[results.size()];
							for (int i = 0; i < results.size(); i++) {
								numList[i] = (results.get(i)).getStudentNumber();
							}
							Object selectedStudent = JOptionPane.showInputDialog(null, ("Warning: Multiple students found with name " + searchStudent + ", please select student number"), "Select Student", JOptionPane.DEFAULT_OPTION, null, numList, "0");
							match = (findStudent(selectedStudent.toString())).get(0);
						}
						window.remove(listPanel);
						addPanel = new AddStudentPanel(match);
						addPanel.setBackground(Color.CYAN);
						window.add(addPanel);
						window.repaint();
						window.pack();
						window.setSize(width, height);
					}
				}
				if (e.getSource() == backButton) {
					window.remove(listPanel);
					mainPanel.setBackground(Color.CYAN);
					window.add(mainPanel, BorderLayout.CENTER);
					window.repaint();
					window.pack();
					window.setSize(width, height);
				}
			}
		}

		/*public void paintComponent(Graphics g) {
			super.paintComponent(g); //required
			setDoubleBuffered(true);

		}*/
	}
}
