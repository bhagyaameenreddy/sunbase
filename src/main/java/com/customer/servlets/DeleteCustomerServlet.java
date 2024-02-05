package com.customer.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.customer.dao.CustomerDAO;
import com.customer.daoimpl.CustomerDaoImpl;
import com.customer.model.Customer;

@WebServlet("/DeleteCustomerServlet")
public class DeleteCustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CustomerDAO customerDao;

    public void init() {
        customerDao = new CustomerDaoImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String customerId = request.getParameter("id");

        // Check if customerId is not null and not empty
        if (customerId != null && !customerId.isEmpty()) {
            // Convert customerId to integer
            String id = customerId;

            // Delete the customer record from the database
            int rowsAffected = customerDao.deleteCustomer(id);

            // Redirect to appropriate JSP page based on deletion result
            if (rowsAffected > 0) {
                // Remove the deleted customer from the session attribute
                HttpSession session = request.getSession();
                List<Customer> customers = (List<Customer>) session.getAttribute("customers");
                if (customers != null) {
                    customers.removeIf(customer -> customer.getUuid().equals(id));
                }
                // Redirect to success page
                response.sendRedirect("SuccessDelete.jsp");
            } else {
                // Redirect to failure page
                response.sendRedirect("FailureDelete.jsp");
            }
        } else {
            // If customerId is null or empty, redirect back to the home page
            response.sendRedirect("home.jsp");
        }
    }

}
