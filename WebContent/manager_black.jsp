<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>블랙리스트</title>
        
        <link rel="icon" href="icon.jpg">
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
            .paging{
                margin-top: 40px;
            }
            p{
                font-weight: 600;
            }
        </style>
    </head>
    <body>
        <h1>관리자 모드</h1>
        <h3>블랙 리스트</h3>


        <table>
            <tr>
                <th>블랙리스트 아이디</th>
                <th>등록날짜</th>
                <th>해제</th>
            </tr>
            <c:forEach items="${blackList}" var="blackList">
            <tr>
                <td>ID:${blackList.user_id}</td>
                <td>${blackList.proc_date}</td>
                <td><a href="./blackCancel?idx=${blackList.report_idx}">해제</a></td>
            </tr>
            </c:forEach>
        </table>    
        <div class="pageArea">
			<span>
				<c:if test="${currPage == 1}">이전</c:if>
				<c:if test="${currPage > 1}">
					<a href="./black_list?page=${currPage-1}">이전</a>
				</c:if>				
			</span>
			<span id="page">${currPage}</span>
			<span>
				<c:if test="${currPage == maxPage}">다음</c:if>
				<c:if test="${currPage < maxPage}">
					<a href="./black_list?page=${currPage+1}">다음</a>
				</c:if>	
				
			</span>
		</div>  
        <iframe src="manager_bottom.jsp" width="800px" height="500px" scrolling="no" frameborder="0"></iframe>
       
    </body>
    	<script>
		var msg = "${msg}";
		if(msg != ""){
			alert(msg);
		}
	</script>
    
</html>
