package svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import dao.BoardDAO;
import vo.BoardBean;

public class BoardDetailService {
	// Action Ŭ�����κ��� ��û���� �۾��� DAO Ŭ������ ����Ͽ� ó���ϰ� �� ����� �����ϴ� Ŭ����
	
	public BoardBean getArticle(int board_num) {
//		System.out.println("BoardDetailService - getArticle()");
//		System.out.println("board_num = " + board_num);
		
		BoardBean article = null;
		
		// DB �۾��� ���� �غ� => Connection ��ü, DAO ��ü, DAO ��ü�� �޼��� ȣ��
		// �����۾�-1. DB �۾��� �ʿ��� Connection ��ü ��������
		Connection con = getConnection(); // static import �� ������ �޼��� ȣ��
		
		// �����۾�-2. DB �۾��� ���� BoardDAO ��ü ���� => �̱��� �������� ������ ��ü ��������
		BoardDAO boardDAO = BoardDAO.getInstance();
		
		// �����۾�-3. BoardDAO ��ü�� Connection ��ü ����
		boardDAO.setConnection(con);
		
		// 4. �Խù� �� ���� ��ȸ �� ��ȸ�� ����
		article = boardDAO.selectArticle(board_num);
		
		if(article != null) {
			
			int updateCount = boardDAO.updateReadcount(board_num);
			
			if(updateCount > 0) {
				
				commit(con);
			
				
			} else {
				
				rollback(con);
			}
			
		}
		
		
		
		
		// �����۾�-5. Connection ��ü ��ȯ
		close(con);
		
		return article;
	}
	
}
	


