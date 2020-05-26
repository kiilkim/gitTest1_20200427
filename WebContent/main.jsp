<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
    <%
	// 세션 객체에 저장된 아이디 가져오기
	String id = null;

	if(session.getAttribute("id") != null) {
		id = (String)session.getAttribute("id");
	}
	
// 	out.println(id);
	%>   
	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC_Board</title>
</head>
<body>
	<section id="login">
	<!-- 세션에 저장된 ID 가 있을 경우 "로그아웃"(MemberLogout.me) 페이지 링크 -->
	<!-- 세션에 저장된 ID 가 없을 경우 "로그인"(MemberLoginForm.me) 페이지 링크 -->
	<%if(id == null) {%>
		<a href="MemberLoginForm.me">로그인</a> | <a href="MemberJoinForm.me">회원가입</a>
	<%} else { %>
		<%=id %>님 | <a href="MemberLogout.me">로그아웃</a>
	<%} %>
	</section>
	<div align="center"><h1>메인 페이지</h1></div>
	<div align="center">
		<h2><a href="BoardWriteForm.bo">글쓰기 페이지</a>&nbsp;&nbsp;
			<a href="BoardList.bo">글목록 페이지</a></h2>
	</div>
	
	
	
	
	
	
	
	
</body>








</html>