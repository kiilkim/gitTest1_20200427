package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import vo.MemberBean;
import vo.LoginException;



public class MemberDAO {
	
	
	public MemberDAO() {}
	
	private static MemberDAO instance = new MemberDAO();

	public static MemberDAO getInstance() {
		return instance;
	}
	// ---------------------------------------------------------------------------------
	

	Connection con = null;

	public void setConnection(Connection con) {
		this.con = con;
	}
	
	public int insertMember(MemberBean member) {
		
		boolean loginResult = false;
		
		Connection con = null;	
		PreparedStatement pstmt = null;
		
		int insertCount = 0;
	
		try {
			String sql = "INSERT INTO member VALUES (?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getName());
			pstmt.setString(2, member.getId());
			pstmt.setString(3, member.getPass());
			pstmt.setInt(4, member.getAge());
			pstmt.setString(5, member.getEmail());
			pstmt.setString(6, member.getGender() + "");
			
			insertCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return insertCount;
	}
// insertMember()
	
	



	private Connection getConnection() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean selectLoginMember(String id, String pass) throws LoginException {
		// TODO Auto-generated method stub
		
		boolean loginResult = false;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		//false라면 나오고
		//true라면 메인페이지로 이동
		
		//아이디에 해당하는 패스워드 존재여부
		//_db에서 확인해야한다. 
		// 1. 아이디와 패스워드가 일치하는 레코드 검색.
		
	    //_ 비교대상필요
		
		

		try {
			
			
			
			//  1. 아이디와 패스워드가 일치하는 레코드 검색
			// => 아이디, 패스워드 중 틀린 항목에 대한 구분이 불가능
			
			// 2. 아이디에 해당하는 패스워드 검색
			// String sql = "Select pass from member where =id? ";
			// 이렇게 하면 id가 맞아면 pass만 확인하면 되고 아예 id조차틀린경우
			// 아이디만 바로 판별가능
			
			
			String sql = "SELECT pass FROM member WHERE id=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) { // 아이디가 존재하는 경우(패스워드가 조회되는 경우)
				// 패스워드 일치 여부 판별
				if(pass.equals(rs.getString(1))) { // 패스워드가 일치할 경우
					loginResult = true;
				} else {
					//예외객체 직접 발생시켜서 예외메세지에
				//패스워드 오류 메세지 전달.
					
					throw new LoginException("패스워드 틀림");//예외클래스 꼭대기 exception 쓰거나 우리가 만들거나, 
					//   이 과정이 예외 객체 생성
					// 정상적인 리턴이면 결과값 나오는데
					// 정상적 로그인이 안된다면, ? ? 트라이캐치하면 또,, 
					// 예외강제 throw ,,고 밖으로 던지는건 throws
				}
				
				
			}else {
				
				throw new LoginException("존재하지 않는 아이디");
			}
			
			
			
			} catch (SQLException e) { //조심해야하는데 sql 이 틀려도 튀어나감.. 
				
				e.printStackTrace();
				
			} finally {
				close(rs);
				close(pstmt);
			}
			
			return loginResult;
		}




	private void close(PreparedStatement pstmt) {
		// TODO Auto-generated method stub
		
	}




	private void close(ResultSet rs) {
		// TODO Auto-generated method stub
		
	}
	
	
	

	
	

}
