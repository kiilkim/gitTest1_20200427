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
	������ web.xml �ȸ��� �ȴ� .
	
	annotation ������� �ϸ�ȴ�
	
	
	 -->
	
	
	<h2>�α���</h2>
	

	<form action="LoginProc.do" method="post" id="fr">
	
		<table width="300" border="1">
		
			<tr height="40">
				<td width="120"> ���̵�</td>
				<td width="180"> 
					<input type="text" name="id">
					
				</td>
			
			</tr>
			
			<tr height="40">
				<td width="120"> �н�����</td>
				<td width="180"> 
					<input type="password" name="password">
					
				</td>
			
			</tr>
			
			<tr height="40">
			
				<td align ="center" colspan="2">
				
					<input type="submit" value="�α���">
				
				</td>
			
			</tr>
		
		
		
		
		</table>
	
	
	</form>
	
	
	


</body>














</html>