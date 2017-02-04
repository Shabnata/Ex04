package Controller;

import DB.CookieDB;
import DB.LibraryPropsDB;
import DB.LoanDB;
import DB.StudentDB;
import DB.UserDB;
import Model.Loan;
import Model.Student;
import Model.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.GregorianCalendar;
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
@WebServlet(name = "LoanBookFromSearchServlet", urlPatterns = {"/LoanBookFromSearchServlet"})
public class LoanBookFromSearchServlet extends HttpServlet{

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
		/*
		 *
		 *
		 * TODO
		 * Remember to test this Servlet
		 *
		 *
		 *
		 */

		Connection cn;
		RequestDispatcher rd;
		Cookie currentUser = CookieDB.getCookieValue(request.getCookies(), "username");

		UserDB myUserDb;
		LoanDB lnDB;
		LibraryPropsDB lpDB;
		StudentDB sDB;

		User crrUser;
		Student st = null;

		String bookISBN = request.getParameter("bookIsbn");
		String userID = request.getParameter("userId");
		String stID = request.getParameter("stId");

		try{
			cn = DriverManager.getConnection(this.sc.getInitParameter("cnurl"), this.sc.getInitParameter("DBUsername"), this.sc.getInitParameter("DBPassword"));
			myUserDb = new UserDB(cn);

			if(bookISBN == null || currentUser == null || (crrUser = myUserDb.getUser(currentUser.getValue())) == null){
				rd = request.getRequestDispatcher("SearchBookServlet");
				rd.forward(request, response);
				return;
			}

			sDB = new StudentDB(cn);
			if(stID == null){
				if(crrUser.getUserType().equals("user")){
					st = sDB.getStudent(crrUser.getUserID());
					stID = st.getStudentID();
				} else if(crrUser.getUserID().equals("admin")){
					rd = request.getRequestDispatcher("IdentifyUserServlet"); //TODO Write this page/servlet
					rd.forward(request, response);
					return;
				}
			}

			if(st == null){
				st = sDB.getStudent(crrUser.getUserID());
			}

			lnDB = new LoanDB(cn);
			GregorianCalendar today = new GregorianCalendar();
			GregorianCalendar returnBy = new GregorianCalendar();
			returnBy.add(GregorianCalendar.DAY_OF_MONTH, 14);
			Loan newLoan = lnDB.getNewLoan(stID, today, returnBy);

			if(newLoan == null){
				// TODO
				// Redirect to an error page saying something bad has happned.
				/*
				 * rd = request.getRequestDispatcher("SearchBookServlet");
				 * rd.forward(request, response);
				 */
				return;
			}

			lpDB = new LibraryPropsDB(cn);
			int maxFinesPerStudent = lpDB.getMaxFinesPerStudent();
			int maxBooksPerStudent = lpDB.getMaxBooksPerStudent();

			if(st.getCurrentFines() >= maxFinesPerStudent){
				/*
				 * TODO
				 * Redirect to error page about fines.
				 */
				return;
			}
			if(sDB.getCountLoanedBooks(stID) >= maxBooksPerStudent){
				/*
				 * TODO
				 * Redirect to error page about book count.
				 */
				return;
			}

			if(lnDB.addBookToLoan(newLoan.getLoanID(), bookISBN)){
				// TODO
				// Redirect to a page that handles loans.
				/*
				 * rd = request.getRequestDispatcher("");
				 * request.setAttribute("loanID", newLoan.getLoanID());
				 * request.setAttribute("studentID", userID);
				 * rd.forward(request, response);
				 */
				return;
			} else {
				// TODO
				// Redirect to an error page saying something bad has happned.
				/*
				 * rd = request.getRequestDispatcher("SearchBookServlet");
				 * rd.forward(request, response);
				 */
				return;
			}
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
