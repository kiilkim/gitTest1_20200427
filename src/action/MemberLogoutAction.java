package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import vo.ActionForward;

// 로그아웃
public class MemberLogoutAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("MemberLogoutAction");
		
		ActionForward forward = null;
		
		// HttpSession 객체 가져와서 세션 초기화
		HttpSession session = request.getSession();
		session.invalidate();
		
		// 메인페이지로 포워딩
		forward = new ActionForward();
		forward.setRedirect(true);
		forward.setPath("./");
		
		return forward;
	}

}
