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
	
	// > ia�� �Ϳ� 3�̶� ���� ��´�. 
	
	
%>

	<p>
	
	<!-- 
	��ü�� ��� �̸��ȵ�
	 -->
	i =<%= i + 4 %>
	
	<!--EL��? 
	
	���̷�Ʈ�� ���°� �ƴ϶� �����ϴ°� �ƴ϶�
	��Ƽ� ���� .
	
	jsp ���� out.println �� �Ѱ� expression�� �Ѱ踦 �˾Ƶ־�
	�Ѵ�. 
	
	-->
	
	
	</p>
	${i > 4}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

</body>
</html>