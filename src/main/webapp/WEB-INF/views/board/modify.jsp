<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시글 수정</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 900px;
            margin: 50px auto;
            padding: 20px;
            background-color: #f5f5f5;
        }

        h1 {
            text-align: center;
            color: #333;
            margin-bottom: 30px;
        }

        form {
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th {
            background-color: #007bff;
            color: white;
            padding: 15px;
            text-align: left;
            width: 20%;
            font-weight: bold;
        }

        td {
            padding: 15px;
            border-bottom: 1px solid #ddd;
        }

        input[type="text"],
        textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 14px;
        }

        textarea {
            resize: vertical;
            font-family: Arial, sans-serif;
        }

        .btn-group {
            text-align: center;
            margin-top: 30px;
        }

        button {
            padding: 12px 30px;
            margin: 0 5px;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
        }

        .btn-submit {
            background-color: #007bff;
            color: white;
        }

        .btn-submit:hover {
            background-color: #3795ff;
        }

        .btn-cancel {
            background-color: #6c757d;
            color: white;
        }

        .btn-cancel:hover {
            background-color: #5a6268;
        }
    </style>
</head>
<body>
<h1>게시글 수정</h1>
<form action="${pageContext.request.contextPath}/board/update" method="post" enctype="multipart/form-data">
    <%-- 정보를 보내야 하는데 보여줄 필요가 없을때 --%>
    <input type="hidden" name="bno" value="${board.bno}">
        <input type="hidden" name="imagefile" value = "${board.imagefile}">
    <table>
        <tr>
            <th>번호</th>
            <td>${board.bno}</td>
        </tr>
        <tr>
            <th>제목</th>
            <td><input type="text" name="title" value="${board.title}" required></td>
        </tr>
        <tr>
            <th>작성자</th>
            <td>${board.writer}</td>
        </tr>
        <tr>
            <th>작성일</th>
            <td>${board.regdate}</td>
        </tr>
        <tr>
            <th>수정일</th>
            <td>${board.moddate}</td>
        </tr>
        <tr>
            <th>내용</th>
            <td><textarea rows="10" cols="30" name="content" required>${board.content}</textarea></td>
        </tr>
        <tr>
            <th>imageFile</th>
            <td><input type="file" name="newFile"> </td>
        </tr>
    </table>
    <div class="btn-group">
        <button type="submit" class="btn-submit">수정</button>
        <button type="reset" class="btn-cancel">취소</button>
    </div>
</form>
</body>
</html>