package com.customer.utill;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class J2EEHttpPost {

	public static void main(String[] args) {

		try {

			String loginId = "test@sunbasedata.com";
			String password = "Test@123";
			
			// Define the credentials for authentication
			String credentialsJson = "{\"login_id\":\"test@sunbasedata.com\",\"password\":\"Test@123\"}";

			// Make a POST request with the credentials
			String response = sendPostRequest(loginId, password);

			// Print the response
			System.out.println("Response: " + response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String sendPostRequest(String loginId, String password) throws IOException {

		// Specify the endpoint URL
		String url = "https://qa.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";
		URL endpoint = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) endpoint.openConnection();
		
		 // Create a JSON object for the request body
        String postData = "{\"login_id\":\"" + loginId + "\",\"password\":\"" + password + "\"}";

		// Set the request method to POST
		connection.setRequestMethod("POST");

		// Enable input/output streams
		connection.setDoOutput(true);

		// Set the content type
		connection.setRequestProperty("Content-Type", "application/json");

		// Write the data to the output stream
		try (OutputStream os = connection.getOutputStream()) {
			os.write(postData.getBytes());
		}

		// Get the response from the server
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			String[] split = response.toString().split(":");
			String s = "";
			for (int i = 0; i < split[1].length(); i++) {
				char ch = split[1].charAt(i);
				if (ch != '"' && ch != '}') {
					s = s + ch;
				}
			}
			return s;
		} finally {
			connection.disconnect();
		}
	}

}