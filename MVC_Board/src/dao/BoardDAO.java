package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static db.JdbcUtil.*;
import vo.BoardBean;

public class BoardDAO {
	/*
	 * ------------ 싱글톤 디자인 패턴을 활용한 BoardDAO 인스턴스 작업 -------------
	 * 1. 외부에서 인스턴스 생성이 불가능하도록 private 접근제한자를 사용하여 생성자 정의
	 * 2. 직접 인스턴스를 생성하여 변수(instance)에 저장
	 * 3. 외부에서 인스턴스를 전달받을 수 있도록 Getter 메서드 정의
	 * 4. getInstance() 메서드에 인스턴스 생성없이 접근 가능하도록 static 메서드로 정의
	 *    => 메서드 내에서 접근하는 멤버변수 instance 도 static 변수로 정의
	 * 5. 인스턴스를 통해 instance 변수에 접근 불가능하도록 접근제한자 private 지정
	 */
	private BoardDAO() {}
	
	private static BoardDAO instance;

	public static BoardDAO getInstance() {
		// BoardDAO 객체가 없을 경우에만 생성
		if(instance == null) {
			instance = new BoardDAO();
		}
		
		return instance;
	}
	// ---------------------------------------------------------------------------------
	
	Connection con; // Connection 객체 전달받아 저장할 변수 선언
	
	// Service 클래스로부터 Connection 객체를 전달받는 메서드 setConnection() 정의
	public void setConnection(Connection con) {
		this.con = con;
	}

	// 게시물 등록
	public int insertArticle(BoardBean boardBean) {
		// Service 클래스로부터 BoardBean 객체를 전달받아 DB 에 INSERT 작업 수행
		// => 수행 결과 값으로 int형 insertCount 를 리턴받아 다시 Service 클래스로 리턴
		int insertCount = 0;
		
		// DB 작업에 필요한 변수 선언(Connection 객체는 이미 외부로부터 전달받음)
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			int num = 1; // 새 게시물 번호를 저장할 변수(초기값으로 초기번호 1번 설정)
			
			// 현재 게시물의 번호 중 가장 큰 번호(최대값)를 조회하여 다음 새 글 번호 결정(+1)
			String sql = "SELECT MAX(board_num) FROM board"; // 최대값 조회 쿼리문
			
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) { // 등록된 게시물이 하나라도 존재할 경우 게시물 번호 조회 성공
				// 조회된 번호 + 1 을 수행하여 새 글 번호로 저장
				num = rs.getInt(1) + 1;
			} 
			// => 만약, 조회가 실패할 경우 새 글 번호는 1번이므로 기본값 num 그대로 사용
			
			// 전달받은 데이터와 계산된 글 번호를 사용하여 INSERT 작업 수행
			// => board_re_ref 는 새 글 번호, board_re_lev ,board_re_seq, board_readcount 는 0
			// => 마지막 파라미터인 board_date(게시물 작성일) 값은 DB의 now() 함수를 사용하여
			//    DB 서버의 시스템 현재 시각 가져오기
			sql = "INSERT INTO board VALUES (?,?,?,?,?,?,?,?,?,?,now())";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num); // 계산된 새 글 번호 사용
			pstmt.setString(2, boardBean.getBoard_name());
			pstmt.setString(3, boardBean.getBoard_pass());
			pstmt.setString(4, boardBean.getBoard_subject());
			pstmt.setString(5, boardBean.getBoard_content());
			pstmt.setString(6, boardBean.getBoard_file());
			pstmt.setInt(7, num); // board_re_ref(새 글 번호 그대로 지정)
			pstmt.setInt(8, 0); // board_re_lev
			pstmt.setInt(9, 0); // board_re_seq
			pstmt.setInt(10, 0); // board_readcount
			
			// INSERT 구문 실행 후 리턴되는 결과값을 insertCount 변수에 저장
			insertCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
