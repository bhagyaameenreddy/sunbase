package com.customer.servlets;

import com.customer.dao.CustomerDAO;
import com.customer.daoimpl.CustomerDaoImpl;
import com.customer.model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
    private CustomerDAO customerDAO;

    public void init() {
        customerDAO = new CustomerDaoImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchBy = request.getParameter("searchBy");
        String searchInput = request.getParameter("searchInput");
        String searchKeyword = searchInput.substring(0, Math.min(searchInput.length(), 3)); // Take first three characters


        if (searchBy != null && !searchBy.isEmpty() && searchInput != null && !searchInput.isEmpty()) {
            List<Customer> searchResults = null;

            switch (searchBy) {
                case "firstName":
                    searchResults = customerDAO.searchByFirstName(searchInput);
                    break;
                case "city":
                    searchResults = customerDAO.searchByCity(searchInput);
                    break;
                case "email":
                    searchResults = customerDAO.searchByEmail(searchInput);
                    break;
                case "phoneNumber":
                    try {
                        int phoneNumber = Integer.parseInt(searchInput);
                        searchResults = customerDAO.searchByPhoneNumber(phoneNumber);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                	 response.sendRedirect("InvalidSearchCriteria.jsp");
                    break;
            }

            if (searchResults != null && !searchResults.isEmpty()) {
                request.setAttribute("searchResults", searchResults);
                request.getRequestDispatcher("/Search.jsp").forward(request, response);
            } else {
                response.sendRedirect("NoResults.jsp");
            }
        } else {
            response.sendRedirect("home.jsp");
        }
    }
}