package com.customer.servlets;

import com.customer.dao.CustomerDAO;
import com.customer.daoimpl.CustomerDaoImpl;
import com.customer.model.Customer;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UpdateCustomerServlet")
public class UpdateCustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CustomerDAO customerDAO;

    public void init() {
        customerDAO = new CustomerDaoImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String street = request.getParameter("street");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String email = request.getParameter("email");
       String phoneNumber = request.getParameter("phoneNumber");

        Customer customer = new Customer(id, firstName, lastName, street, address, city, state, email, phoneNumber);

        int rowsAffected = customerDAO.updateCustomer(customer);

        if (rowsAffected > 0) {
            // Retrieve the updated customer list from the database
            List<Customer> updatedCustomers = customerDAO.getAllCustomer();
            // Set the updated customer list in the request attribute
            request.setAttribute("customers", updatedCustomers);
            // Forward to the SuccessEdit.jsp page
            request.getRequestDispatcher("SuccessEdit.jsp").forward(request, response);
        } else {
            response.sendRedirect("FailureEdit.jsp");
        }
    }
}