//			e.printStackTrace();
			System.out.println("BoardDAO - insertArticle() 실패! : " + e.getMessage());
		} finally {
			// DB 자원 반환
			// => 주의! Connection 객체는 Service 클래스에서 별도로 사용해야하므로 닫으면 안됨!
//			JdbcUtil.close(rs);
//			JdbcUtil.close(pstmt);
			// => static import 기능을 사용하여 db.JdbcUtil 클래스 내의 모든 static 멤버 import
			close(rs);
			close(pstmt);
		}
		
		return insertCount;
	}

	
	// 전체 게시물 수 계산
	public int selectListCount() {
		int listCount = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// board_num 컬럼의 전체 갯수를 조회하기(모든 컬럼을 뜻하는 * 기호 사용해도 됨)
			String sql = "SELECT COUNT(board_num) FROM board"; // COUNT() 함수 사용
			
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				listCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("BoardDAO - selectListCount() 실패! : " + e.getMessage());
		} finally {
			// DB 자원 반환
			close(rs);
			close(pstmt);
		}
		
		return listCount;
	}

	public ArrayList<BoardBean> selectArticleList(int page, int limit) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int startRow = (page - 1) * limit; // 시작행번호 계싼 
		
		// 전체 게시물을 저장할 ArrayList 객체 생성 => 제네릭 타입 BoardBean 타입 지정
		ArrayList<BoardBean> articleList = new ArrayList<BoardBean>();
		
		// 게시물 갯수 조회할 SQL 구문 작성
		// => 정렬 : board_re_ref 기준 내림차순, board_re_seq 기준 오름차순
		// => limit : 시작 행 번호부터 지정된 게시물 갯수만큼 제한
		
		
		try {
			// 게시물 갯수 조회할 SQL 구문 작성
			// => 정렬 : board_re_ref 기준 내림차순, board_re_seq 기준 오름차순
			// => limit : 시작 행 번호부터 지정된 게시물 갯수만큼 제한
			String sql = "SELECT * FROM board "
					+ "ORDER BY board_re_ref DESC, board_re_seq ASC LIMIT ?,?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				//1개 레코드(게시물)를 저장할 BoardBean 객체 생성
				
		
				
				
				// 1개 레코드(게시물)를 저장할 BoardBean 객체 생성
				BoardBean article = new BoardBean();
				// BoardBean 객체에 조회된 레코드(게시물) 정보를 저장
				article.setBoard_num(rs.getInt("board_num"));
				article.setBoard_name(rs.getString("board_name"));
				article.setBoard_subject(rs.getString("board_subject"));
				article.setBoard_content(rs.getString("board_content"));
				article.setBoard_file(rs.getString("board_file"));
				article.setBoard_re_ref(rs.getInt("board_re_ref"));
				article.setBoard_re_lev(rs.getInt("board_re_lev"));
				article.setBoard_re_seq(rs.getInt("board_re_seq"));
				article.setBoard_readcount(rs.getInt("board_readcount"));
				article.setBoard_date(rs.getDate("board_date"));
				
				// 전체 레코드 저장하는 ArrayList 객체에 1개 레코드를 저장한 BoardBean 객체 전달
				articleList.add(article);
				
				
				
				
			}
			
			
			
			
		} catch (SQLException e) {
			System.out.println("BoardDAO - selectArticleList() 실패! : " + e.getMessage());
		} finally {
			// DB 자원 반환
			close(rs);
			close(pstmt);
		}
		
		
		
		
		return articleList;
	}
	
	

	public BoardBean selectArticle(int board_num) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		BoardBean article = null;
		
		//게시물 번호에 해당하는 게시물 상세 내용 조회 boardBean 객체에 저장
		
		// 게시물 번호(board_num)에 해당하는 게시물 상세 내용 조회 후 BoardBean 객체에 저장
				try {
					String sql = "SELECT * FROM board WHERE board_num=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, board_num);
					rs = pstmt.executeQuery();
					
					// 게시물이 존재할 경우 BoardBean 객체에 모든 데이터 저장
					if(rs.next()) {
						article = new BoardBean();
						article.setBoard_num(rs.getInt("board_num"));
						article.setBoard_name(rs.getString("board_name"));
						article.setBoard_subject(rs.getString("board_subject"));
						article.setBoard_content(rs.getString("board_content"));
						article.setBoard_file(rs.getString("board_file"));
						article.setBoard_re_ref(rs.getInt("board_re_ref"));
						article.setBoard_re_lev(rs.getInt("board_re_lev"));
						article.setBoard_re_seq(rs.getInt("board_re_seq"));
						article.setBoard_readcount(rs.getInt("board_readcount"));
						article.setBoard_date(rs.getDate("board_date"));
//						System.out.println(rs.getString("board_content"));
					}
					
				} catch (SQLException e) {
					System.out.println("selectArticle() 오류 - " + e.getMessage());
				} finally {
					close(rs);
					close(pstmt);
				}
		
		
		
		
		return article;
	}
	
	

	public int updateReadcount(int board_num) {
		// TODO Auto-generated method stub
		
		int updateCount = 0;
		
		// 게시물 번호(board_num) 에 해당하는 레코드의 board_readcount 값을 1 증가시킴
		PreparedStatement pstmt = null;
		
		
		
		try {
			// 조회수를 증가시킬 게시물 번호(board_num)를 SQL 구문 작성 시 바로 결합해도 되고
//			String sql = "UPDATE board SET board_readcount=board_readcount+1 WHERE board_num=" + board_num;
			
			// 만능문자(?)를 사용하여 파라미터 값 지정해도 됨(setXXX() 필수)
			String sql = "UPDATE board SET board_readcount=board_readcount+1 WHERE board_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			updateCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("updateReadcount() 오류 - " + e.getMessage());
		} finally {
			close(pstmt);
		}
		
		
		
		
		return 0;
	}
	
	
	
	
}









