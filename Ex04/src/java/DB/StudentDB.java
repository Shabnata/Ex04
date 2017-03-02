package DB;

import Model.*;
import Util.DButil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentDB {

	private Connection cn;

	public StudentDB() {
		cn = DButil.getConnection();
	}

	public void closeConnection(){
		if(this.cn != null){
			try {
				this.cn.close();
			} catch(SQLException e){
				Logger.getLogger(StudentDB.class.getName()).log(Level.SEVERE, null, e);
			}
		}
	}

	public Student getStudent(String st_id) {
		

		Student st = null;
		//PreparedStatement ps = null;
		Statement ps = null;
		try {
			//ps = this.cn.prepareStatement("SELECT * FROM students WHERE st_id = ?");
			//ps.setString(1, st_id);
			ps = cn.createStatement();

			ResultSet rs = ps.executeQuery("SELECT * FROM students WHERE st_id=\'" + st_id + "\'");
			while (rs.next()) {
				st = new Student();
				st.setStudentID(rs.getString("st_id"));
				st.setFirstName(rs.getString("f_name"));
				st.setLastName(rs.getString("l_name"));
				st.setEmailAddress(rs.getString("email"));
				st.setCurrentFines(Double.parseDouble(rs.getString("fine")));
			}
		} catch (SQLException e) {
			// TODO
			// Write an error
		}
		return st;
	}

	public Boolean addStudent(String stID, String fName, String lName, String email, float fine) {
		Boolean added = false;


		Student st = null;
		//PreparedStatement ps = null;
		Statement ps = null;
		try {
			PreparedStatement pst = cn.prepareStatement("insert into students (st_id, f_name, l_name, email, fine) values (?,?,?,?,?)");
			pst.setString(1, stID);
			pst.setString(2, fName);
			pst.setString(3, lName);
			pst.setString(4, email);
			pst.setFloat(5, fine);
			int nor = pst.executeUpdate();
			if (nor != 0) {
				added = true;
			}
		} catch (SQLException e) {
			// TODO
			// Write an error
		}
		return added;
	}

	public Boolean updateStudent(Student stud)  {
		Boolean updated = false;
		

		Student st = stud;
		Statement ps = null;

		try {
			PreparedStatement pst = cn.prepareStatement("UPDATE students SET fine=? WHERE st_id=?");
			pst.setDouble(1, st.getCurrentFines());
			pst.setString(2, st.getStudentID());
			updated = pst.execute();
		} catch (SQLException e) {
			// TODO
			// Write an error
		}
		return updated;
	}

	public int getCountLoanedBooks(String st_id)  {
		int cnt = 0;
		String s = "";
	

		Student st = null;
		//PreparedStatement ps = null;
		Statement ps = null;

		try {
			//ps = cn.createStatement();
			PreparedStatement pst = cn.prepareStatement("select count(*) as num from students , loans, loaned_books "
					+ "where students.st_id=? "
					+ "and students.st_id = loans.st_id "
					+ "and loans.loan_id=loaned_books.loan_id "
					+ "and loaned_books.returned=? "
					+ "group by students.st_id");

			pst.setString(1, st_id);
			pst.setString(2, "false");

			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				cnt = rs.getInt("num");
			}
		} catch (SQLException e) {
			s = e.getMessage();
		}

		return cnt;
	}

	//get Array of books that this Student has in loans
	public ArrayList<BookCopy> getBooksInLoans(String st_id)  {


		ArrayList<BookCopy> hasBooks = new ArrayList<BookCopy>();
		Statement ps = null;
		try {

			ps = cn.createStatement();

			PreparedStatement pst = cn.prepareStatement("SELECT book_copies.copy_cond, "
					+ "       loaned_books.copy_code, "
					+ "       loaned_books.loan_id, "
					+ "       loaned_books.returned, "
					+ "       loaned_books.days_over, "
					+ "       loans.start_d, "
					+ "       loans.ret_d "
					+ "FROM   book_copies, "
					+ "       students, "
					+ "       loans, "
					+ "       loaned_books "
					+ "WHERE  book_copies.copy_code = loaned_books.copy_code "
					+ "       AND students.st_id = ? "
					+ "       AND students.st_id = loans.st_id "
					+ "       AND loans.loan_id = loaned_books.loan_id "
					+ "       AND loaned_books.returned = 'false'  ");
			pst.setString(1, st_id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				BookCopy bcC = new BookCopy();
				bcC.setCOPY_CODE(rs.getString("copy_code"));
				hasBooks.add(bcC);
			}
			
		} catch (SQLException e) {
			// TODO
			// Write an error
		}
		return hasBooks;
	}

	//get Array of Loans per this student
	public ArrayList<Loan> getLoans(String st_id){
		
		ArrayList<Integer> loanIds = new ArrayList<Integer>();
		ArrayList<Loan> loans = new ArrayList<Loan>();
		Statement ps = null;
		int loanID;
		try {

			ps = cn.createStatement();

			PreparedStatement pst = cn.prepareStatement("select loan_id from loans where st_id=?");
			pst.setString(1, st_id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				loanID = rs.getInt("loan_id");
				loanIds.add(loanID);
			}

		} catch (SQLException e) {
			// TODO
			// Write an error
		}
		LoanDB tmpLoanDB = new LoanDB();
		Loan tmpLoan = new Loan();
		for (Integer lnId : loanIds) {
			tmpLoan = tmpLoanDB.getLoanByID((int) lnId);
			loans.add(tmpLoan);
		}
		
		return loans;
	}

}
