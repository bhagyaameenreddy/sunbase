package com.customer.dao;

import java.util.List;

import com.customer.model.Customer;

public interface CustomerDAO {
	int addCustomer(Customer customer);
	int updateCustomer(Customer customer);
	int deleteCustomer(String uuid);
	List<Customer> getAllCustomer();
	Customer getAllCustomerByID(String uuid);
	List<Customer> searchByFirstName(String searchInput);
	List<Customer> searchByCity(String city);
	 List<Customer> searchByEmail(String email);
	 List<Customer> searchByPhoneNumber(int phoneNumber);
	

}
