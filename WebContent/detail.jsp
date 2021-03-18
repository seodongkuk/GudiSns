<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="utf-8">
<title>구디SNS</title>
<style>
	body {
		width: 850px;
		height: 90%;
		margin-left: 25%;
		padding: 5px;
		border: 1px solid black;
		overflow: hidden;
	}
	table, td, th, tr {
		border: 1px solid white;
		border-collapse: collapse;
	}
	
	h2 {
		font-size: 70;
		padding-left: 265px;
	}

	#like{
		margin-left: -610px; 
		float: left;
	}
	div.board{
		height: 75%;
		width: 100%;
		overflow: scroll;
	}
	/* #replylist{
	 	border: 1px solid black;
		border-collapse: collapse;
		padding: 5px 10px;
		text-align: center;
	} */
</style>
</head>
<div class="board">
	<body>
		<table style="float: left; margin-top: 55px;">
			<tr>
				<td rowspan="2"><img src="유저프로필.gif" width="80" height="80"></td>
				<td><input type="button" value="${loginId}"
					style="background-color: white; font-weight: bold;"></td>
			</tr>
			<tr>
				<td><input type="button" value="로그아웃"
					onclick="location.href='logout'"></td>
			</tr>
			<button onclick="location.href='newWriting.jsp'">
				새 글쓰기
			</button>
		</table>
		
		<h2>구디SNS</h2>
		<hr/>
	
		<div>
			<img src="타인프로필.gif" width="75" height="75" style="float: left;">
            <input type="button"value="${loginId}" style="background-color: white;font-weight:bold; float: left; margin-top: 40px;font-size: 20px;" >
       <table id="board_idx">
           <tr>
           		<td>
            		<img src="게시물이미지.gif" alt="이미지 없음"width="600" height="450"style="margin-top:100px" />
        		</td>
           </tr>
       	<tr>
       		<td>
	            <button id="like">♥</button>
	            <input style="float: left;margin-top: 15px;border: none;" type="text" value="좋아요숫자">
	            <input type="text"value="${dto.writedate}"style="float: right;border: none;margin-top: 15px;">
	        </td>
        </tr>
        </table>
        
        <table>
        	<tr>
		        <td>
		            <p style="font-size: 15px; color: grey;" >
		            	댓글 모두 ${rcnt}개입니다.</p>
		        </td>
		    </tr>
	    </table>    
        
        
        <table>
		    <tr>
				<th>작성자</th>
				<th>내용</th>
				<th>삭제</th>
			</tr>	
			
			<c:forEach items="${list}" var="Reply2">
			<tr>
				<td>${Reply2.user_id}</td>
				<td>${Reply2.content}</td>
				<td><a href="rdel?board_idx=${dto.board_idx}&&cmt_idx=${Reply2.cmt_idx}">삭제</a></td>
			</tr>
			</c:forEach>
		</table>
	    <form action="rwrite" method="POST">
	        <td>
	        	<input type="hidden" name="user_id" value="${loginId}"/>
	        	<input type="hidden" name="board_idx" value="${dto.board_idx}"/>
	            <b>${loginId}</b> : <input type="text" name="content" size="70" placeholder="댓글을 입력해주세요."> 
	            <button>댓글 작성</button>
	        </td>
	    </form>
		</div>
	<iframe src="navi.jsp" width="850px" height="1000px" scrolling="no" frameborder="0"></iframe>
	</body>
</div>
<script src="//code.jquery.com/jquery.min.js"></script>
<script>
	var msg = "${msg}";
	
	if(msg != ""){
		alert(msg);
	}
</script>
</html>