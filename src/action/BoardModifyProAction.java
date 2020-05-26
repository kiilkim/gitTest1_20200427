package action;

import java.io.PrintWriter;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import svc.BoardModifyProService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardModifyProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 특정 게시물 수정을 위한 Action 클래스
//		System.out.println("BoardModifyProAction");
		ActionForward forward = null;
		
		// 게시물 수정에 필요한 게시물 번호(board_num), 페이지 번호(page) 파라미터 가져오기
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		String page = request.getParameter("page");
//		System.out.println(board_num + ", " + page);
		
		// BoardModifyProService 클래스 인스턴스 생성
		// => isArticleWriter() 메서드를 호출하여 적합한 사용자인지 판별
		// => 파라미터 : 게시물번호(board_num), 패스워드(String), 리턴타입 : boolean(isRightUser)
		BoardModifyProService boardModifyProService = new BoardModifyProService();
		boolean isRightUser = boardModifyProService.isArticleWriter(
											board_num, request.getParameter("board_pass"));
//		System.out.println("isRightUser : " + isRightUser);
		
		// 판별 결과에 따른 처리
		if(!isRightUser) { // 패스워드가 틀릴 경우(= 적합한 사용자가 아닐 경우)
			// 자바스크립트를 사용하여 "수정 권한이 없습니다!" 출력 후 이전페이지로 돌아가기
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('수정 권한이 없습니다!')");
			out.println("history.back()");
			out.println("</script>");
		} else { // 패스워드가 맞을 경우
//			System.out.println("올바른 사용자입니다!");
			
			// BoardBean 객체 생성하여 수정폼에서 입력된 데이터 가져와서 저장
			BoardBean article = new BoardBean();
			article.setBoard_num(board_num);
			article.setBoard_name(request.getParameter("board_name"));
			article.setBoard_subject(request.getParameter("board_subject"));
			article.setBoard_content(request.getParameter("board_content"));
			
			// BoardModifyProSerivce 클래스의 modifyArticle() 메서드 호출하여 글 수정
			// ->어떻게 다른클레스 매서드 불러오지? 클래스 
			
			boolean isModifySuccess = boardModifyProService.modifyArticle(article);
			
			// 수정 결과에 따른 처리
			// => isModifySuccess 가 false 일 경우
			//    자바스크립트 사용하여 "수정 실패!" 출력 후 이전페이지로 이동
			// => 아니면 ActionForward 객체를 사용하여 BoardDetail.bo 로 포워딩
			//    => Redirect 방식 이동(URL 에 파라미터를 함께 전달 => 글번호, 페이지번호)
			if(!isModifySuccess) {
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('수정 실패!')");
				out.println("history.back()");
				out.println("</script>");
			} else {
				
				forward = new ActionForward();
				forward.setRedirect(true);
				forward.setPath("BoardDetail.bo?board_num=" + board_num + "&page=" + page);
			
			} 
		
		}
		
		
		
		return forward;
	}

}





