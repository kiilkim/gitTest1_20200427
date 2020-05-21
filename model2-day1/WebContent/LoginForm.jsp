<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>

	<meta charset="EUC-KR">
	<title>Insert title here</title>

	
	<style>
	body {
	
	text-aling : center;
	
	}
	
	form {
	
	margin:0 auto;
	
	width:250px;
	
	
	
	}
	
	
	h2 {
	
	text-align : center;
	
	}
	
	
	
	</style>
	<script>
	
	
	
	</script>


</head>

<body>
	
	<!-- 
	0519
	요즘은 web.xml 안만들어도 된다 .
	
	annotation 기능으로 하면된다
	
	
	 -->
	
	
	<h2>로그인</h2>
	

	<form action="LoginProc.do" method="post" id="fr">
	
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