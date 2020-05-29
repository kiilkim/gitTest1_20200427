package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Hello") //url ���� �ݵ�� / �ʿ�.
public class HelloWorld extends HttpServlet {
	// /HelloWorld��� �ּ� url ǥ�����־�� �� ���� Ŭ������ ����
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		reqPro(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		reqPro(request, response);
	
	}

	
	//�ϰ�ó�� doget�̴� dopost �� reqPro ��� �޼��尡 ����ǰ����ش�. 
	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//ȭ�鿡 helloworld��� ����� �ϰ� �ʹ�. ������?
		
		//jsp ������ �Ѱ��� �����͸� ����
		
		String msg = "Hello World ~~����";
		
		Integer data = 12;
		
		//jsp ������ �����͸� request�� �����Ͽ� �Ѱ���.
		
		request.setAttribute("msg", msg); //setAttri�� object�� ��� �Ѱ��� 
		
		request.setAttribute("data", data);
		
		//servlet���� jsp�� ȣ���ϸ鼭 ������ ���� �ѱ���� ��ü�� �����ؾ��Ѵ�
		
		RequestDispatcher rd = request.getRequestDispatcher("HelloWorld.jsp");//jsp���ϸ� ���		
		
		rd.forward(request, response);
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
