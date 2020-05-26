package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BoardDetailService;
import vo.ActionForward;
import vo.BoardBean;

// 목록에서 클릭된 게시물에 대한 상세 내용을 가져오기 위한 BoardDetailAction 클래스 정의
// => 가져온 상세 내용을 qna_board_view.jsp 페이지로 전달
public class BoardDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		System.out.println("BoardDetailAction");
		
		ActionForward forward = null;
		
		// 파라미터로 전달된 게시물 번호(board_num) 가져오기
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		
		// BoardDetailService 인스턴스 생성 후 getArticle() 메서드 호출하여 상세내용 가져오기
		// => 파라미터 : 게시물 번호(board_num), 리턴타입 : BoardBean(article)
		BoardDetailService boardDetailService = new BoardDetailService();
		BoardBean article = boardDetailService.getArticle(board_num);
		
		// request 객체에 BoardBean 객체 저장
		request.setAttribute("article", article);
		// page 파라미터는 setAttribute() 메서드로 전달하지 않아도 URL 이 유지되므로 생략 가능
//		request.setAttribute("page", request.getParameter("page"));
		
		// board 폴더 내의 qna_board_view.jsp 페이지로 포워딩
		// => 요청된 서블릿 주소가 유지되므로 Dispatcher 방식으로 포워딩
		forward = new ActionForward();
		forward.setPath("/board/qna_board_view.jsp");
		
		return forward;
	}

}








