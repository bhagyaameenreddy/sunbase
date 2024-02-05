package com.customer.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/LogoutServlet")
public class LogOutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	        HttpSession session = request.getSession();
    	        // Invalidate the session to log out the user
    	        session.invalidate();
    	        // Redirect the user to the login page
    	        response.sendRedirect("index.jsp");
    	    }

    	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	        // Call the doGet method to handle both GET and POST requests
    	        doGet(request, response);
    	    }
}
