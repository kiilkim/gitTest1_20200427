package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginProc
 */
@WebServlet("/LoginProc.do")
public class LoginProc extends HttpServlet {
	
	
	
	
	//결국은 doPost 방식 쓴다.
	
	//그러나 대부분 하나더 만든다 . 
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		reqPro(request, response);
	
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		reqPro(request, response);
	}
		
		
	public void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id =request.getParameter("id");
		String password =request.getParameter("password");
		
		
		System.out.println("id="+id);
		
		request.setAttribute("id", id); //이 id를 저장하겠다. request 객체에 
		request.setAttribute("password", password);
		//session에다가 하면 모든 페이지에 공유 
		
		
		RequestDispatcher dis = request.getRequestDispatcher("LoginProc.jsp");
		
		dis.forward(request, response); 
	}
		
		
	
	
	
		
		
		

}

