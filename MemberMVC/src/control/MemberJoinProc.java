package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.MemberBean;

/**
 * Servlet implementation class MemberJoinProc
 */
@WebServlet("/proc")
public class MemberJoinProc extends HttpServlet {
	

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		reqPro(request,response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		reqPro(request,response);
	}
	
	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		MemberBean bean = new MemberBean();
		bean.setId(request.getParameter("id"));
		
		String pass1 = request.getParameter("pass1");
		String pass2 = request.getParameter("pass2");
		
		bean.setPass1(pass1);
		bean.setPass2(pass2);
		bean.setEmail(request.getParameter("email"));
		
		bean.setTel(request.getParameter("tel"));
		
		String [] arr = request.getParameterValues("hobby");
		String data ="";
		
			for (String string : arr) {
				
				data+=string+" ";
			}
		
		
		bean.setHobby(data);
		bean.setJob(request.getParameter("job"));
		bean.setAge(request.getParameter("age"));
		bean.setInfo(request.getParameter("info"));
		
		
		//�н����尡 ���� ��쿡�� ������ ���̽��� ����
		
		if(pass1 == pass2) {
			
			//�����ͺ��̽� ��ü����
			memberDAO mdao = new MemberDAO();
			mdao.insertMember(bean);
			RequestDispatcher dis = request.getRequestDispatcher("MemberList.jsp");
			dis.forward(request, response);
			
			
			
		} else {
			
			request.setAttribute("msg", "�н����尡 ��ġ���� �ʽ��ϴ�.");
			RequestDispatcher dis = request.getRequestDispatcher("LoginError.jsp");
			dis.forward(request, response);
			
			
		}
		
		
		
		//������ ���̽� ��ü ���� 
		
		
		
		
		
	}
	
	

}
