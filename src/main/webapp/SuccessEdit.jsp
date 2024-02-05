<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.customer.model.Customer,java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<style>
body {
    font-family: Arial, sans-serif;
    background-color: #f2f2f2;
    margin: 0;
    padding: 0;
}
 a{
text-decoration:none;
border: 2px solid black;
width:200px;
margin-left:650px;
background-color:black;
color:white;
padding:10px;
}

h1 {
    text-align: center;
}

h2 {
    text-align: center;
    margin-top: 20px;
}

table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 20px;
}

table, th, td {
    border: 1px solid white;
}

th, td {
    padding: 8px;
    text-align: left;
    color:black;
}

tr{
    
}
tr th{
 background-color: #333;
    color: #fff;
}

a {
    display: block;
    text-align: center;
    margin-top: 20px;
}
</style>
</head>
<body>
<h1> Successfully Edited</h1>
    <a href="HomeServlet">Go back to Home</a>
    
    <h2>All Customers:</h2>
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
            <% List<Customer> customers = (List<Customer>) request.getAttribute("customers");
               for(Customer c : customers) { %>
                <tr>
                    <td><%= c.getUuid()%></td>
                    <td><%= c.getFirst_name() %></td>
                    <td><%= c.getLast_name()%></td>
                    <td><%= c.getStreet() %></td>
                    <td><%= c.getAddress() %></td>
                    <td><%= c.getCity() %></td>
                    <td><%= c.getState() %></td>
                    <td><%= c.getEmail() %></td>
                    <td><%= c.getPhone() %></td>
                </tr>
            <% } %>
        </tbody>
    </table>
</body>
</html>