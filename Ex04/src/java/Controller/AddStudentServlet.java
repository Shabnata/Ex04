
package Controller;

import DB.StudentDB;
import Model.Student;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AddStudentServlet", urlPatterns = {"/AddStudentServlet"})
public class AddStudentServlet extends HttpServlet{

	ServletContext sc;

	@Override
	public void init(){
		this.sc = this.getServletContext();

		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
		} catch(ClassNotFoundException e){

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

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException, ClassNotFoundException, SQLException{

		StudentDB sDB = new StudentDB();
		RequestDispatcher dispatcher;
		String stId = request.getParameter("id");
		String fName = request.getParameter("fname");
		String lName = request.getParameter("lname");
		String email = request.getParameter("email");
		Boolean added = sDB.addStudent(stId, fName, lName, email, 0);
		if(!added){
			dispatcher = request.getRequestDispatcher("AddStudentPageNotAdded.jsp");
			dispatcher.forward(request, response);

		} else {
			Student st = sDB.getStudent(request.getParameter("id"));
			request.setAttribute("student", st);
			dispatcher = request.getRequestDispatcher("AddStudentPageResult.jsp");
			dispatcher.forward(request, response);
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
		try {
			processRequest(request, response);
		} catch(ClassNotFoundException ex){
			//todo
		} catch(SQLException ex){
			//todo
		}
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
		try {
			processRequest(request, response);
		} catch(ClassNotFoundException ex){
			//todo
		} catch(SQLException ex){
			//todo
		}
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
