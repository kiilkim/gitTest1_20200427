package control;

import java.sql.PreparedStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.activation.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import com.mysql.jdbc.Connection;

import member.MemberBean;

public class memberDAO {

	
		Connection con;
		PreparedStatement patmt;
		Resultet=rs;
		
		public void getCon() {
			
			
			try {
				
				 context initctx = new InnitialContext();
				 
				 context envctx = (Contex)initctx.lookup("java:comp:env");
				 
				 DataSource ds = envctx.lookup("jdbc/pool");
				 
			} catch(Exception e) {
				
				e.printStackTrace();
				
			}
		}	
		
		//ȸ�� �ѻ�� ���� ���� �޼ҵ�
		
		public void insertMember(MemberBean bean) {
			getCon();
			
			try {
				
				String sql = "insert into member values(?,?,?,?,?,?,?,?)";
				
				pstmt = con.clientPrepareStatement(sql);
				
				pstsm.setString(1,bean.getId());
				
				
				
			}
			
			
			
		}
}
