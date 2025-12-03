<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시글 상세</title>
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

        table {
            width: 100%;
            border-collapse: collapse;
            background-color: white;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
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

        .content-cell {
            min-height: 200px;
            vertical-align: top;
            line-height: 1.6;
        }

        .btn-group {
            text-align: center;
            margin-top: 30px;
        }

        .btn {
            padding: 12px 30px;
            margin: 0 5px;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
        }

        .btn-list {
            background-color: #6c757d;
            color: white;
        }

        .btn-list:hover {
            background-color: #5a6268;
        }

        .btn-modify {
            background-color: #007bff;
            color: white;
        }

        .btn-modify:hover {
            background-color: #0056b3;
        }

        .btn-delete {
            background-color: #007bff;
            color: white;
        }

        .btn-delete:hover {
            background-color: #0056b3;
        }
        /* ===== 댓글 섹션 ===== */
        .comment-section {
            margin-top: 50px;
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }

        .comment-section h3 {
            color: #333;
            margin-bottom: 20px;
            padding-bottom: 10px;
            border-bottom: 2px solid #007bff;
        }

        /* ===== 댓글 입력 영역 ===== */
        .comment-input-area {
            display: flex;
            gap: 10px;
            margin-bottom: 30px;
            padding: 20px;
            background-color: #f8f9fa;
            border-radius: 5px;
        }

        .comment-input-area input[type="text"] {
            flex: 1;
            padding: 12px 15px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
        }

        .comment-input-area input[type="text"]:focus {
            outline: none;
            border-color: #007bff;
        }

        .comment-input-area input[type="text"]#cmtCommenter {
            flex: 0 0 150px;
        }

        .comment-input-area button {
            flex: 0 0 80px;
            padding: 12px 20px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 14px;
            font-weight: bold;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .comment-input-area button:hover {
            background-color: #0056b3;
        }

        /* ===== 댓글 목록 ===== */
        .comment-list {
            margin-top: 20px;
        }

        .no-comments {
            text-align: center;
            padding: 40px;
            color: #999;
            font-size: 16px;
        }

        /* ===== 댓글 아이템 ===== */
        .comment-item {
            padding: 20px;
            border-bottom: 1px solid #e9ecef;
            transition: background-color 0.2s;
        }

        .comment-item:hover {
            background-color: #f8f9fa;
        }

        .comment-item:last-child {
            border-bottom: none;
        }

        /* ===== 댓글 헤더 ===== */
        .comment-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 12px;
        }

        .comment-info {
            display: flex;
            align-items: center;
            gap: 15px;
        }

        .comment-number {
            color: #6c757d;
            font-size: 13px;
            font-weight: bold;
        }

        .comment-writer {
            color: #333;
            font-weight: bold;
            font-size: 15px;
        }

        .comment-date {
            color: #999;
            font-size: 13px;
        }

        /* ===== 댓글 액션 버튼 ===== */
        .comment-actions {
            display: flex;
            gap: 8px;
        }

        .comment-actions button {
            padding: 6px 15px;
            border: 1px solid #ddd;
            background-color: white;
            color: #666;
            border-radius: 4px;
            font-size: 13px;
            cursor: pointer;
            transition: all 0.2s;
        }

        .comment-actions button:hover {
            background-color: #007bff;
            color: white;
            border-color: #007bff;
        }

        /* ===== 댓글 내용 (input) ===== */
        .comment-content {
            padding-left: 10px;
        }

        .comment-content input[type="text"] {
            width: 95%;
            padding: 12px 15px;
            border: none;
            border-left: 3px solid #007bff;
            border-radius: 4px;
            font-size: 14px;
            background-color: #f8f9fa;
            color: #333;
            line-height: 1.6;
        }

        .comment-content input[type="text"]:read-only {
            cursor: default;
        }

        .comment-content input[type="text"]:not(:read-only) {
            border: 1px solid #007bff;
            background-color: white;
            cursor: text;
        }

        .comment-content input[type="text"]:focus {
            outline: none;
            border-color: #0056b3;
            box-shadow: 0 0 0 3px rgba(0, 123, 255, 0.1);
        }
    </style>
</head>
<body>
<h1>게시글 상세보기</h1>
<table>
    <tr>
        <th>번호</th>
        <td>${board.bno}</td>
    </tr>
    <tr>
        <th>제목</th>
        <td>${board.title}</td>
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
        <td class="content-cell">${board.content}</td>
    </tr>
    <!-- 이미지 표시 -->
    <c:if test="${board.imagefile != null}">
        <tr>
            <th>첨부 이미지</th>
            <td>
                <img src="${board.imagefile}" alt="첨부 이미지" style="max-width: 600px; border-radius: 5px; box-shadow: 0 2px 5px rgba(0,0,0,0.1);">
            </td>
        </tr>
    </c:if>
</table>

<div class="btn-group">
    <a href="/board/list" class="btn btn-list">목록</a>
    <%-- 로그인한 사용자와 게시글 작성자가 같을 때만 버튼 표시 --%>
    <c:if test="${ses.id eq board.writer}">
        <a href="/board/modify?bno=${board.bno}" class="btn btn-modify">수정</a>
        <a href="/board/remove?bno=${board.bno}" class="btn btn-delete"
           onclick="return confirm('정말 삭제하시겠습니까?')">삭제</a>
    </c:if>
</div>

<!-- 댓글 섹션 -->
<div class="comment-section">
    <h3>댓글</h3>
    <!-- 댓글 입력 -->
    <div class="comment-input-area">
        <input type="text" id="cmtCommenter" value="${ses.id }" readonly placeholder="작성자">
        <input type="text" id="cmtContent" placeholder="댓글을 입력하세요...">
        <button type="button" id="cmtAddBtn">등록</button>
    </div>

    <!-- 댓글 목록 -->
    <div class="comment-list" id="commentLine">
        <!-- 댓글 항목이 여기에 동적으로 추가됩니다 -->
    </div>
</div>


<script type="text/javascript">
    const bnoValue = `<c:out value="${board.bno}"></c:out>`;
    const loginUser = `<c:out value="${ses.id }"></c:out>`;
    console.log(loginUser);
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/boardDetail.js"></script>

</body>
</html>