<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>


<%
	int i = 3;


	out.println("i = " +i);
	
	request.setAttribute("i", "3");
	
	// > ia란 것에 3이란 값을 담는다. 
	
	
%>

	<p>
	
	<!-- 
	객체에 담긴 이름안됨
	 -->
	i =<%= i + 4 %>
	
	<!--EL은? 
	
	다이렉트로 쓰는게 아니라 직접하는게 아니라
	담아서 쓴다 .
	
	jsp 에서 out.println 의 한계 expression의 한계를 알아둬야
	한다. 
	
	-->
	
	
	</p>
	${i > 4}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

</body>
</html>