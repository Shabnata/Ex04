package Controller;

import DB.BookDB;
import DB.CategoryDB;
import DB.CookieDB;
import DB.LibraryPropsDB;
import DB.LoanDB;
import DB.StudentDB;
import DB.UserDB;
import Model.Book;
import Model.Category;
import Model.Loan;
import Model.Student;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Denis Sh
 */
@WebServlet(name = "AddLoanServlet", urlPatterns = {"/AddLoanServlet"})
public class AddLoanServlet extends HttpServlet{

	ServletContext sc;

	@Override
	public void init(){
		this.sc = this.getServletContext();

		try{
			Class.forName("org.apache.derby.jdbc.ClientDriver");
		} catch(ClassNotFoundException e){
			// TODO
			// Write an error
			System.err.println("*\n*\n*\n" + e.getMessage() + "\n*\n*\n*");
		}
	}

	// <editor-fold defaultstate="collapsed" desc="Pancackes.">
	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 *
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */// </editor-fold>
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Connection cn;
		RequestDispatcher rd;

		LibraryPropsDB lpDB;
		StudentDB sDB;
		LoanDB lnDB;
		CategoryDB cDB;
		BookDB bDB;
		UserDB myUserDb;

		Student st;
		Loan ln;

		boolean errors = false;
		ArrayList<String> errorLst = null;

		String stID = request.getParameter("stId");
		String loanID = request.getParameter("loanId");
		String catName = request.getParameter("catName");
		String bookISBN = request.getParameter("bookIsbn");

		try{
			cn = DriverManager.getConnection(this.sc.getInitParameter("cnurl"), this.sc.getInitParameter("DBUsername"), this.sc.getInitParameter("DBPassword"));

			if(stID == null){
				Cookie crrUsr = CookieDB.getCookieValue(request.getCookies(), "username");
				myUserDb = new UserDB(cn);
				if(crrUsr != null && !myUserDb.getUser(crrUsr.getValue()).getUserType().equals("admin")){
					stID = myUserDb.getUser(crrUsr.getValue()).getUserID();
				} else {
					rd = request.getRequestDispatcher("IdentifyStudentForLoanPage.jsp");
					if(bookISBN != null){
						request.setAttribute("bookISBN", bookISBN);
					}
					rd.forward(request, response);
					return;
				}

			}
			if(catName == null && bookISBN == null){
				sDB = new StudentDB(cn);
				if(sDB.getStudent(stID) == null){
					request.setAttribute("errMsg", "Student not found.");
					rd = request.getRequestDispatcher("IdentifyStudentForLoanPage.jsp");
					rd.forward(request, response);
					return;
				}
				cDB = new CategoryDB(cn);
				ArrayList<Category> catLst = cDB.getCategories();
				rd = request.getRequestDispatcher("SelectCategoryForLoanPage.jsp");
				request.setAttribute("stId", stID);
				if(loanID != null){
					lnDB = new LoanDB(cn);
					ln = lnDB.getLoanByID(Integer.parseInt(loanID));
					request.setAttribute("loanId", loanID);
					request.setAttribute("loan", ln);

					bDB = new BookDB(cn);
					HashMap<String, Book> bksMap = new HashMap<>();
//					for(BookCopy bc : ln.getBooksInLoan()){
//						Book bk = bDB.getBookByBookCopy(bc);
//						if(bk != null){
//							bksMap.put(bc.getCOPY_CODE(), bk);
//						}
//					}
					ln.getBooksInLoan().forEach((bc) -> {
						Book bk = bDB.getBookByBookCopy(bc);
						if(bk != null){
							bksMap.put(bc.getCOPY_CODE(), bk);
						}
					});
					request.setAttribute("bksMap", bksMap);
				}
				request.setAttribute("catLst", catLst);
				rd.forward(request, response);
				return;
			} else if(bookISBN == null){
				cDB = new CategoryDB(cn);
				ArrayList<Book> bookLst = cDB.getBooksByCategoryName(catName);
				//TODO Add method to CategoryDB that returns all books in a category. getBooksByCategoryName
				rd = request.getRequestDispatcher("SelectBookForLoanPage.jsp");
				request.setAttribute("stId", stID);
				if(loanID != null){
					request.setAttribute("loanId", loanID);
				}
				request.setAttribute("bookLst", bookLst);
				rd.forward(request, response);
				return;
			}

			sDB = new StudentDB(cn);
			st = sDB.getStudent(stID);

			lpDB = new LibraryPropsDB(cn);
			int maxFinesPerStudent = lpDB.getMaxFinesPerStudent();
			int maxBooksPerStudent = lpDB.getMaxBooksPerStudent();

			if(st.getCurrentFines() >= maxFinesPerStudent){
				errors = true;
				if(errorLst == null){
					errorLst = new ArrayList<>();
				}
				errorLst.add("Student with ID: " + st.getStudentID() + " has fines that exceed the library maximum.");
			}
			if(sDB.getCountLoanedBooks(st.getStudentID()) >= maxBooksPerStudent){
				errors = true;
				if(errorLst == null){
					errorLst = new ArrayList<>();
				}
				errorLst.add("Student with ID: " + st.getStudentID() + " loaned books count has exceeded the library maximum.");

			}

			if(errors == true){
				rd = request.getRequestDispatcher("AddLoanPageError.jsp");
				request.setAttribute("errors", errorLst);
				rd.forward(request, response);
			}

			lnDB = new LoanDB(cn);
			if(loanID == null){
				GregorianCalendar start_d = new GregorianCalendar();
				GregorianCalendar ret_d = new GregorianCalendar();
				ret_d.add(GregorianCalendar.DAY_OF_MONTH, 14);
				ln = lnDB.getNewLoan(stID, start_d, ret_d);
			} else {
				ln = lnDB.getLoanByID(Integer.parseInt(loanID));
			}

			lnDB.addBookToLoan(ln.getLoanID(), bookISBN);
			ln = lnDB.getLoanByID(ln.getLoanID());

			cDB = new CategoryDB(cn);
			ArrayList<Category> catLst = cDB.getCategories();
			rd = request.getRequestDispatcher("SelectCategoryForLoanPage.jsp");
			request.setAttribute("stId", stID);
			request.setAttribute("loanId", Integer.toString(ln.getLoanID()));
			request.setAttribute("loan", ln);
			bDB = new BookDB(cn);
			HashMap<String, Book> bksMap = new HashMap<>();

			ln.getBooksInLoan().forEach((bc) -> {
				Book bk = bDB.getBookByBookCopy(bc);
				if(bk != null){
					bksMap.put(bc.getCOPY_CODE(), bk);
				}
			});
			request.setAttribute("bksMap", bksMap);
			request.setAttribute("catLst", catLst);
			rd.forward(request, response);

		} catch(SQLException | ClassNotFoundException e){
			// TODO
			// Write an error
			System.err.println("*\n*\n*\n" + e.getMessage() + "\n*\n*\n*");
		}

	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 *
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 *
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo(){
		return "Short description";
	}// </editor-fold>

}
