<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.customer.model.Customer" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Search Results</title>
    <style>
    @charset "ISO-8859-1";
body {
    font-family: Arial, sans-serif;
    background-color: #f2f2f2;
    margin: 0;
    padding: 0;
}

h1 {
    text-align: center;
    margin-bottom: 20px;
}

table {
    width: 80%;
    margin: 20px auto;
    border-collapse: collapse;
}


thead th, tbody td {
    padding: 10px;
    border: 1px solid #dddddd;
    text-align: left;
}

thead th {
    background-color: #333;
    color: #ffffff;
}

tbody td {
    background-color:black;
    color: black;
}

tbody tr td {
    background-color:#f2f2f2;
}

tbody td[colspan="9"] {
    text-align: center;
}
    
    
    
    </style>
</head>
<body>
    <h1>Search Results</h1>
    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Street</th>
                <th>Address</th>
                <th>City</th>
                <th>State</th>
                <th>Email</th>
                <th>Phone Number</th>
            </tr>
        </thead>
        <tbody>
            <%@ page import="java.util.List" %>
            <%@ page import="com.customer.model.Customer" %>
            <% 
                List<Customer> searchResults = (List<Customer>) request.getAttribute("searchResults");
                if(searchResults != null && !searchResults.isEmpty()) {
                    for(Customer customer : searchResults) {
            %>
            
                        <tr>
                            <td><%= customer.getUuid() %></td>
                            <td><%= customer.getFirst_name() %></td>
                            <td><%= customer.getLast_name() %></td>
                            <td><%= customer.getStreet() %></td>
                            <td><%= customer.getAddress() %></td>
                            <td><%= customer.getCity() %></td>
                            <td><%= customer.getState() %></td>
                            <td><%= customer.getEmail() %></td>
                            <td><%= customer.getPhone() %></td>
                        </tr>
                        
            <% 
                    }
                } else {
            %>
                    <tr>
                        <td colspan="9">No results found.</td>
                    </tr>
            <% } %>
        </tbody>
    </table>
</body>
</html>