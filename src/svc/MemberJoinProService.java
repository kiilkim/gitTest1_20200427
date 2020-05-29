package svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.MemberDAO;
import vo.LoginException;
import vo.MemberBean;

public class MemberJoinProService {

	public boolean registMember(MemberBean member) {
		
		boolean isJoinSuccess = false;
		
		Connection con = getConnection();
		MemberDAO memberDAO = MemberDAO.getInstance();
		memberDAO.setConnection(con);
		
		int insertCount = memberDAO.insertMember(member);
		
		if(insertCount > 0) {
			isJoinSuccess = true;
			commit(con);
		} else {
			rollback(con);
		}
		
		close(con);
		
		
		close(con);
		
		return isJoinSuccess;
	}

}

