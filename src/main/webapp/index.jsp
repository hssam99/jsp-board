<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>JSP - Hello World</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: -apple-system, BlinkMacSystemFont, 'SF Pro Display', 'Helvetica Neue', sans-serif;
            background: #f5f5f7;
            color: #1d1d1f;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 40px 20px;
        }

        .container {
            max-width: 600px;
            width: 100%;
        }

        h1 {
            font-size: 40px;
            font-weight: 600;
            letter-spacing: -0.5px;
            color: #1d1d1f;
            margin-bottom: 8px;
            text-align: center;
        }

        .subtitle {
            font-size: 17px;
            font-weight: 400;
            color: #86868b;
            text-align: center;
            margin-bottom: 48px;
        }

        .section {
            background: #fff;
            border-radius: 18px;
            padding: 8px;
            margin-bottom: 20px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.04);
        }

        .section-title {
            font-size: 13px;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 0.5px;
            color: #86868b;
            padding: 16px 20px 8px 20px;
        }

        .links {
            display: flex;
            flex-direction: column;
        }

        a {
            display: flex;
            align-items: center;
            justify-content: space-between;
            color: #1d1d1f;
            text-decoration: none;
            font-size: 17px;
            font-weight: 400;
            padding: 14px 20px;
            border-radius: 10px;
            transition: background 0.2s ease;
        }

        a:hover {
            background: #f5f5f7;
        }

        a::after {
            content: '›';
            font-size: 24px;
            font-weight: 300;
            color: #86868b;
        }

        .board-links a {
            color: #06c;
        }

        .board-links a::after {
            color: #06c;
        }

        .divider {
            height: 1px;
            background: #d2d2d7;
            margin: 8px 20px;
        }

        /* 로그인 폼 영역 */
        .login-container {
            padding: 12px 12px 4px 12px;
        }

        form {
            background: #f5f5f7;
            padding: 20px;
            border-radius: 12px;
        }

        form input[type="text"],
        form input[type="password"] {
            width: 100%;
            padding: 12px 16px;
            margin: 8px 0;
            border: 1px solid #d2d2d7;
            border-radius: 8px;
            font-size: 17px;
            font-family: inherit;
            background: #fff;
            display: block;
        }

        form input:focus {
            outline: none;
            border-color: #06c;
        }

        form button[type="submit"] {
            width: 100%;
            padding: 12px;
            background: #06c;
            color: #fff;
            border: none;
            border-radius: 8px;
            font-size: 17px;
            font-weight: 500;
            cursor: pointer;
            margin-top: 12px;
            font-family: inherit;
            transition: background 0.2s ease;
        }

        form button:hover {
            background: #0077ed;
        }

        /* 환영 메시지 영역 */
        .welcome-info {
            padding: 20px;
            background: #f5f5f7;
            border-radius: 12px;
            margin: 12px;
            font-size: 17px;
            line-height: 1.6;
        }

        .user-actions {
            padding: 0 12px 12px 12px;
        }

        .user-actions a {
            margin-bottom: 8px;
        }

        @media (max-width: 640px) {
            h1 {
                font-size: 32px;
            }

            .container {
                max-width: 100%;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Hello JSP World!</h1>
    <p class="subtitle">The ultimate web application experience.</p>

    <div class="section">
        <h2 class="section-title">Board</h2>
        <div class="links board-links">
            <a href="${pageContext.request.contextPath}/board/register">
                게시판 글쓰기
            </a>
            <div class="divider"></div>
            <a href="${pageContext.request.contextPath}/board/list">
                게시판 리스트 보기
            </a>
        </div>
    </div>

    <div class="section">
        <h2 class="section-title">Account</h2>
        <div class="links user-links">
            <c:if test="${ses.id eq null}">
                <a href="/user/register">회원가입</a>
                <div class="divider"></div>
                <div class="login-container">
                    <form action="/user/login" method="post">
                        id: <input type="text" name="id" placeholder="id">
                        pwd: <input type="password" name="password" placeholder="password">
                        <button type="submit">로그인</button>
                    </form>
                </div>
            </c:if>

            <c:if test="${ses.id ne null}">
                <div class="welcome-info">
                    Welcome! ${ses.id}(${ses.email})<br>
                    (마지막 접속일 : ${ses.lastlogin})
                </div>
                <div class="divider"></div>
                <div class="user-actions">
                    <a href="/user/logout">로그아웃</a>
                    <a href="/user/modify">회원정보수정</a>
                </div>
            </c:if>
        </div>
    </div>
</div>

<script type="text/javascript">
    const login_msg = `<c:out value="${sessionScope.login_msg}"/>`;
    if(login_msg == 'notUser'){
        alert("로그인 정보를 찾을 수 없습니다.");
        <c:if test="${not empty sessionScope.login_msg}">
            <% session.removeAttribute("login_msg"); %>
        </c:if>
    }

    const update_msg = `<c:out value="${sessionScope.update_msg}"/>`;
    if(update_msg == 'OK'){
        alert("회원정보가 수정되었습니다. 다시 로그인해주세요.");
        <c:if test="${not empty sessionScope.update_msg}">
            <% session.removeAttribute("update_msg"); %>
        </c:if>
    }

    const remove_msg = `<c:out value="${sessionScope.remove_msg}"/>`;
    if(remove_msg == 'OK'){
        alert("회원 탈퇴가 완료되었습니다.🥲");

        <c:if test="${not empty sessionScope.remove_msg}">
            <% session.removeAttribute("remove_msg"); %>
        </c:if>
    }

</script>
</body>
</html>