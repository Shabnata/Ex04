
package DB;

import Model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class BookDB{

	private Connection cn;

	public BookDB(Connection cn){
		this.cn = cn;
	}

	public Book getBookByISBN(String isbn){

		Book bk = null;
		PreparedStatement ps;
		Category bk_cat;
		PreparedStatement bkCatPS;
		PreparedStatement copyCntPS;

		try {
			ps = this.cn.prepareStatement("SELECT * FROM books WHERE LOWER(isbn)=?");
			ps.setString(1, isbn.toLowerCase());

			bkCatPS = this.cn.prepareStatement("SELECT * FROM categories WHERE cat_name=?");
			copyCntPS = this.cn.prepareStatement("SELECT COUNT(*) AS copy_count FROM book_copies WHERE book_copies.isbn=? AND copy_code NOT IN("
				+ "SELECT bc.copy_code FROM "
				+ "(SELECT copy_code FROM book_copies WHERE book_copies.isbn=?) AS bc "
				+ "JOIN loaned_books "
				+ "ON "
				+ "bc.copy_code=loaned_books.copy_code WHERE loaned_books.returned=false "
				+ ")");

			ResultSet rs = ps.executeQuery();
			ResultSet bkCatRS;
			ResultSet copyCntRS;
			while(rs.next()){
				bk = new Book();
				bk.setISBN(rs.getString("isbn"));
				bk.setTitle(rs.getString("title"));
				bk.setAuthorName(rs.getString("author"));

				bkCatPS.setString(1, rs.getString("book_category"));
				bkCatRS = bkCatPS.executeQuery();
				while(bkCatRS.next()){
					bk_cat = new Category();
					bk_cat.setCatID(bkCatRS.getInt("id"));
					bk_cat.setCatName(bkCatRS.getString("cat_name"));
					bk.setCategory(bk_cat);
				}

				bk.setBookYear(Year.parse(rs.getString("p_year"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				bk.setCoverPath(rs.getString("cover"));
				bk.setCopyCounter(Integer.parseInt(rs.getString("copy_cnt")));

				copyCntPS.setString(1, bk.getISBN());
				copyCntPS.setString(2, bk.getISBN());
				copyCntRS = copyCntPS.executeQuery();
				while(copyCntRS.next()){
					bk.setAvailableCopies(copyCntRS.getInt("copy_count"));
				}

			}
		} catch(SQLException e){
			// TODO
			// Write an error
			System.err.println("*\n*\n*\n" + e.getMessage() + "\n*\n*\n*");
		}
		return bk;
	}

	public ArrayList<Book> getBooksByTitle(String title){

		ArrayList<Book> bksLst = new ArrayList<>();
		Book bk;
		Category bk_cat;
		PreparedStatement ps;
		PreparedStatement bkCatPS;
		PreparedStatement copyCntPS;

		try {
			ps = this.cn.prepareStatement("SELECT * FROM books WHERE LOWER(title) LIKE ?");
			ps.setString(1, "%" + title.toLowerCase() + "%");

			bkCatPS = this.cn.prepareStatement("SELECT * FROM categories WHERE cat_name=?");
			copyCntPS = this.cn.prepareStatement("SELECT COUNT(*) AS copy_count FROM book_copies WHERE book_copies.isbn=? AND copy_code NOT IN("
				+ "SELECT bc.copy_code FROM "
				+ "(SELECT copy_code FROM book_copies WHERE book_copies.isbn=?) AS bc "
				+ "JOIN loaned_books "
				+ "ON "
				+ "bc.copy_code=loaned_books.copy_code WHERE loaned_books.returned=false "
				+ ")");

			ResultSet rs = ps.executeQuery();
			ResultSet bkCatRS;
			ResultSet copyCntRS;
			while(rs.next()){
				bk = new Book();
				bk.setISBN(rs.getString("isbn"));
				bk.setTitle(rs.getString("title"));
				bk.setAuthorName(rs.getString("author"));

				bkCatPS.setString(1, rs.getString("book_category"));
				bkCatRS = bkCatPS.executeQuery();
				while(bkCatRS.next()){
					bk_cat = new Category();
					bk_cat.setCatID(bkCatRS.getInt("id"));
					bk_cat.setCatName(bkCatRS.getString("cat_name"));
					bk.setCategory(bk_cat);
				}

				bk.setBookYear(Year.parse(rs.getString("p_year"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				bk.setCoverPath(rs.getString("cover"));

				copyCntPS.setString(1, bk.getISBN());
				copyCntPS.setString(2, bk.getISBN());
				copyCntRS = copyCntPS.executeQuery();
				while(copyCntRS.next()){
					bk.setAvailableCopies(copyCntRS.getInt("copy_count"));
				}

				bksLst.add(bk);
			}
		} catch(SQLException e){
			// TODO
			// Write an error
			System.err.println("*\n*\n*\n" + e.getMessage() + "\n*\n*\n*");
		}
		return bksLst;
	}

}
