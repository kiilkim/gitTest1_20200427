<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
   
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>


<!-- 변수선언 방법 -->

<c:set var="i" value="4">




</c:set>



<c:if test=" ${i > 3}">


	안녕하세요d

</c:if>



<!-- 반복문 java의 for문과 비슷 -->








</body>
</html>