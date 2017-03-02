package Controller;

import DB.*;
import DB.StudentDB;
import Model.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Kotya
 */
@WebServlet(name = "ReturnBookActionServlet", urlPatterns = {"/ReturnBookActionServlet"})
public class ReturnBookActionServlet extends HttpServlet {

	ServletContext sc;

	@Override
	public void init() {
		this.sc = this.getServletContext();

		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
		} catch (ClassNotFoundException e) {

			// TODO
			// Write an error
			//public void init() {
			//try {
			//Class.forName("org.apache.derby.jdbc.ClientDriver");
			//} catch (ClassNotFoundException ex) {
			//Logger.getLogger(dbServlet.class.getName()).log(Level.SEVERE, null, ex);
			//}
		}
	}

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Connection cn = DriverManager.getConnection(this.sc.getInitParameter("cnurl"), this.sc.getInitParameter("DBUsername"), this.sc.getInitParameter("DBPassword"));
		RequestDispatcher dispatcher;
		ArrayList<Loan> loans;

		LoanDB lnDB = new LoanDB();
		Loan ln = lnDB.getLoanByID((Integer.parseInt(request.getParameter("loanID"))));

		//Update fines
		StudentDB sDB = new StudentDB();
		Student st = sDB.getStudent(request.getParameter("studentID"));

		Double oldFines = st.getCurrentFines();
		Double newFines = oldFines + Double.parseDouble(request.getParameter("generalFines")) + (Double.parseDouble(request.getParameter("lateFines")));
		st.setCurrentFines(newFines);
		sDB.updateStudent(st); // change id DB

		//Update bookCopy condition
		ConditionDB conDB = new ConditionDB();
		BookCondition con = conDB.getCondition((Integer.parseInt(request.getParameter("newCondition"))));

		BookCopyDB bcDB = new BookCopyDB();
		BookCopy bc = new BookCopy();
		bc = bcDB.getBookCopyByCopyCode(request.getParameter("copyCode"));
		bc.setCopyCondition(con);
		bcDB.updateBookCopyCondition(bc);

		//set book to returned in loaned_books
		String cpCode = request.getParameter("copyCode");
		bcDB.updateBookCopyLoanState(cpCode);

		if (st == null) {
			dispatcher = request.getRequestDispatcher("ReturnBookPageResult.jsp");
			dispatcher.forward(request, response);

		} else {
			loans = sDB.getLoans(request.getParameter("studentID"));
			request.setAttribute("loans", loans);
			request.setAttribute("student", st);
			dispatcher = request.getRequestDispatcher("ReturnBookPageResult.jsp");
			dispatcher.forward(request, response);
		}
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}
