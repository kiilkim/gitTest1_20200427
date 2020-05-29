

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.ActionForward;

public interface action {
	/*
	 * - 서블릿 요청이 들어올 때 각 요청에 따른 다른 클래스들의 객체를 생성하여
	 *   요청을 처리해야하는 데, 이 때 공통 부모인 Action 인터페이스를 정의하여
	 *   추상메서드를 각 Action 클래스에서 구현하도록 강제하면
	 *   다형성을 통해 하나의 Action 인터페이스 타입으로 Action 클래스를 관리할 수 있음
	 * - 각 요청을 받아들일 execute() 메서드를 통해 request, response 객체를 전달받고
	 *   이동할 URL 과 포워딩 방식이 저장된 ActionForward 타입 객체를 리턴하도록 함
	 */
	public ActionForward execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	
}
