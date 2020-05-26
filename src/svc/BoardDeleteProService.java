package svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.BoardDAO;


public class BoardDeleteProService {

	public boolean isArticleWriter(int board_num, String board_pass) {
		// TODO Auto-generated method stub
		
		
		
		
		//db작업을 위한 기본 정보
		boolean isArticleWriter = false;
		
		
		
		Connection con = getConnection();
		
		BoardDAO boardDAO = BoardDAO.getInstance();
		
		boardDAO.setConnection(con);
		
		
		
		// BoardDAO 객체의 isArticleBoardWriter() 메서드 호출하여 사용자 판별 수행
		// => 파라미터 : 글번호, 패스워드    리턴타입 : boolean(isArticleWriter)
		isArticleWriter = boardDAO.isArticleBoardWriter(board_num, board_pass);
		
		
		
		
		
		close(con);
		
		return isArticleWriter;
		
		
		
	}

	public boolean removeArticle(int board_num) {
		// TODO Auto-generated method stub
		boolean isDeleteSuccess = false;
		
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		
		// BoardDAO 객체의 deleteArticle() 메서드를 호출하여 삭제 작업 수행
				// => 파라미터 : 글번호      리턴타입 : int(deleteCount)
				int deleteCount = boardDAO.deleteArticle(board_num);
				
				// deleteCount > 0 일 경우 commit, isDeleteSuccess 를 true 로 변경
				// 아니면, rollback 수행
				if(deleteCount > 0) {
					commit(con);
					isDeleteSuccess = true;
				} else {
					rollback(con);
				}
				
				close(con);
				
				return isDeleteSuccess;
			}
}
