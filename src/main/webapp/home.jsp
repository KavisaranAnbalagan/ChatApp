<%@page import="com.zoho.utility.ProxyObject"%>
<%@page import="com.zoho.interfaces.UserInterface"%>
<%@page import="com.zoho.apis.UserDetailsdao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">

</head>
</head>
<body>

	<%@ page import="com.zoho.model.*"%>
	<%
UserDetails ud = CheckFilter.threadLocalVariable.get();
	
%>


	<i class="fas fa-envelope"></i>

	<button class="downArrow" onclick="fetchAllMails()">ALL EMAILS</button>
	<form action="AddMail.html" method="POST">
		AddMail <input type="submit">
	</form>
	<div id="emailContainer"></div>


	<div id="container"></div>
	<script>
		function fetchAllMails() {
			$.ajax({
				url : "showMail",
				type : "GET",
				success : function(response) {
					$("#emailContainer").html(response);
					// Display the fetched emails below the button

				}
			});

		}
	</script>


	<br>
	<i class="fas fa-mobile-alt"></i>
	<button type="submit" formaction="/showPhno">Show All
		PhoneNumbers</button>


	<form align="right" method="post" action="log_out">
		<input type="submit" value="log out">
	</form>
	<h1>Personal Information</h1>
	<table border="1" width="500" align="center">
		<tr bgcolor="00FF7F">
			<th><b>Field</b></th>
			<th><b>Details</b></th>
		</tr>
		<%@ page import="java.util.*"%>
		<%
		
		//Set<String> keys = std.keySet();
		// printing the elements of LinkedHashMap
		//for (String key : keys) {
		%>

		<tr>
			<td><%="FirstName"%></td>
			<td><%=(ud.getFname())%></td>
		</tr>
		<tr>
			<td><%="LastName"%></td>
			<td><%=(ud.getLname())%></td>
		</tr>
		<tr>
			<td><%="Gender"%></td>
			<td><%=(ud.getGender())%></td>
		</tr>
		<tr>
			<td><%="DOB"%></td>
			<td><%=(ud.getDob())%></td>
		</tr>



	</table>


</body>
</html>