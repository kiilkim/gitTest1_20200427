package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BoardDetailService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardModifyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 특정 게시물 수정을 위한 기존 게시물 정보 가져오기 위한 Action 클래스
//		System.out.println("BoardModifyFormAction");
		ActionForward forward = null;
		
		// 게시물 수정에 필요한 게시물 번호(board_num) 파라미터 가져오기
		int board_num = Integer.parseInt(request.getParameter("board_num"));
//		System.out.println(board_num);
		
		// BoardDetailService 클래스의 인스턴스 생성 
		BoardDetailService boardDetailService = new BoardDetailService();
		
		// getArticle() 메서드 호출하여 게시물 정보 가져오기
		BoardBean article = boardDetailService.getArticle(board_num);
		
		// request 객체에 BoardBean 객체 저장
		request.setAttribute("article", article);
		
		// ActionForward 객체에 포워딩 경로를 board 폴더 내의 qna_board_modify.jsp 로 지정
		forward = new ActionForward();
		forward.setPath("/board/qna_board_modify.jsp");
		
		return forward;
	}

}









