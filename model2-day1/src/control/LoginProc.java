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
	
	
	
	
	//�ᱹ�� doPost ��� ����.
	
	//�׷��� ��κ� �ϳ��� ����� . 
	
	
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
		
		request.setAttribute("id", id); //�� id�� �����ϰڴ�. request ��ü�� 
		request.setAttribute("password", password);
		//session���ٰ� �ϸ� ��� �������� ���� 
		
		
		RequestDispatcher dis = request.getRequestDispatcher("LoginProc.jsp");
		
		dis.forward(request, response); 
	}
		
		
	
	
	
		
		
		

}

