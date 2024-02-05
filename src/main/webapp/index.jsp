<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style>
body {
    font-family: Arial, sans-serif;
    background-color: #f0f0f0;
    margin: 0;
    padding: 0;
}

.container {
    width: 100%;
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
}

.login-box {
    width: 300px;
    padding: 20px;
    background-color: #ffffff;
    border-radius: 5px;
    padding-right:100px;
   padding-left:100px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    width:400px;
    height:380px;
}

h2 {
    text-align: center;
    color: #333333;
    margin-bottom: 30px;
}

form {
    text-align: center;
}

label {
    display: block;
    margin-bottom: 10px;
    color: #666666;
}

input[type="text"],
input[type="password"] {
    width: 100%;
    padding: 10px;
    margin-bottom: 20px;
    border: 1px solid #cccccc;
    border-radius: 5px;
}

input[type="submit"] {
    width: 100%;
    padding: 10px;
    background-color: #007bff;
    color: #ffffff;
    border: none;
    border-radius: 5px;
    width:300px;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

input[type="submit"]:hover {
    background-color: #0056b3;
}

</style>
</head>
<body>

    <div class="container">
    <div class="login-box">
        <h2>Login</h2>
        <form action="LoginServlet" method="get">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required><br><br>
            
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required><br><br>
            
            <input type="submit" value="Submit">
        </form>
    </div>
</div>
    
</body>
</html>