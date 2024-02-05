package com.customer.daoimpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.customer.dao.CustomerDAO;
import com.customer.model.Customer;


public class CustomerDaoImpl implements CustomerDAO{
int i=0;
	
	private static Connection connection = null;
	private static PreparedStatement statement = null;
	private static Statement createStatement =null;
	private static final String INSERT_QUERY = "insert into customer(uuid, first_name, last_name, street, address, city, state, Email, phone)values(?,?,?,?,?,?,?,?,?)";
	private static final String UPDATE_QUERY = "update customer set first_name= ?, last_name=?, street=?, address=?, city=?, state=?, email=?, phone=?";
	private static final String DELETE_QUERY = "delete  from customer where uuid=? ";
	private final static String SELECT_ALL_QUERY ="select * from customer";
	private static final String SELECT_QUERY1 = "select * from customer where uuid=?";
    private static final String SORT_BY_CITY_QUERY = "SELECT * FROM customer WHERE city = ?";

    public CustomerDaoImpl() {
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			 connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/customer","root","bhagya@09");
		} catch (ClassNotFoundException | SQLException e) {
			
			e.printStackTrace();
		}
			
	}

	@Override
	public int addCustomer(Customer customer) {
		 try {
				statement = connection.prepareStatement(INSERT_QUERY);
				statement.setString(1, customer.getUuid());
				statement.setString(2, customer.getFirst_name());
				statement.setString(3, customer.getLast_name());
				statement.setString(4,customer.getStreet());
				statement.setString(5,customer.getAddress());
				statement.setString(6, customer.getCity());
				statement.setString(7, customer.getState());
				statement.setString(8, customer.getEmail());
				statement.setString(9, customer.getPhone());
				int i= statement.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			
			
			return i;
	}

	@Override
	public int updateCustomer(Customer customer) {
		  int rowsAffected = 0;
	        try {
	            // Prepare update statement
	            String updateQuery = "update customer set first_name= ?, last_name=?, street=?, address=?, city=?, state=?, email=?, phone=? where uuid=?";
	            statement = connection.prepareStatement(updateQuery);
	            statement.setString(1, customer.getFirst_name());
	            statement.setString(2, customer.getLast_name());
	            statement.setString(3, customer.getStreet());
	            statement.setString(4, customer.getAddress());
	            statement.setString(5, customer.getCity());
	            statement.setString(6, customer.getState());
	            statement.setString(7, customer.getEmail());
	            statement.setString(8, customer.getPhone());
	            statement.setString(9, customer.getUuid());

	            // Execute update
	            rowsAffected = statement.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            // Close statement
	            try {
	                if (statement != null) {
	                    statement.close();
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        return rowsAffected;
	}

	@Override
	public int deleteCustomer(String uuid) {
		int rowsAffected = 0;
	    try {
	        statement = connection.prepareStatement(DELETE_QUERY);
	        statement.setString(1, uuid);
	        rowsAffected = statement.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (statement != null) {
	                statement.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return rowsAffected;
	}

	@Override
	public List<Customer> getAllCustomer() {
		ArrayList<Customer> customers = new ArrayList<Customer>();
		 try {
			createStatement = connection.createStatement();
			ResultSet res = createStatement.executeQuery(SELECT_ALL_QUERY);
			
			while(res.next())
			{
				String id = res.getString("uuid");
				String firstname = res.getString("first_name");
				String lastname = res.getString("last_name");
				String street = res.getString("street");
				String address = res.getString("address");
				String city = res.getString("city");
				String state = res.getString("state");
				String email= res.getString("email");
				String phonenumber= res.getString("phone");
				
			   Customer customer = new Customer(id,firstname,lastname,street,address,city,state,email,phonenumber);
				
				customers.add(customer);
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return customers;
	}

	@Override
	public Customer getAllCustomerByID(String uuid) {
		try {
			statement = connection.prepareStatement(SELECT_QUERY1);
			statement.setString(1, uuid);
			ResultSet res = statement.executeQuery();
			if(res.next())
			{
				String id = res.getString("uuid");
				String firstname = res.getString("first_name");
				String lastname = res.getString("last_name");
				String street = res.getString("street");
				String address = res.getString("address");
				String city = res.getString("city");
				String state = res.getString("state");
				String email= res.getString("email");
				String phonenumber= res.getString("phone");
				
				return new Customer(id,firstname,lastname,street,address,city,state,email,phonenumber);
				
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<Customer> searchByFirstName(String searchInput) {
		   List<Customer> customers = new ArrayList<>();
		    String query = "SELECT * FROM customer WHERE first_name LIKE ?";
		    
		    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/customer","root","bhagya@09");
		         PreparedStatement statement = connection.prepareStatement(query)) {
		        
		        statement.setString(1, "%" + searchInput + "%");
		        ResultSet res = statement.executeQuery();
		        
		        while (res.next()) {
		        	String id = res.getString("uuid");
					String firstname = res.getString("first_name");
					String lastname = res.getString("last_name");
					String street = res.getString("street");
					String address = res.getString("address");
					String city = res.getString("city");
					String state = res.getString("state");
					String email= res.getString("email");
					String phonenumber= res.getString("phone");

		            Customer customer = new Customer(id, firstname, lastname, street, address, city, state, email, phonenumber);
		            customers.add(customer);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		    return customers;
	}

	@Override
	public List<Customer> searchByCity(String city) {
		 List<Customer> customers = new ArrayList<>();
		    String query = "SELECT * FROM customer WHERE city LIKE ?";
		    
		    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/customer","root","bhagya@09");
		         PreparedStatement statement = connection.prepareStatement(query)) {
		        
		        statement.setString(1, "%" + city + "%");
		        ResultSet res = statement.executeQuery();
		        
		        while (res.next()) {
		        	String id = res.getString("uuid");
					String firstname = res.getString("first_name");
					String lastname = res.getString("last_name");
					String street = res.getString("street");
					String address = res.getString("address");
					String city1= res.getString("city");
					String state = res.getString("state");
					String email= res.getString("email");
					String phonenumber= res.getString("phone");
		            Customer customer = new Customer(id, firstname, lastname, street, address, city1, state, email, phonenumber);
		            customers.add(customer);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		    return customers;
		
	}

	@Override
	public List<Customer> searchByEmail(String email) {
		 List<Customer> customers = new ArrayList<>();
		    String query = "SELECT * FROM customer WHERE email LIKE ?";
		    
		    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/customer","root","bhagya@09");
		         PreparedStatement statement = connection.prepareStatement(query)) {
		        
		        statement.setString(1, "%" + email + "%");
		        ResultSet res = statement.executeQuery();
		        
		        while (res.next()) {
		        	String id = res.getString("uuid");
					String firstname = res.getString("first_name");
					String lastname = res.getString("last_name");
					String street = res.getString("street");
					String address = res.getString("address");
					String city = res.getString("city");
					String state = res.getString("state");
					String email1= res.getString("email");
					String phonenumber= res.getString("phone");

		            Customer customer = new Customer(id, firstname, lastname, street, address, city, state, email1, phonenumber);
		            customers.add(customer);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		    return customers;
	}

	@Override
	public List<Customer> searchByPhoneNumber(int phoneNumber) {
		 List<Customer> customers = new ArrayList<>();
		    String query = "SELECT * FROM customer WHERE phone LIKE ?";
		    
		    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/customer","root","bhagya@09");
		         PreparedStatement statement = connection.prepareStatement(query)) {
		        
		        statement.setString(1, "%" + phoneNumber + "%");
		        ResultSet res = statement.executeQuery();
		        
		        while (res.next()) {
		        	String id = res.getString("uuid");
					String firstname = res.getString("first_name");
					String lastname = res.getString("last_name");
					String street = res.getString("street");
					String address = res.getString("address");
					String city = res.getString("city");
					String state = res.getString("state");
					String email= res.getString("email");
					String phonenumber= res.getString("phone");
		            Customer customer = new Customer(id, firstname, lastname, street, address, city, state, email, phonenumber);
		            customers.add(customer);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		    return customers;
		
	}

}
