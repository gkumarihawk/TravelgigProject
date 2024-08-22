<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ page isELIgnored="false" %> <%@ taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%@ taglib prefix="sec"
uri="http://www.springframework.org/security/tags" %> <%@ taglib
uri="http://www.springframework.org/tags/form" prefix="frm" %>
<!DOCTYPE html>
<html>
  <script
    src="https://code.jquery.com/jquery-3.6.3.min.js"
    integrity="sha256-pvPw+upLPUjgMXY0G+8O0xUf+/Im1MZjXxxgOcBQBXU="
    crossorigin="anonymous"
  ></script>
  <link
    rel="stylesheet"
    href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
  />
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
  <link
    rel="stylesheet"
    href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
  />
  <head>
    <meta charset="UTF-8" />
    <title>Login to TravelGig</title>
  </head>
<body>
    <div class="container">
        <h2>User Signup</h2>
        <form action="/signup" method="post">
    <div class="form-group">
        <label for="userName">Name:</label>
        <input type="text" class="form-control" id="userName" name="userName" required>
    </div>
    <div class="form-group">
        <label for="userEmail">Email:</label>
        <input type="email" class="form-control" id="userEmail" name="userEmail" required>
    </div>
    <div class="form-group">
        <label for="userPassword">Password:</label>
        <input type="password" class="form-control" id="userPassword" name="userPassword" required>
    </div>
    <input class="btn btn-primary mt-5" type="submit" value="Submit" />
</form>
    </div>

</body>
</html>
