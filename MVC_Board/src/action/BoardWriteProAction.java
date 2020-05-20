package action;

import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import svc.BoardWriteProService;
import vo.ActionForward;
import vo.BoardBean;

// ����ڷκ��� ���޵Ǵ� �����͸� �����ͼ� DB �۾��� ���� �غ� �����ϰ�
// DB �۾� �� ����� ���޹޾� ����ڿ��� ������ ������ �غ��ϴ� Ŭ����
public class BoardWriteProAction implements Action {
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardWriteProAction");
		
		ActionForward forward = null;
		
		
		// ���� ���ؽ�Ʈ(��ü) ���� �������� ���� request ��ü�κ��� getServletContext() �޼��� ȣ��
		ServletContext context = request.getServletContext();
		
		// ������Ʈ �󿡼��� ���� ���ε� ���� ��ġ ����
		String saveFolder = "/boardUpload"; // ���� ��ġ(Webcontent)�� ���������̹Ƿ� "/������" ���
		
		// ServletContext ��ü�� ����Ͽ� ���� ������ �����ϴ� ���� ���� ��ġ ��������
		// => ��Ŭ���� ��� �� ���� ���ε� ���� ��ġ
		// ��ũ�����̽�\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps ���� ����
		// ������Ʈ�� �ش��ϴ� ������ ������ �� ���� ���� ���ε� ������ ��ġ��
		String realFolder = context.getRealPath(saveFolder);
		
		// ���ε� �� �ִ� ���� ������ ����(��ü ���� �Է� ���� �������� �и��ϴ� ���� ����!)
		int fileSize = 1024 * 1024 * 10; // 1024byte = 1KByte * 1024 = 1MB * 10 = 10MB 
		
		// MultiPartRequest ��ü ���� => cos.jar �ʿ�
		MultipartRequest multi = new MultipartRequest(
				request, // request ��ü
				realFolder,  // ������ ���ε� �� ���� ����
				fileSize, // �� ���� ���ε� ������ ���� �ִ� ũ��
				"UTF-8", // ���ϸ� ���� ���ڵ� ���
				new DefaultFileRenamePolicy()); // ���ϸ� �ߺ� �� �ߺ� ���ϸ��� ó���� ��ü
		
		// �Խñ� �ۼ��� ���� �Է¹��� �����͸� ������ BoardBean ��ü ����
		// => MultipartRequest ��ü�κ��� �Է¹��� �����͸� �����ͼ� BoardBean ��ü�� ����
		BoardBean boardBean = new BoardBean();
		boardBean.setBoard_name(multi.getParameter("board_name"));
		boardBean.setBoard_pass(multi.getParameter("board_pass"));
		boardBean.setBoard_subject(multi.getParameter("board_subject"));
		boardBean.setBoard_content(multi.getParameter("board_content"));
		
		// �������! request ��ü�κ��� getRemoteAddr() �޼��带 ȣ���ϸ�
		// ������ Ŭ���̾�Ʈ�� IP �ּҸ� �� �� �ִ�!
//		System.out.println("���� IP : " + request.getRemoteAddr());
		
		// ���ε� �� ���ϸ��� �����ͼ� ����
		// multi.getOriginalFileName() �޼��� : ������ ���� �̸�(���� ���ϸ�)�� ��������
		// => �ߺ��� ������ �����ϸ� ���ϸ� �ڿ� ���ڰ� ������, ���� ���ϸ��� �������Ե�(���� X)
		boardBean.setBoard_file(
				multi.getOriginalFileName( (String)multi.getFileNames().nextElement() ));
		
		// multi.getFilesystemName() �޼��� : ���ε� �Ǵ� ���� ���� �̸�(�ߺ� ó���� ���ϸ�) ��������
		// => �ߺ��� ������ �����ϸ� ���ϸ� �ڿ� ���ڰ� �ٰ�, �� �̸��� �������� ��
//		boardBean.setBoard_file(
//				multi.getFilesystemName( (String)multi.getFileNames().nextElement() ));
		
		// => ���� ���� ���ε� �ÿ��� ���� ���ϸ�� ���� ���ε� �Ǵ� ���ϸ��� �Բ� ������ ��
		//    ȭ�鿡 �������� ����(���� ���ϸ�)�� Ŭ���ϸ� ���� ���ε� �� ������ �ٿ�ε� ó��
		
//		System.out.println("�ۼ��� : " + boardBean.getBoard_name());
//		System.out.println("�н����� : " + boardBean.getBoard_pass());
//		System.out.println("���� : " + boardBean.getBoard_subject());
//		System.out.println("���� : " + boardBean.getBoard_content());
//		System.out.println("���ϸ� : " + boardBean.getBoard_file());
		
		
		// BoardWriteProService Ŭ������ �ν��Ͻ� ���� 
		// registArticle() �޼��带 ȣ���Ͽ� �� ��� ��û
		// => �Ķ���� : BoardBean ��ü, ����Ÿ�� : boolean(isWriteSuccess)
		BoardWriteProService boardWriteProService = new BoardWriteProService();
		boolean isWriteSuccess = boardWriteProService.registArticle(boardBean);
		
		// ���Ϲ��� ����� ����Ͽ� �� ��� ��� �Ǻ�
		if(!isWriteSuccess) {
//			System.out.println("�� ��� ����!");
			// �ڹٽ�ũ��Ʈ�� ����Ͽ� ���̾�α׸� ���� ���� �޼��� ��� �� ���� �������� �̵�
			// 1. response ��ü�� ����Ͽ� ���� Ÿ�� �� ���ڵ� ����
			response.setContentType("text/html;charset=UTF-8");
			// 2. response ��ü�� getWriter() �޼��带 ȣ���Ͽ�
			//    ��½�Ʈ�� ��ü(PrintWriter)�� ���Ϲ���
			PrintWriter out = response.getWriter();
			
			// 3. PrintWriter ��ü�� println() �޼��带 ȣ���Ͽ�
			//    ������ ������ �۾�(�ڹٽ�ũ��Ʈ ��� ��)�� ���
			out.println("<script>"); // �ڹٽ�ũ��Ʈ ���� �±�
			out.println("alert('�� ��� ����!')"); // ���̾�α� �޼��� ���
			out.println("history.back()"); // ���� �������� ���ư���
			
			out.println("</script>"); // �ڹٽ�ũ��Ʈ �� �±�
			
			
			
		} else {
			System.out.println("�� ��� ����!");
			
			// ���� BoardWritePro.bo ���� BoardList.bo ���� �ּҸ� ��û�Ͽ� Redirect ������� ������
			// 1. ActionForward ��ü ����
			forward = new ActionForward();
			// 2. ������ ��� ���� => Redirect ����̹Ƿ� �Ķ���Ϳ� true ����(�ʼ�)
			forward.setRedirect(true);
			// 3. ������ �� �ּ� ���� => ���� �ּ� BoardList.bo ��û
			forward.setPath("BoardList.bo");
		}
		
		
		// 4. ActionForward ��ü ���� 
		return forward; // => BoardFrontController �� ����
	}

}

