<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
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
	
	
	<form action="proc.do" method="post">  <!--
	
	 action에 서블릿매핑주소 
	 
	 proc.do?gubun=1 라고 해서 
	 
	 sevlet 가서 구분 에 따라 뭐 2에따라 뭐 이렇게 가능
	 
	 -->
	
		<table width="500" border="1" bordercolor="black">
		
			<tr height="50">
			
				<td width="150" align="center">아이디</td>
				<td width="150" align="center">
					<input type="text" name="id" size="40" placeholder="id넣으세요">
				</td>
				
			</tr>
			
			<tr height="50">
			
				<td width="150" align="center">패스워드</td>
				<td width="150" align="center">
					<input type="password" name="pass1" size="40" placeholder="비밀번호를 넣으세요">
				</td>
				
			</tr>
			
			<tr height="50">
			
				<td width="150" align="center">패스워드확인</td>
				<td width="150" align="center">
					<input type="password" name="pass2" size="40" >
				</td>
				
			</tr>
			
			<tr height="50">
			
				<td width="150" align="center">이메일</td>
				<td width="150" align="center">
					<input type="email" name="email" size="40" >
				</td>
				
			</tr>
			
			<tr height="50">
			
				<td width="150" align="center">전화번호</td>
				<td width="150" align="center">
					<input type="tel" name="tel" size="40" >
				</td>
				
			</tr>
			
			<tr height="50">
			
				<td width="150" align="center">당신의 관심분야</td>
				<td width="150" align="center">
					<input type="checkbox" name="hobby" value="캠핑"> &nbsp;&nbsp;
					<input type="checkbox" name="hobby" value="등산"> &nbsp;&nbsp;
					<input type="checkbox" name="hobby" value="영화"> &nbsp;&nbsp;
					<input type="checkbox" name="hobby" value="독서"> &nbsp;&nbsp;
					
				</td>
				
			</tr>
			
		
			<tr height="50">
			
				<td width="150" align="center">당신의 직업은</td>
				<td width="150" align="center">
					<select name="job">
					
						<option value="교사">교사</option>
						<option value="변호사">변호사</option>
						<option value="의사">의사</option>
						<option value="기술사">기술사</option>
						 
					
					</select>
				</td>
			</tr>
			
			
			<tr height="50">
			
				<td width="150" aling="center"> 당신의연령은 </td>
				<td width="150" aling="center">
				
					<input type="radio" name="age" value="10">10대 &nbsp;&nbsp;
					<input type="radio" name="age" value="20">20대 &nbsp;&nbsp;
					<input type="radio" name="age" value="30">30대 &nbsp;&nbsp;
					<input type="radio" name="age" value="40">40대 &nbsp;&nbsp;
			
				</td>
			</tr>
			
			
			<tr height="50">
				<td width="150" align="center">하고싶은말</td>
				<td width="350" aling="center">
					<textarea rows="5" cols="40" name="info"><</textarea>
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

















</html>