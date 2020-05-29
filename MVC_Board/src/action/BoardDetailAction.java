package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BoardDetailService;
import vo.ActionForward;
import vo.BoardBean;

// 목록에서 클릭된 게시물에 대한 상세 내용을 가져오기 위한 BoardDetailAction 클래스 정의
// => 가져온 상세 내용을 qna_board_view.jsp 페이지로 전달 590 jsp책 
public class BoardDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardDetailAction");
		
		ActionForward forward = null;
		
		
		// 파라미터로 전달된 게시물 번호(board_num) 가져오기
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		// board 폴더 내의 qna_board_view.jsp 페이지로 포워딩
		
		// 글목록을 눌렀을 때 
		// boardDetail.bo 인데 주소 유지 되려면 dipatch 방식이다. 
		
		// 게시물 가져오려면 sevice class 필요하다. 
		
		
		forward = new ActionForward();
		forward.setPath("/board/qna_board_view.jsp");
		
		
		
		
		// BoardDetailService 인스턴스 생성 후 0  getArticle() 메서드 호출하여 상세내용 가져오기 ? 
		
		
		BoardDetailService bds = new BoardDetailService(); //인스턴스 
		
		BoardBean article = bds.getArticle(board_num);
		
		// request 객체에 BoardBean 객체 저장
		request.setAttribute("article", article);
		// page 파라미터는 setAttribute() 메서드로 전달하지 않아도 URL 이 유지되므로 생략 가능
//		request.setAttribute("page", request.getParameter("page"));
	
		
		
		
		
		
		
		
		
		return forward;
	}

}
