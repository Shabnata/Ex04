package Controller;

import DB.StudentDB;
import Model.Student;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
@WebServlet(name = "PayFineServlet", urlPatterns = {"/PayFineServlet"})
public class PayFineServlet extends HttpServlet {

	ServletContext sc;

	@Override
	public void init() {
		this.sc = this.getServletContext();

		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
		} catch (ClassNotFoundException e) {

		}
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException {
		String stID = request.getParameter("stID");

		String stFine=request.getParameter("payAmount");
		
		if (stID != null && stFine.equals("")) {
			response.sendRedirect("SearchStudentServlet?stID=" + stID);
			return;
		}
		StudentDB sDB = new StudentDB();
		Student st = sDB.getStudent(request.getParameter("stID"));
		if (st == null) {
			response.sendRedirect("SearchStudentPage");
			return;
		}

		double stFines = st.getCurrentFines();
		double payAmount = Double.parseDouble(stFine);

		int newFine = (payAmount > stFines) ? 0 : (int) (stFines - payAmount);
		st.setCurrentFines(newFine);
		sDB.updateStudent(st);

		response.sendRedirect("SearchStudentServlet?stID=" + stID);

		/*----------------------------------
		StudentDB sDB = new StudentDB();
		RequestDispatcher dispatcher;
		Student st = sDB.getStudent(request.getParameter("stID"));
		if(st == null){
			dispatcher = request.getRequestDispatcher("SearchStudentPageNotFound.jsp");
			dispatcher.forward(request, response);

		} else {
			request.setAttribute("student", st);
			dispatcher = request.getRequestDispatcher("SearchStudentPageResult.jsp");
			dispatcher.forward(request, response);
		}
		----------------------------------
		 */
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
		try {
			processRequest(request, response);
		} catch (ClassNotFoundException ex) {
			//todo
		} catch (SQLException ex) {
			//todo
		}
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
		try {
			processRequest(request, response);
		} catch (ClassNotFoundException ex) {
			//todo
		} catch (SQLException ex) {
			//todo
		}
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