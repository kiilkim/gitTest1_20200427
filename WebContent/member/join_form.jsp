<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
table {
	margin: 0 auto;
	
}

h2 {
 	text-align: center;
}

</style>

</head>

<body>
	<h2>회원가입 양식</h2>
	
	
	<form action="MemberJoinPro.me" method="post">  <!--
	
	 action에 서블릿매핑주소 
	 
	 proc.do?gubun=1 라고 해서 
	 
	 sevlet 가서 구분 에 따라 뭐 2에따라 뭐 이렇게 가능
	 
	 -->
	
		<table width="500" border="1" bordercolor="black">
		
			<tr height="50">
			
				<td width="150" align="center">이름</td>
				<td width="150" align="center">
					<input type="text" name="name" size="40" placeholder="이름 넣으세요">
				</td>
				
			</tr>
			
			<tr height="50">
			
				<td width="150" align="center">아이디</td>
				<td width="150" align="center">
					<input type="text" name="id" size="40" placeholder="아이디 넣으세요">
				</td>
				
			</tr>
			
			<tr height="50">
			
				<td width="150" align="center">패스워드</td>
				<td width="150" align="center">
					<input type="password" name="pass" size="40" placeholder="비밀번호를 넣으세요">
				</td>
				
			</tr>
			
			<tr height="50">
			
				<td width="150" align="center">이메일</td>
				<td width="150" align="center">
					<input type="email" name="email" size="40" >
				</td>
				
			</tr>
			
			
			<tr height="50">
			
				<td width="150" align="center">성별</td>
				<td width="150" align="center">
					<input type="text" name="gender" size="40" >
				</td>
				
			</tr>
			
		
			
	
	
			
			<tr height="50">
				<td align="center" colspan="2">
					<input type="submit" value="회원가입">
					<input type="reset" value="취소">
				</td>
			</tr>
			
		
		
		</table>
	
	
	</form>






</body>

