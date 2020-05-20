package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.MemberBean;

/**
 * Servlet implementation class MemberJoinProc2
 */
@WebServlet("/Mproc2")
public class MemberJoinProc2 extends HttpServlet {

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}
	
	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//어쩔땐 get 방식 어쩔때는 post 방식 하나의 컨트롤러로 조절
		
	
			//String id = request.getParameter("id");
			
			//이렇게 일일이 받아야 한다..그래서 beanCLass만들어야한다.
			//model이라는 곳에 
			//jsp 땐 useBean 이 있었는데,, 여기선,, 없다. 
		
			
			MemberBean bean = new MemberBean();
			
			bean.setId(request.getParameter("id"));
			bean.setPassword(request.getParameter("password"));
			bean.setEmail(request.getParameter("email"));
			bean.setTel(request.getParameter("tel"));
			bean.setAddress(request.getParameter("address"));
		
			//request객체에 bean 클래스를 추가해야한다.
			
			request.setAttribute("bean", bean); //object든 뭐든 넘어갈 수 있으니
			
			RequestDispatcher dis = request.getRequestDispatcher("MemberVeiw.jsp");
			
			dis.forward(request, response);
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
