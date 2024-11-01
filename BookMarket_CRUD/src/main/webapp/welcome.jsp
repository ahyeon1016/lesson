<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
	<!-- 정적 리소스는 절대경로 표시를 추천함 -->
	<link href="/BookMarket_CRUD/resources/css/bootstrap.min.css" rel="stylesheet">
	<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
  <div class ="container py-4">
	  
	  <%@ include file ="menu.jsp" %>
	  
	
	
		<%!
			String greeting = "Welcome to Book Shopping"; 
			String tagline = "Welcome to Web Market!";
		%>
		
	  <div class = "p-5 mb-4 bg-body-tertiary rounded-3">
	    <div class = "container-fluid py-5">
	      <h1 class = "display-5 fw-bold"><%= greeting %></h1>
	      <p class = "col-md-8 fs-4">BookMarket</p>
	    </div>
	  </div>
	  
	  <div class = "row align-items-md-stretch text-center">
	    <div class = "col-md-12">
	      <div class = "h-100 p-5">
	        <h3><%= tagline %></h3>
	      </div>
	    </div>
	  </div>
	  
	  <%@ include file ="footer.jsp" %>
	  
  </div>
</body>
</html>