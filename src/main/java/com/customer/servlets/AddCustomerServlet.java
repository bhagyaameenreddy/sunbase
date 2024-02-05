package com.customer.servlets;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.customer.dao.CustomerDAO;
import com.customer.daoimpl.CustomerDaoImpl;
import com.customer.model.Customer;

@WebServlet("/AddCustomerServlet")
public class AddCustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CustomerDAO customerDao;

    public void init() {
        customerDao = new CustomerDaoImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	 String customerid = request.getParameter("customerid");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String street = request.getParameter("street");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        
        Customer newCustomer = new Customer(customerid,firstName, lastName, street, address, city, state, email, phoneNumber);
        
        // Add the new customer to the database
        customerDao.addCustomer(newCustomer);
        
        // Redirect back to the home page after adding the customer
        response.sendRedirect("HomeServlet");
    }
}