package svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import dao.BoardDAO;
import vo.BoardBean;

public class BoardDetailService {
	// Action 클래스로부터 요청받은 작업을 DAO 클래스를 사용하여 처리하고 그 결과를 리턴하는 클래스
	
	public BoardBean getArticle(int board_num) {
//		System.out.println("BoardDetailService - getArticle()");
//		System.out.println("board_num = " + board_num);
		
		BoardBean article = null;
		
		// DB 작업을 위한 준비 => Connection 객체, DAO 객체, DAO 객체의 메서드 호출
		// 공통작업-1. DB 작업에 필요한 Connection 객체 가져오기
		Connection con = getConnection(); // static import 로 지정된 메서드 호출
		
		// 공통작업-2. DB 작업을 위한 BoardDAO 객체 생성 => 싱글톤 패턴으로 생성된 객체 가져오기
		BoardDAO boardDAO = BoardDAO.getInstance();
		
		// 공통작업-3. BoardDAO 객체에 Connection 객체 전달
		boardDAO.setConnection(con);
		
		// 4. 게시물 상세 내용 조회 및 조회수 증가
		article = boardDAO.selectArticle(board_num);
		
		if(article != null) {
			
			int updateCount = boardDAO.updateReadcount(board_num);
			
			if(updateCount > 0) {
				
				commit(con);
			
				
			} else {
				
				rollback(con);
			}
			
		}
		
		
		
		
		// 공통작업-5. Connection 객체 반환
		close(con);
		
		return article;
	}
	
}
	


