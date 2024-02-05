package com.customer.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.customer.dao.UserDAO;
import com.customer.daoimpl.UserDAOImpl;
import com.customer.model.User;
import com.customer.utill.J2EEHttpPost;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
  

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		UserDAOImpl adminDaoIml = new UserDAOImpl();
		List<User> allUser = adminDaoIml.getAllUser();
		String authtoken = J2EEHttpPost.sendPostRequest(username, password);

		if (authtoken != null) {
			HttpSession session = request.getSession();
			session.setAttribute("admin", authtoken);
			request.getRequestDispatcher("HomeServlet").forward(request, response);

		}

		else {
			request.setAttribute("errorPage", "You are not logined");
			request.getRequestDispatcher("index.jsp").include(request, response);
		}
	}
}