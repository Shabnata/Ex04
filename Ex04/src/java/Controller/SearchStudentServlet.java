/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DB.StudentDB;
import Model.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import javax.security.auth.message.callback.PrivateKeyCallback;
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
@WebServlet(name = "SearchStudentServlet", urlPatterns = {"/SearchStudentServlet"})
public class SearchStudentServlet extends HttpServlet {

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

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ClassNotFoundException, SQLException {
//--Connection to DB ----//
//		Connection cn = null;
//		try {
//			cn = DriverManager.getConnection("jdbc:derby//localhost:1527/LibraryDB");
//		} catch (SQLException e) {
//			// TODO
//			// Write an error
//		}
		StudentDB sDB = new StudentDB();
//---END---//
		RequestDispatcher dispatcher;

		if (sDB.getStudent(request.getParameter("stID")) == null) {
			dispatcher = request.getRequestDispatcher("SearchStudentPageNotFound.jsp");
			dispatcher.forward(request, response);
			
		} else {
			dispatcher = request.getRequestDispatcher("SearchStudentPageResult.jsp");
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
		try {
			processRequest(request, response);
		} catch (ClassNotFoundException ex) {
			//todo
		}
		catch (SQLException ex) {
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
		}
		catch (SQLException ex) {
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
