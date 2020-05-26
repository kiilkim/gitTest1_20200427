package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BoardDeleteProService;
import vo.ActionForward;

public class BoardDeleteProAction implements Action {

	
	private boolean isDeleteSuccess;

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardDeleteProAction");
		
		ActionForward forward = null;
		
		// 전달받은 게시물 번호와 패스워드를 사용하여 적합한 사용자인지 판별
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		String board_pass = request.getParameter("board_pass");
		System.out.println(board_num + ", " + board_pass);
		
		
		
		//BoardDeleteProService 인스턴스 생성
		BoardDeleteProService boardDeleteProService = new BoardDeleteProService();
		
		
		
		// isArticleWriter() 메서드를 호출하여 적합한 사용자인지 판별
		
		boolean isArticleWriter = boardDeleteProService.isArticleWriter(board_num, board_pass);
		
		
		// => 파라미터 : 글번호, 패스워드     리턴타입 : boolean(isArticleWriter)
		
		// 만약, isArticleWriter 가 false 일 경우
		// 자바스크립트를 사용하여 "삭제 권한이 없습니다!" 출력 후 이전페이지로 돌아가기
		
		if(!isArticleWriter) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('삭제 권한이 없습니다!')");
			out.println("history.back()");
			out.println("</script>");
		} else {
			 // isArticleWriter 가 true 일 경우
			// removeArticle() 메서드를 호출하여 글 삭제 작업 수행
			// => 파라미터 : 글번호   리턴타입 : boolean(isDeleteSuccess)
			boolean isDeleteSuccess = boardDeleteProService.removeArticle(board_num);
			
			// 만약, isDeleteSuccess 가 false 일 경우
			// 자바스크립트를 사용하여 "삭제 실패!" 출력 후 이전페이지로 돌아가기
			// 아니면 ActionForward 객체를 생성하여 BoardList.bo 서블릿 주소 지정(Redirect)
			// => 포워딩 시 URL 주소 뒤에 page 파라미터 결합하여 전달
			if(!isDeleteSuccess) {
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('삭제 실패!')");
				out.println("history.back()");
				out.println("</script>");
			} else {
				forward = new ActionForward();
				forward.setRedirect(true);
				forward.setPath("BoardList.bo?page=" + request.getParameter("page"));
			}
			
		}
		
			

		
	
		
		return forward;
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	

	
	
	
	
	
	
	
	
}
