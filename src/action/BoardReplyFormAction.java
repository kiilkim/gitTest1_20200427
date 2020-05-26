package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BoardDeleteProService;
import svc.BoardDetailService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardReplyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 답변글 작성을 위한 원본 게시물 정보 가져오기
		System.out.println("BoardReplyFormAction");
		
		ActionForward forward = null;
		
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		
		// BoardDetailService 클래스의 인스턴스 생성
		BoardDetailService boardDetailService = new BoardDetailService();
		
		// getArticle() 메서드를 호출하여 원본 게시물 정보 가져오기
		// => 파라미터 : 글번호     리턴타입 : BoardBean(article)
		BoardBean article = boardDetailService.getArticle(board_num);
		
		// request 객체에 BoardBean 객체 저장(article)
		request.setAttribute("article", article);
		
		// board 폴더 내의 qna_board_reply.jsp 페이지로 포워딩 => Dispatcher 방식
		forward = new ActionForward();
		forward.setPath("/board/qna_board_reply.jsp");
		
		return forward;
	}

}













