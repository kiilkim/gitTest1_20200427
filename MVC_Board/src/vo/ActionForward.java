package vo;

public class ActionForward {

	/*
	 * �������� Ŭ���̾�Ʈ�κ��� ��û�� �޾� ó�� �� View �������� ������ �� ��
	 * �̵��� View �������� URL(�ּ�)�� ������ ���(Dispatch or Redirect)�� �ٷ�� ���� Ŭ���� 
	 */
	private String path; // ������ �� View �������� URL �� ������ ����
	private boolean redirect; // ������ ����� ������ ����(true : Redirect, false : Dispatch)
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isRedirect() {
		return redirect;
	}
	public void setRedirect(boolean redirect) {
		this.redirect = redirect;
	}
	
	
}
