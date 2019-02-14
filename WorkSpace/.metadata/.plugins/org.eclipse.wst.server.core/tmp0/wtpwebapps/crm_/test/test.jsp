<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.atuigu.crm.entity.Customer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<% 
		request.setAttribute("customer", new Customer());
		
		List<String> regions = new ArrayList();
		regions.add("AA");
		regions.add("BB");
		regions.add("CC");
		regions.add("DD");
		regions.add("EE");
		
		request.setAttribute("regions", regions);
	%>
	
	<form:form modelAttribute="customer">
		<form:select path="region">
			<form:option value="">全部</form:option>
			<form:options items="${regions }"/>
		</form:select>
	</form:form>

</body>
</html>