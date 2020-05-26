package svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.BoardDAO;
import vo.BoardBean;

public class BoardModifyProService {

	public boolean isArticleWriter(int board_num, String board_pass) {
		
//		System.out.println("BoardModifyProService - isArticleWriter()");
		boolean isArticleWriter = false;
		
//		System.out.println("board_num = " + board_num + ", board_pass = " + board_pass);
		
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		
		// BoardDAO 클래스의 isArticleBoardWriter() 메서드를 호출하여 적합한 사용자인지 판별
		// => 파라미터 : 게시물번호(board_num), 패스워드(board_pass)  
		//    리턴타입 : boolean(isArticleWriter)
		isArticleWriter = boardDAO.isArticleBoardWriter(board_num, board_pass);
		
		close(con);
		
		return isArticleWriter;
	}

	//글수정
	
		public boolean modifyArticle(BoardBean article) {
			System.out.println("BoardModifyProService - modifyArticle()");
			boolean isModifySuccess = false;
			
			Connection con = getConnection();
			BoardDAO boardDAO = BoardDAO.getInstance();
			boardDAO.setConnection(con);
			
			
//			System.out.println("수정할 제목 : " + article.getBoard_subject());
			
			// BoardDAO 클래스의 updateArticle() 메서드를 호출하여 게시물 수정
			// => 파라미터 : BoardBean, 리턴타입 : int(updateCount)
			int updateCount = boardDAO.updateArticle(article);
			
			// 리턴받은 updateCount 가 0보다 크면 commit, isModifySuccess 를 true 로 변경
			// 아니면 rollback 수행
			if(updateCount > 0) {
				commit(con);
				isModifySuccess = true;
			} else {
				rollback(con);
			}
			
			
			close(con);
			
			return isModifySuccess;
		}

}
