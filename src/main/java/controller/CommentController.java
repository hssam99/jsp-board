package controller;

import domain.Comment;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.CommentService;
import service.CommentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/comments/*")
public class CommentController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(CommentController.class);

    // 비동기 방식의 요청을 처리하는 컨트롤러
    // 데이터를 요청한 곳으로 결과를 보냄
    // 객체형태로(JSON), 또는 텍스트 형태로 보냄
    // RequestDispatcher / viewPath / setContentType => 필요 없음

    private CommentService csv;
    private int isOk;

    public CommentController(){
        csv = new CommentServiceImpl();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String uri = req.getRequestURI();
        String path = uri.substring(uri.lastIndexOf("/")+1);
        log.info(">>> path : {}", path);

        switch (path){
            case "post":
                log.info("✅ post 들어옴");
//                동기 방식 => req.getParameter()
//                비동기 방식 => 파일 입출력처럼 읽고(Reader) 쓰기(Writer)
//                req.getReader()
//                req.getWriter()
                try{
                    BufferedReader br = req.getReader();
                    StringBuffer sb = new StringBuffer();
                    String line = "";
//                while 문 돌리는 건 안전장치
                    while((line = br.readLine()) != null){
                        sb.append(line);
//                    log.info("line:{}", line);
                    }
                    log.info(">>> StringBuffer : {}", sb.toString());
//                {bno: '4', commenter: 'temp', content: '1234'}
//                String -> 객체 형태로 parser -> Json object -> key:value -> Comment
//                라이브러리 json-simple-1.1.1 JSON parser 라이브러리
                    JSONParser parser = new JSONParser();
                    JSONObject jsonobj = (JSONObject) parser.parse(sb.toString());
                    log.info(">>> JSONObject : {}", jsonobj.toString());
                    int bno = Integer.parseInt(jsonobj.get("bno").toString());
                    String commenter = jsonobj.get("commenter").toString();
                    String content = jsonobj.get("content").toString();
                    log.info(">>> bno: {}, commenter: {}, content: {}",
                            jsonobj.get("bno"), commenter, content);

                    Comment newComment = new Comment(bno, commenter, content);

                    isOk = csv.post(newComment);
                    log.info(">>> post  : {}", isOk > 0 ? "성공" : "실패");
                    PrintWriter out = resp.getWriter();
                    out.print(isOk);
                    out.flush();

                    break;
                }catch (Exception e){
                    e.printStackTrace();
                }
            case "list":
                log.info("✅ list 들어옴");
                try{
                    int bno = Integer.parseInt(req.getParameter("bno"));
                    log.info(">>> bno: {}", bno);
                    List<Comment> list = csv.getList(bno);
                    log.info(">>> list 조회 결과: {}", list);
//                    log.info(">>> list size: {}", list != null ? list.size() : "null");
//                    JSONArray: add => JSONObject []: put
//                    List<Comment> list -> json 형식으로 변환
//                    [{...},{...}]
                    JSONArray jsonArray = new JSONArray();
                    for(Comment c : list){
                        JSONObject obj = new JSONObject();
                        obj.put("cno", c.getCno());
                        obj.put("bno", c.getBno());
                        obj.put("commenter", c.getCommenter());
                        obj.put("content", c.getContent());
                        obj.put("regdate", c.getRegdate());
                        jsonArray.add(obj);
                    }

                    log.info(">>> jsonArray: {}", jsonArray.toString());
                    PrintWriter out = resp.getWriter();
                    out.print(jsonArray.toString());
                    out.flush();

                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case "remove":
                log.info("✅ remove 실행됨");
                try{
//                    ? queryString은 getParameter로 꺼낼 수 있음
                    int cno = Integer.parseInt(req.getParameter("cno"));
                    log.info(">>> cno: {}", cno);
                    isOk = csv.remove(cno);
                    log.info(">>> remove  : {}", isOk > 0 ? "성공" : "실패");

                    PrintWriter out = resp.getWriter();
                    out.print(isOk);
                    out.flush();
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case "modify":
                log.info("✅ modify 실행됨");
                try{
                    BufferedReader br = req.getReader();
                    JSONParser parser = new JSONParser();
                    JSONObject jsonObj = (JSONObject) parser.parse(br);

                    int cno = Integer.parseInt(jsonObj.get("cno").toString());
                    String content = jsonObj.get("content").toString();

                    Comment c = new Comment(cno, content);
                    log.info(">>> modifedComment: {}", c);
                    isOk = csv.update(c);

                    log.info(">>> modify  : {}", isOk > 0 ? "성공" : "실패");
                    PrintWriter out = resp.getWriter();
                    out.print(isOk);
                    out.flush();

                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            default:
                log.info("❌ 알 수 없는 요청");
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // GET으로 들어오는 요청을 처리 => service() 메서드 호출
        service(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // GET으로 들어오는 요청을 처리 => service() 메서드 호출
        service(req, resp);
    }
}