package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.BoardListAction;
import action.BoardWriteProAction;
import vo.ActionForward;

@WebServlet("*.bo") // 서블릿 주소 중 XXX.bo 주소에 대한 요청을 전달받음
public class BoardFrontController extends HttpServlet {
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// POST 방식 한글 처리
		request.setCharacterEncoding("UTF-8");

		// 서블릿 주소 가져오기
		String command = request.getServletPath();
		System.out.println(command);
		
		// 각 요청을 공통적으로 처리하기 위해 필요한 변수 선언
		Action action = null;
		ActionForward forward = null;
		
		// if문을 사용하여 서블릿 주소 판별 및 각 요청 처리를 위한 작업 요청
		if(command.equals("/Main.bo")) {
			// 글쓰기 페이지 요청은 비즈니스 로직이 없는 View 페이지(JSP)로 바로 연결 수행
			// => ActionForward 객체를 생성하여 Dispatcher 방식으로 jsp 페이지로 바로 포워딩
			// => ActionForward 객체의 포워딩 방식을 별도로 설정하지 않음(주소 변경 X)
			forward = new ActionForward();
//			forward.setRedirect(false); // 포워딩 방식을 Dispatcher 방식으로 설정(기본값 생략 가능)
			forward.setPath("/main.jsp"); // 이동할 View 페이지 경로 지정
		} else if(command.equals("/BoardWriteForm.bo")) {
			// 글쓰기 페이지 요청은 비즈니스 로직이 없는 View 페이지(JSP)로 바로 연결 수행
			// => ActionForward 객체를 생성하여 Dispatcher 방식으로 jsp 페이지로 바로 포워딩
			// => ActionForward 객체의 포워딩 방식을 별도로 설정하지 않음(주소 변경 X)
			forward = new ActionForward();
//			forward.setRedirect(false); // 포워딩 방식을 Dispatcher 방식으로 설정(기본값 생략 가능)
			forward.setPath("/board/qna_board_write.jsp"); // 이동할 View 페이지 경로 지정
		} else if(command.equals("/BoardWritePro.bo")) {
			// BoardWriteProAction 클래스 인스턴스 생성 => Action 타입으로 업캐스팅
			action = new BoardWriteProAction();
			// 공통 메서드인 execute() 메서드를 호출하여 request, response 객체 전달
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/BoardList.bo")) {
			// 글 목록 조회를 위해 BoardListAction 클래스 인스턴스 생성 => Action 타입 업캐스팅
			action = new BoardListAction();
			// 공통 메서드인 execute() 메서드를 호출하여 request, response 객체 전달
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		// ActionForward 객체 내의 포워딩 방식에 따라 각각의 포워딩 작업 수행
		if(forward != null) {
			// ActionForward 객체 내의 포워딩 방식(Dispatcher or Redirect) 판별
			if(forward.isRedirect()) { // Redirect 방식
				// Redirect 방식으로 포워딩(주소표시줄 변경 O, request 객체가 공유되지 않음)
				// => response 객체의 sendRedirect() 메서드를 호출하여 포워딩 할 페이지 전달
				response.sendRedirect(forward.getPath());
			} else { // Dispatcher 방식
				// Dispatcher 방식으로 포워딩(주소표시줄 변경 X, request 객체가 공유됨)
				// => request 객체의 getRequestDispatcher() 메서드를 호출하여 포워딩 할 페이지 전달
				//    => RequestDispatcher 객체가 리턴됨
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				// => RequestDispatcher 객체의 forward() 메서드를 호출하여 request, response 객체 전달
				dispatcher.forward(request, response);
			}
		}
		
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}

