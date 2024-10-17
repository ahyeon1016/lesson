<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		Date now = (Date)request.getAttribute("now");
	%>
	<jsp:useBean id="now1" class="java.util.Date"></jsp:useBean>
	<p><fmt:formatDate value="${now}" type="date"/>
	<p><fmt:formatDate value="${now}" type="time"/>
	<p><fmt:formatDate value="${now}" type="both"/>
	<p><fmt:formatDate value="${now}" type="both" dateStyle="default" timeStyle="default"/>
	<p><fmt:formatDate value="${now1}" type="both" dateStyle="short" timeStyle="short"/>
	<p><fmt:formatDate value="${now1}" type="both" dateStyle="medium" timeStyle="medium"/>
	<p><fmt:formatDate value="${now1}" type="both" dateStyle="long" timeStyle="long"/>
	<p><fmt:formatDate value="${now1}" type="both" dateStyle="full" timeStyle="full"/>
	<p><fmt:formatDate value="${now1}" type="both" pattern="yyyy년 MM월 dd일 HH시 mm분 ss초 E요일"/>
</body>
</html>