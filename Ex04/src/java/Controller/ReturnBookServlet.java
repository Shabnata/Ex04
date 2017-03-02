
package Controller;

import DB.StudentDB;
import Model.Loan;
import Model.Student;
import java.io.IOException;
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
@WebServlet(name = "ReturnBookServlet", urlPatterns = {"/ReturnBookServlet"})
public class ReturnBookServlet extends HttpServlet{

	ServletContext sc;

	@Override
	public void init(){
		this.sc = this.getServletContext();
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		StudentDB sDB = new StudentDB();
		RequestDispatcher dispatcher;
		Student st = null;
		ArrayList<Loan> loans;

		try {
			st = sDB.getStudent(request.getParameter("stID"));
			if(st == null){
				dispatcher = request.getRequestDispatcher("ReturnBookPageResult.jsp");
				dispatcher.forward(request, response);

			} else {
				loans = sDB.getLoans(request.getParameter("stID"));
				request.setAttribute("loans", loans);
				request.setAttribute("student", st);
				dispatcher = request.getRequestDispatcher("ReturnBookPageResult.jsp");
				dispatcher.forward(request, response);
			}
		} catch(SQLException e){
			// TODO
			// Write an error
			System.err.println("*\n*\n*\n" + e.getMessage() + "\n*\n*\n*");
		} finally {
			if(sDB != null){
				sDB.closeConnection();
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{
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
