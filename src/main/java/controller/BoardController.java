package controller;

import domain.Board;
import domain.PagingVO;
import domain.User;
import handler.FileRemoveHandler;
import handler.PagingHandler;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.BoardService;
import service.BoardServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.util.List;

@WebServlet("/board/*")
public class BoardController extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

//    로그 객체 생성(slf4j)
    private static final Logger log = LoggerFactory.getLogger(BoardController.class);

//    RequestDispatcher
//    jsp에서 받은 요청을 처리한 후 결과를 다른 jsp로 보내주는 역할을 하는 객체
    private RequestDispatcher rdp;

//    jsp (목적지)주소를 저장하는 변수
    private String viewPath;

//    isOk 변수 DB에서 구문값 체크를 위해 저장하는 변수
    private int isOk;

//    service 연결 인터페이스
//    인터페이스 -> 구현제 BoardServiceImpl
    private BoardService bsv;

    public BoardController() {
        bsv = new BoardServiceImpl();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        log.info(">>> BoardController service() 호출됨");
//        jsp에서 요청하는 주소를 받는 객체 /board/register

//        post로 들어오는 객체는 한글깨짐발생 -> 인코딩 설정
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
//        응답 객체의 contentType 설정 -> jsp 파일로 응답
        resp.setContentType("text/html;charset=UTF-8");

        String uri = req.getRequestURI();
        log.info(">>> uri : {}", uri);

        String path = uri.substring(uri.lastIndexOf("/")+1);
        log.info(">>> path : {}", path);

        switch (path){
            case "register":
                viewPath = "/WEB-INF/views/board/register.jsp";
                log.info(">>> switch register case...");
                resForward(req, resp);
                break;
            case "insert":
                try{
                    log.info(">>> switch insert case...");

//                    첨부파일이 있을 때 수정코드
//                    title / writer / content + imgFile
                    String uploadPath = getServletContext().getRealPath("/resources/upload/board");
                    log.info(">>> uploadPath: {}", uploadPath);

                    File uploadDir = new File(uploadPath);
                    log.info(">>> uploadDir: {}", uploadDir);

                    if(!uploadDir.exists()) {
                        boolean created = uploadDir.mkdirs();
                        log.info(">>> 폴더 생성 결과: {}", created);
                        log.info(">>> 업로드 폴더 생성 완료");
                    }

                    DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
//                    파일 저장 경로 => repository
                    fileItemFactory.setRepository(uploadDir);
//                    파일 저장시 사용할 임시 메모리 공간
                    fileItemFactory.setSizeThreshold(1024*1024*3);
                    Board board = new Board();
//                    multipart / from-data 형식으로 넘어온 객체를 다루기 쉽게 변환해주는 클래스
                    ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
                    List<FileItem> itemList = fileUpload.parseRequest(req);

                    log.info(">>> itemList : {}", itemList);
                    for(FileItem item : itemList){
                        // FieldName
                        // title, writer, content => text
                        // imagefile => image(file) => 경로 주소 필요
                        switch (item.getFieldName()){
                            case "title":
                                board.setTitle(item.getString("UTF-8"));
                                break;
                            case "writer":
                                board.setWriter(item.getString("UTF-8"));
                                break;
                            case "content":
                                board.setContent(item.getString("UTF-8"));
                                break;
                            case "imagefile":
                                if(item.getSize()>0){
//                                    이름추출
                                    String fileName = item.getName();
//                                    파일이름은 내부에서 구분하기 쉽게 파일의 고유번호를 붙여서 관리
//                                    UUID / 시스템의 현재 시간을 이용하여 구분
                                    fileName = System.currentTimeMillis()+"_"+fileName;
//                                    경로 + 파일이름
//                                    경로(UploadDir) + 파일구분자 + 파일이름(fileName)
//                                    파일 구분자 (경로기호) => 운영체제마다 다름 / \
//                                    File.separator : 파일 경로 기호
                                    File uploadFile = new File(uploadDir + File.separator+fileName);
                                    log.info(">>> uploadFile >> {}", uploadFile);

                                    // 저장
                                    try{
                                        item.write(uploadFile);
                                        // 썸네일 생성 (200x200)

                                        File thumbFile = new File(uploadDir + File.separator + "th_"+ fileName);

                                        Thumbnails.of(uploadFile)
                                                .size(200, 200)  // 썸네일 크기
                                                .toFile(thumbFile);

                                        board.setImagefile("/resources/upload/board/" + fileName);
                                        log.info(">>> 파일 저장 완료: {}", fileName);
                                        log.info(">>> 썸네일 생성 완료: {}", thumbFile.getName());

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        log.info(">>> file write or thumbnail creation error");
                                    }
                                }
                                break;
                        }
                    }

                    isOk = bsv.insert(board);

//                    String title = req.getParameter("title");
//                    String writer = req.getParameter("writer");
//                    String content = req.getParameter("content");
//
//                    // DB에 등록하기 위한 객체 생성
//                    Board b = new Board(title, writer, content);
//                    log.info(">>> board : {}", b);
//
//                    // boardService 객체로 해당 객체를 전달
//                    isOk = bsv.insert(b);
//                    log.info(">>> insert isOk : {}", isOk > 0 ? "성공" : "실패");
//
                    // 결과에 따라 페이지 이동
                    if(isOk > 0) {
                        // 등록 성공 → 목록으로 이동
                        viewPath = "list";
                    } else {
                        // 등록 실패 → 다시 등록 페이지로
                        viewPath = "/board/register";
                    }
                } catch (Exception e) {
                    log.error(">>> 글 등록 중 에러 발생", e);
                    e.printStackTrace();
                    viewPath = "/board/register";
                }
                resp.sendRedirect("list");
                break;
            case "list":
                log.info(">>> switch list case...");
//                DB에서 전체 글 목록을 가져와서 jsp로 전달
                try{
//                    log.info(">>> 글 목록 조회 처리");
//                    List<Board> list = bsv.getList();
//                    log.info(">>> list : {}", list);
                    log.info("✅ 리스트 페이징");
                    // 파라미터 받기 전에 먼저 로그 찍어보기
                    PagingVO pageVO = new PagingVO();

                    if(req.getParameter("pageNo")!=null){
                        int pageNo = Integer.parseInt(req.getParameter("pageNo"));
                        int qty = Integer.parseInt(req.getParameter("qty"));
                        String type = req.getParameter("type");
                        String keyword = req.getParameter("keyword");
                        pageVO = new PagingVO(pageNo, qty, type, keyword);
                    }

//                    select * from board order by bno desc limit #{pageStart}, #{qty};
                    List <Board> list = bsv.getPageList(pageVO);
                    log.info(">>> list : {}", list);

//                    select count(bno) from board;
                    int totalCount = bsv.getTotal(pageVO);

                    PagingHandler pgHandler = new PagingHandler(pageVO, totalCount);
                    log.info(">>> pgHandler : {}", pgHandler);

                    req.setAttribute("list", list);
                    req.setAttribute("pgHandler", pgHandler);
                    viewPath = "/WEB-INF/views/board/list.jsp";
                    resForward(req, resp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "detail" : case "modify":
                if(path.equals("detail")) log.info(">>> switch detail case...");
//                상세페이지
                try{
                    int bno = Integer.parseInt(req.getParameter("bno"));

                    // ✅ 권한 체크 추가
                    HttpSession ses = req.getSession();
                    User loginUser = (User) ses.getAttribute("ses");

                    Board board = bsv.getDetail(bno);
                    log.info(">>> detail : {}", board);

                    req.setAttribute("board", board);
                    viewPath = "/WEB-INF/views/board/"+ path +".jsp";
                    resForward(req, resp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "update":
                log.info(">>> switch update case...");
                try{
                    // 이미지 있는 경우
                    String uploadPath = getServletContext().getRealPath("/resources/upload/board");
                    File uploadDir = new File(uploadPath);
                    int size = 1024*1024*3;

                    DiskFileItemFactory fileItemFactory = new DiskFileItemFactory(size, uploadDir);

                    Board board = new Board();
                    ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);

                    List<FileItem> itemList = fileUpload.parseRequest(req);

                    String old_file = null;

                    for(FileItem item: itemList){
                        switch (item.getFieldName()){
                            case "bno":
                                board.setBno(Integer.parseInt(item.getString("UTF-8")));
                                break;
                            case "title":
                                board.setTitle(item.getString("UTF-8"));
                                break;
                            case "content":
                                board.setContent(item.getString("UTF-8"));
                                break;
                            case "imagefile":
                                old_file = item.getString("UTF-8");
                                break;
                            case "newFile":
                                // 새로 추가되는 파일이 있으면 ...
                                if(item.getSize()>0){
                                    // 새로 추가되는 파일 있는 경우
                                    if(old_file != null){
                                        // old_file 삭제작업
                                        FileRemoveHandler fh = new FileRemoveHandler();
                                        boolean isDel = fh.deleteFile(uploadPath, old_file);
                                    }
                                    // 새파일 등록 작업
                                    String fileName = System.currentTimeMillis()+"_"+item.getName();

                                    // 경로 + 구분자 + 파일이름
                                    File uploadFile = new File(uploadDir + File.separator+fileName);
                                    try{
                                        item.write(uploadFile);
                                        board.setImagefile("/resources/upload/board/" + fileName);

                                        Thumbnails.of(uploadFile)
                                                .size(75,75)
                                                .toFile((new File(uploadDir + File.separator + "th_" + fileName)));

                                    }catch (Exception e){
                                        e.printStackTrace();
                                        log.info("file write update error");
                                    }

                                }else{
                                    board.setImagefile(old_file);
                                }

                                break;
                        }
                    }
//                    int bno = Integer.parseInt(req.getParameter("bno"));
//                    String title = req.getParameter("title");
//                    String content = req.getParameter("content");
//
//                    Board board = new Board(bno, title, content);
//                    log.info(">>> update board : {}", board);
                    isOk = bsv.update(board);
                    log.info(">>> update isOk : {}", isOk > 0 ? "성공" : "실패");
//                    resp.sendRedirect("detail?bno=" + bno);
                    resp.sendRedirect("detail?bno=" + board.getBno());
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case "remove":
                log.info(">>> switch remove case...");
                try{
                    int bno = Integer.parseInt(req.getParameter("bno"));

                    // ✅ 권한 체크 추가
                    HttpSession ses = req.getSession();
                    User loginUser = (User) ses.getAttribute("ses");

                    // 로그인 체크
                    if(loginUser == null) {
                        resp.sendRedirect("/user/login");
                        return;
                    }

                    // 게시글 작성자 확인
                    Board board = bsv.getDetail(bno);
                    if(!board.getWriter().equals(loginUser.getId())) {
                        // 권한 없음
                        req.setAttribute("error_msg", "권한이 없습니다.");
                        resp.sendRedirect("/board/detail?bno=" + bno);
                        return;
                    }
                    isOk = bsv.remove(bno);
                    log.info(">>> remove isOk : {}", isOk > 0 ? "성공" : "실패");

                    // 결과에 따라 페이지 이동
                    if(isOk > 0) {
                        // 삭제 성공 → 목록으로 이동
                        resp.sendRedirect("/board/list");
                    } else {
                        // 삭제 실패 → 다시 상세 페이지로
                        resp.sendRedirect("detail?bno=" + bno);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                log.info(">>> 잘못된 요청입니다.");
                viewPath = "/index.jsp";
                break;
        }
    }

    private void resForward(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        rdp = req.getRequestDispatcher(viewPath);
        rdp.forward(req, resp);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
//        Get으로 오는 요청에 대한 처리를 하고, HTML 파일을 생성하여 response 객체를 생성하여 jsp로 보내기
//        resp.getWriter().append("Served at: ").append(req.getContextPath());
//        service(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
//        doGet(req, resp);
//        service(req, resp);

    }


}