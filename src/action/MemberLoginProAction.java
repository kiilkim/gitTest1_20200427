package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDAO;
import svc.MemberLoginProService;
import vo.ActionForward;
import vo.LoginException;
 



public class MemberLoginProAction implements Action {

	
	@Override
	 public ActionForward execute(HttpServletRequest request,
	            HttpServletResponse response) throws Exception {

		System.out.println("MemberLoginProAction");
		
		
		ActionForward forward = null;
		
		
		 // 아이디와 비밀번호를 가져온다.
        String id = request.getParameter("id");
        String pass = request.getParameter("pass");
        
        System.out.println(id+pass);
        
       
        
        // MemberLoginProService 클래스 인스턴스 생성 후
        MemberLoginProService mlps = new MemberLoginProService();
        
     	// 로그인 요청을 위한 isLoginMember() 메서드 호출 (어디서부터?)
      
        
        
        
        // => 파라미터 : 아이디, 패스워드    리턴타입 : boolean(isMember)
		
        // isMember 가 false 일 경우
     	// 자바스크립트를 활용하여 "로그인 실패!" 출력 후 이전페이지로 이동
     	// 아니면 index.jsp 페이지로 이동
        
        
        try {
			boolean isMember = mlps.isLoginMember(id, pass);
			

			// LoginException 예외가 발생하지 않으면 로그인 성공 처리
			if(isMember) {
				
				// 세션 객체를 사용하여 로그인에 성공한 아이디를 저장
				// => request 객체로부터 HttpSession 객체 가져와서 setAttribute() 호출하여 저장
				HttpSession session = request.getSession();
				session.setAttribute("id", id);
				
				forward = new ActionForward();
				forward.setRedirect(true);
				forward.setPath("./");
				
			}
			
			
			
			
			
		} catch (LoginException e) {
			
			// LoginException 예외가 발생하여 전달되면 catch 구문 실행됨
			// => 전달받은 예외 객체의 메세지를 자바스크립트로 출력하면
			//    로그인 실패에 대한 결과 메세지 출력 구분 가능
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('로그인 실패! - "+e.getMessage() +"')");
			out.println("history.back()");
			out.println("</script>");
		}		
        
        
        
      
        
        return forward;
        
        
        
        
        
        
        
   
        
        
        
		
	}
}
