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
	 * ------------ �̱��� ������ ������ Ȱ���� BoardDAO �ν��Ͻ� �۾� -------------
	 * 1. �ܺο��� �ν��Ͻ� ������ �Ұ����ϵ��� private ���������ڸ� ����Ͽ� ������ ����
	 * 2. ���� �ν��Ͻ��� �����Ͽ� ����(instance)�� ����
	 * 3. �ܺο��� �ν��Ͻ��� ���޹��� �� �ֵ��� Getter �޼��� ����
	 * 4. getInstance() �޼��忡 �ν��Ͻ� �������� ���� �����ϵ��� static �޼���� ����
	 *    => �޼��� ������ �����ϴ� ������� instance �� static ������ ����
	 * 5. �ν��Ͻ��� ���� instance ������ ���� �Ұ����ϵ��� ���������� private ����
	 */
	private BoardDAO() {}
	
	private static BoardDAO instance;

	public static BoardDAO getInstance() {
		// BoardDAO ��ü�� ���� ��쿡�� ����
		if(instance == null) {
			instance = new BoardDAO();
		}
		
		return instance;
	}
	// ---------------------------------------------------------------------------------
	
	Connection con; // Connection ��ü ���޹޾� ������ ���� ����
	
	// Service Ŭ�����κ��� Connection ��ü�� ���޹޴� �޼��� setConnection() ����
	public void setConnection(Connection con) {
		this.con = con;
	}

	// �Խù� ���
	public int insertArticle(BoardBean boardBean) {
		// Service Ŭ�����κ��� BoardBean ��ü�� ���޹޾� DB �� INSERT �۾� ����
		// => ���� ��� ������ int�� insertCount �� ���Ϲ޾� �ٽ� Service Ŭ������ ����
		int insertCount = 0;
		
		// DB �۾��� �ʿ��� ���� ����(Connection ��ü�� �̹� �ܺηκ��� ���޹���)
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			int num = 1; // �� �Խù� ��ȣ�� ������ ����(�ʱⰪ���� �ʱ��ȣ 1�� ����)
			
			// ���� �Խù��� ��ȣ �� ���� ū ��ȣ(�ִ밪)�� ��ȸ�Ͽ� ���� �� �� ��ȣ ����(+1)
			String sql = "SELECT MAX(board_num) FROM board"; // �ִ밪 ��ȸ ������
			
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) { // ��ϵ� �Խù��� �ϳ��� ������ ��� �Խù� ��ȣ ��ȸ ����
				// ��ȸ�� ��ȣ + 1 �� �����Ͽ� �� �� ��ȣ�� ����
				num = rs.getInt(1) + 1;
			} 
			// => ����, ��ȸ�� ������ ��� �� �� ��ȣ�� 1���̹Ƿ� �⺻�� num �״�� ���
			
			// ���޹��� �����Ϳ� ���� �� ��ȣ�� ����Ͽ� INSERT �۾� ����
			// => board_re_ref �� �� �� ��ȣ, board_re_lev ,board_re_seq, board_readcount �� 0
			// => ������ �Ķ������ board_date(�Խù� �ۼ���) ���� DB�� now() �Լ��� ����Ͽ�
			//    DB ������ �ý��� ���� �ð� ��������
			sql = "INSERT INTO board VALUES (?,?,?,?,?,?,?,?,?,?,now())";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num); // ���� �� �� ��ȣ ���
			pstmt.setString(2, boardBean.getBoard_name());
			pstmt.setString(3, boardBean.getBoard_pass());
			pstmt.setString(4, boardBean.getBoard_subject());
			pstmt.setString(5, boardBean.getBoard_content());
			pstmt.setString(6, boardBean.getBoard_file());
			pstmt.setInt(7, num); // board_re_ref(�� �� ��ȣ �״�� ����)
			pstmt.setInt(8, 0); // board_re_lev
			pstmt.setInt(9, 0); // board_re_seq
			pstmt.setInt(10, 0); // board_readcount
			
			// INSERT ���� ���� �� ���ϵǴ� ������� insertCount ������ ����
			insertCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
