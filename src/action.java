

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.ActionForward;

public interface action {
	/*
	 * - ���� ��û�� ���� �� �� ��û�� ���� �ٸ� Ŭ�������� ��ü�� �����Ͽ�
	 *   ��û�� ó���ؾ��ϴ� ��, �� �� ���� �θ��� Action �������̽��� �����Ͽ�
	 *   �߻�޼��带 �� Action Ŭ�������� �����ϵ��� �����ϸ�
	 *   �������� ���� �ϳ��� Action �������̽� Ÿ������ Action Ŭ������ ������ �� ����
	 * - �� ��û�� �޾Ƶ��� execute() �޼��带 ���� request, response ��ü�� ���޹ް�
	 *   �̵��� URL �� ������ ����� ����� ActionForward Ÿ�� ��ü�� �����ϵ��� ��
	 */
	public ActionForward execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	
}
