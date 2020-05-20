package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.BoardListAction;
import action.BoardWriteProAction;
import vo.ActionForward;

@WebServlet("*.bo") // ���� �ּ� �� XXX.bo �ּҿ� ���� ��û�� ���޹���
public class BoardFrontController extends HttpServlet {
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// POST ��� �ѱ� ó��
		request.setCharacterEncoding("UTF-8");

		// ���� �ּ� ��������
		String command = request.getServletPath();
		System.out.println(command);
		
		// �� ��û�� ���������� ó���ϱ� ���� �ʿ��� ���� ����
		Action action = null;
		ActionForward forward = null;
		
		// if���� ����Ͽ� ���� �ּ� �Ǻ� �� �� ��û ó���� ���� �۾� ��û
		if(command.equals("/Main.bo")) {
			// �۾��� ������ ��û�� ����Ͻ� ������ ���� View ������(JSP)�� �ٷ� ���� ����
			// => ActionForward ��ü�� �����Ͽ� Dispatcher ������� jsp �������� �ٷ� ������
			// => ActionForward ��ü�� ������ ����� ������ �������� ����(�ּ� ���� X)
			forward = new ActionForward();
//			forward.setRedirect(false); // ������ ����� Dispatcher ������� ����(�⺻�� ���� ����)
			forward.setPath("/main.jsp"); // �̵��� View ������ ��� ����
		} else if(command.equals("/BoardWriteForm.bo")) {
			// �۾��� ������ ��û�� ����Ͻ� ������ ���� View ������(JSP)�� �ٷ� ���� ����
			// => ActionForward ��ü�� �����Ͽ� Dispatcher ������� jsp �������� �ٷ� ������
			// => ActionForward ��ü�� ������ ����� ������ �������� ����(�ּ� ���� X)
			forward = new ActionForward();
//			forward.setRedirect(false); // ������ ����� Dispatcher ������� ����(�⺻�� ���� ����)
			forward.setPath("/board/qna_board_write.jsp"); // �̵��� View ������ ��� ����
		} else if(command.equals("/BoardWritePro.bo")) {
			// BoardWriteProAction Ŭ���� �ν��Ͻ� ���� => Action Ÿ������ ��ĳ����
			action = new BoardWriteProAction();
			// ���� �޼����� execute() �޼��带 ȣ���Ͽ� request, response ��ü ����
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/BoardList.bo")) {
			// �� ��� ��ȸ�� ���� BoardListAction Ŭ���� �ν��Ͻ� ���� => Action Ÿ�� ��ĳ����
			action = new BoardListAction();
			// ���� �޼����� execute() �޼��带 ȣ���Ͽ� request, response ��ü ����
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		// ActionForward ��ü ���� ������ ��Ŀ� ���� ������ ������ �۾� ����
		if(forward != null) {
			// ActionForward ��ü ���� ������ ���(Dispatcher or Redirect) �Ǻ�
			if(forward.isRedirect()) { // Redirect ���
				// Redirect ������� ������(�ּ�ǥ���� ���� O, request ��ü�� �������� ����)
				// => response ��ü�� sendRedirect() �޼��带 ȣ���Ͽ� ������ �� ������ ����
				response.sendRedirect(forward.getPath());
			} else { // Dispatcher ���
				// Dispatcher ������� ������(�ּ�ǥ���� ���� X, request ��ü�� ������)
				// => request ��ü�� getRequestDispatcher() �޼��带 ȣ���Ͽ� ������ �� ������ ����
				//    => RequestDispatcher ��ü�� ���ϵ�
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				// => RequestDispatcher ��ü�� forward() �޼��带 ȣ���Ͽ� request, response ��ü ����
				dispatcher.forward(request, response);
			}
		}
		
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}

