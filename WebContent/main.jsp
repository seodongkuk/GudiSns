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
	
	th, td {
		padding: 5px;
	}
	
	th {
		width: 50px;
		font-size: 20;
	}
	
	input[type='submit'] {
		height: 70px;
	}
	
	input[type='text'] {
		height: 30px;
	}
	
	input[type='password'] {
		height: 30px;
	}
	
	input[type='submit'] {
		background-color: rgb(240, 207, 207);
		border: 0px;
	}
	
	td {
		text-align: center;
	}
	
	#WriteBtn{
		background-color: rgb(240, 207, 207);
		border: 0px;
		position: absolute; 
		left: 1300px; 
		top: 130px;
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
	#array{
		position: absolute; 
		left: 1300px; 
		z-index: 1;
	}
</style>

<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
</head>
<body>
	<div class="main">
			<table style="float: left; margin-top: 55px;">
				<tr>
					<td rowspan="2"><img src="유저프로필.gif" width="80" height="80"></td>
					<td><input type="button" id="loginId" value="${loginId}"
						style="background-color: white; font-weight: bold;"></td>
				</tr>
				<tr>
					<td><input type="button" value="로그아웃"
						onclick="location.href='logout'"></td>
				</tr>
				<button id="WriteBtn" onclick="location.href='newWriting.jsp'">새글쓰기</button>
			</table>
			<h2>구디SNS</h2>
			<hr>
			<select id="array" onchange="arrayEvt()">
				<option class="recommend" value="recommend">추천순</option>
				<option class="latest" value="latest">최신순</option>
			</select>
			<div class="board">
				<c:forEach items="${flist}" var="flist">
					<input type="hidden" name="board_idx" value="${flist.board_idx}"/>
					<img src="타인프로필.gif" width="75" height="75" style="float: left;">
		            <input type="button"value="USER_ID" style="background-color: white;font-weight:bold; float: left; margin-top: 40px;font-size: 20px;" >
		       		<table id="board_idx">
			           	<tr>
				           <td>
				           		<img src="게시물이미지.gif" width="600" height="350"style="margin-top:50px" />
			        		</td>
			       		</tr>
		        		<tr>
		        			<td>
		        			<c:forEach items="${list}" var="myL">
		        				<p>${flist.content}</p>
		        				<button id="moreShow" name="board_idx" value="${myL.board_idx}" onclick="location.href='detail?board_idx=${myL.board_idx}'">더보기</button>
		        			</c:forEach>
		        			</td>
		        			
                     
		       				<td>
					            <button id="like">♥</button>
					            <input style="float: left; border: none; margin-left: -580px;" 
					            	type="text" value="좋아요숫자"><span id="likecnt"></span>
					            <input style="float: right;border: none; margin-left: -500px;"
					            	type="text"value="작성날짜:2021-03-10">
		        			</td>
		        		</tr>
		   				<form action="rlist" method="POST">
				        	<tr>
					        	<td>
					            <p style="font-size: 15px; color: grey;" >
					            	댓글 모두 ${rcnt}개입니다.</p>
				        		</td>
				    		</tr>
		   	 			</form>
		     		</table>
				</c:forEach>
			</div>
			<iframe 
				src="navi.jsp" width="850px" height="80px" scrolling="no" frameborder="0"></iframe>
	</div>
</body>
<script>
	var msg = "${msg}";
	
	if(msg != ""){
		alert(msg);
	}
	//ReferenceError 일어남.
 	/* function arrayEvt(){
	   	var selectVal = document.getElementById('array').value;
        $.ajax({
			type:'get'//전송속도가 post보다 빠름
			,url:'array'
			,data:{'loginId':$('#loginId').val()}
			,dataType:'JSON'
			,success:function(obj){
				console.log(obj);
				 if(selectVal=="latest"){
					alert("최신순으로 정렬해드릴게요.");
					
				} if(selectVal=="recommend"){
					alert("추천순으로 정렬해드릴게요."); 
					
				},error:function(e){
					console.log(e);	
				}
			}
		});
	} */
</script>
</html>