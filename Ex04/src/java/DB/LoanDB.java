package DB;

import Model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Denis Sh
 */
public class LoanDB{

	/*
		*
		*
		* TODO
		* Remember to test this Class
		*
		*
		*
	 */
	private Connection cn;

	public LoanDB(Connection cn){
		this.cn = cn;
	}

	public Loan getLoanByID(int loan_id){
		Loan ln = null;

		try{
			BookCopy bc;
			BookCondition bcCond;
			PreparedStatement ps;

			ps = this.cn.prepareStatement(""
				+ "SELECT tblB.loan_id, "
				+ "       tblB.st_id, "
				+ "       students.f_name, "
				+ "       students.l_name, "
				+ "       students.email, "
				+ "       students.fine,"
				+ "       tblB.start_d, "
				+ "       tblB.ret_d, "
				+ "       tblB.copy_code, "
				+ "       tblB.returned, "
				+ "       tblB.days_over, "
				+ "       tblB.isbn, "
				+ "       tblB.copy_cond, "
				+ "       conditions.con_key, "
				+ "       conditions.con_desc "
				+ "FROM   (SELECT tblA.loan_id, "
				+ "               tblA.st_id, "
				+ "               tblA.start_d, "
				+ "               tblA.ret_d, "
				+ "               tblA.copy_code, "
				+ "               tblA.returned, "
				+ "               tblA.days_over, "
				+ "               book_copies.isbn, "
				+ "               book_copies.copy_cond "
				+ "        FROM   (SELECT loans.loan_id, "
				+ "                       loans.st_id, "
				+ "                       loans.start_d, "
				+ "                       loans.ret_d, "
				+ "                       loaned_books.copy_code, "
				+ "                       loaned_books.returned, "
				+ "                       loaned_books.days_over "
				+ "                FROM   loans "
				+ "                       JOIN loaned_books "
				+ "                         ON loans.loan_id = loaned_books.loan_id "
				+ "                            AND loans.loan_id = ?) AS tblA "
				+ "               JOIN book_copies "
				+ "                 ON tblA.copy_code = book_copies.copy_code) AS tblB "
				+ "       JOIN conditions "
				+ "         ON tblB.copy_cond = conditions.con_key "
				+ "		  JOIN students "
				+ "         ON tblB.st_id = students.st_id");
			ps.setInt(1, loan_id);
			ResultSet rs = ps.executeQuery();
			if(rs.first()){
				ln = new Loan();
				ln.setLoanID(rs.getInt("loan_id"));

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				//sdf.setLenient(true); This does what?
				GregorianCalendar loanedDate = new GregorianCalendar();
				GregorianCalendar returnByDate = new GregorianCalendar();
				Date lDate = sdf.parse(rs.getString("start_d"));
				Date rDate = sdf.parse(rs.getString("ret_d"));
				loanedDate.setTime(lDate);
				returnByDate.setTime(rDate);
				ln.setLoanedDate(loanedDate);
				ln.setReturnByDate(returnByDate);

				Student st = new Student();
				st.setStudentID(rs.getString("st_id"));
				st.setFirstName(rs.getString("f_name"));
				st.setLastName(rs.getString("l_name"));
				st.setEmailAddress(rs.getString("email"));
				st.setCurrentFines(rs.getDouble("fine"));
				ln.setLoaningStudent(st);

				ArrayList<BookCopy> booksInLoan = new ArrayList<>();
				ArrayList<BookCopy> booksReturned = new ArrayList<>();

				do {
					bc = new BookCopy();
					bc.setCOPY_CODE(rs.getString("copy_code"));
					bcCond = new BookCondition();
					bcCond.setConKey(rs.getInt("con_key"));
					bcCond.setConDesc(rs.getString("con_desc"));
					bc.setCopyCondition(bcCond);
					if(rs.getBoolean("returned")){
						booksReturned.add(bc);
					} else {
						booksInLoan.add(bc);
					}
				} while(rs.next());

				ln.setBooksInLoan(booksInLoan);
				ln.setBooksReturned(booksReturned);
			}
		} catch(SQLException | ParseException e){
			// TODO
			// Write an error
			// Make sure the parsing works correctly and then combine the catches.
			System.err.println("*\n*\n*\n" + e.getMessage() + "\n*\n*\n*");
		}
		return ln;
	}

	public Loan getNewLoan(String st_id, GregorianCalendar start_d, GregorianCalendar ret_d){
		return null;
	}

	public boolean addBookCopyToLoan(int loan_id, BookCopy bc){
		return false;
	}

}
