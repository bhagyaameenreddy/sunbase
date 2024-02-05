package com.customer.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AuthenticateUserServlet {
    public static void main(String[] args) throws IOException {
        // Get bearer token
        String bearerToken = getBearerToken();
        
        // Print the token received
        System.out.println("Bearer Token: " + bearerToken);
    }

    public static String getBearerToken() throws IOException {
        // API endpoint URL for authentication
        String authUrl = "https://qa.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";
        
        // User credentials
        String loginId = "test@sunbasedata.com";
        String password = "Test@123";
        
        // Create JSON object with login credentials
        String requestBody = "{\"login_id\":\"" + loginId + "\",\"password\":\"" + password + "\"}";

        // Make POST request to authentication API
        String token = sendGetRequest(authUrl, requestBody);
        
        return token;
    }

    private static String sendGetRequest(String apiUrl, String requestBody) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            
            // Write request body to output stream
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = requestBody.getBytes("utf-8");
                os.write(input, 0, input.length);			
            }
            
            // Check the HTTP response code
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read response
                StringBuilder response = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line.trim());
                    }
                }
                return response.toString();
            } else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
                // Read error response
                try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"))) {
                    StringBuilder errorResponse = new StringBuilder();
                    String errorLine;
                    while ((errorLine = errorReader.readLine()) != null) {
                        errorResponse.append(errorLine.trim());
                    }
                    System.out.println("Failed to fetch customer list. HTTP error code: " + responseCode);
                    System.out.println("Error response: " + errorResponse.toString());
                }
                return null;
            } else {
                // Handle other HTTP error responses
                System.out.println("Failed to fetch customer list. HTTP error code: " + responseCode);
                return null;
            }
        } catch (IOException e) {
            // Handle IOException
            System.out.println("An error occurred while fetching customer list: " + e.getMessage());
            return null;
        }
    }

    private static String extractToken(String response) {
        // Assuming the response contains the token in a specific format
        // Example: "token":"xyz123"
        int startIndex = response.indexOf("\"token\":\"") + "\"token\":\"".length();
        int endIndex = response.indexOf("\"", startIndex);
        
        if (startIndex != -1 && endIndex != -1) {
            return response.substring(startIndex, endIndex);
        } else {
            return null; // Token not found in response
        }
    }
}