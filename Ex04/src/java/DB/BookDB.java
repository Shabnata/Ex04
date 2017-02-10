
package DB;

import Model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookDB{

	private Connection cn;

	//Added by Natalie
	public BookDB(Connection cn){
		this.cn = cn;
	}

	public Book getBookByISBN(String isbn){
		String bookQuery = ""
			+ "SELECT books.isbn, "
			+ "       books.title, "
			+ "       books.author, "
			+ "       books.p_year, "
			+ "       books.cover, "
			+ "       books.copy_cnt, "
			+ "       books.book_category, "
			+ "       categories.id                AS cat_id, "
			+ "       Count(book_copies.copy_code) AS usable_copy_count "
			+ "FROM   books "
			+ "       JOIN categories "
			+ "         ON books.book_category = categories.cat_name "
			+ "       LEFT JOIN book_copies "
			+ "         ON books.isbn = book_copies.isbn "
			+ "            AND book_copies.copy_cond != 5 "
			+ "            AND book_copies.copy_code NOT IN (SELECT copy_code "
			+ "                                              FROM   loaned_books "
			+ "                                              WHERE  returned = false) "
			+ "       --LEFT JOIN loaned_books "
			+ "              --ON book_copies.copy_code = loaned_books.copy_code "
			+ "                --AND loaned_books.returned = false "
			+ "WHERE  books.isbn = ? "
			+ "GROUP  BY books.isbn, "
			+ "          books.title, "
			+ "          books.author, "
			+ "          books.p_year, "
			+ "          books.cover, "
			+ "          books.copy_cnt, "
			+ "          books.book_category, "
			+ "          categories.id";
		Book bk = null;
		try {
			PreparedStatement ps = this.cn.prepareStatement(bookQuery);
			ps.setString(1, isbn);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				bk = new Book();
				bk.setISBN(rs.getString("isbn"));
				bk.setTitle(rs.getString("title"));
				bk.setAuthorName(rs.getString("author"));

				Category bk_cat = new Category();
				bk_cat.setCatID(rs.getInt("cat_id"));
				bk_cat.setCatName(rs.getString("book_category"));
				bk.setCategory(bk_cat);

				bk.setBookYear(Year.parse(rs.getString("p_year"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				bk.setCoverPath(rs.getString("cover"));
				bk.setCopyCounter(rs.getInt("copy_cnt"));
				bk.setAvailableCopies(rs.getInt("usable_copy_count"));
			}
		} catch(SQLException e){
			Logger.getLogger(BookDB.class.getName()).log(Level.SEVERE, null, e);
		}

		return bk;
	}

	public ArrayList<Book> getBooksByTitle(String title){
		String bookQuery = ""
			+ "SELECT books.isbn, "
			+ "       books.title, "
			+ "       books.author, "
			+ "       books.p_year, "
			+ "       books.cover, "
			+ "       books.copy_cnt, "
			+ "       books.book_category, "
			+ "       categories.id                AS cat_id, "
			+ "       Count(book_copies.copy_code) AS usable_copy_count "
			+ "FROM   books "
			+ "       JOIN categories "
			+ "         ON books.book_category = categories.cat_name "
			+ "       LEFT JOIN book_copies "
			+ "         ON books.isbn = book_copies.isbn "
			+ "            AND book_copies.copy_cond != 5 "
			+ "            AND book_copies.copy_code NOT IN (SELECT copy_code "
			+ "                                              FROM   loaned_books "
			+ "                                              WHERE  returned = false) "
			+ "       --LEFT JOIN loaned_books "
			+ "              --ON book_copies.copy_code = loaned_books.copy_code "
			+ "                 --AND loaned_books.returned = false "
			+ "WHERE  Lower(books.title) LIKE ? "
			+ "GROUP  BY books.isbn, "
			+ "          books.title, "
			+ "          books.author, "
			+ "          books.p_year, "
			+ "          books.cover, "
			+ "          books.copy_cnt, "
			+ "          books.book_category, "
			+ "          categories.id";
		ArrayList<Book> bksLst = new ArrayList<>();

		try {
			Book bk;
			Category bk_cat;
			PreparedStatement ps = this.cn.prepareStatement(bookQuery);
			ps.setString(1, "%" + title.toLowerCase() + "%");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				bk = new Book();
				bk.setISBN(rs.getString("isbn"));
				bk.setTitle(rs.getString("title"));
				bk.setAuthorName(rs.getString("author"));

				bk_cat = new Category();
				bk_cat.setCatID(rs.getInt("cat_id"));
				bk_cat.setCatName(rs.getString("book_category"));
				bk.setCategory(bk_cat);

				bk.setBookYear(Year.parse(rs.getString("p_year"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				bk.setCoverPath(rs.getString("cover"));
				bk.setCopyCounter(rs.getInt("copy_cnt"));
				bk.setAvailableCopies(rs.getInt("usable_copy_count"));

				bksLst.add(bk);
			}
		} catch(SQLException e){
			Logger.getLogger(BookDB.class.getName()).log(Level.SEVERE, null, e);
		}

		return bksLst;
	}

	public boolean addBook(String isbn, String title, String author, Category cat, Year year, String cover, int numOfCopies){

		PreparedStatement ps;
		boolean failed = false;
		try {
			ps = cn.prepareStatement(""
				+ "INSERT INTO books "
				+ "            (isbn, "
				+ "             title, "
				+ "             author, "
				+ "             book_category, "
				+ "             p_year, "
				+ "             cover, "
				+ "             copy_cnt) "
				+ "VALUES     (?, "
				+ "            ?, "
				+ "            ?, "
				+ "            ?, "
				+ "            ?, "
				+ "            ?, "
				+ "            ?)");
			ps.setString(1, isbn);
			ps.setString(2, title);
			ps.setString(3, author);
			ps.setString(4, cat.getCatName());
			ps.setString(5, Integer.toString(year.getValue()) + "-01-01");
			ps.setString(6, cover);
			ps.setInt(7, numOfCopies);

			if(ps.executeUpdate() != 0){
				PreparedStatement psc = cn.prepareStatement(""
					+ "INSERT INTO book_copies "
					+ "            (isbn, "
					+ "             copy_code, "
					+ "             copy_cond) "
					+ "VALUES     (?, "
					+ "            ?, "
					+ "            1)");
				psc.setString(1, isbn);
				for(int i = 1; i <= numOfCopies && !failed; i++){
					psc.setString(2, isbn + "_" + String.format("%03d", i));
					if(psc.executeUpdate() == 0){
						failed = true;
					}
				}

			} else {
				failed = true;
			}

		} catch(SQLException e){
			Logger.getLogger(BookDB.class.getName()).log(Level.SEVERE, null, e);
			return false;
		}
		return !failed;
	}

	public boolean deleteBookByISBN(String isbn){
		String getLoanedCopiesQuery = ""
			+ "SELECT books.isbn, "
			+ "       book_copies.copy_code, "
			+ "       loaned_books.loan_id "
			+ "FROM   books "
			+ "       LEFT JOIN book_copies "
			+ "              ON books.isbn = book_copies.isbn "
			+ "       LEFT JOIN loaned_books "
			+ "              ON book_copies.copy_code = loaned_books.copy_code "
			+ "                 AND loaned_books.returned = false "
			+ "WHERE  books.isbn = ?";
		/*
		 * Returns a table of book_isbn X copy_code X loan_id
		 * Where copy_code is a copy of the book with isbn = book_isbn
		 * And loan_id is for a loan where copy_code exists and is not returned
		 * LEFT JOIN in both joins ensures that even if the book has no copies,
		 * there will be at least one row with book_isbn and copy_code = null IF
		 * book_isbn exists
		 * And if copy_code has no loans, there will be at least one row with
		 * copy_code and loan_id = null
		 *
		 * If the result table has no rows, the book doesn't exist
		 * If one of the rows has loan_id != null, then at least one copy is in
		 * a loan
		 */

		String deleteBookQuery = ""
			+ "DELETE FROM books "
			+ "WHERE  isbn = ?";

		try {
			PreparedStatement checkLoanedCopiesPS = this.cn.prepareStatement(getLoanedCopiesQuery);
			checkLoanedCopiesPS.setString(1, isbn);
			ResultSet clcRS = checkLoanedCopiesPS.executeQuery();
			if(!clcRS.next()){ // Result has no rows, the book doesn't exists
				return false;
			} else {

				do {
					// TODO Make sure the comparison is correct for requesting null objects
					if(clcRS.getString("loan_id") != null){ // At least one copy is being loaned right now
						return false;
					}
				} while(clcRS.next());

				PreparedStatement deleteBookPS = this.cn.prepareStatement(deleteBookQuery);
				deleteBookPS.setString(1, isbn);
				/*
				 * Deleting this book will cascade down and delete all entries
				 * in
				 * book_copies and loaned_books with the corrosponding book_isbn
				 * .
				 */
				return (deleteBookPS.executeUpdate() == 1);
			}
		} catch(SQLException e){
			Logger.getLogger(BookDB.class.getName()).log(Level.SEVERE, null, e);
		}
		return false;
	}

	//Natalie: get book by bookCopy
	public Book getBookByBookCopy(BookCopy bc){
		Book tmpBook = new Book();
		String copyCode = bc.getCOPY_CODE();
		String[] parts = copyCode.split("_");
		String isbn = parts[0];
		tmpBook = this.getBookByISBN(isbn);
		return tmpBook;
	}

}
