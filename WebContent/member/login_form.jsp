<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
#loginFormArea {


	margin : auto;
	width: 400px;
	height : 400px;
	border : 2px double purple;
	border-radius : 10px;
	text-align : center;
	
}


fieldset {

	text-align : center;
	border : none;
	
}

table {


	width : 380px;
	margin : auto;
	
}

.td_left {
	width: 180px;
}

.td_right {
	width: 200px;
}

</style>

</head>
<body>

<section id ="loginFormArea">
<h2>로그인 양식</h2>
	
	
	<form action="MemberLoginPro.me" method="post">  <!--
	
	 action에 서블릿매핑주소 
	 
	 proc.do?gubun=1 라고 해서 
	 
	 sevlet 가서 구분 에 따라 뭐 2에따라 뭐 이렇게 가능
	 
	 -->
	<fieldset>
		<table width="500" border="1" bordercolor="black">
		
			<tr height="50">
			
				<td class="td_left" width="150" align="center">아이디</td>
				<td class="td_left" width="150" align="center">
					<input type="text" name="id" size="40" placeholder="id넣으세요">
				</td>
				
			</tr>
			
			<tr height="50">
			
				<td class="td_left" width="150" align="center">패스워드</td>
				<td class="td_left" width="150" align="center">
					<input type="password" name="pass" size="40" placeholder="비밀번호를 넣으세요">
				</td>
				
			</tr>
			
			
			
			<tr height="50">
				<td align="center" colspan="2">
					<input type="submit" value="로그인">
					<input type="button" value="회원가입" onclick="join_form.jsp">
					<input type="reset" value="취소">
				</td>
			</tr>
			
		
		
		</table>
	
	</fieldset>
	</form>
</section>


</body>
</html>