//			e.printStackTrace();
			System.out.println("BoardDAO - insertArticle() ����! : " + e.getMessage());
		} finally {
			// DB �ڿ� ��ȯ
			// => ����! Connection ��ü�� Service Ŭ�������� ������ ����ؾ��ϹǷ� ������ �ȵ�!
//			JdbcUtil.close(rs);
//			JdbcUtil.close(pstmt);
			// => static import ����� ����Ͽ� db.JdbcUtil Ŭ���� ���� ��� static ��� import
			close(rs);
			close(pstmt);
		}
		
		return insertCount;
	}

	
	// ��ü �Խù� �� ���
	public int selectListCount() {
		int listCount = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// board_num �÷��� ��ü ������ ��ȸ�ϱ�(��� �÷��� ���ϴ� * ��ȣ ����ص� ��)
			String sql = "SELECT COUNT(board_num) FROM board"; // COUNT() �Լ� ���
			
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				listCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("BoardDAO - selectListCount() ����! : " + e.getMessage());
		} finally {
			// DB �ڿ� ��ȯ
			close(rs);
			close(pstmt);
		}
		
		return listCount;
	}

	public ArrayList<BoardBean> selectArticleList(int page, int limit) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int startRow = (page - 1) * limit; // �������ȣ ��� 
		
		// ��ü �Խù��� ������ ArrayList ��ü ���� => ���׸� Ÿ�� BoardBean Ÿ�� ����
		ArrayList<BoardBean> articleList = new ArrayList<BoardBean>();
		
		// �Խù� ���� ��ȸ�� SQL ���� �ۼ�
		// => ���� : board_re_ref ���� ��������, board_re_seq ���� ��������
		// => limit : ���� �� ��ȣ���� ������ �Խù� ������ŭ ����
		
		
		try {
			// �Խù� ���� ��ȸ�� SQL ���� �ۼ�
			// => ���� : board_re_ref ���� ��������, board_re_seq ���� ��������
			// => limit : ���� �� ��ȣ���� ������ �Խù� ������ŭ ����
			String sql = "SELECT * FROM board "
					+ "ORDER BY board_re_ref DESC, board_re_seq ASC LIMIT ?,?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				//1�� ���ڵ�(�Խù�)�� ������ BoardBean ��ü ����
				
		
				
				
				// 1�� ���ڵ�(�Խù�)�� ������ BoardBean ��ü ����
				BoardBean article = new BoardBean();
				// BoardBean ��ü�� ��ȸ�� ���ڵ�(�Խù�) ������ ����
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
				
				// ��ü ���ڵ� �����ϴ� ArrayList ��ü�� 1�� ���ڵ带 ������ BoardBean ��ü ����
				articleList.add(article);
				
				
				
				
			}
			
			
			
			
		} catch (SQLException e) {
			System.out.println("BoardDAO - selectArticleList() ����! : " + e.getMessage());
		} finally {
			// DB �ڿ� ��ȯ
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
		
		//�Խù� ��ȣ�� �ش��ϴ� �Խù� �� ���� ��ȸ boardBean ��ü�� ����
		
		// �Խù� ��ȣ(board_num)�� �ش��ϴ� �Խù� �� ���� ��ȸ �� BoardBean ��ü�� ����
				try {
					String sql = "SELECT * FROM board WHERE board_num=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, board_num);
					rs = pstmt.executeQuery();
					
					// �Խù��� ������ ��� BoardBean ��ü�� ��� ������ ����
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
					System.out.println("selectArticle() ���� - " + e.getMessage());
				} finally {
					close(rs);
					close(pstmt);
				}
		
		
		
		
		return article;
	}
	
	

	public int updateReadcount(int board_num) {
		// TODO Auto-generated method stub
		
		int updateCount = 0;
		
		// �Խù� ��ȣ(board_num) �� �ش��ϴ� ���ڵ��� board_readcount ���� 1 ������Ŵ
		PreparedStatement pstmt = null;
		
		
		
		try {
			// ��ȸ���� ������ų �Խù� ��ȣ(board_num)�� SQL ���� �ۼ� �� �ٷ� �����ص� �ǰ�
//			String sql = "UPDATE board SET board_readcount=board_readcount+1 WHERE board_num=" + board_num;
			
			// ���ɹ���(?)�� ����Ͽ� �Ķ���� �� �����ص� ��(setXXX() �ʼ�)
			String sql = "UPDATE board SET board_readcount=board_readcount+1 WHERE board_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			updateCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("updateReadcount() ���� - " + e.getMessage());
		} finally {
			close(pstmt);
		}
		
		
		
		
		return 0;
	}
	
	
	
	
}









