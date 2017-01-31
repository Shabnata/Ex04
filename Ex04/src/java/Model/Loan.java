package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Loan implements Serializable{

	private int loanID;
	private Student loaningStudent;
	private GregorianCalendar loanedDate;
	private GregorianCalendar returnByDate;
	private ArrayList<BookCopy> booksInLoan;
	private ArrayList<BookCopy> booksReturned;

	public Loan(){
	}

	public int getLoanID(){
		return loanID;
	}

	public void setLoanID(int loanID){
		this.loanID = loanID;
	}

	public Student getLoaningStudent(){
		return loaningStudent;
	}

	public void setLoaningStudent(Student loaningStudent){
		this.loaningStudent = loaningStudent;
	}

	public GregorianCalendar getLoanedDate(){
		return loanedDate;
	}

	public void setLoanedDate(GregorianCalendar loanedDate){
		this.loanedDate = loanedDate;
	}

	public GregorianCalendar getReturnByDate(){
		return returnByDate;
	}

	public void setReturnByDate(GregorianCalendar returnByDate){
		this.returnByDate = returnByDate;
	}

	public ArrayList<BookCopy> getBooksInLoan(){
		return booksInLoan;
	}

	public void setBooksInLoan(ArrayList<BookCopy> booksInLoan){
		this.booksInLoan = booksInLoan;
	}

	public ArrayList<BookCopy> getBooksReturned(){
		return booksReturned;
	}

	public void setBooksReturned(ArrayList<BookCopy> booksReturned){
		this.booksReturned = booksReturned;
	}

}
