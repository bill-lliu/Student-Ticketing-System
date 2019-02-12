//Student Object
import java.util.ArrayList;
public class Student{
	
	//class variables
	private String name;
	private String studentNumber;
	private ArrayList<String> dietaryRestrictions;
	private ArrayList<String> friendStudentNumbers;
	
	//class constructor
	Student(String name, String studentNumber, ArrayList<String> dietaryRestrictions, ArrayList<String> friendStudentNumbers){
	this.name = name;
	this.studentNumber = studentNumber;
	this.dietaryRestrictions = dietaryRestrictions;
	this.friendStudentNumbers = friendStudentNumbers;
	}
	
	//getters and setters for the object
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStudentNumber() {
		return this.studentNumber;
	}
	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}
	public ArrayList<String> getDietaryRestrictions() {
		return this.dietaryRestrictions;
	}
	public void setDietaryRestrictions(ArrayList<String> dietaryRestrictions) {
		this.dietaryRestrictions = dietaryRestrictions;
	}
	public ArrayList<String> getFriendStudentNumbers() {
		return this.friendStudentNumbers;
	}
	public void setFriendStudentNumbers(ArrayList<String> friendStudentNumbers) {
		this.friendStudentNumbers = friendStudentNumbers;
	}
}
