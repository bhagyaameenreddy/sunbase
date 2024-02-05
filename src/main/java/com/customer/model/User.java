package com.customer.model;

public class User {
	private int UserID;
	private String Username;
	private String  Password;
	
	public User() {
		
	}

	public User(int userID, String username, String password) {
		super();
		UserID = userID;
		Username = username;
		Password = password;
	}

	public int getUserID() {
		return UserID;
	}

	public void setUserID(int userID) {
		UserID = userID;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

}
