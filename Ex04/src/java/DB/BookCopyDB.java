package DB;

import Model.BookCondition;
import Model.BookCopy;
import Model.Category;
import Model.Student;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Denis Sh
 */
public class BookCopyDB{

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

	public BookCopyDB(Connection cn){
		this.cn = cn;
	}

	// Returns null if book not found, empty list if no copies
	public ArrayList<BookCopy> getAllCopiesOfBook(String book_isbn){
		ArrayList<BookCopy> bcLst = null;
		String copiesQuery = ""
			+ "SELECT book_copies.copy_code, "
			+ "       conditions.con_key, "
			+ "       conditions.con_desc "
			+ "FROM   books "
			+ "       JOIN book_copies "
			+ "         ON books.isbn = book_copies.isbn "
			+ "       JOIN conditions "
			+ "         ON book_copies.copy_cond = conditions.con_key "
			+ "WHERE  books.isbn = ?";

		try{
			PreparedStatement ps = this.cn.prepareStatement(copiesQuery);
			ps.setString(1, book_isbn);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				bcLst = new ArrayList<>();
				BookCopy bc;
				BookCondition bcC;

				do {
					bcC = new BookCondition();
					bcC.setConDesc(rs.getString("con_desc"));
					bcC.setConKey(rs.getInt("con_key"));

					bc = new BookCopy();
					bc.setCOPY_CODE(rs.getString("copy_code"));
					bc.setCopyCondition(bcC);

					bcLst.add(bc);
				} while(rs.next());
			}
		} catch(SQLException e){
			// TODO
			// Write an error
			System.err.println("*\n*\n*\n" + e.getMessage() + "\n*\n*\n*");
		}
		return bcLst;
	}

	// Returns null if book not found, empty list if no usable copies
	public ArrayList<BookCopy> getUsableCopiesOfBook(String book_isbn){
		ArrayList<BookCopy> bcLst = null;
		String copiesQuery = ""
			+ "SELECT book_copies.copy_code, "
			+ "       conditions.con_key, "
			+ "       conditions.con_desc "
			+ "FROM   books "
			+ "       JOIN book_copies "
			+ "         ON books.isbn = book_copies.isbn "
			+ "       LEFT JOIN loaned_books "
			+ "              ON book_copies.copy_code = loaned_books.copy_code "
			+ "                 AND loaned_books.returned = true "
			+ "       JOIN conditions "
			+ "         ON book_copies.copy_cond = conditions.con_key "
			+ "WHERE  books.isbn = ?";

		try{
			PreparedStatement ps = this.cn.prepareStatement(copiesQuery);
			ps.setString(1, book_isbn);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				bcLst = new ArrayList<>();
				BookCopy bc;
				BookCondition bcC;

				do {
					bcC = new BookCondition();
					bcC.setConDesc(rs.getString("con_desc"));
					bcC.setConKey(rs.getInt("con_key"));

					bc = new BookCopy();
					bc.setCOPY_CODE(rs.getString("copy_code"));
					bc.setCopyCondition(bcC);

					bcLst.add(bc);
				} while(rs.next());
			}
		} catch(SQLException e){
			// TODO
			// Write an error
			System.err.println("*\n*\n*\n" + e.getMessage() + "\n*\n*\n*");
		}
		return bcLst;
	}
	public Boolean updateBookCopyCondition(BookCopy bookCopy){
	Boolean updated = false;
	
		BookCopy bc = bookCopy;
		Statement ps = null;

		try {
			PreparedStatement pst = cn.prepareStatement("UPDATE book_copies SET copy_cond=? WHERE copy_code=?");
			pst.setDouble(1, bc.getCopyCondition().getConKey());
			pst.setString(2, bc.getCOPY_CODE());
			updated = pst.execute();
			//cn.close();
		} catch (SQLException e) {
			// TODO
			// Write an error
		}
		
	return updated;
	}
	public BookCopy getBookCopyByCopyCode(String copyCode) throws ClassNotFoundException{
	BookCopy bc = null;
		String copiesQuery = "select * from book_copies where copy_code=?";
			

		try{
			PreparedStatement ps = this.cn.prepareStatement(copiesQuery);
			ps.setString(1, copyCode);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				bc = new BookCopy();
				
				do {
					bc.setCOPY_CODE(copyCode);
					ConditionDB conDB = new ConditionDB();
					BookCondition bCon = new BookCondition();
					bCon=conDB.getCondition(rs.getInt("copy_cond"));
					bc.setCopyCondition(bCon);
				
				} while(rs.next());
			}
		} catch(SQLException e){
			// TODO
			// Write an error
			System.err.println("*\n*\n*\n" + e.getMessage() + "\n*\n*\n*");
		}
		return bc;
	}
	
	//TODO! still doesnt work
	public Boolean updateBookCopyLoanState(String copyCode){
		Boolean updated = false;
	
	
		Statement ps = null;

		try {
			PreparedStatement pst = this.cn.prepareStatement("UPDATE loaned_books SET returned=? WHERE copy_code=? and returned=?");
			pst.setString(1, "true");
			pst.setString(2, copyCode);
			pst.setString(1, "false");
			updated = pst.execute();
			cn.close();
		} catch (SQLException e) {
			// TODO
			// Write an error
		}
		
	return updated;
		
		
		
	}
}
