<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <meta charset="UTF-8">
        <title>신고접수 리스트</title>
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
        <h3>신고접수 리스트</h3>
        <input type="button" onclick="location.href='logout'" value="로그아웃"/>
        <table class="table">
            <tr>
                <th>신고접수 번호</th>
                <th>등록일</th>
                <th>신고사유</th>
                <th>신고자</th>
                <th>블랙</th>
                <th>블라<br/>
                    인드
                </th>
                <th>처리</th>
            </tr>
            <c:forEach items="${reportList}" var="reportList">
            <tr>
                <td>${reportList.report_idx}</td>
                <td>${reportList.report_date}</td>
                <td><a href="./report_detail?board_idx=${reportList.board_idx}">${reportList.content}</a></td>
                <td>${reportList.user_id}</td>
                <td><input type="checkbox" id="ck_blind"></td>
                <td><input type="checkbox" id="ck_black"></td>
                <td><a href="#">처리하기</a></td>
            </tr>
            </c:forEach>
        </table>        
      <div class="pageArea">
			<span>
				<c:if test="${currPage == 1}">이전</c:if>
				<c:if test="${currPage > 1}">
					<a href="./report_list?page=${currPage-1}">이전</a>
				</c:if>				
			</span>
			<span id="page">${currPage}</span>
			<span>
				<c:if test="${currPage == maxPage}">다음</c:if>
				<c:if test="${currPage < maxPage}">
					<a href="./report_list?page=${currPage+1}">다음</a>
				</c:if>	
				
			</span>
		</div>
        <iframe src="manager_bottom.jsp" width="800px" height="500px" scrolling="no" frameborder="0"></iframe>
    </body>
  
</html>