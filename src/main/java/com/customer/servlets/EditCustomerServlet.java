package com.customer.servlets;

import com.customer.dao.CustomerDAO;
import com.customer.daoimpl.CustomerDaoImpl;
import com.customer.model.Customer;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EditCustomerServlet")
public class EditCustomerServlet extends HttpServlet {
    
    private CustomerDAO customerDAO;

    public void init() {
        customerDAO = new CustomerDaoImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String customerId = request.getParameter("id");

        if (customerId != null && !customerId.isEmpty()) {
            String id = customerId;
            Customer customer = customerDAO.getAllCustomerByID(id);
            request.setAttribute("customer", customer);
            request.getRequestDispatcher("/EditCustomerForm.jsp").forward(request, response);
        } else {
            response.sendRedirect("FailureEdit.jsp");
        }
    }
}
