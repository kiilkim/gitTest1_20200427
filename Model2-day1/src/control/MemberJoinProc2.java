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

		//��¿�� get ��� ��¿���� post ��� �ϳ��� ��Ʈ�ѷ��� ����
		
	
			//String id = request.getParameter("id");
			
			//�̷��� ������ �޾ƾ� �Ѵ�..�׷��� beanCLass�������Ѵ�.
			//model�̶�� ���� 
			//jsp �� useBean �� �־��µ�,, ���⼱,, ����. 
		
			
			MemberBean bean = new MemberBean();
			
			bean.setId(request.getParameter("id"));
			bean.setPassword(request.getParameter("password"));
			bean.setEmail(request.getParameter("email"));
			bean.setTel(request.getParameter("tel"));
			bean.setAddress(request.getParameter("address"));
		
			//request��ü�� bean Ŭ������ �߰��ؾ��Ѵ�.
			
			request.setAttribute("bean", bean); //object�� ���� �Ѿ �� ������
			
			RequestDispatcher dis = request.getRequestDispatcher("MemberVeiw.jsp");
			
			dis.forward(request, response);
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
