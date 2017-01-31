
package Controller;

import DB.CategoryDB;
import DB.StudentDB;
import Model.Category;
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


@WebServlet(name = "AddCategoryServlet", urlPatterns = {"/AddCategoryServlet"})
public class AddCategoryServlet extends HttpServlet {
	
	ServletContext sc;
	@Override
	public void init(){
		this.sc = this.getServletContext();

		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
		} catch(ClassNotFoundException e){

			
		}
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException {
		CategoryDB cDB = new CategoryDB();
		RequestDispatcher dispatcher;
		String catName = request.getParameter("category");
		
		Boolean added = cDB.addCategory(catName);
		if(!added){
			dispatcher = request.getRequestDispatcher("AddCategoryPageNotAdded.jsp");
			dispatcher.forward(request, response);

		} else {
			Category ct = cDB.getCategory(request.getParameter("category"));
			request.setAttribute("category", ct);
			dispatcher = request.getRequestDispatcher("AddCategoryPageResult.jsp");
			dispatcher.forward(request, response);
		}
	
	}


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch(ClassNotFoundException ex){
			//todo
		} catch(SQLException ex){
			//todo
		}
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch(ClassNotFoundException ex){
			//todo
		} catch(SQLException ex){
			//todo
		}
	}


	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}
