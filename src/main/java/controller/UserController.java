package controller;

import domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import service.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user/*")
public class UserController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    // 요청에 대한 응답 데이터를 jsp 전송하는 역할
    private RequestDispatcher rdp;
    private String destPage;

    // 서비스 인터페이스
    private UserService usv;

    // 서비스 클래스
    public UserController(){
        usv = new UserServiceImpl();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        //uri 경로
        String uri = req.getRequestURI();
        String path = uri.substring(uri.lastIndexOf("/")+1);
        log.info(">>> path {}", path);

        switch (path){
            case "register":
                destPage = "/WEB-INF/views/user/register.jsp";
                resForward(req, resp);
                break;
            case "insert":
                try{
                    String id = req.getParameter("id");
                    String pwd = req.getParameter("password");
                    String email = req.getParameter("email");
                    String phone = req.getParameter("phone");

                    User user = new User(id, pwd, email, phone);

                    int isOk = usv.insert(user);

                    log.info(">>> insert user : {}", (isOk>0) ? "✅성공" : "❌실패");
                    resp.sendRedirect("/");

                }catch(Exception e){
                    e.printStackTrace();
                }
                break;
            case "login":
                try{
                    String id = req.getParameter("id");
                    String pwd = req.getParameter("password");

                    User loginUser = usv.getUser(new User(id,pwd));
//                    log.info(">>> 로그인 유저: ", loginUser);

                    // 유저가 있다면
                    // 모든 jsp에 해당 객체를 인지 -> session 객체 저장
                    if(loginUser!=null){
                        // 세션 객체에 저장 -> 객체 가져오기
                        log.info(">>> ✅ 로그인 완료");
                        HttpSession ses = req.getSession();
                        ses.setAttribute("ses", loginUser); // 세션 객체에 로그인 유저를 저장
                        // 로그인 유지 시간 설정 (초단위)
                        ses.setMaxInactiveInterval(60*10);
                    }else{
                        // 로그인 정보를 찾을 수 없다면
                        // index.jsp 페이지로 메시지 전송
                        // 새 세션에 일회성 메시지 저장
                        HttpSession ses = req.getSession();
                        ses.setAttribute("login_msg", "notUser");
                    }
                    // 로그인 후 홈페이지로 이동
                    resp.sendRedirect("/");
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;

            case "logout":
                try{
                    // 세션 끊기 (=세션 무효화)
                    // 세션의 객체 지우기
                    HttpSession ses = req.getSession();
                    // ses.setAttribute("ses", loginUser);
                    User loginUser = (User) ses.getAttribute("ses");

                    // 로그인 날짜 기록 -> lastLogin
                    int isOk = usv.lastLoginUpdate(loginUser);
                    log.info(">>> 유저 로그아웃 타임 기록 : {}", (isOk>0) ? "✅성공" : "❌실패");

                    // 세션에 저장해 놓은 객체 삭제
                    ses.removeAttribute("ses");
                    // 세션 끊기 -> 세션에 저장된 모든 정보 삭제됨
                    ses.invalidate();

                    resp.sendRedirect("/");
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case "modify":
                destPage = "/WEB-INF/views/user/modify.jsp";
                resForward(req, resp);
                break;
            case "update":
                try{
                    String id = req.getParameter("id");
                    String pwd = req.getParameter("password");
                    String email = req.getParameter("email");
                    String phone = req.getParameter("phone");

                    User user = new User(id, pwd, email, phone);

                    int isOk = usv.update(user);
                    log.info(">>> 회원정보 수정 : {}", (isOk>0) ? "✅성공" : "❌실패");

                    // 세션을 끊고, 다시 로그인할 수 있게 유도
                    if(isOk>0){
                        HttpSession ses = req.getSession();
                        ses.removeAttribute("ses");
                        ses.invalidate();

                        HttpSession newSes = req.getSession(true);
                        newSes.setAttribute("update_msg", "OK");

                        resp.sendRedirect("/");
                    }else{
                        req.setAttribute("update_msg", "Fail");
                        destPage = "/WEB-INF/views/user/modify.jsp";
                        resForward(req, resp);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case "remove":
                try {
                    HttpSession ses = req.getSession();
                    User user = (User) ses.getAttribute("ses");

                    int isOk = usv.remove(user);
                    log.info(">>> remove user : {}", (isOk>0) ? "✅성공" : "❌실패");

                    // 회원탈퇴 시 세션 끊기
                    if(isOk>0){
                        ses.removeAttribute("ses");
                        ses.invalidate();

                        // 새 세션에 일회성 메시지 저장
                        HttpSession newSes = req.getSession(true);
                        newSes.setAttribute("remove_msg", "OK");

                        resp.sendRedirect("/");
                    }else{
                        req.setAttribute("remove_msg", "Fail");
                        destPage = "/WEB-INF/views/user/modify.jsp";
                        resForward(req, resp);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void resForward(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        rdp = req.getRequestDispatcher(destPage);
        rdp.forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    }
}