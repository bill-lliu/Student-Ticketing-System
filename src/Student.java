/* February 11, 2019
 * Bill Liu and Victor Lin
 * Student.java
 */

import java.util.ArrayList;

/**
 * Student
 * This object represents a single student.
 *
 * @author Bill Liu
 * @author Victor Lin
 */
public class Student{
	
	//class variables
	private String name;
	private String studentNumber;
	private ArrayList<String> dietaryRestrictions;
	private ArrayList<String> friendStudentNumbers;

	/**
	 * Student
	 * This constructor creates a new student.
	 * @param name String containing student name.
	 * @param studentNumber String containing student number.
	 * @param dietaryRestrictions ArrayList of type String containing dietary restrictions.
	 * @param friendStudentNumbers ArrayList of type String containing the student's friends.
	 */
	Student(String name, String studentNumber, ArrayList<String> dietaryRestrictions, ArrayList<String> friendStudentNumbers){
	this.name = name;
	this.studentNumber = studentNumber;
	this.dietaryRestrictions = dietaryRestrictions;
	this.friendStudentNumbers = friendStudentNumbers;
	}

	/**
	 * getName
	 * This method returns the name of the student.
	 * @return name String containing student name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * setName
	 * This method sets the name of the student.
	 * @param name String to change student name to.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * getStudentNumber
	 * This method returns the student number of the student.
	 * @return studentNumber String containing student number.
	 */
	public String getStudentNumber() {
		return this.studentNumber;
	}

	/**
	 * setStudentNumber
	 * This method sets the student number of the student.
	 * @param studentNumber String to change student number to.
	 */
	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}

	/**
	 * getDietaryRestrictions
	 * This method returns the dietary restrictions of the student.
	 * @return dietaryRestrictions ArrayList of type String containing dietary restrictions.
	 */
	public ArrayList<String> getDietaryRestrictions() {
		return this.dietaryRestrictions;
	}

	/**
	 * setDietaryRestrictions
	 * This method sets the dietary restrictions of the student.
	 * @param dietaryRestrictions ArrayList of type String to change dietary restrictions to.
	 */
	public void setDietaryRestrictions(ArrayList<String> dietaryRestrictions) {
		this.dietaryRestrictions = dietaryRestrictions;
	}

	/**
	 * getFriendStudentNumbers
	 * This method returns the friends of the student, formatted as student numbers.
	 * @return friendStudentNumbers ArrayList of type String containing the student's friends.
	 */
	public ArrayList<String> getFriendStudentNumbers() {
		return this.friendStudentNumbers;
	}

	/**
	 * setFriendStudentNumbers
	 * This method sets the friends of the student, formatted as student numbers.
	 * @param friendStudentNumbers ArrayList of type String to change the student's friends to.
	 */
	public void setFriendStudentNumbers(ArrayList<String> friendStudentNumbers) {
		this.friendStudentNumbers = friendStudentNumbers;
	}
}
