package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.MemberJoinProService;
import vo.ActionForward;
import vo.MemberBean;

public class MemberJoinProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		System.out.println("MemberJoinProAction");
		
		ActionForward forward = null;
		
		// 전달받은 회원가입 정보를 저장할 MemberBean 객체를 생성하여 데이터 저장
		MemberBean member = new MemberBean(
				request.getParameter("name"), 
				request.getParameter("id"), 
				request.getParameter("pass"), 
				Integer.parseInt(request.getParameter("age")), 
				request.getParameter("email"), 
				request.getParameter("gender").charAt(0));
		
		// MemberJoinProService 클래스의 인스턴스 생성하여 registMember() 메서드 호출
		// 파라미터 : MemberBean    리턴타입 : boolean(isJoinSuccess)
		MemberJoinProService memberJoinProService = new MemberJoinProService();
		boolean isJoinSuccess = memberJoinProService.registMember(member);
		
		// isJoinSuccess 가 false 이면 자바스크립트를 사용하여 
		// "회원가입 실패!" 출력 후 이전페이지로 돌아가기
		// 아니면, 메인페이지로 이동
		if(!isJoinSuccess) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('회원가입 실패!')");
			out.println("history.back()");
			out.println("</script>");
		} else {
			forward = new ActionForward();
			forward.setRedirect(true);
			forward.setPath("./");
		}
		
		
		
		return forward;
	}

}














