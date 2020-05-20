package svc;

import java.sql.Connection;

import dao.BoardDAO;
//import db.JdbcUtil;
import vo.BoardBean;

// static import 기능을 사용하여 db 패키지의 JdbcUtil 클래스명 내에 있는 메서드를 지정시
// 클래스명 없이 바로 메서드를 호출할 수 있다!
// => db.JdbcUtil.getConnection; 이라고 지정하면 getConnection() 메서드만 호출 가능하지만
//    db.JdbcUtil.*; 형태로 지정하면 JdbcUtil 클래스의 모든 static 메서드를 호출 가능
import static db.JdbcUtil.*;

// Action 클래스로부터 요청받은 작업을 DAO 클래스를 사용하여 처리하고 그 결과를 리턴하는 클래스
public class BoardWriteProService {
	
	// 글 등록 요청 처리를 위한 registArticle() 메서드 정의
	// => 파라미터 : BoardBean 객체(boardBean)
	// => 리턴타입 : boolean
	public boolean registArticle(BoardBean boardBean) { 
		System.out.println("BoardWriteProService - registArticle()");
		
		boolean isWriteSucces = false; // 글 등록 성공 여부를 리턴할 변수
		
		// DB 작업을 위한 준비 => Connection 객체, DAO 객체, DAO 객체의 메서드 호출
		// 1. DB 작업에 필요한 Connection 객체 가져오기
//		Connection con = JdbcUtil.getConnection();
		Connection con = getConnection(); // static import 로 지정된 메서드 호출
		
		// 2. DB 작업을 위한 BoardDAO 객체 생성 => 싱글톤 패턴으로 생성된 객체 가져오기
		BoardDAO boardDAO = BoardDAO.getInstance();
		
		// 3. BoardDAO 객체에 Connection 객체 전달
		boardDAO.setConnection(con);
		
		// 4. BoardDAO 객체의 insertArticle() 메서드를 호출하여 글 등록 처리
		// => 파라미터 : BoardBean 객체, 리턴타입 : int(insertCount)
		int insertCount = boardDAO.insertArticle(boardBean);
		
		// 5. 리턴받은 작업 결과 판별
		// => insertCount 가 0보다 크면 commit() 실행, isWriteSuccess 를 true 로 변경
		// => 아니면, rollback() 실행
		if(insertCount > 0) {
			commit(con);
			isWriteSucces = true;
		} else {
			rollback(con);
		}
		
		// 6. Connection 객체 반환
		close(con);
		
		// 7. 작업 결과 리턴
		return isWriteSucces;
	}

	private void rollback(Connection con) {
		// TODO Auto-generated method stub
		
	}

	private void close(Connection con) {
		// TODO Auto-generated method stub
		
	}

	private void commit(Connection con) {
		// TODO Auto-generated method stub
		
	}

	private Connection getConnection() {
		// TODO Auto-generated method stub
		return null;
	}
	
}





