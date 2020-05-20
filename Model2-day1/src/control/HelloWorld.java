package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Hello") //url 매핑 반드시 / 필요.
public class HelloWorld extends HttpServlet {
	// /HelloWorld라고 주소 url 표시해주어야 이 서블릿 클래스가 실행
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		reqPro(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		reqPro(request, response);
	
	}

	
	//일괄처리 doget이던 dopost 던 reqPro 라는 메서드가 실행되게해준다. 
	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//화면에 helloworld라고 출력을 하고 싶다. 문제는?
		
		//jsp 쪽으로 넘겨질 데이터를 설정
		
		String msg = "Hello World ~~하이";
		
		Integer data = 12;
		
		//jsp 쪽으로 데이터를 request에 부착하여 넘겨줌.
		
		request.setAttribute("msg", msg); //setAttri는 object라 모두 넘겨짐 
		
		request.setAttribute("data", data);
		
		//servlet에서 jsp를 호출하면서 데이터 같이 넘기려는 객체를 선언해야한다
		
		RequestDispatcher rd = request.getRequestDispatcher("HelloWorld.jsp");//jsp파일명 기술		
		
		rd.forward(request, response);
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
