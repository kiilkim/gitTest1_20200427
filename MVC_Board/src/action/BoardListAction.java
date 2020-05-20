package action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BoardListService;
import vo.ActionForward;
import vo.BoardBean;
import vo.PageInfo;

public class BoardListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardListAction");
		
		ActionForward forward = null;
		
		int page = 1; // ���� ������ ��ȣ�� ������ ����
		int limit = 10; // �� ������ �� ����� �Խù� �� ����
		
		// �Ķ���ͷ� ���޹��� ������ ��ȣ�� ���� ��� �����ͼ� page ������ ����
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page")); // String -> int ��ȯ
		}
		
		// BoardListService Ŭ���� �ν��Ͻ� ����
		// �Խù� ��ü ������ ��ȸ�ϱ� ���� getListCount() �޼��� ȣ���Ͽ� ���� ���Ϲޱ�
		BoardListService boardListService = new BoardListService();
		int listCount = boardListService.getListCount();
		System.out.println("��ü �Խù� �� : " + listCount);
		
		// ������ ���� ��ŭ�� �Խù� ��������
		// BoardListService Ŭ������ getArticleList() �޼��带 ȣ���Ͽ� �Խù� ��� ��������
		// => �Ķ���� : ������������ȣ(page), �� ���� ������ �Խù� �ִ� ����(limit)
		// => ����Ÿ�� : ArrayList<BoardBean> => �Խù� 1�� ������ BoardBean ���׸� Ÿ������ ����
		ArrayList<BoardBean> articleList = boardListService.getArticleList(page, limit);
		
		// ����¡ ó���� ���� ������ �� ���
		// 1. �ִ� ������ ��ȣ ��� : ��ü �Խù� �� / limit ����� �ݿø� ó�� ���� 0.95 ����
		int maxPage = (int)((double)listCount / limit + 0.95);
		
		
		// 2. ���� ���������� ǥ���� ���� ������ ��ȣ ���(1, 11, 21 ��)
		int startPage = (((int)((double)page / 10 + 0.9)) - 1) * 10 + 1;
				// 3. ���� ���������� ǥ���� �� ������ ��ȣ ���(10, 20, 30 ��)
		int endPage = startPage + 10 - 1;
				// ��, �� ������ ��ȣ�� �ִ� ������ ��ȣ���� Ŭ ��� 
				// => �ִ� ������ ��ȣ�� �� ������ ��ȣ�� ���
		if(endPage > maxPage) {
		endPage = maxPage;
		}
				
		// ����¡ ������ ������ PageInfo ��ü ���� �� ������ ����
		PageInfo pageInfo = new PageInfo(page, maxPage, startPage, endPage, listCount);
		
		
		// request ��ü�� PageInfo ��ü�� ArrayList ��ü ����
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("articleList", articleList);
		
		
		
		// ActionForward ��ü�� �����Ͽ� Dispatcher ������� 
		// ������ ��θ� board ���� ���� qna_board_list.jsp �������� ����
		forward = new ActionForward();
		forward.setPath("/board/qna_board_list.jsp");
		
		return forward;
	}

}






