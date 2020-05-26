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

	// 지정된 갯수 만큼의 게시물 목록 가져오기
	// => 파라미터로 현재 페이지번호(page) 와 가져올 게시물 수(limit) 전달받음
	public ArrayList<BoardBean> selectArticleList(int page, int limit) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int startRow = (page - 1) * limit; // 가져올 게시물에 대한 시작 행 번호 계산
		
		// 전체 게시물을 저장할 ArrayList 객체 생성 => 제네릭 타입 BoardBean 타입 지정
		ArrayList<BoardBean> articleList = new ArrayList<BoardBean>();
		
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
			
			// 조회된 레코드 만큼 반복
			while(rs.next()) {
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

	
	// 게시물 상세 정보 가져오기
	public BoardBean selectArticle(int board_num) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		BoardBean article = null;
		
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
//				System.out.println(rs.getString("board_content"));
			}
			
		} catch (SQLException e) {
			System.out.println("BoardDAO - selectArticle() 실패 : " + e.getMessage());
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return article;
	}

	// 게시물 조회수 증가
	public int updateReadcount(int board_num) {
		int updateCount = 0;
		
		PreparedStatement pstmt = null;
		
		try {
			// 게시물 번호(board_num) 에 해당하는 레코드의 board_readcount 값을 1 증가시킴
			String sql = "UPDATE board SET board_readcount=board_readcount+1 WHERE board_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			updateCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("BoardDAO - updateReadcount() 실패 : " + e.getMessage());
		} finally {
			close(pstmt);
		}
		
		return updateCount;
	}

	
	// 게시물 작업을 위한 적합한 사용자 판별(게시물 패스워드 일치여부 확인)
	public boolean isArticleBoardWriter(int board_num, String board_pass) {
		boolean isArticleWriter = false;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// 글번호(board_num) 에 해당하는 게시물의 패스워드를 조회
			// => 입력받은 패스워드(board_pass)와 일치 여부 확인
			// 만약, 패스워드가 일치하는 경우 isArticleWriter 를 true 로 변경
			String sql = "SELECT board_pass FROM board WHERE board_num=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) { // 게시물이 존재할 경우
				if(board_pass.equals(rs.getString(1))) { // 패스워드 비교하여 일치하면
					isArticleWriter = true; // true 값 설정
				}
			}
			
		} catch (SQLException e) {
			System.out.println("BoardDAO - isArticleBoardWriter() 실패 : " + e.getMessage());
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return isArticleWriter;
	}

	
	// 글 수정 작업
	public int updateArticle(BoardBean article) {
		int updateCount = 0;
		
		PreparedStatement pstmt = null;
		
		try {
			// 글번호(board_num)에 해당하는 게시물의 작성자, 제목, 내용을 수정
			// BoardBean 객체에 저장되어 있는 board_name, board_subject, board_content 사용
			String sql = "UPDATE board SET board_name=?,board_subject=?,board_content=? "
							+ "WHERE board_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, article.getBoard_name());
			pstmt.setString(2, article.getBoard_subject());
			pstmt.setString(3, article.getBoard_content());
			pstmt.setInt(4, article.getBoard_num());
			updateCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("updateArticle() 오류 - " + e.getMessage());
		} finally {
			close(pstmt);
		}
		
		return updateCount;
	}

	// 글 삭제 작업
	public int deleteArticle(int board_num) {
		int deleteCount = 0;
		
		PreparedStatement pstmt = null;
		
		try {
			String sql = "DELETE FROM board WHERE board_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			deleteCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("deleteArticle() 오류 - " + e.getMessage());
		} finally {
			close(pstmt);
		}
		
		return deleteCount;
	}
	
	/*
	 * 답변글 등록
	 * -------------
	 *  >> 63번 게시물에 대한 답변 글 작성할 경우(새 게시물이 100번이라고 가정) <<
	 * ------------------------------------------------------------------------------------
	 *  원본글번호(board_num)  새글번호(num)  참조글번호(ref)  순서번호(seq)  들여쓰기(lev)
	 * ------------------------------------------------------------------------------------
	 *          63                  100             63              1              1
	 * => 63번글에 대한 답글이므로 63번글 번호를 참조글번호(ref)로 사용
	 *    기존에 답글이 존재할 수 있기 때문에 기존 답글 순서번호(seq)를 전부 +1 시킴
	 *    => 그리고 추가되는 새 답글 순서번호(seq)를 다시 1번으로 지정
	 *    => 기존 참조글의 들여쓰기 레벨 + 1 값을 들여쓰기 값(lev)으로 지정
	 *
	 * => 63번글에 대한 새로운 답변글 추가할 경우(새 게시물이 110번이라고 가정) <<
	 *    => 참조글번호(ref)를 63번으로 지정
	 * ------------------------------------------------------------------------------------
	 *  원본글번호(board_num)  새글번호(num)  참조글번호(ref)  순서번호(seq)  들여쓰기(lev)
	 * ------------------------------------------------------------------------------------
	 *          63                  100             63              2(기존1)       1
	 *          63                  110             63              1              1
	 *    => 기존 답글(새글번호 100번)의 순서번호를 +1
	 *    => 새로 추가되는 답글의 순서번호는 다시 1번으로 지정
	 *    => 기존 참조글의 들여쓰기 레벨 + 1 값을 들여쓰기 값(lev)으로 지정
	 *    
	 * => 63번글에 대한 새로운 답변글 추가할 경우(새 게시물이 122번이라고 가정) <<
	 *    => 참조글번호(ref)를 63번으로 지정
	 * ------------------------------------------------------------------------------------
	 *  원본글번호(board_num)  새글번호(num)  참조글번호(ref)  순서번호(seq)  들여쓰기(lev)
	 * ------------------------------------------------------------------------------------
	 *          63                  100             63              3(기존2)       1
	 *          63                  110             63              2(기존1)       1
	 *          63                  122             63              1              1            
	 *    => 기존 답글(새글번호 100번)의 순서번호를 +1
	 *    => 새로 추가되는 답글의 순서번호는 다시 1번으로 지정
	 *    => 기존 참조글의 들여쓰기 레벨 + 1 값을 들여쓰기 값(lev)으로 지정
	 *    
	 * => 110번 글에 대한 새로운 답변글 추가할 경우(새 게시물이 130번이라고 가정) <<
	 *    => 참조글번호
	 * ------------------------------------------------------------------------------------
	 *  원본글번호(board_num)  새글번호(num)  참조글번호(ref)  순서번호(seq)  들여쓰기(lev)
	 * ------------------------------------------------------------------------------------
	 *          63                  100             63              3(기존2)       1
	 *          63                  110             63              2(기존1)       1
	 *          63                  122             63              1              1  
	 *         110                  130            110              1              2     
	 *    
	 */
	public int insertReplyArticle(BoardBean article) {
		int insertCount = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		//새글 번호 계산을 위한 기존 글 중가장 큰 번호 조회
		int num = 1;
		
		try {
			String sql = "SELECT max(board_num) FROM board";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) { // 기존 게시물 중 가장 큰 번호 조회 성공 시
				num = rs.getInt(1) + 1; // 가장 큰 번호 + 1 값을 새 글 번호로 지정
			}
			
			// 답변글 등록에 필요한 번호 정보 가져오기(ref, lev, seq)
			int board_re_ref = article.getBoard_re_ref();
			int board_re_lev = article.getBoard_re_lev();
			int board_re_seq = article.getBoard_re_seq();	
			
			// 기존 답변글의 seq 번호를 1씩 증가시키기
						// => 참조글번호(ref)가 같은 게시물 중 기존 순서번호(seq) 보다 큰 게시물만 증가
		    sql = "UPDATE board SET board_re_seq=board_re_seq+1 "
					+ "WHERE board_re_ref=? AND board_re_seq>?";
				
		    pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_re_ref);
			pstmt.setInt(2, board_re_seq);
			
			int updateCount = pstmt.executeUpdate();
			// updateCount > 0 일 경우 commit 작업 수행
			if(updateCount > 0) {
				commit(con);
			}
			
			//아 줜나하기싫다 `~~~~~
			
			// 답변글(새글) 추가
			// => 단, 추가 전 순서번호(seq) 와 들여쓰기레벨(lev) 값 1씩 증가시킴
			board_re_seq++;
			board_re_lev++;
			
			
			
			//창업, 사업을해야 상방이 열려있는건 맞는데, 그걸
			//할만한 내가 무언가 욕망, 의지, 뭐 결핍 이런게 있던가
			//아니 그런게 꼭 있어야 창업하는건가.
			pstmt.setInt(1, num); // 계산된 새 글 번호 사용
			pstmt.setString(2, article.getBoard_name());
			pstmt.setString(3, article.getBoard_pass());
			pstmt.setString(4, article.getBoard_subject());
			pstmt.setString(5, article.getBoard_content());
			pstmt.setString(6, ""); // board_file(업로드 X)
			pstmt.setInt(7, board_re_ref); 
			pstmt.setInt(8, board_re_lev);
			pstmt.setInt(9, board_re_seq);
			pstmt.setInt(10, 0); // board_readcount
			

			// INSERT 구문 실행 후 리턴되는 결과값을 insertCount 변수에 저장
			insertCount = pstmt.executeUpdate();
			
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		
		
		
		
		
		
		
		
		return insertCount;
		
		
		
		
		
	}
	
	
}