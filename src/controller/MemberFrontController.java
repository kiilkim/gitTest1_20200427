package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.MemberLoginProAction;
import action.MemberLogoutAction;
import vo.ActionForward;


@WebServlet("*.me") //서블릿 주소중 xxx.me 에 대한 요청 전달받음.
public class MemberFrontController  extends HttpServlet {
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	//자동으로 request, response
	//근데 우린 공통 프로세스 따로 정의 
		

	
	protected void doProcess(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
	
	
		
		// POST 방식 한글 처리
		request.setCharacterEncoding("UTF-8");

		
		// 서블릿 주소 가져오기 뒤에 프로젝트명 제외 슬래시뒤가 서블릿주소. 
		
		String command = request.getServletPath();
		
		System.out.println(command);
				
		// 각 요청을 공통적으로 처리하기 위해 필요한 변수 선언
		Action action = null;
		ActionForward forward = null;
		
		
		//action으로 이동
		//로그인 폼
		if(command.equals("/MemberLoginForm.me")) {
			
			forward = new ActionForward();
			forward.setPath("/member/login_form.jsp");
			
			
			
			
		//회원가입 폼	
		} else if(command.equals("/MemberJoinForm.me")) {
			
			forward = new ActionForward();
			forward.setPath("/member/join_form.jsp");
			
			//데이터가 입력되면 db에 쌓이고, (action?service?)
			//로그인화면 보여줘야 한다.
		
		//
		} else if(command.equals("/MemberLoginPro.me")) {
			
			action = new MemberLoginProAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		} else if(command.equals("/MemberLogout.me")) {
			
			action = new MemberLogoutAction();
			

			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
		if(forward != null) {
			if(forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

		
	
	}
	
}
	
