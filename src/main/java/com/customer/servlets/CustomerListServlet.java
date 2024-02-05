package com.customer.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.customer.model.Customer;

public class CustomerListServlet {
    public static void main(String[] args) throws IOException {
        try {
            // Get bearer token
            String bearerToken = "dGVzdEBzdW5iYXNlZGF0YS5jb206VGVzdEAxMjM="; // Replace this with the bearer token obtained

            // Fetch customer list using bearer token
            List<Customer> customerList = fetchCustomerList(bearerToken);

            // Sync customers with database
            syncCustomers(customerList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Customer> fetchCustomerList(String bearerToken) throws IOException {
        // API endpoint URL for fetching customer list
        String apiUrl = "https://qa.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list";

        // Set up HTTP request
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Set Bearer token in the request header
        connection.setRequestProperty("Authorization", "Bearer " + bearerToken);

        // Check the HTTP response code
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Read response
            StringBuilder response = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }

            // Parse JSON response and obtain list of Customer objects
            return parseCustomerList(response.toString());
        } else {
            // Handle HTTP error responses
            System.out.println("Failed to fetch customer list. HTTP error code: " + responseCode);
            return new ArrayList<>();
        }
    }

    public static List<Customer> parseCustomerList(String jsonData) {
        List<Customer> customerList = new ArrayList<>();
        String[] customersData = jsonData.replaceAll("\\[|\\]", "").split("\\},\\{");
        for (String customerData : customersData) {
            String[] attributes = customerData.replaceAll("\\{|\\}", "").split(",");
            Customer customer = new Customer();
            for (String attribute : attributes) {
                String[] keyValue = attribute.split(":");
                String key = keyValue[0].replaceAll("\"", "").trim();
                String value = keyValue[1].replaceAll("\"", "").trim();
                switch (key) {
                    case "uuid":
                        customer.setUuid(value);
                        break;
                    case "first_name":
                        customer.setFirst_name(value);
                        break;
                    case "last_name":
                        customer.setLast_name(value);
                        break;
                    case "street":
                        customer.setStreet(value);
                        break;
                    case "address":
                        customer.setAddress(value);
                        break;
                    case "city":
                        customer.setCity(value);
                        break;
                    case "state":
                        customer.setState(value);
                        break;
                    case "email":
                        customer.setEmail(value);
                        break;
                    case "phone":
                        customer.setPhone(value);
                        break;
                    default:
                        // Ignore unknown keys
                        break;
                }
            }
            customerList.add(customer);
        }
        return customerList;
    }

    public static void syncCustomers(List<Customer> customerList) {
        Connection connection = null;
        try {
            // Establish database connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/customer","root","bhagya@09");

            // Iterate through the customer list
            for (Customer customer : customerList) {
                // Insert or update the customer information in the database
                if (customerExists(connection, customer.getUuid())) {
                    updateCustomer(connection, customer);
                } else {
                    insertCustomer(connection, customer);
                }
            }

            System.out.println("Sync completed successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the database connection
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean customerExists(Connection connection, String uuid) throws SQLException {
        // Check if the customer exists in the database based on the UUID
        String query = "SELECT COUNT(*) FROM customer WHERE uuid = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, uuid);
           
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public static void updateCustomer(Connection connection, Customer customer) throws SQLException {
        // Update customer information in the database
        String query = "UPDATE customer SET first_name = ?, last_name = ?, street = ?, address = ?, city = ?, state = ?, email = ?, phone = ? WHERE uuid = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, customer.getFirst_name());
            statement.setString(2, customer.getLast_name());
            statement.setString(3, customer.getStreet());
            statement.setString(4, customer.getAddress());
            statement.setString(5, customer.getCity());
            statement.setString(6, customer.getState());
            statement.setString(7, customer.getEmail());
            statement.setString(8, customer.getPhone());
            statement.setString(9, customer.getUuid());
            statement.executeUpdate();
        }
    }

    public static void insertCustomer(Connection connection, Customer customer) throws SQLException {
        // Insert a new customer record into the database
        String query = "INSERT INTO customer (uuid, first_name, last_name, street, address, city, state, email, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, customer.getUuid());
            statement.setString(2, customer.getFirst_name());
            statement.setString(3, customer.getLast_name());
            statement.setString(4, customer.getStreet());
            statement.setString(5, customer.getAddress());
            statement.setString(6, customer.getCity());
            statement.setString(7, customer.getState());
            statement.setString(8, customer.getEmail());
            statement.setString(9, customer.getPhone());
            statement.executeUpdate();
        }
    }
}
