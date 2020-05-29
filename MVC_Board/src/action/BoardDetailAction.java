package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BoardDetailService;
import vo.ActionForward;
import vo.BoardBean;

// ��Ͽ��� Ŭ���� �Խù��� ���� �� ������ �������� ���� BoardDetailAction Ŭ���� ����
// => ������ �� ������ qna_board_view.jsp �������� ���� 590 jspå 
public class BoardDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardDetailAction");
		
		ActionForward forward = null;
		
		
		// �Ķ���ͷ� ���޵� �Խù� ��ȣ(board_num) ��������
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		// board ���� ���� qna_board_view.jsp �������� ������
		
		// �۸���� ������ �� 
		// boardDetail.bo �ε� �ּ� ���� �Ƿ��� dipatch ����̴�. 
		
		// �Խù� ���������� sevice class �ʿ��ϴ�. 
		
		
		forward = new ActionForward();
		forward.setPath("/board/qna_board_view.jsp");
		
		
		
		
		// BoardDetailService �ν��Ͻ� ���� �� 0  getArticle() �޼��� ȣ���Ͽ� �󼼳��� �������� ? 
		
		
		BoardDetailService bds = new BoardDetailService(); //�ν��Ͻ� 
		
		BoardBean article = bds.getArticle(board_num);
		
		// request ��ü�� BoardBean ��ü ����
		request.setAttribute("article", article);
		// page �Ķ���ʹ� setAttribute() �޼���� �������� �ʾƵ� URL �� �����ǹǷ� ���� ����
//		request.setAttribute("page", request.getParameter("page"));
	
		
		
		
		
		
		
		
		
		return forward;
	}

}
