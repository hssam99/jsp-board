<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원정보수정</title>
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
            max-width: 500px;
            width: 100%;
        }

        h1 {
            font-size: 40px;
            font-weight: 600;
            letter-spacing: -0.5px;
            color: #1d1d1f;
            margin-bottom: 40px;
            text-align: center;
        }

        .form-card {
            background: #fff;
            border-radius: 18px;
            padding: 40px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.04);
        }

        form {
            display: flex;
            flex-direction: column;
            gap: 20px;
        }

        .form-group {
            display: flex;
            flex-direction: column;
            gap: 8px;
        }

        label {
            font-size: 14px;
            font-weight: 500;
            color: #1d1d1f;
        }

        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 14px 16px;
            border: 1px solid #d2d2d7;
            border-radius: 10px;
            font-size: 17px;
            font-family: inherit;
            background: #fff;
            transition: border-color 0.2s ease;
        }

        input:focus {
            outline: none;
            border-color: #06c;
        }

        input::placeholder {
            color: #86868b;
        }

        button[type="submit"] {
            width: 100%;
            padding: 14px;
            background: #06c;
            color: #fff;
            border: none;
            border-radius: 10px;
            font-size: 17px;
            font-weight: 500;
            cursor: pointer;
            margin-top: 12px;
            font-family: inherit;
            transition: background 0.2s ease;
        }

        button:hover {
            background: #0077ed;
        }

        button:active {
            background: #0051a8;
        }

        .back-link {
            text-align: center;
            margin-top: 20px;
        }

        .back-link a {
            color: #06c;
            text-decoration: none;
            font-size: 15px;
        }

        .back-link a:hover {
            text-decoration: underline;
        }

        @media (max-width: 640px) {
            h1 {
                font-size: 32px;
            }

            .form-card {
                padding: 30px 20px;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <h1>회원정보수정</h1>
    <div class="form-card">
        <form action="/user/update" method="post">
            <div class="form-group">
                <label for="id">아이디</label>
                <input type="text" id="id" name="id" value="${ses.id }" readonly="readonly" required>
            </div>

            <div class="form-group">
                <label for="password">비밀번호</label>
                <input type="password" id="password" name="password" value="${ses.pwd }" required>
            </div>

            <div class="form-group">
                <label for="email">이메일</label>
                <input type="text" id="email" name="email" value="${ses.email}" required>
            </div>

            <div class="form-group">
                <label for="phone">전화번호</label>
                <input type="text" id="phone" name="phone" value="${ses.phone} ">
            </div>

            <div class="form-group">
                <label for="regdate"></label>
                <input type="text" id="regdate" name="regdate" readonly="readonly" value="${ses.regdate }">
            </div>

            <div class="form-group">
                <label for="lastlogin"></label>
                <input type="text" id="lastlogin" name="lastlogin" readonly="readonly" value="${ses.lastlogin }">
            </div>
            <button type="submit">수정하기</button>
        </form>
        <a href="/user/remove"><button class="back-link">회원탈퇴</button></a>
    </div>

    <div class="back-link">
        <a href="${pageContext.request.contextPath}/">← 메인으로 돌아가기</a>
    </div>
</div>
<script type="text/javascript">
    const update_msg = `<c:out value="${update_msg}"/>`;
    if(update_msg == 'Fail'){
        alert("오류가 발생했습니다. 재입력 바랍니다.")
    }

    const remove_msg = `<c:out value="${remove_msg}"/>`;
    if(remove_msg == 'Fail'){
        alert("오류가 발생했습니다. 다시 시도해주세요.")
    }
</script>
</body>
</html>