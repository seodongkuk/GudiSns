<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <meta charset="UTF-8">
        <title>블라인드 리스트</title>
        <!-- favicon:사이트를 대표하는 탭창에 보여지는 이미지 -->
    
        <style>
			.pageArea span{
				font-size: 12px;
				border : 1px solid lightgray;
				padding: 2px 10px;		
				margin: 5px;		
				color : gray;
			}
            body{
                width: 700px;
                margin: 20px 550px;
                padding: 50px;
                text-align: center;
                border: 1px solid black;
            }
            table{
                width: 650px;
                height: 100px;
                margin: auto;
                text-align: center;
            }
            table,td,th{                
                border: 1px solid black;
                border-collapse: collapse;
            } 
            th{
                background-color: rgba(238, 178, 66, 0.596);
            }
            th, td{
                padding: 10px;
            }
            button{
                margin-top: 40px;
            }
            input[type='button']{
                position: absolute;
                left: 1207px;
                top: 80px;
            }
        </style>
    </head>
    <body>
        <h1>관리자 모드</h1>
        <h3>블라인드 리스트</h3>
        
        <table class="table">
            <tr>
                <th>신고번호</th>
                <th>등록일</th>
                <th>블라인드 글</th>
                <th>관리자</th>
                <th>해제</th>
            </tr>
            <c:forEach items="${blindList}" var="blindList">
            <tr>
                <td>${blindList.report_idx}</td>
                <td>${blindList.blind_date}</td>
                <td><a href="./report_detail?board_idx=${blindList.board_idx}">${blindList.content}</a></td>
                <td>${blindList.admin_id}</td>
                <td><a href="./blindCancel?report_idx=${blindList.report_idx}">처리하기</a></td>
            </tr>
            </c:forEach>
        </table>        
      <div class="pageArea">
			<span>
				<c:if test="${currPage == 1}">이전</c:if>
				<c:if test="${currPage > 1}">
					<a href="./blind_list?page=${currPage-1}">이전</a>
				</c:if>				
			</span>
			<span id="page">${currPage}</span>
			<span>
				<c:if test="${currPage == maxPage}">다음</c:if>
				<c:if test="${currPage < maxPage}">
					<a href="./blind_list?page=${currPage+1}">다음</a>
				</c:if>	
				
			</span>
		</div>
        <iframe src="manager_bottom.jsp" width="800px" height="500px" scrolling="no" frameborder="0"></iframe>
    </body>
  
</html>