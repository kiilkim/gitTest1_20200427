package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class JdbcUtil {
	// DB ���� �⺻ ���(����, �ڿ���ȯ, commit, rollback ��)�� ����ϴ� Ŭ����
	// => �����ϴ� �޼��� ��� �ν��Ͻ� �������� ȣ���ϵ��� static �޼���� ����
	// 1. DBCP ����� Ȱ���� Connection ��ü �������� �޼��� getConnection() ����
	public static Connection getConnection() {
		Connection con = null; // DBCP �κ��� ���޹��� Connection ��ü�� ������ ���� ����
		
		try {
			
			// context.xml �� <Context> �±� ������ ��������
			Context initCtx = new InitialContext();
			
			// context.xml �� <Context> �±� ���� <Resource> �±� �κ� ��������
			Context envCtx = (Context)initCtx.lookup("java:comp/env");
			
			// context.xml �� JNDI(name �Ӽ�) ��������
			DataSource ds = (DataSource)envCtx.lookup("jdbc/MySQL");
			
			// DataSource ��ü�� getConnection() �޼��带 ȣ���Ͽ� Connection ��ü ��������
			con = ds.getConnection();
			
			// DB �۾��� ���� Auto Commit ��� ����
			con.setAutoCommit(false); // �⺻���� true(= Auto Commit Ȱ��ȭ)
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return con; // Connection ��ü ����
	}
	
	// �޼��� �����ε��� Ȱ���Ͽ� Connection, PreparedStatement, ResultSet ��ü �ڿ� ��ȯ ����
	public static void close(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(PreparedStatement pstmt) {
		try {
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rs) {
		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Auto Commit ������ ���� Commit, Rollback ����� �����ϴ� ������ �޼��� ����
	public static void commit(Connection con) {
		try {
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void rollback(Connection con) {
		try {
			con.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}





















