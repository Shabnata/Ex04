
package Model;

public class Student{

	private String studentID;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private double currentFines;

	public Student(){
	}

	public String getFirstName(){
		return firstName;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getStudentID(){
		return studentID;
	}

	public void setStudentID(String studentID){
		this.studentID = studentID;
	}

	public String getEmailAddress(){
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress){
		this.emailAddress = emailAddress;
	}

	public double getCurrentFines(){
		return currentFines;
	}

	public void setCurrentFines(double currentFines){
		this.currentFines = currentFines;
	}

}
