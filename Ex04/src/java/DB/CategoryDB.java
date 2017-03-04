package DB;

import Model.*;
import Util.DButil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoryDB {

	private Connection cn;

	public CategoryDB() {
		cn = DButil.getConnection();
	}

	public void closeConnection() {
		if (this.cn != null) {
			try {
				this.cn.close();
			} catch (SQLException e) {
				Logger.getLogger(CategoryDB.class.getName()).log(Level.SEVERE, null, e);
			}
		}

	}

	public Category getCategory(String catName) {

		Category ct = null;
		//PreparedStatement ps = null;
		Statement ps = null;
		try {
			//ps = this.cn.prepareStatement("SELECT * FROM students WHERE st_id = ?");
			//ps.setString(1, st_id);
			ps = cn.createStatement();

			ResultSet rs = ps.executeQuery("SELECT * FROM categories WHERE cat_name=\'" + catName + "\'");
			while (rs.next()) {
				ct = new Category();
				ct.setCatID(rs.getInt("id"));
				ct.setCatName(rs.getString("cat_name"));
			}
		} catch (SQLException e) {
			Logger.getLogger(CategoryDB.class.getName()).log(Level.SEVERE, null, e);
		}
		return ct;
	}

	public ArrayList<Category> getCategories() {

		ArrayList<Category> cat = new ArrayList<Category>();
		Statement ps = null;
		try {

			ps = cn.createStatement();

			ResultSet rs = ps.executeQuery("select * from Categories");

			while (rs.next()) {
				Category c = new Category();
				c.setCatID(rs.getInt(1));
				c.setCatName(rs.getString(2));
				cat.add(c);
			}
		} catch (SQLException e) {
			Logger.getLogger(CategoryDB.class.getName()).log(Level.SEVERE, null, e);
		}
		return cat;
	}

	public Boolean addCategory(String catName) {
		Boolean added = false;

		Category cat = null;
		//PreparedStatement ps = null;
		Statement ps = null;
		try {
			PreparedStatement pst = cn.prepareStatement("insert into categories (cat_name) values (?)");
			pst.setString(1, catName);

			int nor = pst.executeUpdate();
			if (nor != 0) {
				added = true;
			}
		} catch (SQLException e) {
			Logger.getLogger(CategoryDB.class.getName()).log(Level.SEVERE, null, e);
		}
		return added;
	}

	public ArrayList<Book> getBooksByCategoryName(String catName) {

		String booksQuery = ""
							+ "SELECT books.isbn, "
							+ "       books.title, "
							+ "       books.author, "
							+ "       books.p_year, "
							+ "       books.cover, "
							+ "       books.copy_cnt, "
							+ "       Count(book_copies.copy_code) AS usable_copy_count "
							+ "FROM   books "
							+ "       LEFT JOIN book_copies "
							+ "              ON books.isbn = book_copies.isbn "
							+ "                 AND book_copies.copy_cond != 5 "
							+ "                 AND book_copies.copy_code NOT IN (SELECT copy_code "
							+ "                                                   FROM   loaned_books "
							+ "                                                   WHERE  returned = false) "
							+ "WHERE  Lower(book_category) = ? "
							+ "GROUP  BY books.isbn, "
							+ "          books.title, "
							+ "          books.author, "
							+ "          books.p_year, "
							+ "          books.cover, "
							+ "          books.copy_cnt";

		ArrayList<Book> books = new ArrayList<Book>();
		Statement ps = null;
		try {
			ps = cn.createStatement();
//			PreparedStatement pst = cn.prepareStatement("select * from books where Lower(book_category)=?");
			PreparedStatement pst = cn.prepareStatement(booksQuery);
			pst.setString(1, catName.toLowerCase());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Book b = new Book();
				b.setISBN(rs.getString("isbn"));
				b.setTitle(rs.getString("title"));
				b.setAuthorName(rs.getString("author"));
				Category cat = new Category();
				cat = this.getCategory(catName);
				b.setCategory(cat);
				b.setBookYear(Year.parse(rs.getString("p_year"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				b.setCoverPath(rs.getString("cover"));
				b.setCopyCounter(rs.getInt("copy_cnt"));
				b.setAvailableCopies(rs.getInt("usable_copy_count"));
				books.add(b);
			}
		} catch (SQLException e) {
			Logger.getLogger(CategoryDB.class.getName()).log(Level.SEVERE, null, e);
		}
		return books;

	}

}
