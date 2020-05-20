<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>

<style>
table {
	margin:0 auto;
	
}

</style>


</head>
<body>

	<h2>회원가입</h2>


	<form action="Mproc2" method="post">  <!-- 서블릿 매픽이름 -->
	
		<table width="400"  border="1" bordercolor="gray">
		
			<tr height="40">
				<td width="150" align="center"> 아이디</td>
				<td width="250"> 
					<input type="text" name="id"></td>
					
			<tr>
			
			<tr height="40">
				<td width="150" align="center"> 패스워드</td>
				<td width="250"> 
					<input type="password" name="password"></td>
					
			<tr>
		
		
			<tr height="40">
				<td width="150" align="center"> 이메일</td>
				<td width="250"> 
					<input type="email" name="email"></td>
					
			<tr>
		
		
			<tr height="40">
				<td width="150" align="center"> 전화</td>
				<td width="250"> 
					<input type="text" name="tel"></td>
					
			<tr>
		
			<tr height="40">
				<td width="150" align="center"> 주소 </td>
				<td width="250"> 
					<input type="text" name="address"></td>
					
			<tr>
		
			<tr height="40">
				<td colspan="2" align="center">
					<input type="submit" value="회원가입">
				</td>
			<tr>
		
		
		
		
		</table>
		
		
		
		
	
	
	
	</form>




</body>
</html>