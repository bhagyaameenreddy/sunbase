package com.customer.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.customer.daoimpl.CustomerDaoImpl;
import com.customer.model.Customer;
import com.customer.utill.CustomerList2;


@WebServlet("/SyncCustomerServlet")
public class SyncCustomerServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
//		// Specify the customer list endpoint URL
//		String customerListUrl = "https://qa.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list";
//
//		// Specify the bearer token received from the authentication API call
//		String bearerToken = "dGVzdEBzdW5iYXNlZGF0YS5jb206VGVzdEAxMjM=";
		HttpSession session = request.getSession();
		String authtoken = (String) session.getAttribute("admin");

		JSONArray customerList = CustomerList2.customerList(authtoken);

		// Parse JSON response

		// Sync customers with the database
		CustomerDaoImpl customerDao = new CustomerDaoImpl();
		for (int i = 0; i < customerList.length(); i++) {
			JSONObject jsonObject = customerList.getJSONObject(i);
			Customer customer = new Customer(jsonObject.getString("uuid"), jsonObject.getString("first_name"),
					jsonObject.getString("last_name"), jsonObject.getString("street"), jsonObject.getString("address"),
					jsonObject.getString("city"), jsonObject.getString("state"), jsonObject.getString("email"),
					jsonObject.getString("phone"));
			// Check if the customer already exists in the database
			Customer existingCustomer = customerDao.getAllCustomerByID(customer.getUuid());
			if (existingCustomer == null) {
				// If not, insert the customer
				customerDao.addCustomer(customer);

			} else {
				// If yes, update the customer
				customerDao.updateCustomer(customer);
			}
		}
		// Redirect back to the home page with a success message
		request.setAttribute("successfullMessage", "Customer data synced successfully.");
		try {
			request.getRequestDispatcher("home.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}
}