<%--
  Created by IntelliJ IDEA.
  User: suminhong
  Date: 2025. 11. 26.
  Time: 오후 5:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>게시판 보기</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 1200px;
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
            text-align: center;
            font-weight: bold;
        }

        td {
            padding: 12px;
            text-align: center;
            border-bottom: 1px solid #ddd;
        }

        tr:hover {
            background-color: #f8f9fa;
        }

        td:nth-child(2) {
            text-align: left;
        }

        td a {
            color: #333;
            text-decoration: none;
        }

        td a:hover {
            color: #007bff;
            text-decoration: underline;
        }
        .btn-group {
            text-align: center;
            margin-top: 30px;
        }

        .btn {
            padding: 12px 30px;
            margin: 0 10px;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
        }

        .btn-home {
            background-color: #6c757d;
            color: white;
        }

        .btn-home:hover {
            background-color: #5a6268;
        }

        .btn-write {
            background-color: #007bff;
            color: white;
        }

        .btn-write:hover {
            background-color: #3795ff;
        }

        /* 페이지네이션 스타일 */
        .pagination {
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 5px;
            margin: 30px 0;
        }

        .pagination a {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            min-width: 35px;
            height: 35px;
            padding: 0 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            color: #333;
            text-decoration: none;
            background-color: white;
            transition: all 0.3s ease;
        }

        .pagination a:hover {
            background-color: #007bff;
            color: white;
            border-color: #007bff;
        }

        .pagination a.active {
            background-color: #007bff;
            color: white;
            border-color: #007bff;
            font-weight: bold;
        }

        .pagination svg {
            display: block;
        }

        /* 검색 폼 스타일 */
        form[action="/board/list"] {
            margin: 20px 0;
        }

        form[action="/board/list"] > div {
            display: flex;
            gap: 10px;
            align-items: center;
            max-width: 600px;
        }

        /* 셀렉트 박스 */
        form[action="/board/list"] select {
            padding: 8px 12px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
            background-color: white;
            cursor: pointer;
            min-width: 120px;
        }

        form[action="/board/list"] select:focus {
            outline: none;
            border-color: #007bff;
        }

        /* 검색어 입력 */
        form[action="/board/list"] input[type="text"] {
            flex: 1;
            padding: 8px 12px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
        }

        form[action="/board/list"] input[type="text"]:focus {
            outline: none;
            border-color: #007bff;
        }

        /* 검색 버튼 */
        form[action="/board/list"] button[type="submit"] {
            padding: 8px 20px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 14px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        form[action="/board/list"] button[type="submit"]:hover {
            background-color: #007bff;
        }

        form[action="/board/list"] button[type="submit"]:active {
            background-color: #007bff;
        }
    </style>

</head>
<body>
    <h1>게시판 리스트 페이지</h1>
<%--    search line --%>
<%--    동기식 GET 방식으로 데이터 전송 => 구분 / 검색어 / 페이지 / qty --%>
    <div>
        <form action="/board/list" method="get">
            <div>
                <select name="type" id="">
                    <option ${pgHandler.pageVO.type == null ? 'selected' : ''}>선택하세요</option>
                    <option ${pgHandler.pageVO.type == 't' ? 'selected' : ''} value="t">title</option>
                    <option ${pgHandler.pageVO.type == 'w' ? 'selected' : ''} value="w">writer</option>
                    <option ${pgHandler.pageVO.type == 'c' ? 'selected' : ''} value="c">content</option>
                </select>
                <input type="text" name="keyword" id="" placeholder="검색어를 입력하세요" value="${pgHandler.pageVO.keyword}">
                <input type="hidden" name="pageNo" value="1">
                <input type="hidden" name="qty" value="${pgHandler.pageVO.qty}">
                <button type="submit">검색</button>
            </div>
            <div>
            </div>
        </form>
    </div>

    <table>
        <tr>
            <th>no.</th>
            <th>title</th>
            <th>writer</th>
            <th>regdate</th>
        </tr>
        <c:forEach items="${list }" var="board">
            <tr>
                <td>${board.bno}</td>
                <td><a href="/board/detail?bno=${board.bno}">
                        <%-- 썸네일 이미지 추가 --%>
                        <c:if test="${board.imagefile != null}">
                            <img src="/resources/upload/board/th_${board.imagefile.substring(board.imagefile.lastIndexOf('/') + 1)}"
                                 alt="썸네일"
                                 style="width: 30px; height: 30px; vertical-align: middle; margin-right: 5px;">
                        </c:if>
                        ${board.title}</a></td>
                <td>${board.writer}</td>
                <td>${board.regdate}</td>
            </tr>
        </c:forEach>
    </table>
    <div class="pagination">
        <c:if test="${pgHandler.prev}">
            <a href="/board/list?pageNo=${pgHandler.startPage-1}&qty=${pgHandler.pageVO.qty}&type={"pgHandler.pageVO.type}&keyword={"pgHandler.pageVO.keyword}"}>
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-left-short" viewBox="0 0 16 16">
                    <path fill-rule="evenodd" d="M12 8a.5.5 0 0 1-.5.5H5.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L5.707 7.5H11.5a.5.5 0 0 1 .5.5"/>
                </svg>
            </a>
        </c:if>

        <c:forEach begin="${pgHandler.startPage}" end="${pgHandler.endPage}" var="i">
            <a href="/board/list?pageNo=${i}&qty=${pgHandler.pageVO.qty}"
               class="${i == pgHandler.pageVO.pageNo ? 'active' : ''}">${i}</a>
        </c:forEach>

        <c:if test="${pgHandler.next}">
            <a href="/board/list?pageNo=${pgHandler.endPage+1}&qty=${pgHandler.pageVO.qty}">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-right-short" viewBox="0 0 16 16">
                    <path fill-rule="evenodd" d="M4 8a.5.5 0 0 1 .5-.5h5.793L8.146 5.354a.5.5 0 1 1 .708-.708l3 3a.5.5 0 0 1 0 .708l-3 3a.5.5 0 0 1-.708-.708L10.293 8.5H4.5A.5.5 0 0 1 4 8"/>
                </svg>
            </a>
        </c:if>
    </div>

    <div class="btn-group">
        <a href="${pageContext.request.contextPath}/" class="btn btn-home">홈으로</a>
        <a href="${pageContext.request.contextPath}/board/register" class="btn btn-write">글쓰기</a>
    </div>

</body>
</html>
