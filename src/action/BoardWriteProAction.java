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

// 사용자로부터 전달되는 데이터를 가져와서 DB 작업을 위한 준비 수행하고
// DB 작업 후 결과를 전달받아 사용자에게 보여질 정보를 준비하는 클래스
public class BoardWriteProAction implements Action {
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		System.out.println("BoardWriteProAction");
		
		ActionForward forward = null;
		
		
		// 현재 컨텍스트(객체) 정보 가져오기 위해 request 객체로부터 getServletContext() 메서드 호출
		ServletContext context = request.getServletContext();
		
		// 프로젝트 상에서의 가상 업로드 폴더 위치 지정
		String saveFolder = "/boardUpload"; // 현재 위치(Webcontent)의 하위폴더이므로 "/폴더명" 사용
		
		// ServletContext 객체를 사용하여 가상 폴더에 대응하는 실제 폴더 위치 가져오기
		// => 이클립스 사용 시 실제 업로드 폴더 위치
		// 워크스페이스\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps 폴더 내의
		// 프로젝트명에 해당하는 폴더가 있으며 그 폴더 내에 업로드 폴더가 위치함
		String realFolder = context.getRealPath(saveFolder);
		
		// 업로드 할 최대 파일 사이즈 지정(전체 숫자 입력 보단 단위별로 분리하는 것이 좋다!)
		int fileSize = 1024 * 1024 * 10; // 1024byte = 1KByte * 1024 = 1MB * 10 = 10MB 
		
		// MultiPartRequest 객체 생성 => cos.jar 필요
		MultipartRequest multi = new MultipartRequest(
				request, // request 객체
				realFolder,  // 파일이 업로드 될 실제 폴더
				fileSize, // 한 번에 업로드 가능한 파일 최대 크기
				"UTF-8", // 파일명에 대한 인코딩 방식
				new DefaultFileRenamePolicy()); // 파일명 중복 시 중복 파일명을 처리할 객체
		
		// 게시글 작성을 위해 입력받은 데이터를 저장할 BoardBean 객체 생성
		// => MultipartRequest 객체로부터 입력받은 데이터를 가져와서 BoardBean 객체에 저장
		BoardBean boardBean = new BoardBean();
		boardBean.setBoard_name(multi.getParameter("board_name"));
		boardBean.setBoard_pass(multi.getParameter("board_pass"));
		boardBean.setBoard_subject(multi.getParameter("board_subject"));
		boardBean.setBoard_content(multi.getParameter("board_content"));
		
		// 참고사항! request 객체로부터 getRemoteAddr() 메서드를 호출하면
		// 접속한 클라이언트의 IP 주소를 얻어낼 수 있다!
//		System.out.println("접속 IP : " + request.getRemoteAddr());
		
		// 업로드 할 파일명을 가져와서 전달
		// multi.getOriginalFileName() 메서드 : 파일의 실제 이름(원본 파일명)을 가져오기
		// => 중복된 파일이 존재하면 파일명 뒤에 숫자가 붙지만, 원본 파일명을 가져오게됨(숫자 X)
		boardBean.setBoard_file(
				multi.getOriginalFileName( (String)multi.getFileNames().nextElement() ));
		
		// multi.getFilesystemName() 메서드 : 업로드 되는 실제 파일 이름(중복 처리된 파일명) 가져오기
		// => 중복된 파일이 존재하면 파일명 뒤에 숫자가 붙고, 그 이름을 가져오게 됨
//		boardBean.setBoard_file(
//				multi.getFilesystemName( (String)multi.getFileNames().nextElement() ));
		
		// => 실제 파일 업로드 시에는 원본 파일명과 실제 업로드 되는 파일명을 함께 저장한 후
		//    화면에 보여지는 파일(원본 파일명)을 클릭하면 실제 업로드 된 파일을 다운로드 처리
		
//		System.out.println("작성자 : " + boardBean.getBoard_name());
//		System.out.println("패스워드 : " + boardBean.getBoard_pass());
//		System.out.println("제목 : " + boardBean.getBoard_subject());
//		System.out.println("내용 : " + boardBean.getBoard_content());
//		System.out.println("파일명 : " + boardBean.getBoard_file());
		
		
		// BoardWriteProService 클래스의 인스턴스 생성 
		// registArticle() 메서드를 호출하여 글 등록 요청
		// => 파라미터 : BoardBean 객체, 리턴타입 : boolean(isWriteSuccess)
		BoardWriteProService boardWriteProService = new BoardWriteProService();
		boolean isWriteSuccess = boardWriteProService.registArticle(boardBean);
		
		// 리턴받은 결과를 사용하여 글 등록 결과 판별
		if(!isWriteSuccess) {
//			System.out.println("글 등록 실패!");
			// 자바스크립트를 사용하여 다이얼로그를 통해 실패 메세지 출력 후 이전 페이지로 이동
			// 1. response 객체를 사용하여 문서 타입 및 인코딩 설정
			response.setContentType("text/html;charset=UTF-8");
			// 2. response 객체의 getWriter() 메서드를 호출하여
			//    출력스트림 객체(PrintWriter)를 리턴받음
			PrintWriter out = response.getWriter();
			// 3. PrintWriter 객체의 println() 메서드를 호출하여
			//    웹에서 수행할 작업(자바스크립트 출력 등)을 기술
			out.println("<script>"); // 자바스크립트 시작 태그
			out.println("alert('글 등록 실패!')"); // 다이얼로그 메세지 출력
			out.println("history.back()"); // 이전 페이지로 돌아가기
			out.println("</script>"); // 자바스크립트 끝 태그
		} else {
			System.out.println("글 등록 성공!");
			
			// 현재 BoardWritePro.bo 에서 BoardList.bo 서블릿 주소를 요청하여 Redirect 방식으로 포워딩
			// 1. ActionForward 객체 생성
			forward = new ActionForward();
			// 2. 포워딩 방식 지정 => Redirect 방식이므로 파라미터에 true 전달(필수)
			forward.setRedirect(true);
			// 3. 포워딩 할 주소 지정 => 서블릿 주소 BoardList.bo 요청
			forward.setPath("BoardList.bo");
		}
		
		
		// 4. ActionForward 객체 리턴 
		return forward; // => BoardFrontController 로 전달
	}

}


