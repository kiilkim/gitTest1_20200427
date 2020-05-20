package control;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import javax.activation.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import com.mysql.jdbc.Connection;

import member.MemberBean;

public class MemberDAO {

	
		Connection con;
		PreparedStatement pstmt;
		ResultSet rs;
		
		public void getCon() {
			
			
			try {
				
				 Context initctx = new InitialContext();
				 
				 Context envctx = (Contex)initctx.lookup("java:comp/env");
				 
				 DataSource ds = (DataSource)envctx.lookup("jdbc/pool");
				 
			} catch(Exception e) {
				
				e.printStackTrace();
				
			}
		}	
		
		//ȸ�� �ѻ�� ���� ���� �޼ҵ�
		
		public void insertMember(MemberBean bean) {
			getCon();
			
			try {
				//�����غ�.
				
				String sql = "insert into member values(?,?,?,?,?,?,?,?)";
				//���������� ��ü
				
				pstmt = con.clientPrepareStatement(sql);
				
				pstmt.setString(1,bean.getId());
				pstmt.setString(2,bean.getPass1());
				pstmt.setString(3,bean.getPass2());
				pstmt.setString(4,bean.getEmail());
				pstmt.setString(5,bean.getTel());
				pstmt.setString(6,bean.getHobby());
				pstmt.setString(7,bean.getJob());
				pstmt.setString(8,bean.getInfo());
			
				//��������
				pstmt.executeUpdate();
				con.close();
				
			}catch(Exception e) {
				
				e.printStackTrace();
			}
		
			
		}
		
}

			
		
