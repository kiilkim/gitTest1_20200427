package svc;

import java.sql.Connection;

import dao.BoardDAO;
//import db.JdbcUtil;
import vo.BoardBean;

// static import ����� ����Ͽ� db ��Ű���� JdbcUtil Ŭ������ ���� �ִ� �޼��带 ������
// Ŭ������ ���� �ٷ� �޼��带 ȣ���� �� �ִ�!
// => db.JdbcUtil.getConnection; �̶�� �����ϸ� getConnection() �޼��常 ȣ�� ����������
//    db.JdbcUtil.*; ���·� �����ϸ� JdbcUtil Ŭ������ ��� static �޼��带 ȣ�� ����
import static db.JdbcUtil.*;

// Action Ŭ�����κ��� ��û���� �۾��� DAO Ŭ������ ����Ͽ� ó���ϰ� �� ����� �����ϴ� Ŭ����
public class BoardWriteProService {
	
	// �� ��� ��û ó���� ���� registArticle() �޼��� ����
	// => �Ķ���� : BoardBean ��ü(boardBean)
	// => ����Ÿ�� : boolean
	public boolean registArticle(BoardBean boardBean) { 
		System.out.println("BoardWriteProService - registArticle()");
		
		boolean isWriteSucces = false; // �� ��� ���� ���θ� ������ ����
		
		// DB �۾��� ���� �غ� => Connection ��ü, DAO ��ü, DAO ��ü�� �޼��� ȣ��
		// 1. DB �۾��� �ʿ��� Connection ��ü ��������
//		Connection con = JdbcUtil.getConnection();
		Connection con = getConnection(); // static import �� ������ �޼��� ȣ��
		
		// 2. DB �۾��� ���� BoardDAO ��ü ���� => �̱��� �������� ������ ��ü ��������
		BoardDAO boardDAO = BoardDAO.getInstance();
		
		// 3. BoardDAO ��ü�� Connection ��ü ����
		boardDAO.setConnection(con);
		
		// 4. BoardDAO ��ü�� insertArticle() �޼��带 ȣ���Ͽ� �� ��� ó��
		// => �Ķ���� : BoardBean ��ü, ����Ÿ�� : int(insertCount)
		int insertCount = boardDAO.insertArticle(boardBean);
		
		// 5. ���Ϲ��� �۾� ��� �Ǻ�
		// => insertCount �� 0���� ũ�� commit() ����, isWriteSuccess �� true �� ����
		// => �ƴϸ�, rollback() ����
		if(insertCount > 0) {
			commit(con);
			isWriteSucces = true;
		} else {
			rollback(con);
		}
		
		// 6. Connection ��ü ��ȯ
		close(con);
		
		// 7. �۾� ��� ����
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





