<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>


<body>

<h2>로그인</h2>
	

	<form action="ElLoginProc.jsp" method="post" id="fr">
	
		<table width="300" border="1">
		
			<tr height="40">
				<td width="120"> 아이디</td>
				<td width="180"> 
					<input type="text" name="id">
					
				</td>
			
			</tr>
			
			<tr height="40">
				<td width="120"> 패스워드</td>
				<td width="180"> 
					<input type="password" name="password">
					
				</td>
			
			</tr>
			
			<tr height="40">
			
				<td align ="center" colspan="2">
				
					<input type="submit" value="로그인">
				
				</td>
			
			</tr>
		
		
		
		
		</table>
	
	
	</form>
	
</body>
</html>