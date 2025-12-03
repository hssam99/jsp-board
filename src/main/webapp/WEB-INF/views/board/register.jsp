<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>게시판 글쓰기</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 800px;
            margin: 50px auto;
            padding: 20px;
        }
        h1 {
            text-align: center;
        }
        form {
            border: 1px solid #ddd;
            padding: 20px;
            border-radius: 5px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"],
        textarea {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        textarea {
            resize: vertical;
            min-height: 200px;
        }
        .btn-group {
            text-align: center;
            margin-top: 20px;
        }
        button {
            padding: 10px 30px;
            margin: 0 5px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        .btn-submit {
            background-color: #007bff;
            color: white;
        }
        .btn-cancel {
            background-color: #6c757d;
            color: white;
        }
    </style>
</head>
<body>
<h1>게시판 글쓰기</h1>

<form action="/board/insert" method="post" enctype="multipart/form-data">
    <div class="form-group">
        <label for="title">제목</label>
        <input type="text" id="title" name="title" required>
    </div>

    <div class="form-group">
        <label for="writer">작성자</label>
        <input type="text" id="writer" name="writer" value="${ses.id }" readonly >
    </div>

    <div class="form-group">
        <label for="content">내용</label>
        <textarea id="content" name="content" required></textarea>
    </div>
    <div class="form-group">
        <label for="imagefile">파일</label>
        <input type="file" name="imagefile"/>
    </div>
    <div class="btn-group">
        <button type="submit" class="btn-submit">등록</button>
        <button type="button" class="btn-cancel" onclick="location.href='${pageContext.request.contextPath}/board/list'">취소</button>
    </div>
</form>
</body>
</html>