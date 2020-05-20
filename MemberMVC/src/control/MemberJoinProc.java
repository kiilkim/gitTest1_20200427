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
		
		
		//패스워드가 같을 경우에만 데이터 베이스에 저장
		
		if(pass1 == pass2) {
			
			//데이터베이스 객체생성
			memberDAO mdao = new MemberDAO();
			mdao.insertMember(bean);
			RequestDispatcher dis = request.getRequestDispatcher("MemberList.jsp");
			dis.forward(request, response);
			
			
			
		} else {
			
			request.setAttribute("msg", "패스워드가 일치하지 않습니다.");
			RequestDispatcher dis = request.getRequestDispatcher("LoginError.jsp");
			dis.forward(request, response);
			
			
		}
		
		
		
		//데이터 베이스 객체 선언 
		
		
		
		
		
	}
	
	

}
