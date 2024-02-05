<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.customer.model.Customer, java.util.List " %>
  <%
    // Check if the customer list has been updated
    String updated = request.getParameter("updated");
    if (updated != null && updated.equals("true")) {
        // Retrieve the updated customer list from wherever it's stored
        List<Customer> updatedCustomers = (List<Customer>) request.getAttribute("customers");
        session.setAttribute("customers", updatedCustomers);
    }
%>
<%

    // Pagination variables
    int pageSize = 5; // Number of records per page
    List<Customer> customers = (List<Customer>) session.getAttribute("customers");
    int totalCustomers = customers.size();
    int totalPages = (int) Math.ceil((double) totalCustomers / pageSize);
    int currentPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;

    // Ensure currentPage is within valid bounds
    currentPage = Math.min(Math.max(currentPage, 1), totalPages);

    // Calculate start and end index of records for the current page
    int startIndex = (currentPage - 1) * pageSize;
    int endIndex = Math.min(startIndex + pageSize, totalCustomers);

    // Get the sublist of customers for the current page
    List<Customer> currentCustomers = customers.subList(startIndex, endIndex);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home</title>

 <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
 <style type="text/css">
 body {
    font-family: Arial, sans-serif;
    background-color: #f2f2f2;
    margin: 0;
    padding: 0;
    text-align: center;
}

.container {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: #333;
    color: #fff;
    padding: 8px; /* Slightly reduced padding */
}

.container button {
    padding: 6px; /* Slightly reduced padding */
    cursor: pointer;
    border: none;
}

.container button.add-customer {
    background-color: #28a745;
    color: #fff;
}

.container button.logout {
    background-color: #dc3545;
    color: #fff;
}

.container form {
    display: flex;
    align-items: center;
}

.container select,
.container input[type="text"],
.container input[type="submit"] {
    padding: 6px; /* Slightly reduced padding */
    margin-right: 3px; /* Slightly reduced margin */
}

.container input[type="submit"] {
    cursor: pointer;
    background-color: #007bff;
    color: #fff;
    border: none;
}

.logout {
    margin-top: 8px; /* Slightly reduced margin-top */
     
}

h1 {
    color: #333;
    margin-top: 15px; /* Slightly reduced margin-top */
}

table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 10px; /* Slightly reduced margin-top */
}

table, th, td {
    border: 1px solid #ddd;
}

th, td {
    padding: 8px; /* Slightly reduced padding */
    text-align: left;
}

th {
    background-color: #333;
    color: #fff;
}

tbody tr:nth-child(even) {
    background-color: #f2f2f2;
}

.pagination {
    margin-top: 10px; /* Slightly reduced margin-top */
}

.pagination a {
    padding: 6px; /* Slightly reduced padding */
    margin: 0 3px; /* Slightly reduced margin */
    text-decoration: none;
    color: black;
    background-color: #fff;
    border: 1px solid #ddd;
    border-radius: 4px;
}

.pagination a.active {
    background-color: #007bff;
    color: #ddd;
}


 </style>
</head>
<body>
   

    <div class="container">
        <button onclick="window.location.href='AddCustomer.jsp'">Add Customer</button>
        <form action="SearchServlet" method="get">
            <select name="searchBy">
                <option value="">Search By</option>
                <option value="firstName">First Name</option>
                <option value="city">City</option>
                <option value="email">Email</option>
                <option value="phoneNumber">Phone Number</option>
            </select>
            <input type="text" name="searchInput" placeholder="Enter search term">
            <input type="submit" value="Search">
            
              <form action="SyncCustomerServlet" method="post">
            <button type="submit">Sync</button>
        </form>
        </form>
        <div class="logout">
         <form action="LogoutServlet" method="post">
            <button type="submit">Logout</button>
        </form>
        
        
        
      
    
        </div>
    
    </div>
    
    
    
       
    
     <h1>Customer Records</h1>
    
   
    
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
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <% for(Customer customer : currentCustomers) { %>
                <tr>
                    <td><%= customer.getUuid()%></td>
                    <td><%= customer.getFirst_name() %></td>
                    <td><%= customer.getLast_name()%></td>
                    <td><%= customer.getStreet() %></td>
                    <td><%= customer.getAddress() %></td>
                    <td><%= customer.getCity() %></td>
                    <td><%= customer.getState() %></td>
                    <td><%= customer.getEmail() %></td>
                    <td><%= customer.getPhone()%></td>
                    <td>
                        <a href="DeleteCustomerServlet?id=<%= customer.getUuid() %>" class="delete-link"><i class="fas fa-trash-alt"></i></a>
                        |
                        <a href="EditCustomerServlet?id=<%= customer.getUuid() %>" class="edit-link"><i class="fas fa-edit"></i></a>
                    </td>
                </tr>
            <% } %>
        </tbody>
    </table>
    
    <%-- Pagination links --%>
    <% if (totalPages > 1) { %>
        <div class="pagination">
            <strong>Pages:</strong>
            <% for (int pageLink = 1; pageLink <= totalPages; pageLink++) { %>
                <a href="HomeServlet?page=<%= pageLink %>"><%= pageLink %></a>
            <% } %>
        </div>
    <% } %>
   

</body>
</html>