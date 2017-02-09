
package Controller;

import DB.CookieDB;
import DB.LibraryPropsDB;
import DB.LoanDB;
import DB.StudentDB;
import Model.Loan;
import Model.Student;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
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

		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
		} catch(ClassNotFoundException e){
			Logger.getLogger(LoanBookFromSearchServlet.class.getName()).log(Level.SEVERE, null, e);
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

		//Marking this for deletion
		if(true){
			return;
		}

		Connection cn = null;
		RequestDispatcher rd;
		Cookie currentUser = CookieDB.getCookieValue(request.getCookies(), "username");

		LoanDB lnDB;
		LibraryPropsDB lpDB;
		StudentDB sDB;

		Student st = null;

		String bookISBN = request.getParameter("bookIsbn");
		String stID = request.getParameter("stId");

		boolean errors = false;
		ArrayList<String> errorLst = null;
		try {
			cn = DriverManager.getConnection(this.sc.getInitParameter("cnurl"), this.sc.getInitParameter("DBUsername"), this.sc.getInitParameter("DBPassword"));

			if(bookISBN == null || currentUser == null){
				response.sendRedirect("SearchBookServlet");
				return;
			}

			sDB = new StudentDB(cn);
			if(stID == null || (st = sDB.getStudent(stID)) == null){
				rd = request.getRequestDispatcher("IdentifyStudentPage.jsp");
				request.setAttribute("bookISBN", bookISBN);
				request.removeAttribute("stId");
				rd.forward(request, response);
				return;
			}

			lnDB = new LoanDB(cn);
			GregorianCalendar today = new GregorianCalendar();
			GregorianCalendar returnBy = new GregorianCalendar();
			returnBy.add(GregorianCalendar.DAY_OF_MONTH, 14);
			Loan newLoan = lnDB.getNewLoan(st.getStudentID(), today, returnBy);

			if(newLoan == null){
				errors = true;
				if(errorLst == null){
					errorLst = new ArrayList<>();
				}
				errorLst.add("Failed to recieve newLoan.");
			}

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

			if(errors == false && lnDB.addBookToLoan(newLoan.getLoanID(), bookISBN)){

				rd = request.getRequestDispatcher("AddLoanServlet");
				request.setAttribute("stId", stID);
				request.setAttribute("LoanId", newLoan.getLoanID());
				rd.forward(request, response);
				return;
			} else {
				errors = true;
				if(errorLst == null){
					errorLst = new ArrayList<>();
				}
				errorLst.add("Failed to add book copy to loan.");
			}

			if(errors == true){
				rd = request.getRequestDispatcher("LoanBookFromSearchPageError.jsp");
				request.setAttribute("errors", errorLst);
				rd.forward(request, response);
			}
		} catch(SQLException | ClassNotFoundException e){
			Logger.getLogger(LoanBookFromSearchServlet.class.getName()).log(Level.SEVERE, null, e);
		} finally {
			try {
				if(cn != null){
					cn.close();
				}
			} catch(SQLException e){
				Logger.getLogger(LoanBookFromSearchServlet.class.getName()).log(Level.SEVERE, null, e);
			}
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
