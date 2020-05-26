package svc;

import java.sql.Connection;
import java.util.ArrayList;

import dao.BoardDAO;
import vo.BoardBean;

import static db.JdbcUtil.*;

public class BoardListService {
	
	// 전체 게시물 목록 갯수 가져오기
	public int getListCount() {
//		System.out.println("BoardListService - getListCount()");
		int listCount = 0;
		
		// 공통작업-1. DB 작업에 필요한 Connection 객체 가져오기
		Connection con = getConnection();
		
		// 공통작업-2. DB 작업을 위한 BoardDAO 객체 생성 => 싱글톤 패턴으로 생성된 객체 가져오기
		BoardDAO boardDAO = BoardDAO.getInstance();
		
		// 공통작업-3. BoardDAO 객체에 Connection 객체 전달
		boardDAO.setConnection(con);
		
		// 4. BoardDAO 클래스의 selectListCount() 메서드를 호출하여 전체 게시물 수 가져오기
		listCount = boardDAO.selectListCount();
		
		// 공통작업-5. Connection 객체 반환
		close(con);
		
		// 6. 작업 결과 리턴
		return listCount;
	}

	public ArrayList<BoardBean> getArticleList(int page, int limit) {
//		System.out.println("BoardListService - getArticleList()");
		
		ArrayList<BoardBean> articleList = null;
		
		// 공통작업-1. DB 작업에 필요한 Connection 객체 가져오기
		Connection con = getConnection();
		
		// 공통작업-2. DB 작업을 위한 BoardDAO 객체 생성 => 싱글톤 패턴으로 생성된 객체 가져오기
		BoardDAO boardDAO = BoardDAO.getInstance();
		
		// 공통작업-3. BoardDAO 객체에 Connection 객체 전달
		boardDAO.setConnection(con);
		
		// 4. BoardDAO 클래스의 selectArticleList() 메서드를 호출하여 
		//    => 페이지번호(page)와 글 갯수(limit) 를 사용하여 
		//       지정된 번호부터 지정된 게시물 갯수만큼 게시물 가져오기
		//    파라미터 : page, limit, 리턴타입 : ArrayList<BoardBean>
		articleList = boardDAO.selectArticleList(page, limit);
		
		
		// 공통작업-5. Connection 객체 반환
		close(con);
		
		
		return articleList;
	}
	
}














